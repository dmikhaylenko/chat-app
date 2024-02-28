package org.github.dmikhaylenko.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DatabaseUtils {
	public Optional<Long> lastInsertedId(Connection connection) {
		return executeWithPreparedStatement(connection, "SELECT LAST_INSERT_ID() FROM DUAL", (conn, statement) -> {
			return parseResultSetSingleRow(statement.executeQuery(), RowParsers.longValueRowMapper());
		});
	}

	public <T> T executeWithPreparedStatement(DataSource dataSource, String sql, PreparedStatementExecutor<T> fn) {
		return executeWithConnection(dataSource, connection -> {
			return executeWithPreparedStatement(connection, sql, fn);
		});
	}

	public <T> T executeWithPreparedStatement(Connection connection, String sql, PreparedStatementExecutor<T> fn) {
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			return fn.consumeStatement(connection, statement);
		} catch (SQLException error) {
			throw ExceptionUtils.uncheckedOf(error);
		}
	}

	public <T> T executeWithCallStatement(DataSource data, String sql, CallStatementExecutor<T> fn) {
		return executeWithConnection(data, connection -> {
			return executeWithCallStatement(connection, sql, fn);
		});
	}

	public <T> T executeWithCallStatement(Connection connection, String sql, CallStatementExecutor<T> fn) {
		try (CallableStatement statement = connection.prepareCall(sql)) {
			return fn.consumeStatement(connection, statement);
		} catch (SQLException error) {
			throw ExceptionUtils.uncheckedOf(error);
		}
	}

	public <T> T executeWithConnection(DataSource dataSource, DbConnectionConsumer<T> fn) {
		try (Connection connection = dataSource.getConnection()) {
			return fn.consumeStatement(connection);
		} catch (SQLException error) {
			throw ExceptionUtils.uncheckedOf(error);
		}
	}

	public <T> Optional<T> parseResultSetSingleRow(ResultSet resultSet, RsRowParser<T> parser) throws SQLException {
		return parseResultSet(resultSet, parser).stream().findAny();
	}

	public <T> List<T> parseResultSet(ResultSet resultSet, RsRowParser<T> parser) throws SQLException {
		List<T> result = new LinkedList<>();
		while (resultSet.next()) {
			Optional.ofNullable(parser.parseRow(resultSet)).ifPresent(result::add);
		}
		return result;
	}

	@UtilityClass
	public class RowParsers {
		public RsRowParser<Long> longValueRowMapper() {
			return rs -> {
				return rs.getLong(1);
			};
		}

		public RsRowParser<String> stringValueRowMapper() {
			return rs -> {
				return rs.getString(1);
			};
		}

		public RsRowParser<Boolean> booleanValueRowMapper() {
			return rs -> {
				return rs.getBoolean(1);
			};
		}
	}

	@FunctionalInterface
	public interface DbConnectionConsumer<T> {
		T consumeStatement(Connection connection) throws SQLException;
	}

	@FunctionalInterface
	public interface PreparedStatementExecutor<T> {
		T consumeStatement(Connection connection, PreparedStatement statement) throws SQLException;
	}

	@FunctionalInterface
	public interface CallStatementExecutor<T> {
		T consumeStatement(Connection connection, CallableStatement statement) throws SQLException;
	}

	@FunctionalInterface
	public interface RsRowParser<T> {
		T parseRow(ResultSet resultSet) throws SQLException;
	}
}
