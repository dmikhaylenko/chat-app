package org.github.dmikhaylenko.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.utils.DatabaseUtils;
import org.github.dmikhaylenko.utils.Resources;
import org.github.dmikhaylenko.utils.TimeUtils;
import org.github.dmikhaylenko.utils.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.utils.DatabaseUtils.RsRowParser;
import org.github.dmikhaylenko.utils.PageUtils;

import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageViewModel {
	@XmlElement
	private Long id;

	@XmlElement
	private UserModel author;

	@XmlElement
	private String message;

	@XmlElement
	private boolean watched;

	@XmlElement
	private LocalDateTime posted;

	// @formatter:off
	private static final String GET_LAST_PAGE_QUERY = "SELECT \r\n"
			+ "    (COUNT(*) DIV ?) + 1 AS LAST_PAGE \r\n"
			+ "FROM \r\n"
			+ "    VW_MESSAGE\r\n"
			+ "WHERE\r\n"
			+ "    USER_ID = ?";
	// @formatter:on

	public static Long getLastPage(Long currentUserId, Long ps) {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), GET_LAST_PAGE_QUERY,
				(connection, statement) -> {
					statement.setLong(1, ps);
					statement.setLong(2, currentUserId);
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
				});
	}

	// @formatter:off
	private static final String FIND_MESSAGES_QUERY = "SELECT\r\n"
			+ "    MSG.MESSAGE_ID AS ID,\r\n"
			+ "    U.USERNAME AS AUTHOR_NAME,\r\n"
			+ "    U.AVATAR_HREF AS AUTHOR_AVATAR,\r\n"
			+ "    MSG.MESSAGE_BODY AS MESSAGE,\r\n"
			+ "    MSG.MSG_WATCHED AS WATCHED,\r\n"
			+ "    MSG.MSG_POSTED AS POSTED\r\n"
			+ "FROM\r\n"
			+ "    VW_MESSAGE MSG\r\n"
			+ "    LEFT JOIN USER U\r\n"
			+ "        ON MSG.MSG_AUTHOR = U.ID\r\n"
			+ "WHERE\r\n"
			+ "    MSG.OPPONENT_ID = ? AND\r\n"
			+ "    MSG.USER_ID = ?\r\n"
			+ "LIMIT ?\r\n"
			+ "OFFSET ?";
	// @formatter:on

	public static List<MessageViewModel> findMessages(Long userId, Long currentUserId, Long pg, Long ps) {
		Long offset = PageUtils.calculateOffset(pg, ps);
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), FIND_MESSAGES_QUERY,
				(connection, statement) -> {
					statement.setLong(1, userId);
					statement.setLong(2, currentUserId);
					statement.setLong(3, ps);
					statement.setLong(4, offset);
					return DatabaseUtils.parseResultSet(statement.executeQuery(), new MessageViewModelRowParser());
				});
	}

	// @formatter:off
	private static final String GET_TOTAL_MESSAGES_QUERY = "SELECT\r\n"
			+ "    COUNT(*) AS TOTAL\r\n"
			+ "FROM\r\n"
			+ "    VW_MESSAGE MSG\r\n"
			+ "    LEFT JOIN USER U\r\n"
			+ "        ON MSG.MSG_AUTHOR = U.ID\r\n"
			+ "WHERE\r\n"
			+ "    MSG.OPPONENT_ID = ? AND\r\n"
			+ "    MSG.USER_ID = ?";
	// @formatter:on

	public static Long getTotalMessages(Long userId, Long currentUserId) {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), GET_TOTAL_MESSAGES_QUERY,
				(connection, statement) -> {
					statement.setLong(1, userId);
					statement.setLong(2, currentUserId);
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
				});
	}

	private static final class MessageViewModelRowParser implements RsRowParser<MessageViewModel> {
		@Override
		public MessageViewModel parseRow(ResultSet resultSet) throws SQLException {
			MessageViewModel result = new MessageViewModel();
			UserModel author = new UserModel();
			result.setAuthor(author);
			result.setId(resultSet.getLong("ID"));
			author.setPublicName(resultSet.getString("AUTHOR_NAME"));
			author.setAvatar(resultSet.getString("AUTHOR_AVATAR"));
			result.setMessage(resultSet.getString("MESSAGE"));
			result.setWatched(resultSet.getBoolean("WATCHED"));
			result.setPosted(TimeUtils.createLocalDateTime(resultSet.getTimestamp("POSTED")));
			return result;
		}
	}
}
