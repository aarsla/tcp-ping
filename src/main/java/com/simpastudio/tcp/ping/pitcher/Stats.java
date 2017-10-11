package com.simpastudio.tcp.ping.pitcher;

import java.text.SimpleDateFormat;
import java.util.TimerTask;

import com.simpastudio.tcp.ping.Session;

public class Stats extends TimerTask {

	private Session session = null;
	
	public Stats() {
		this.session = Pitcher.session;

		System.out.println("+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
		System.out.println("| Time     \t| Total sent \t| Total 1 sec \t| Max A-B-A \t| Avg A-B-A \t| Avg A-B \t| Avg B-A \t|");
		System.out.println("+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
	}
	
	@Override
	public void run() {
		Session.triggerTime = System.currentTimeMillis();
		
		System.out.print(new SimpleDateFormat("| hh:mm:ss").format(session.getCurrentTime()) + " \t| ");
		
		if (session.getTotalMessagesSent() > 0) {
			System.out.print(session.getTotalMessagesSent() + " \t\t| ");
		} else {
			System.out.print("~ \t\t| ");			
		}
		
		if (session.getTotalMessagesInPreviousSecond() > 0) {
			System.out.print(session.getTotalMessagesInPreviousSecond() + " \t\t| ");
		} else {
			System.out.print("~ \t\t| ");			
		}
		
		if (session.getMaxABATime() > 0) {
			System.out.format("%-3d ms \t| ", session.getMaxABATime());
		} else {
			System.out.print("~ \t\t| ");			
		}
		
		if (session.getAverageABATimeInPreviousSecond() > 0) {
			System.out.format("%-3d ms \t| ", session.getAverageABATimeInPreviousSecond());
		} else {
			System.out.print("~ \t\t| ");			
		}

		if (session.getAverageABTimeInPreviousSecond() > 0) {
			System.out.format("%-3d ms \t| ", session.getAverageABTimeInPreviousSecond());
		} else {
			System.out.print("~ \t\t| ");			
		}

		if (session.getAverageBATimeInPreviousSecond() > 0) {
			System.out.format("%-3d ms \t| ", session.getAverageBATimeInPreviousSecond());
		} else {
			System.out.print("~ \t\t| ");			
		}
		
		System.out.println();
	}

}
