package org.github.dmikhaylenko.modules.users;

import org.github.dmikhaylenko.commons.errors.ApplicationException;

import lombok.ToString;

@ToString
public class WrongLoginOrPasswordException extends ApplicationException {
	private static final long serialVersionUID = 4212511644342498929L;

	public WrongLoginOrPasswordException() {
		super(4L);
	}
}
