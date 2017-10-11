package com.simpastudio.tcp.ping.pitcher;

import com.simpastudio.tcp.ping.TCPPing;
import com.simpastudio.tcp.ping.payload.Message;

import java.io.DataInputStream;
import java.net.Socket;

public class Receiver extends Thread {
	
	private Socket socket;
	
	public Receiver(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {

		DataInputStream inputStream = null;
		
		try {
			inputStream = new DataInputStream(socket.getInputStream());
			
			do {
	                String msg = inputStream.readUTF();
	                
	                Message message = new Message(msg);
					message.setReceiverTime(System.currentTimeMillis()+TCPPing.ntpTimeOffset);

					// System.out.println("Received: " + message.getId() + "\t| " + message.byteSize() + " bytes");

					Pitcher.session.storeReceivedMessage(message);

	        } while (true);
	        
		} catch (Exception e) {
			System.err.println("IO Error: " + e.getCause().getLocalizedMessage());
		}
	}
}
