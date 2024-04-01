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
public class DBHistory {
	private Long id;
	private String publicName;
	private String avatar;
	private boolean online;
	private LocalDateTime lastAccess;
	private Long unwatched;
}
