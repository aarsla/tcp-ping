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

		DataInputStream inputStream = null;
		DataOutputStream outputStream = null;

		try {
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());

			System.out.println("Client connected.");

			long startTime = System.currentTimeMillis();
			int numberOfMessages = 0;
			int totalBytesRead = 0;

			Message.Action action;

			do {
				action = Message.Action.valueOf(inputStream.readUTF());

				switch (action) {
				case CONNECT:
					break;
				case MSG:
					String inputMessage = inputStream.readUTF();
					Message message = new Message(inputMessage);

					// System.out.println("Processing: " + message.getId() + "\t| " + message.byteSize() + " bytes");

					message.setCatcherTime(System.currentTimeMillis() + TCPPing.ntpTimeOffset);
					outputStream.writeUTF(message.packet());

					totalBytesRead += message.byteSize();
					numberOfMessages++;
					break;
				case DISCONNECT:
					break;
				}

			} while (action != Message.Action.DISCONNECT);

			long endTime = System.currentTimeMillis();

			System.out.println("Processed " + numberOfMessages + " messages with total of " + totalBytesRead
					+ " bytes in " + (endTime - startTime) + " ms.");

			inputStream.close();
			outputStream.close();
			socket.close();
		} catch (Exception e) {
			// System.err.println("IO Error: " + e.getCause().getLocalizedMessage());
		} finally {
			System.out.println("Closing Connection...\n");
		}
	}
}
