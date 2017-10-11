package com.simpastudio.tcp.ping.input.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class MpsValidator implements IParameterValidator {
	
	public void validate(String name, String value) throws ParameterException {

		int minMps = 1;
		int maxMps = 100;

		try {
			int mps = Integer.parseInt(value);

			if (mps < minMps || mps > maxMps) {
				throw new ParameterException("Please set mps between" + minMps + " and " + maxMps + ".");
			}
		} catch (NumberFormatException e) {
			throw new ParameterException("Invalid number: " + value);
		}
	}
}
