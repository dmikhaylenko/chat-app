package org.github.dmikhaylenko.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.github.dmikhaylenko.adapters.JaxbLocalDateTimeAdapter;
import org.github.dmikhaylenko.utils.DatabaseUtils;
import org.github.dmikhaylenko.utils.DatabaseUtils.RowParsers;
import org.github.dmikhaylenko.utils.DatabaseUtils.RsRowParser;
import org.github.dmikhaylenko.utils.PageUtils;
import org.github.dmikhaylenko.utils.Resources;
import org.github.dmikhaylenko.utils.TimeUtils;

import lombok.Data;

@Data
@XmlRootElement
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

	public static List<HistoryModel> findHistories(AuthTokenModel token, Long pg, Long ps) {
		Long offset = PageUtils.calculateOffset(pg, ps);
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), FIND_HISTORIES_QUERY,
				(connection, statement) -> {
					statement.setString(1, token.getToken());
					statement.setLong(2, ps);
					statement.setLong(3, offset);
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
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), COUNT_HISTORIES_QUERY,
				(connection, statement) -> {
					statement.setString(1, token.getToken());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
				});
	}

	// @formatter:off
	private static final String COUNT_UNWATCHED_HISTORIES_QUERY = "SELECT \r\n"
			+ "    SUM(UW.UNWATCHED) AS TOTAL_UNWATCHED\r\n"
			+ "FROM \r\n"
			+ "    VW_UNWATCHED_MESSAGES UW\r\n"
			+ "    LEFT JOIN AUTH A\r\n"
			+ "        ON A.USER_ID = UW.USER_ID\r\n"
			+ "WHERE\r\n"
			+ "    A.TOKEN = ?\r\n"
			+ "GROUP BY\r\n"
			+ "    UW.USER_ID";
	// @formatter:on

	public static Long countUnwatchedHistories(AuthTokenModel token) {
		return DatabaseUtils.executeWithPreparedStatement(Resources.getChatDb(), COUNT_UNWATCHED_HISTORIES_QUERY,
				(connection, statement) -> {
					statement.setString(1, token.getToken());
					return DatabaseUtils
							.parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper()).get();
				});
	}

	private static class HistoryModelRowParser implements RsRowParser<HistoryModel> {
		@Override
		public HistoryModel parseRow(ResultSet resultSet) throws SQLException {
			HistoryModel model = new HistoryModel();
			model.setId(resultSet.getLong("ID"));
			model.setPublicName(resultSet.getString("NAME"));
			model.setAvatar(resultSet.getString("AVATAR_HREF"));
			model.setLastAccess(TimeUtils.createLocalDateTime(resultSet.getTimestamp("LAST_AUTH")));
			model.setOnline(resultSet.getBoolean("ONLINE"));
			model.setUnwatched(resultSet.getLong("UNWATCHED"));
			return model;
		}

	}
}
