package org.github.dmikhaylenko.modules.users;

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
public class FoundUser {
	@XmlElement
	private Long id;
	
	@XmlElement
	private String avatar;
	
	@XmlElement
	private String phone;
	
	@XmlElement
	private String publicName;
	
	public FoundUser(UserModel model) {
		this();
		this.id = model.getId();
		this.avatar = model.getAvatar();
		this.phone = model.getPhone();
		this.publicName = model.getPublicName();
	}
}
