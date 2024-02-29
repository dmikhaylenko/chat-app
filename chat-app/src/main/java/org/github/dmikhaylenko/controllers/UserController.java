package org.github.dmikhaylenko.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.github.dmikhaylenko.model.ResponseModel;
import org.github.dmikhaylenko.model.UserModel;
import org.github.dmikhaylenko.utils.AuthUtils;
import org.github.dmikhaylenko.utils.ExceptionUtils;
import org.github.dmikhaylenko.utils.PageUtils;
import org.github.dmikhaylenko.utils.ResponseUtils;
import org.github.dmikhaylenko.utils.ValidationUtils;

@Path("/users")
public class UserController {
	@POST
	public ResponseModel registerUser(UserModel model) {
		ValidationUtils.checkConstraints(model);
		if (model.existsWithThePhone()) {
			throw ExceptionUtils.createUserWithPhoneExistsException();
		}

		if (model.existsWithTheNickname()) {
			throw ExceptionUtils.createUserWithNickNameExistsException();
		}

		return ResponseUtils.createRegisterUserResponse(model.insertToUserTable().getId());
	}

	@GET
	@Path("/search")
	public ResponseModel searchUsers(@Context HttpHeaders headers, @QueryParam("sstr") String sstr,
			@QueryParam("pg") Long pg, @QueryParam("ps") Long ps) {
		AuthUtils.checkAuthenticated(AuthUtils.parseAuthToken(headers));
		Long normalizedPg = PageUtils.normalizePage(pg);
		Long normalizedPs = PageUtils.normalizePageSize(ps, 1000L, 50L);
		List<UserModel> users = UserModel.findByPhoneOrUsername(sstr, normalizedPg, normalizedPs);
		Long total = UserModel.countByPhoneOrUsername(sstr);
		return ResponseUtils.createSearchUsersResponse(users, total);
	}
}
