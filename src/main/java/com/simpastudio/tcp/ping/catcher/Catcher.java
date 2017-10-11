package com.simpastudio.tcp.ping.catcher;

import com.simpastudio.tcp.ping.input.Arguments;
import java.net.*;
import java.io.*;

public class Catcher {

	protected ServerSocket serverSocket = null;
	
	public Catcher(Arguments arguments) {

		int serverPort = arguments.getPort();
		String bindAddress = arguments.getBindAddress();
		
		try {
			InetAddress address = InetAddress.getByName(bindAddress);
			serverSocket = new ServerSocket(serverPort, 0, address);
			
			System.out.println("Catcher is listening on " + bindAddress + ":"+ serverPort);
			
			do {
	            new Worker(serverSocket.accept()).run();
	         } while (true);
		} catch (IOException e) {
			System.err.println(e.getCause().getLocalizedMessage());
		}
	}
}
