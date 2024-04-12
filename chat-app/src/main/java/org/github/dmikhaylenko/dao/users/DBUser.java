package org.github.dmikhaylenko.dao.users;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
public class DBUser {
	private Long id;
	private String phone;
	private String password;
	private String username;
	private String avatarHref;
	private LocalDateTime lastAuth;
}
