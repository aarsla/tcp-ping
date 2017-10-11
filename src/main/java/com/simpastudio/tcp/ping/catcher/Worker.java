package com.simpastudio.tcp.ping.catcher;

import java.io.*;
import java.net.*;

import com.simpastudio.tcp.ping.TCPPing;
import com.simpastudio.tcp.ping.payload.Message;

public class Worker extends Thread {

	private Socket socket = null;

	public Worker(Socket socket) {
		super("WorkerThread");
		this.socket = socket;
	}

	@Override
	public void run() {
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			System.out.println("Client connected.");

			long startTime = System.currentTimeMillis();
			byte[] buffer = new byte[3000];

			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			int readBytes;
			int totalBytesRead = 0;
			int numberOfMessages = 0;

			while ((readBytes = inputStream.read(buffer)) != -1) {
				String stringData = new String(buffer, 0, readBytes);

				try {
					Message message = new Message(stringData);
					message.setCatcherTime(System.currentTimeMillis() + TCPPing.ntpTimeOffset);

					// System.out.println("Processing: " + message.getId() + "\t| " + message.byteSize() + " bytes");

					outputStream.write(message.toBytes(), 0, message.byteSize());
					totalBytesRead += readBytes;
					numberOfMessages++;
				} catch (Exception e) {
					System.out.println(
							"Error processing: " + stringData + "\t| " + stringData.getBytes().length + " bytes");
				}
			}

			long endTime = System.currentTimeMillis();
			System.out.println("Processed " + numberOfMessages + " messages with total of " + totalBytesRead
					+ " bytes in " + (endTime - startTime) + " ms.");

		} catch (IOException e) {
			System.err.println("IO Error: " + e.getCause().getLocalizedMessage());
		} finally {
			System.out.println ("Closing Connection...\n");
			
			try {
				if (inputStream != null) 
					inputStream.close();
				
				if (outputStream != null) 
					outputStream.close();
				
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				System.err.println("IO Error: " + e.getCause().getLocalizedMessage());
			}
		}
	}
}
