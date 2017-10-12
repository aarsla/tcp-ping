package com.simpastudio.tcp.ping;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import com.beust.jcommander.JCommander;
import com.simpastudio.tcp.ping.catcher.Catcher;
import com.simpastudio.tcp.ping.input.Arguments;
import com.simpastudio.tcp.ping.input.validation.MainValidator;
import com.simpastudio.tcp.ping.pitcher.Pitcher;

/**
 * TCP Ping
 */
public class TCPPing {
	
	public static long ntpTimeOffset = 0;
	
	public static void main(String[] args) throws IOException {

		Arguments arguments = new Arguments();
		JCommander.newBuilder().addObject(arguments).build().parse(args);
		
		TCPPing.ntpTimeOffset = getNTPTimeOffset();
		System.out.println("NTP time offset: " + TCPPing.ntpTimeOffset);

		MainValidator.validate(arguments);

		if (arguments.isCatcher()) {
			new Catcher(arguments);
		} else {
			new Pitcher(arguments);
		}
	}

	public static long getNTPTimeOffset() throws IOException {

		NTPUDPClient client = new NTPUDPClient();
		client.setDefaultTimeout(3000);
		client.open();

		try {

			TimeInfo timeInfo;

			timeInfo = client.getTime(InetAddress.getByName("pool.ntp.org"));
			timeInfo.computeDetails();

			return timeInfo.getOffset();
		} finally {
			client.close();
		}
	}
}
