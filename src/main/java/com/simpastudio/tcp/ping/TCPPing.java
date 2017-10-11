package com.simpastudio.tcp.ping;

import java.io.IOException;
import java.net.UnknownHostException;
import com.beust.jcommander.JCommander;
import com.simpastudio.tcp.ping.catcher.Catcher;
import com.simpastudio.tcp.ping.input.Arguments;
import com.simpastudio.tcp.ping.input.validation.MainValidator;
import com.simpastudio.tcp.ping.pitcher.Pitcher;

/**
 * TCP Ping
 */
public class TCPPing {
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Arguments arguments = new Arguments();
		JCommander.newBuilder().addObject(arguments).build().parse(args);

		MainValidator.validate(arguments);
		
		if (arguments.isCatcher()) {
			new Catcher(arguments);
		} else {
			new Pitcher(arguments);
		}
	}
}
