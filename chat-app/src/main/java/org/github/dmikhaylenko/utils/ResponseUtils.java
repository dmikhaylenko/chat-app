package org.github.dmikhaylenko.utils;

import java.util.List;

import org.github.dmikhaylenko.errors.ApplicationException;
import org.github.dmikhaylenko.model.ChangePasswordResponse;
import org.github.dmikhaylenko.model.LogoutResponse;
import org.github.dmikhaylenko.model.RegisterUserResponse;
import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.model.SearchUsersResponse;
import org.github.dmikhaylenko.model.UserModel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseUtils {
	public RegisterUserResponse createRegisterUserResponse(Long id) {
		RegisterUserResponse result = new RegisterUserResponse();
		initSuccessfulResponseModel(result);
		result.setId(id);
		return result;
	}

	public SearchUsersResponse createSearchUsersResponse(List<UserModel> users, Long total) {
		SearchUsersResponse result = new SearchUsersResponse();
		initSuccessfulResponseModel(result);
		result.setUsers(users);
		result.setTotal(total);
		return result;
	}

	public LogoutResponse createLogoutResponse() {
		LogoutResponse result = new LogoutResponse();
		initSuccessfulResponseModel(result);
		return result;
	}
	
	public ChangePasswordResponse createChangePasswordResponse(Long id) {
		ChangePasswordResponse result = new ChangePasswordResponse();
		initSuccessfulResponseModel(result);
		result.setUserId(id);
		return result;
	}

	public ResponseModel createNonApplicationErrorResponse() {
		return createErrorResponse(-1L);
	}

	public ResponseModel createErrorResponse(ApplicationException error) {
		return createErrorResponse(error.getCode());
	}

	private ResponseModel createErrorResponse(Long code) {
		ResponseModel result = new ResponseModel();
		initResponseModel(result, code);
		return result;
	}

	private void initSuccessfulResponseModel(ResponseModel responseModel) {
		initResponseModel(responseModel, 0L);
	}

	private void initResponseModel(ResponseModel responseModel, Long code) {
		responseModel.setResponseCode(code);
		MessageUtils.getErrorMessage(code).ifPresent(responseModel::setResponseMessage);
	}
}
