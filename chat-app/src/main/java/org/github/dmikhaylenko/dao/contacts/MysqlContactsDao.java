package org.github.dmikhaylenko.dao.contacts;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.github.dmikhaylenko.dao.Database;
import org.github.dmikhaylenko.dao.Database.RowParsers;

@ApplicationScoped
public class MysqlContactsDao implements ContactsDao {
	private final Database database;
	
	@Inject
	public MysqlContactsDao(Database database) {
		super();
		this.database = database;
	}

	private static String CHECK_CONTACT_EXISTS_QUERY = "SELECT COUNT(*) > 0 FROM CONTACT WHERE WHOSE_ID = ? AND WHO_ID = ?";
	
	@Override
	public boolean existsIntoContactTable(DBContact contact) {
		return database.executeWithPreparedStatement(CHECK_CONTACT_EXISTS_QUERY,
				(connection, statement) -> {
					statement.setLong(1, contact.getWhoseId());
					statement.setLong(2, contact.getWhoId());
					return database
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}

	private static String INSERT_INTO_CONTACT_TABLE_QUERY = "INSERT INTO CONTACT(WHOSE_ID, WHO_ID) VALUES (?, ?)";

	@Override
	public void insertIntoContactTable(DBContact contact) {
		database.executeWithPreparedStatement(INSERT_INTO_CONTACT_TABLE_QUERY,
				(connection, statement) -> {
					statement.setLong(1, contact.getWhoseId());
					statement.setLong(2, contact.getWhoId());
					statement.executeUpdate();
					return null;
				});
	}

	private static String DELETE_FROM_TABLE_QUERY = "DELETE FROM CONTACT WHERE WHOSE_ID = ? AND WHO_ID = ?";

	@Override
	public void deleteFromContactTable(DBContact contact) {
		database.executeWithPreparedStatement(DELETE_FROM_TABLE_QUERY,
				(connection, statement) -> {
					statement.setLong(1, contact.getWhoseId());
					statement.setLong(2, contact.getWhoId());
					statement.executeUpdate();
					return null;
				});
	}
}
