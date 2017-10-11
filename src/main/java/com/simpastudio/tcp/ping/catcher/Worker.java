package com.simpastudio.tcp.ping.catcher;

import java.io.*;
import java.net.*;
import com.simpastudio.tcp.ping.payload.Message;

public class Worker extends Thread {

	private Socket socket = null;

	public Worker(Socket socket) {
		super("WorkerThread");
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			System.out.println("Client connected.");

			long startTime = System.currentTimeMillis();
			byte[] buffer = new byte[3000];
			
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			int readBytes;
			int totalBytesRead = 0;
			int numberOfMessages = 0;
			
			while ((readBytes = inputStream.read(buffer)) != -1) {
				String stringData = new String(buffer, 0, readBytes);
				Message message = new Message(stringData);
				message.setCatcherTime(System.currentTimeMillis());

				//System.out.println("Processing: " + message.getId() + "\t| " + message.byteSize() + " bytes");
				
				outputStream.write(message.toBytes(), 0, message.byteSize());
				totalBytesRead += readBytes;
				numberOfMessages ++;
			}
			
			long endTime = System.currentTimeMillis();
			System.out.println("Processed " + numberOfMessages + " messages with total of " + totalBytesRead + " bytes in " + (endTime - startTime) + " ms.");

			outputStream.close();
			inputStream.close();
			socket.close();
			
			System.out.println("Connection closed.");
		} catch (IOException e) {
			System.err.println("IO Error: " + e.getCause().getLocalizedMessage());
		}
	}
}
