package com.simpastudio.tcp.ping.pitcher;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.TimerTask;

import com.simpastudio.tcp.ping.payload.Message;

public class Sender extends TimerTask {

	Socket socket;
	int messageSize;
	
	public Sender(Socket socket, int messageSize) {
		this.socket = socket;
		this.messageSize = messageSize;
	}
	
	@Override
	public void run() {
		
			try {
				OutputStream outputStream = socket.getOutputStream();

				Message message = new Message(Pitcher.messageId, messageSize);
				outputStream.write(message.toBytes(), 0, message.byteSize());

				//System.out.println("Sending [" + message.getId() + "] \t| " + message.byteSize() + " bytes");

				Pitcher.session.storeSentMessage(message);
				Pitcher.messageId ++;
			} catch (IOException e) {
				System.err.println(e.getCause().getLocalizedMessage());
			}		
	}

}
