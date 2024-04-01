package org.github.dmikhaylenko.modules.users.auth;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@XmlRootElement
@NoArgsConstructor
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginRequest implements LoginCommand {
	@XmlElement
	private String username;
	@XmlElement
	private String password;
}
