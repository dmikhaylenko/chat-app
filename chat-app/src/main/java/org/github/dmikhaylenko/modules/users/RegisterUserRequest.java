package org.github.dmikhaylenko.modules.users;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@XmlRootElement
@NoArgsConstructor
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class RegisterUserRequest implements RegisterUserCommand {
	@NotNull
	@XmlElement
	@Size(max = 1000)
	private String avatar;

	@NotNull
	@XmlElement
	@Size(min = 1, max = 50)
	@Pattern(regexp = "\\+(\\d)+")
	private String phone;

	@NotNull
	@XmlElement
	@Size(min = 1, max = 50)
	private String password;

	@NotNull
	@XmlElement
	@Size(min = 1, max = 50)
	private String publicName;
}
