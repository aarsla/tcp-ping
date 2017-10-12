package com.simpastudio.tcp.ping.pitcher;

import com.simpastudio.tcp.ping.Session;
import com.simpastudio.tcp.ping.input.Arguments;
import com.simpastudio.tcp.ping.payload.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Timer;

public class Pitcher {

	public static int messageId = 1;
	public static Session session = new Session();
	
	private Socket socket;
	private static Timer timer = new Timer();
	private static Receiver reciever = null;

	public Pitcher(Arguments arguments) {
		
		int portNumber = arguments.getPort();
		String hostname = arguments.getHostname();		
		int mps = arguments.getMps();
		int messageSize = arguments.getSize();
		
		try {
			socket = new Socket(hostname, portNumber);
			registerCleanup();
			
			reciever = new Receiver(socket);
			reciever.start();

			System.out.println("Sending packets of " + messageSize + " bytes to " + hostname + " at a rate of " + mps + " mps:");

			timer.schedule(new Sender(socket, messageSize), 0, 1000/mps);
			timer.schedule(new Stats(), 0, 1 * 1000);
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + e.getCause().getLocalizedMessage());
		} catch (IOException e) {
			System.err.println("IO error: " + e.getCause().getLocalizedMessage());
		}
	}
	
	private void registerCleanup() {
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { 
		    		try {
						DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
						outputStream.writeUTF(Message.Action.DISCONNECT.name());
					} catch (IOException e) {
						System.err.println(e.getCause().getLocalizedMessage());
					}
		    }
		 });
	}
}
