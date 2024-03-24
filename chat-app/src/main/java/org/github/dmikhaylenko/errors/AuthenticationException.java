package org.github.dmikhaylenko.errors;

import java.util.Arrays;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class AuthenticationException extends ApplicationException {
	private static final long serialVersionUID = -5342920432048575665L;
	private final List<String> challenges;

	public AuthenticationException(String... challenges) {
		super(5L);
		this.challenges = Arrays.asList(challenges);
	}
}
