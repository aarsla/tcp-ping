package com.simpastudio.tcp.ping.input.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import inet.ipaddr.HostName;

public class HostnameValidator implements IParameterValidator {
	
	public void validate(String name, String value) throws ParameterException {

		HostName host = new HostName(value);

		if (!host.isValid()) {
			throw new ParameterException("Invalid hostname: " + value);
		}
	}
}
