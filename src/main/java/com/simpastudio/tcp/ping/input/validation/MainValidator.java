package com.simpastudio.tcp.ping.input.validation;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.simpastudio.tcp.ping.input.Arguments;

public class MainValidator {
	
	public static void validate(Arguments arguments) throws ParameterException {
		
		if (arguments.isHelp()) {
			showHelp(arguments);
			System.exit(0);
		}
		
		if (arguments.isCatcher() == arguments.isPitcher()) {
			showHelp(arguments);
			throw new ParameterException("Catcher and Pitcher modes are mutually exclusive");
		}
		
		if (arguments.isPitcher() == true) {
			if (arguments.getHostname() == null) {
				showHelp(arguments);
				throw new ParameterException("Pitcher requires a valid catcher hostname or IP address");
			}
		} else {
			if (arguments.getBindAddress() == null) {
				showHelp(arguments);
				throw new ParameterException("Catcher requires a valid binding address");
			}
		}		
	}
	
	private static void showHelp(Arguments arguments) {
		
		JCommander.newBuilder().addObject(arguments).build().usage();

		System.out.println("Examples:");
		System.out.println("–c –port 9900 –bind 192.168.0.1");
		System.out.println("–p –port 9900 –mps 30 –size 1000 catcher.host");
		System.out.println("");
	}
}
