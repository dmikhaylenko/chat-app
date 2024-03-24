package org.github.dmikhaylenko.modules.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.github.dmikhaylenko.commons.DatabaseUtils;
import org.github.dmikhaylenko.commons.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.commons.DatabaseUtils.RsRowParser;
import org.github.dmikhaylenko.commons.adapters.JaxbLocalDateTimeAdapter;
import org.github.dmikhaylenko.commons.pagination.Pagination;
import org.github.dmikhaylenko.commons.time.TimeUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@XmlRootElement
@NoArgsConstructor
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class UserModel {
	@XmlElement
	private Long id;

	@NotNull
	@XmlElement
	@Size(max = 1000)
	private String avatar;

	@NotNull
	@XmlElement
	@Size(min = 1, max = 50)
	@Pattern(regexp = "\\+(\\d)+")
	private String phone;

	@NotNull
	@XmlElement
	@Size(min = 1, max = 50)
	private String password;

	@NotNull
	@XmlElement
	@Size(min = 1, max = 50)
	private String publicName;
	
	@Null
	@XmlElement
	@XmlJavaTypeAdapter(value = JaxbLocalDateTimeAdapter.class)
	private LocalDateTime lastAuth;
	
	private static final String CHECK_EXISTS_BY_ID_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE ID = ?";

	public static boolean existsById(Long id) {
		return DatabaseUtils.executeWithPreparedStatement(CHECK_EXISTS_BY_ID_QUERY,
				(connection, statement) -> {
					statement.setLong(1, id);
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}

	private static final String FIND_BY_ID_QUERY = "SELECT * FROM USER WHERE ID = ?";

	public static Optional<UserModel> findById(Long id) {
		return DatabaseUtils.executeWithPreparedStatement(FIND_BY_ID_QUERY,
				(connection, statement) -> {
					statement.setLong(1, id);
					return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), new UserModelRowParser());
				});
	}

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

	public static List<UserModel> findByPhoneOrUsername(String sstr, Pagination pagination) {
		return DatabaseUtils.executeWithPreparedStatement(FIND_BY_PHONE_OR_USERNAME_QUERY,
				(connection, statement) -> {
					statement.setString(1, sstr);
					statement.setString(2, sstr);
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

	public static Long countByPhoneOrUsername(String sstr) {
		return DatabaseUtils.executeWithPreparedStatement(COUNT_BY_PHONE_OR_USERNAME_QUERY,
				(connection, statement) -> {
					statement.setString(1, sstr);
					statement.setString(2, sstr);
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
				});
	}

	public static void checkThatRequestedUserExits(Long userId) {
		if (!UserModel.existsById(userId)) {
			throw new MissingRequestedUserException();
		}
	}
	
	public Long registerUser() {
		checkThatUserWithPhoneExists();
		checkThatUserWithNickNameExists();
		return insertToUserTable();
	}
	
	public void changePassword(String password) {
		this.password = password;
		updateIntoUserTable();
	}
	
	
	
	
	
	
	private void checkThatUserWithPhoneExists() {
		if (existsWithThePhone()) {
			throw new UserWithPhoneExistsException();
		}
	}
	
	private void checkThatUserWithNickNameExists() {
		if (existsWithTheNickname()) {
			throw new UserWithNickNameExistsException();
		}
	}
	
	private static final String CHECK_EXISTS_WITH_PHONE_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE PHONE = ?";

	private boolean existsWithThePhone() {
		return DatabaseUtils.executeWithPreparedStatement(CHECK_EXISTS_WITH_PHONE_QUERY,
				(connection, statement) -> {
					statement.setString(1, getPhone());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}

	private static final String CHECK_EXISTS_WITH_NICKNAME_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE USERNAME = ?";

	private boolean existsWithTheNickname() {
		return DatabaseUtils.executeWithPreparedStatement(CHECK_EXISTS_WITH_NICKNAME_QUERY,
				(connection, statement) -> {
					statement.setString(1, getPublicName());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}

	private static final String INSERT_TO_USER_TABLE_QUERY = "INSERT INTO USER(PHONE, PASSWORD, USERNAME, AVATAR_HREF) VALUES(?,?,?,?)";

	private Long insertToUserTable() {
		return DatabaseUtils.executeWithPreparedStatement(INSERT_TO_USER_TABLE_QUERY,
				(connection, statement) -> {
					connection.setAutoCommit(false);
					statement.setString(1, getPhone());
					statement.setString(2, getPassword());
					statement.setString(3, getPublicName());
					statement.setString(4, getAvatar());
					statement.executeUpdate();
					Long id = DatabaseUtils.lastInsertedId(connection).get();
					connection.commit();
					return id;
				});
	}

	private static final String UPDATE_INTO_USER_TABLE_QUERY = "UPDATE USER SET PHONE=?, PASSWORD=?, USERNAME=?, AVATAR_HREF=? WHERE ID=?";

	private UserModel updateIntoUserTable() {
		return DatabaseUtils.executeWithPreparedStatement(UPDATE_INTO_USER_TABLE_QUERY,
				(connection, statement) -> {
					connection.setAutoCommit(false);
					statement.setString(1, getPhone());
					statement.setString(2, getPassword());
					statement.setString(3, getPublicName());
					statement.setString(4, getAvatar());
					statement.setLong(5, getId());
					statement.executeUpdate();
					connection.commit();
					return this;
				});
	}

	private static class UserModelRowParser implements RsRowParser<UserModel> {
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
}
