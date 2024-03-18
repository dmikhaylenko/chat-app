package org.github.dmikhaylenko.controllers;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.AuthTokenModel;
import org.github.dmikhaylenko.model.ChangePasswordModel;
import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.model.UserModel;
import org.github.dmikhaylenko.utils.AuthUtils;
import org.github.dmikhaylenko.utils.ExceptionUtils;
import org.github.dmikhaylenko.utils.PageUtils;
import org.github.dmikhaylenko.utils.ResponseUtils;
import org.github.dmikhaylenko.utils.UserUtils;
import org.github.dmikhaylenko.utils.ValidationUtils;

@Path("/users")
public class UserController {
	@POST
	public ResponseModel registerUser(UserModel model) {
		ValidationUtils.checkConstraints(model);
		UserUtils.checkThatUserWithPhoneExists(model);
		UserUtils.checkThatUserWithNickNameExists(model);
		return ResponseUtils.createRegisterUserResponse(model.insertToUserTable());
	}

	@GET
	@Path("/search")
	public ResponseModel searchUsers(@Context HttpHeaders headers, @QueryParam("sstr") String sstr,
			@QueryParam("pg") Long pg, @QueryParam("ps") Long ps) {
		AuthUtils.checkThatAuthenticated(AuthUtils.getTokenFromHeader(headers));
		Long normalizedPg = PageUtils.normalizePage(pg);
		Long normalizedPs = PageUtils.normalizePageSize(ps, 1000L, 50L);
		List<UserModel> users = UserModel.findByPhoneOrUsername(sstr, normalizedPg, normalizedPs);
		Long total = UserModel.countByPhoneOrUsername(sstr);
		return ResponseUtils.createSearchUsersResponse(users, total);
	}
	
	@POST
	@Path("/current/password")
	public ResponseModel changePassword(@Context HttpHeaders headers, ChangePasswordModel model) {
		ValidationUtils.checkConstraints(model);
		AuthTokenModel token = AuthUtils.getTokenFromHeader(headers);
		AuthUtils.checkThatAuthenticated(token);

		Long userId = model.findUserByCredentials().filter(value -> Objects.equals(value, token.getAuthenticatedUser()))
				.orElseThrow(ExceptionUtils::createWrongLoginOrPasswordException);

		UserModel userModel = UserModel.findById(userId).get();
		userModel.setPassword(model.getNewPassword());

		return ResponseUtils.createChangePasswordResponse(userModel.updateIntoUserTable().getId());
	}
}
