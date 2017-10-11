package com.simpastudio.tcp.ping.input.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class PortValidator implements IParameterValidator {
	
	public void validate(String name, String value) throws ParameterException {
		
		try {
			int portNumber = Integer.parseInt(value);
			
			if (portNumber < 1024 || portNumber > 65535) {
				throw new ParameterException("Port " + portNumber + " should be between 1024 and 65535 (found " + value + ")");
			}			
		} catch (NumberFormatException e) {
			throw new ParameterException("Invalid number: " + value);
		}
	}
}
