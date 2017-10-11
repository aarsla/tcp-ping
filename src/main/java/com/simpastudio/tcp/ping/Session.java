package com.simpastudio.tcp.ping;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import com.simpastudio.tcp.ping.payload.Message;

public class Session {

	public static long triggerTime = System.currentTimeMillis();

	private long oneSecond = 1000;
	private static ConcurrentHashMap<Integer, Message> sentMessages = new ConcurrentHashMap<Integer, Message>();
	private static ConcurrentHashMap<Integer, Message> receivedMessages = new ConcurrentHashMap<Integer, Message>();

	public void storeSentMessage(Message message) {
		sentMessages.put(message.getId(), message);
	}

	public void storeReceivedMessage(Message message) {
		receivedMessages.put(message.getId(), message);
	}

	public Date getCurrentTime() {
		return new Date();
	}

	public int getTotalMessagesSent() {
		return sentMessages.size();
	}

	public long getMaxABATime() {

		long max = 0;

		for (Message msg : receivedMessages.values()) {
			long diff = msg.getReceiverTime() - msg.getSenderTime();
			if (diff > max) {
				max = diff;
			}
		}

		return max;
	}

	public int getTotalMessagesInPreviousSecond() {

		int sentCount = 0;
		int receivedCount = 0;

		for (Message msg : sentMessages.values()) {
			long diff = triggerTime - msg.getSenderTime();
			if (diff <= oneSecond) {
				sentCount++;
			}
		}

		for (Message msg : receivedMessages.values()) {
			long diff = triggerTime - msg.getSenderTime();
			if (diff <= oneSecond) {
				receivedCount++;
			}
		}

		return sentCount + receivedCount;
	}

	public int getAverageABATimeInPreviousSecond() {

		long totalTime = 0;
		long messagesCounted = 0;

		for (Message msg : receivedMessages.values()) {
			long diff = triggerTime - msg.getSenderTime();
			if (diff <= oneSecond) {
				long time = msg.getReceiverTime() - msg.getSenderTime();
				totalTime += time;
				messagesCounted++;
			}
		}

		return (int) (totalTime > 0 ? totalTime / messagesCounted : 0);
	}

	public int getAverageABTimeInPreviousSecond() {

		long totalTime = 0;
		long messagesCounted = 0;

		for (Message msg : receivedMessages.values()) {

			long diff = triggerTime - msg.getSenderTime();

			if (diff <= oneSecond) {
				long time = msg.getCatcherTime() - msg.getSenderTime();
				totalTime += time;
				messagesCounted++;
			}
		}

		return (int) (totalTime > 0 ? totalTime / messagesCounted : 0);
	}

	public int getAverageBATimeInPreviousSecond() {

		long totalTime = 0;
		long messagesCounted = 0;

		for (Message msg : receivedMessages.values()) {

			long diff = triggerTime - msg.getSenderTime();

			if (diff <= oneSecond) {
				long time = msg.getReceiverTime() - msg.getCatcherTime();
				totalTime += time;
				messagesCounted++;
			}
		}

		return (int) (totalTime > 0 ? totalTime / messagesCounted : 0);
	}

}