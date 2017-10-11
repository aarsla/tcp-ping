package com.simpastudio.tcp.ping.pitcher;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.TimerTask;

import com.simpastudio.tcp.ping.payload.Message;

public class Sender extends TimerTask {

	private Socket socket;
	private int messageSize;

	public Sender(Socket socket, int messageSize) {
		this.socket = socket;
		this.messageSize = messageSize;
	}

	@Override
	public void run() {

		try {
			
			DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
			outputStream.writeUTF(Message.Action.MSG.name());

			Message message = new Message(Pitcher.messageId, messageSize);
			outputStream.writeUTF(message.packet()) ; // send msg

			// System.out.println("Sending [" + message.getId() + "] \t| " + message.byteSize() + " bytes");

			Pitcher.session.storeSentMessage(message);
			Pitcher.messageId++;
		} catch (IOException e) {
			System.err.println(e.getCause().getLocalizedMessage());
		}
	}

}
