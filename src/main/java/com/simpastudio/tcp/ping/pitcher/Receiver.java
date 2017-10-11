package com.simpastudio.tcp.ping.pitcher;

import com.simpastudio.tcp.ping.TCPPing;
import com.simpastudio.tcp.ping.payload.Message;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Receiver extends Thread {
	
	private Socket socket;
	private InputStream inputStream;
	private int messageSize;
	
	public Receiver(Socket socket, int messageSize) {
		this.socket = socket;
		this.messageSize = messageSize;
	}
	
	@Override
	public void run() {
		
		while (true) {

			byte[] buffer = new byte[messageSize];
			int read = 0;

			try {
				this.inputStream = socket.getInputStream();
				
				while ((read = inputStream.read(buffer)) != -1) {
					String stringData = new String(buffer, 0, read);
					Message message = new Message(stringData);
					message.setReceiverTime(System.currentTimeMillis()+TCPPing.ntpTimeOffset);

					//System.out.println("Received: " + message.getId() + "\t| " + message.byteSize() + " bytes");

					Pitcher.session.storeReceivedMessage(message);
				}
				
			} catch (IOException e) {
				System.err.println(e.getCause().getLocalizedMessage());
			}

		}
	}
}
