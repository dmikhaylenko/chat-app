package org.github.dmikhaylenko.model;

import org.github.dmikhaylenko.dao.Dao;
import org.github.dmikhaylenko.modules.login.LoginRequest;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class LoginModel {
	private String username;
	private String password;

	public LoginModel(LoginRequest request) {
		super();
		this.username = request.getUsername();
		this.password = request.getPassword();
	}

	public String login() {
		return Dao.authDao().executeLogin(username, password).orElseThrow(WrongLoginOrPasswordException::new);
	}
}
