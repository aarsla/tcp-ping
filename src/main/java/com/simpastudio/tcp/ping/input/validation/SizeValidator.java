package com.simpastudio.tcp.ping.input.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class SizeValidator implements IParameterValidator {
	
	public void validate(String name, String value) throws ParameterException {

		int minSize = 50;
		int maxSize = 3000;

		try {
			int messageSize = Integer.parseInt(value);

			if (messageSize < minSize || messageSize > maxSize) {
				throw new ParameterException("Please set message size between " + minSize + " and " + maxSize + ".");
			}
		} catch (NumberFormatException e) {
			throw new ParameterException("Invalid number: " + value);
		}
	}
}
