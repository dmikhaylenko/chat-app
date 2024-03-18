package org.github.dmikhaylenko.utils;

import java.util.List;

import org.github.dmikhaylenko.errors.ApplicationException;
import org.github.dmikhaylenko.model.AddContactResponse;
import org.github.dmikhaylenko.model.ChangePasswordResponse;
import org.github.dmikhaylenko.model.ClearHistoryResponse;
import org.github.dmikhaylenko.model.DeleteContactResponse;
import org.github.dmikhaylenko.model.EditMessageResponse;
import org.github.dmikhaylenko.model.HistoryModel;
import org.github.dmikhaylenko.model.LogoutResponse;
import org.github.dmikhaylenko.model.MessageViewModel;
import org.github.dmikhaylenko.model.PostMessageResponse;
import org.github.dmikhaylenko.model.LoginResponse;
import org.github.dmikhaylenko.model.RegisterUserResponse;
import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.model.SearchHistoriesResponse;
import org.github.dmikhaylenko.model.SearchUsersResponse;
import org.github.dmikhaylenko.model.ShowHistoryMessages;
import org.github.dmikhaylenko.model.UserModel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseUtils {
	public LoginResponse createLoginResponse(String token) {
		LoginResponse result = new LoginResponse();
		initSuccessfulResponseModel(result);
		result.setToken(token);
		return result;
	}

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
	
	public SearchHistoriesResponse createSearchHistoriesResponse(List<HistoryModel> histories, Long total, Long unwatched) {
		SearchHistoriesResponse result = new SearchHistoriesResponse();
		initSuccessfulResponseModel(result);
		result.setHistories(histories);
		result.setUnwatched(unwatched);
		result.setTotal(total);
		return result;
    }

	public AddContactResponse createAddContactResponse() {
		AddContactResponse result = new AddContactResponse();
		initSuccessfulResponseModel(result);
		return result;
	}
	
	public DeleteContactResponse createDeleteContactResponse() {
		DeleteContactResponse result = new DeleteContactResponse();
		initSuccessfulResponseModel(result);
		return result;
	}
	
	public ClearHistoryResponse createClearHistoryResponse() {
		ClearHistoryResponse result = new ClearHistoryResponse();
		initSuccessfulResponseModel(result);
		return result;
	}
	
	public PostMessageResponse createPostMessageResponse(Long messageId) {
		PostMessageResponse result = new PostMessageResponse();
		initSuccessfulResponseModel(result);
		result.setMessageId(messageId);
		return result;
	}
	
	public ShowHistoryMessages createShowHistoryMessages(Long page, Long total, List<MessageViewModel> messages) {
		ShowHistoryMessages result = new ShowHistoryMessages();
		initSuccessfulResponseModel(result);
		result.setPg(page);
		result.setTotal(total);
		result.setMessages(messages);
		return result;
	}
	
	public EditMessageResponse createEditMessageResponse() {
		EditMessageResponse result = new EditMessageResponse();
		initSuccessfulResponseModel(result);
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
