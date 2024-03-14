package org.github.dmikhaylenko.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.utils.DatabaseUtils;
import org.github.dmikhaylenko.utils.Resources;

import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageModel {
	@Null
	@XmlElement
	private Long id;
	
	@Null
	@XmlElement
	private Long srcId;
	
	@Null
	@XmlElement
	private Long destId;
	
	@NotNull
	@XmlElement
	@Size(min = 1, max = 500)
	private String messageText;
	
	@Null
	@XmlElement
	private Boolean watched;
	
	@Null
	@XmlElement
	private LocalDateTime posted;
	
	private static final String INSERT_MESSAGE_QUERY = "INSERT INTO MESSAGE(SRC_ID, DEST_ID, MESSAGE) VALUES (?, ?, ?)";
	
	public Long insertIntoMessageTable() {
		return DatabaseUtils.executeWithCallStatement(Resources.getChatDb(), INSERT_MESSAGE_QUERY, (connection, statement) -> {
			connection.setAutoCommit(false);
			statement.setLong(1, srcId);
			statement.setLong(2, destId);
			statement.setString(3, messageText);
			statement.executeUpdate();
			Long id = DatabaseUtils.lastInsertedId(connection).get();
			connection.commit();
			return id;
		});
	}
}
