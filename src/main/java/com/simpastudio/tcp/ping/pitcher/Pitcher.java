package com.simpastudio.tcp.ping.pitcher;

import com.simpastudio.tcp.ping.Session;
import com.simpastudio.tcp.ping.input.Arguments;
import java.io.IOException;
import java.net.*;
import java.util.Timer;

public class Pitcher {

	public static int messageId = 1;
	public static Session session = new Session();

	private static Timer timer = new Timer();
	private static Receiver reciever = null;

	public Pitcher(Arguments arguments) {

		int portNumber = arguments.getPort();
		String hostname = arguments.getHostname();
		int mps = arguments.getMps();
		int messageSize = arguments.getSize();

		try {
			Socket socket = new Socket(hostname, portNumber);

			reciever = new Receiver(socket, messageSize);
			reciever.start();

			System.out.println("Sending packets of " + messageSize + " bytes to " + hostname + " at a rate of " + mps + " mps:");

			timer.schedule(new Sender(socket, messageSize), 0, 1000/mps);
			timer.schedule(new Stats(), 1100, 1 * 1000);
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + e.getCause().getLocalizedMessage());
		} catch (IOException e) {
			System.err.println("IO error: " + e.getCause().getLocalizedMessage());
		}
	}
}
