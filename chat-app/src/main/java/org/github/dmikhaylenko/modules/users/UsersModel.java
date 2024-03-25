package org.github.dmikhaylenko.modules.users;

import java.util.List;

import org.github.dmikhaylenko.commons.DatabaseUtils;
import org.github.dmikhaylenko.commons.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.model.pagination.Pagination;

public class UsersModel {
	// @formatter:off
	private static final String FIND_BY_PHONE_OR_USERNAME_QUERY = 
			"SELECT \r\n"
			+ "    ID, AVATAR_HREF, PHONE, USERNAME, NULL AS PASSWORD, NULL AS LAST_AUTH \r\n" 
			+ "FROM\r\n" 
			+ "    USER\r\n" 
			+ "WHERE\r\n"
			+ "    LOWER(PHONE) LIKE CONCAT('%', LOWER(TRIM(COALESCE(?, ''))),'%') OR\r\n"
			+ "    LOWER(USERNAME) LIKE CONCAT('%', LOWER(TRIM(COALESCE(?, ''))),'%')\r\n" 
			+ "LIMIT ?\r\n" 
			+ "OFFSET ?";
	// @formatter:on

	public List<UserModel> findByPhoneOrUsername(String searchString, Pagination pagination) {
		return DatabaseUtils.executeWithPreparedStatement(FIND_BY_PHONE_OR_USERNAME_QUERY,
				(connection, statement) -> {
					statement.setString(1, searchString);
					statement.setString(2, searchString);
					statement.setLong(3, pagination.getPageSize());
					statement.setLong(4, pagination.getOffset());
					return DatabaseUtils.parseResultSet(statement.executeQuery(), new UserModelRowParser());
				});
	}

	// @formatter:off
	private static final String COUNT_BY_PHONE_OR_USERNAME_QUERY = 
			"SELECT \r\n" 
	        + "    COUNT(*) AS TOTAL\r\n"
			+ "FROM\r\n" 
	        + "    USER\r\n" 
			+ "WHERE\r\n"
			+ "    LOWER(PHONE) LIKE CONCAT('%', LOWER(TRIM(COALESCE(?, ''))), '%') OR\r\n"
			+ "    LOWER(USERNAME) LIKE CONCAT('%', LOWER(TRIM(COALESCE(?, ''))), '%')";
	// @formatter:on

	public Long countByPhoneOrUsername(String sstr) {
		return DatabaseUtils.executeWithPreparedStatement(COUNT_BY_PHONE_OR_USERNAME_QUERY,
				(connection, statement) -> {
					statement.setString(1, sstr);
					statement.setString(2, sstr);
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
				});
	}
}
