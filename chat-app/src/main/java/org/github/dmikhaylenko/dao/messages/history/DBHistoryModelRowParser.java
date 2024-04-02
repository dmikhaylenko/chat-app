package org.github.dmikhaylenko.dao.messages.history;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.github.dmikhaylenko.dao.Database.RsRowParser;
import org.github.dmikhaylenko.time.Time;

public class DBHistoryModelRowParser implements RsRowParser<DBHistory> {
	// @formatter:off
	@Override
	public DBHistory parseRow(ResultSet resultSet) throws SQLException {
		return DBHistory.builder()
				.id(resultSet.getLong("ID"))
				.publicName(resultSet.getString("NAME"))
				.avatar(resultSet.getString("AVATAR_HREF"))
				.lastAccess(Time.createLocalDateTime(resultSet.getTimestamp("LAST_AUTH")))
				.online(resultSet.getBoolean("ONLINE"))
				.unwatched(resultSet.getLong("UNWATCHED"))
				.build();
	}
	// @formatter:on
}