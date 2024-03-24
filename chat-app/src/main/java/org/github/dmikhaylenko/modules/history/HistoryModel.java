package org.github.dmikhaylenko.modules.history;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.github.dmikhaylenko.commons.DatabaseUtils;
import org.github.dmikhaylenko.commons.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.commons.DatabaseUtils.RsRowParser;
import org.github.dmikhaylenko.commons.adapters.JaxbLocalDateTimeAdapter;
import org.github.dmikhaylenko.commons.auth.AuthTokenModel;
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
public class HistoryModel {
	@XmlElement
	private Long id;

	@XmlElement
	private String publicName;

	@XmlElement
	private String avatar;

	@XmlElement
	private boolean online;

	@XmlElement
	@XmlJavaTypeAdapter(value = JaxbLocalDateTimeAdapter.class)
	private LocalDateTime lastAccess;

	@XmlElement
	private Long unwatched;

	// @formatter:off
	private static final String FIND_HISTORIES_QUERY = "SELECT \r\n"
			+ "    OPPONENT_ID AS ID,\r\n"
			+ "    OPPONENT_NAME AS NAME,\r\n"
			+ "    OPPONENT_HREF AS AVATAR_HREF,\r\n"
			+ "    OPPONENT_LAST_AUTH AS LAST_AUTH,\r\n"
			+ "    IS_ONLINE(OPPONENT_LAST_AUTH) AS ONLINE,\r\n"
			+ "    UNWATCHED\r\n"
			+ "FROM\r\n"
			+ "    VW_MESSAGE_HISTORY\r\n"
			+ "WHERE\r\n"
			+ "    TOKEN = ?\r\n"
			+ "LIMIT ?\r\n"
			+ "OFFSET ?";
	// @formatter:on

	public static List<HistoryModel> findHistories(AuthTokenModel token, Pagination pagination) {
		return DatabaseUtils.executeWithPreparedStatement(FIND_HISTORIES_QUERY,
				(connection, statement) -> {
					statement.setString(1, token.getToken());
					statement.setLong(2, pagination.getPageSize());
					statement.setLong(3, pagination.getOffset());
					return DatabaseUtils.parseResultSet(statement.executeQuery(), new HistoryModelRowParser());
				});
	}

	// @formatter:off
	private static final String COUNT_HISTORIES_QUERY = "SELECT \r\n"
			+ "    COUNT(*)\r\n"
			+ "FROM\r\n"
			+ "    VW_MESSAGE_HISTORY\r\n"
			+ "WHERE\r\n"
			+ "    TOKEN = ?";
	// @formatter:on

	public static Long countHistories(AuthTokenModel token) {
		return DatabaseUtils.executeWithPreparedStatement(COUNT_HISTORIES_QUERY,
				(connection, statement) -> {
					statement.setString(1, token.getToken());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
				});
	}

	// @formatter:off
	private static final String COUNT_UNWATCHED_HISTORIES_QUERY = "SELECT\r\n"
			+ "    SUM(COALESCE(UW.UNWATCHED, 0)) \r\n"
			+ "FROM\r\n"
			+ "    USER U\r\n"
			+ "    LEFT JOIN VW_UNWATCHED_MESSAGES UW\r\n"
			+ "        ON U.ID = UW.USER_ID\r\n"
			+ "    LEFT JOIN AUTH A\r\n"
			+ "        ON U.ID = A.USER_ID\r\n"
			+ "WHERE\r\n"
			+ "    A.TOKEN = ?\r\n"
			+ "GROUP BY\r\n"
			+ "    U.ID";
	// @formatter:on

	public static Long countUnwatchedHistories(AuthTokenModel token) {
		return DatabaseUtils.executeWithPreparedStatement(COUNT_UNWATCHED_HISTORIES_QUERY,
				(connection, statement) -> {
					statement.setString(1, token.getToken());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
				});
	}

	// @formatter:off
	private static final String CLEAR_ALL_MESSAGES_QUERY = "DELETE FROM \r\n"
			+ "    MESSAGE \r\n"
			+ "WHERE\r\n"
			+ "    (SRC_ID = ? AND DEST_ID = ?) OR\r\n"
			+ "    (SRC_ID = ? AND DEST_ID = ?)";
	// @formatter:on

	public static void clearAllMessages(Long currentUserId, Long userId) {
		DatabaseUtils.executeWithPreparedStatement(CLEAR_ALL_MESSAGES_QUERY,
				(connection, statement) -> {
					statement.setLong(1, currentUserId);
					statement.setLong(2, userId);
					statement.setLong(3, userId);
					statement.setLong(4, currentUserId);
					statement.executeUpdate();
					return null;
				});
	}

	private static class HistoryModelRowParser implements RsRowParser<HistoryModel> {
		// @formatter:off
		@Override
		public HistoryModel parseRow(ResultSet resultSet) throws SQLException {
			return HistoryModel.builder()
					.id(resultSet.getLong("ID"))
					.publicName(resultSet.getString("NAME"))
					.avatar(resultSet.getString("AVATAR_HREF"))
					.lastAccess(TimeUtils.createLocalDateTime(resultSet.getTimestamp("LAST_AUTH")))
					.online(resultSet.getBoolean("ONLINE"))
					.unwatched(resultSet.getLong("UNWATCHED"))
					.build();
		}
		// @formatter:on
	}
}
