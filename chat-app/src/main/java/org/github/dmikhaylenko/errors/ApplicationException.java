package org.github.dmikhaylenko.errors;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 8079929291360821838L;
	private Long code;
}
