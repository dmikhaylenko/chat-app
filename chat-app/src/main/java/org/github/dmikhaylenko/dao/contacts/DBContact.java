package org.github.dmikhaylenko.dao.contacts;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public class DBContact {
	private Long whoseId;
	private Long whoId;
}
