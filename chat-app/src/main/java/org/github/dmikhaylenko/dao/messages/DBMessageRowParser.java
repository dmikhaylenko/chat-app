package org.github.dmikhaylenko.dao.messages;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.github.dmikhaylenko.dao.Database.RsRowParser;
import org.github.dmikhaylenko.time.Time;

public class DBMessageRowParser implements RsRowParser<DBMessage> {
	// @formatter:off
	@Override
	public DBMessage parseRow(ResultSet resultSet) throws SQLException {
		return DBMessage.builder()
				.id(resultSet.getLong("ID"))
				.srcId(resultSet.getLong("SRC_ID"))
				.destId(resultSet.getLong("DEST_ID"))
				.message(resultSet.getString("MESSAGE"))
				.watched(resultSet.getBoolean("WATCHED"))
				.posted(Time.createLocalDateTime(resultSet.getTimestamp("POSTED")))
				.build();
	}
	// @formatter:on
}