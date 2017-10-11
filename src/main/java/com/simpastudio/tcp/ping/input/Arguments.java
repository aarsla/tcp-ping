package com.simpastudio.tcp.ping.input;

import com.beust.jcommander.Parameter;
import com.simpastudio.tcp.ping.input.validation.HostnameValidator;
import com.simpastudio.tcp.ping.input.validation.MpsValidator;
import com.simpastudio.tcp.ping.input.validation.PortValidator;
import com.simpastudio.tcp.ping.input.validation.SizeValidator;

public class Arguments {
	
	@Parameter(names = { "--pitcher", "-p" }, description = "Pitcher mode", order = 0)
	private boolean pitcher = false;

	@Parameter(names = { "--catcher", "-c" }, description = "Catcher mode", order = 0)
	private boolean catcher = false;

	@Parameter(names = { "--port",
			"-port" }, description = "TCP socket port", order = 1, validateWith = PortValidator.class)
	private int port = 9900;

	@Parameter(names = { "--bind", "-bind" }, description = "Binding IP address", validateWith = HostnameValidator.class)
	private String bindAddress;

	@Parameter(names = { "--mps",
			"-mps" }, description = "Messages per second", validateWith = MpsValidator.class)
	private int mps = 1;

	@Parameter(names = { "--size",
			"-size" }, description = "Message size in bytes", validateWith = SizeValidator.class)
	private int size = 300;

	@Parameter(description = "Catcher hostname", validateWith = HostnameValidator.class)
	private String hostname;

	@Parameter(names = { "--help", "-help" }, description = "Display help", help = true)
	boolean help = false;

	public boolean isPitcher() {
		return pitcher;
	}

	public void setPitcher(boolean pitcher) {
		this.pitcher = pitcher;
	}

	public boolean isCatcher() {
		return catcher;
	}

	public void setCatcher(boolean catcher) {
		this.catcher = catcher;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getBindAddress() {
		return bindAddress;
	}

	public void setBindAddress(String bindAddress) {
		this.bindAddress = bindAddress;
	}

	public int getMps() {
		return mps;
	}

	public void setMps(int mps) {
		this.mps = mps;
	}

	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public boolean isHelp() {
		return help;
	}

	public void setHelp(boolean help) {
		this.help = help;
	}
}
