package org.github.dmikhaylenko.dao.messages.history;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public class DBMessageView {
	private Long id;
	private String avatar;
	private String publicName;
	private String message;
	private boolean watched;
	private LocalDateTime posted;
}
