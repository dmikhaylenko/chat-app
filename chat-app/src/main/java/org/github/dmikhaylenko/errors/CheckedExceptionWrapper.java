package org.github.dmikhaylenko.errors;

public class CheckedExceptionWrapper extends RuntimeException {
	private static final long serialVersionUID = 2363686746532297513L;

	public CheckedExceptionWrapper(Exception cause) {
		super(cause);
	}
}
