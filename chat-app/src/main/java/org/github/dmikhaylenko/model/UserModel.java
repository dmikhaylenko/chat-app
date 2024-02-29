package org.github.dmikhaylenko.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.utils.DatabaseUtils;
import org.github.dmikhaylenko.utils.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.utils.DatabaseUtils.RsRowParser;
import org.github.dmikhaylenko.utils.PageUtils;
import org.github.dmikhaylenko.utils.Resources;

import lombok.Data;

@Data
@XmlRootElement
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

	private static final String FIND_BY_ID_QUERY = "SELECT * FROM USER WHERE ID = ?";

	public static Optional<UserModel> findById(Long id) {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), FIND_BY_ID_QUERY,
				(connection, statement) -> {
					statement.setLong(1, id);
					return DatabaseUtils.parseResultSetSingleRow(statement.executeQuery(), new UserModelRowParser());
				});
	}

	// @formatter:off
	private static final String FIND_BY_PHONE_OR_USERNAME_QUERY = 
			"SELECT \r\n"
			+ "    ID, AVATAR_HREF, PHONE, USERNAME, NULL AS PASSWORD \r\n" 
			+ "FROM\r\n" + "    USER\r\n" + "WHERE\r\n"
			+ "    LOWER(PHONE) LIKE CONCAT('%', LOWER(TRIM(COALESCE(?, ''))),'%') OR\r\n"
			+ "    LOWER(USERNAME) LIKE CONCAT('%', LOWER(TRIM(COALESCE(?, ''))),'%')\r\n" 
			+ "LIMIT ?\r\n" 
			+ "OFFSET ?";
	// @formatter:on

	public static List<UserModel> findByPhoneOrUsername(String sstr, Long pg, Long ps) {
		Long offset = PageUtils.calculateOffset(pg, ps);
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), FIND_BY_PHONE_OR_USERNAME_QUERY,
				(connection, statement) -> {
					statement.setString(1, sstr);
					statement.setString(2, sstr);
					statement.setLong(3, ps);
					statement.setLong(4, offset);
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
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), COUNT_BY_PHONE_OR_USERNAME_QUERY,
				(connection, statement) -> {
					statement.setString(1, sstr);
					statement.setString(2, sstr);
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
				});
	}

	private static final String CHECK_EXISTS_WITH_PHONE_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE PHONE = ?";

	public boolean existsWithThePhone() {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), CHECK_EXISTS_WITH_PHONE_QUERY,
				(connection, statement) -> {
					statement.setString(1, getPhone());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}

	private static final String CHECK_EXISTS_WITH_NICKNAME_QUERY = "SELECT COUNT(*) > 0 FROM USER WHERE USERNAME = ?";

	public boolean existsWithTheNickname() {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), CHECK_EXISTS_WITH_NICKNAME_QUERY,
				(connection, statement) -> {
					statement.setString(1, getPublicName());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.booleanValueRowMapper())
							.get();
				});
	}

	private static final String INSERT_TO_USER_TABLE_QUERY = "INSERT INTO USER(PHONE, PASSWORD, USERNAME, AVATAR_HREF) VALUES(?,?,?,?)";

	public UserModel insertToUserTable() {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), INSERT_TO_USER_TABLE_QUERY,
				(connection, statement) -> {
					connection.setAutoCommit(false);
					statement.setString(1, getPhone());
					statement.setString(2, getPassword());
					statement.setString(3, getPublicName());
					statement.setString(4, getAvatar());
					statement.executeUpdate();
					DatabaseUtils.lastInsertedId(connection).ifPresent(this::setId);
					connection.commit();
					return this;
				});
	}
	
	private static final String UPDATE_INTO_USER_TABLE_QUERY = "UPDATE USER SET PHONE=?,PASSWORD=?, USERNAME=?, AVATAR_HREF=? WHERE ID=?";
	
	public UserModel updateIntoUserTable() {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), UPDATE_INTO_USER_TABLE_QUERY, (connection, statement) -> {
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
			UserModel model = new UserModel();
			model.setId(resultSet.getLong("ID"));
			model.setAvatar(resultSet.getString("AVATAR_HREF"));
			model.setPhone(resultSet.getString("PHONE"));
			model.setPassword(resultSet.getString("PASSWORD"));
			model.setPublicName(resultSet.getString("USERNAME"));
			return model;
		}
	}
}
