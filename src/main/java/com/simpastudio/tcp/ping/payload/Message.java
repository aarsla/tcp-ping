package com.simpastudio.tcp.ping.payload;

import com.simpastudio.tcp.ping.TCPPing;

public class Message {

	/*
	 * Packet structure 
	 * +--------+----+------------+-------------+--------------+---------+
	 * + Length + ID + senderTime + catcherTime + receiverTime + payload + 
	 * +--------+----+------------+-------------+--------------+---------+
	 */

	private static String delimiter = "|";
	private static String ddelimiter = " | ";
	
	private int id = 1;
	private int size = 300;
	private long senderTime = System.currentTimeMillis() + TCPPing.ntpTimeOffset;
	private long catcherTime = System.currentTimeMillis() + TCPPing.ntpTimeOffset;
	private long receiverTime = System.currentTimeMillis() + TCPPing.ntpTimeOffset;

	public Message(int id, int size) {
		this.id = id;
		this.size = size;
	}

	public Message(String message) {
		String[] parts = message.split("\\" + delimiter);

		this.size = message.getBytes().length;
		this.id = Integer.parseInt(parts[1]);
		this.senderTime = Long.parseLong(parts[2]);
		this.catcherTime = Long.parseLong(parts[3]);
		this.receiverTime = Long.parseLong(parts[4]);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getSenderTime() {
		return senderTime;
	}
	
	public long getCatcherTime() {
		return catcherTime;
	}

	public void setCatcherTime(long catcherTime) {
		this.catcherTime = catcherTime;
	}

	public long getReceiverTime() {
		return receiverTime;
	}

	public void setReceiverTime(long receiverTime) {
		this.receiverTime = receiverTime;
	}

	public String packet() {
		String basePacket = id + delimiter + senderTime + delimiter + catcherTime + delimiter + receiverTime;

		int basePacketSize = basePacket.getBytes().length;
		String packet = basePacketSize + delimiter + basePacket;

		if (packet.getBytes().length < this.size) {
			String extraPayload = "";
			for (int i = packet.getBytes().length; i <= this.size - 2; i++) {
				extraPayload += "1";
			}
			
			packet = packet + delimiter + extraPayload;
		}
		
		return packet;
	}

	public byte[] toBytes() {
		return this.packet().getBytes();
	}

	public int byteSize() {
		return this.packet().getBytes().length;
	}
	
	@Override
	public String toString() {
		String basePacket = id + ddelimiter + senderTime + ddelimiter + catcherTime + ddelimiter + receiverTime;

		int basePacketSize = basePacket.getBytes().length;
		String readablePacketWihoutPayload = basePacketSize + ddelimiter + basePacket;
		
		return readablePacketWihoutPayload;
	}
}
