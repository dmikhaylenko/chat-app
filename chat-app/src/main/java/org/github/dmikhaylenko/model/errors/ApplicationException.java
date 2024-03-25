package org.github.dmikhaylenko.model.errors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 8079929291360821838L;
	private final Long code;
}
