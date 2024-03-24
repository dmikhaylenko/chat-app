package org.github.dmikhaylenko.commons.errors;

public class CheckedExceptionWrapper extends RuntimeException {
	private static final long serialVersionUID = 2363686746532297513L;

	public CheckedExceptionWrapper(Exception cause) {
		super(cause);
	}
	
	public static CheckedExceptionWrapper uncheckedOf(Exception error) {
		return new CheckedExceptionWrapper(error);
	}
}
