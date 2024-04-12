package org.github.dmikhaylenko.dao;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.github.dmikhaylenko.xml.JaxbLocalDateTimeAdapter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlAuth {
	@XmlElement
	private String token;

	@XmlElement
	private Long userId;

	@XmlElement
	@XmlJavaTypeAdapter(value = JaxbLocalDateTimeAdapter.class)
	private LocalDateTime exp;

	@XmlElement
	private boolean loggedIn;
	
	public XmlAuth(DBAuth dbAuth) {
		this();
		this.token = dbAuth.getToken();
		this.userId = dbAuth.getUserId();
		this.exp = dbAuth.getExp();
		this.loggedIn = dbAuth.isLoggedIn();
	}

	public DBAuth createDBAuth() {
		// @formatter:off
		return DBAuth.builder()
				.token(token)
				.userId(userId)
				.exp(exp)
				.loggedIn(loggedIn)
				.build();
		// @formatter:on
	}
}
