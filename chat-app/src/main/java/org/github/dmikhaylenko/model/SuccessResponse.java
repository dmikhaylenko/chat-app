package org.github.dmikhaylenko.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.github.dmikhaylenko.modules.contacts.AddContactResponse;
import org.github.dmikhaylenko.modules.contacts.DeleteContactResponse;
import org.github.dmikhaylenko.modules.history.ClearHistoryResponse;
import org.github.dmikhaylenko.modules.history.PostMessageResponse;
import org.github.dmikhaylenko.modules.history.SearchHistoriesResponse;
import org.github.dmikhaylenko.modules.history.ShowHistoryMessages;
import org.github.dmikhaylenko.modules.messages.DeleteMessageResponse;
import org.github.dmikhaylenko.modules.messages.EditMessageResponse;
import org.github.dmikhaylenko.modules.users.ChangePasswordResponse;
import org.github.dmikhaylenko.modules.users.LoginResponse;
import org.github.dmikhaylenko.modules.users.LogoutResponse;
import org.github.dmikhaylenko.modules.users.RegisterUserResponse;
import org.github.dmikhaylenko.modules.users.SearchUsersResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@XmlRootElement
@ToString(callSuper = true)
@XmlType(name = "SuccessResponse")
@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
//@formatter:off
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
public class SuccessResponse extends ResponseModel {
	public SuccessResponse() {
		super(0L);
	}
}
