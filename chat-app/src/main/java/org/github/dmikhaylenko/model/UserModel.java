package org.github.dmikhaylenko.model;

import java.sql.ResultSet;
import java.sql.SQLException;
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
