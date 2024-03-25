package org.github.dmikhaylenko.modules.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.github.dmikhaylenko.commons.DatabaseUtils.RsRowParser;
import org.github.dmikhaylenko.commons.time.TimeUtils;

public class UserModelRowParser implements RsRowParser<UserModel> {
	@Override
	public UserModel parseRow(ResultSet resultSet) throws SQLException {
		// @formatter:off
		return UserModel.builder()
				.id(resultSet.getLong("ID"))
				.avatar(resultSet.getString("AVATAR_HREF"))
				.phone(resultSet.getString("PHONE"))
				.password(resultSet.getString("PASSWORD"))
				.publicName(resultSet.getString("USERNAME"))
				.lastAuth(TimeUtils.createLocalDateTime(resultSet.getTimestamp("LAST_AUTH")))
				.build();
		// @formatter:on
	}
}