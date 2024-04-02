package org.github.dmikhaylenko.dao.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.github.dmikhaylenko.dao.Database.RsRowParser;
import org.github.dmikhaylenko.time.Time;

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
				.lastAuth(Time.createLocalDateTime(resultSet.getTimestamp("LAST_AUTH")))
				.build();
		// @formatter:on
	}
}