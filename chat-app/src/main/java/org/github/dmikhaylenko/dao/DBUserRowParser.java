package org.github.dmikhaylenko.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.github.dmikhaylenko.commons.DatabaseUtils.RsRowParser;
import org.github.dmikhaylenko.commons.time.TimeUtils;

public class DBUserRowParser implements RsRowParser<DBUser> {
	@Override
	public DBUser parseRow(ResultSet resultSet) throws SQLException {
		// @formatter:off
		return DBUser.builder()
				.id(resultSet.getLong("ID"))
				.avatarHref(resultSet.getString("AVATAR_HREF"))
				.phone(resultSet.getString("PHONE"))
				.password(resultSet.getString("PASSWORD"))
				.username(resultSet.getString("USERNAME"))
				.lastAuth(TimeUtils.createLocalDateTime(resultSet.getTimestamp("LAST_AUTH")))
				.build();
		// @formatter:on
	}
}