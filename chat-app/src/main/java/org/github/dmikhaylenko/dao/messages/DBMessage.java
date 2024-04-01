package org.github.dmikhaylenko.dao.messages;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public class DBMessage {
	private Long id;
	private Long srcId;
	private Long destId;
	private String message;
	private Boolean watched;
	private LocalDateTime posted;
}
