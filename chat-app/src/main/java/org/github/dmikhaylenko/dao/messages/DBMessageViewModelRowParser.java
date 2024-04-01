package org.github.dmikhaylenko.dao.messages;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.github.dmikhaylenko.dao.Database.RsRowParser;
import org.github.dmikhaylenko.time.Time;

public final class DBMessageViewModelRowParser implements RsRowParser<DBMessageView> {
	// @formatter:off
	@Override
	public DBMessageView parseRow(ResultSet resultSet) throws SQLException {
		return DBMessageView.builder()
				.publicName(resultSet.getString("AUTHOR_NAME"))
				.avatar(resultSet.getString("AUTHOR_AVATAR"))
				.id(resultSet.getLong("ID"))
				.message(resultSet.getString("MESSAGE"))
				.watched(resultSet.getBoolean("WATCHED"))
				.posted(Time.createLocalDateTime(resultSet.getTimestamp("POSTED")))
				.build();
	}
	// @formatter:on
}