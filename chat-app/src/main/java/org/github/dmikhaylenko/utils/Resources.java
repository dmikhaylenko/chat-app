package org.github.dmikhaylenko.utils;

import javax.sql.DataSource;
import javax.validation.Validator;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Resources {
	@Getter
	@Setter
	private DataSource chatDb = null;
	
	@Getter
	@Setter
	private Validator validator = null;
}
