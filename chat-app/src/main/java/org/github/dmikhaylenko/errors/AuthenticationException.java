package org.github.dmikhaylenko.errors;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthenticationException extends ApplicationException {
	private static final long serialVersionUID = -5342920432048575665L;
	private List<String> challenge;
}
