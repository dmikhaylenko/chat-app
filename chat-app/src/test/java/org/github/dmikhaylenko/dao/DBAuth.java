package org.github.dmikhaylenko.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.github.dmikhaylenko.time.Time;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public class DBAuth {
	private String token;
	private Long userId;
	private LocalDateTime exp;
	private boolean loggedIn;

	@SneakyThrows
	public DBAuth(Long userId) {
		super();
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encoded = digest.digest(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
		this.token = new String(encoded, StandardCharsets.UTF_8);
		this.userId = userId;
		this.exp = Time.currentLocalDateTime().plus(1, ChronoUnit.MONTHS);
		this.loggedIn = true;
	}

	public void logout() {
		this.loggedIn = false;
	}
}
