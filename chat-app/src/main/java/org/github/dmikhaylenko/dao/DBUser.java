package org.github.dmikhaylenko.dao;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public class DBUser {
	private Long id;
	private String phone;
	private String password;
	private String username;
	private String avatarHref;
	private LocalDateTime lastAuth;
}
