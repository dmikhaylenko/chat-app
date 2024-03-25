package org.github.dmikhaylenko.model;

import org.github.dmikhaylenko.model.errors.ApplicationException;

import lombok.ToString;

@ToString
public class WrongLoginOrPasswordException extends ApplicationException {
	private static final long serialVersionUID = 4212511644342498929L;

	public WrongLoginOrPasswordException() {
		super(4L);
	}
}
