package org.github.dmikhaylenko.dao;

import org.github.dmikhaylenko.dao.Database.RowParsers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MysqlSequence implements Sequence<Long> {
	private final Database database;
	private final String sequenceName;

	@Override
	public Long nextValue() {
		return database.executeWithPreparedStatement("SELECT SEQ_NEXT(?) AS NEXTVAL FROM DUAL",
				(connection, statement) -> {
					statement.setString(1, sequenceName);
					return database.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper())
							.get();
				});
	}
}
