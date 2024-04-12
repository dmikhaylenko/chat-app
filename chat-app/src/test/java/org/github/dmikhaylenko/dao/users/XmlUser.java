package org.github.dmikhaylenko.dao.users;

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
public class XmlUser {
	@XmlElement
	private Long id;

	@XmlElement
	private String phone;

	@XmlElement
	private String password;

	@XmlElement
	private String username;

	@XmlElement
	private String avatarHref;

	@XmlElement
	@XmlJavaTypeAdapter(value = JaxbLocalDateTimeAdapter.class)
	private LocalDateTime lastAuth;

	public XmlUser(DBUser dbUser) {
		this();
		this.id = dbUser.getId();
		this.phone = dbUser.getPhone();
		this.password = dbUser.getPassword();
		this.username = dbUser.getUsername();
		this.avatarHref = dbUser.getAvatarHref();
		this.lastAuth = dbUser.getLastAuth();
	}

	public DBUser createDBUser() {
		// @formatter:off
		return DBUser.builder()
				.id(id)
				.phone(phone)
				.password(password)
				.username(username)
				.avatarHref(avatarHref)
				.lastAuth(lastAuth)
				.build();
		// @formatter:on
	}
}
