package org.github.dmikhaylenko.modules.users;

import java.util.Optional;

import org.github.dmikhaylenko.commons.DatabaseUtils;
import org.github.dmikhaylenko.commons.DatabaseUtils.RowParsers;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserIdModel {
	private final Long userId;
	
	public Long unwrap() {
		return userId;
	}
	
	public void checkThatRequestedUserExists() {
		if (!existsById()) {
			throw new MissingRequestedUserException();
		}
	}
	
	private static final String CHECK_EXISTS_BY_ID_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE ID = ?";
	
	public boolean existsById() {
		return DatabaseUtils.executeWithPreparedStatement(CHECK_EXISTS_BY_ID_QUERY,
				(connection, statement) -> {
					statement.setLong(1, userId);
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}
	
	
	
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM USER WHERE ID = ?";

	public Optional<UserModel> findById() {
		return DatabaseUtils.executeWithPreparedStatement(FIND_BY_ID_QUERY,
				(connection, statement) -> {
					statement.setLong(1, userId);
					return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), new UserModelRowParser());
				});
	}

}
