package org.github.dmikhaylenko.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@NoArgsConstructor
@XmlType(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
// @formatter:off
@XmlSeeAlso({
	RegisterUserResponse.class,
	SearchUsersResponse.class,
	LogoutResponse.class,
	ChangePasswordResponse.class,
	LoginResponse.class,
	SearchHistoriesResponse.class,
	AddContactResponse.class,
	DeleteContactResponse.class,
	ClearHistoryResponse.class,
	PostMessageResponse.class,
	ShowHistoryMessages.class,
	EditMessageResponse.class,
	DeleteMessageResponse.class
})
//@formatter:on
public class ResponseModel {
	@XmlElement
	private Long responseCode;

	@XmlElement
	private String responseMessage;
}
