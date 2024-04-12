package org.github.dmikhaylenko.dao.messages;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.github.dmikhaylenko.xml.JaxbLocalDateTimeAdapter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMessage {
	@XmlElement
	private Long id;

	@XmlElement
	private Long srcId;

	@XmlElement
	private Long destId;

	@XmlElement
	private String message;

	@XmlElement
	private Boolean watched;

	@XmlElement
	@XmlJavaTypeAdapter(value = JaxbLocalDateTimeAdapter.class)
	private LocalDateTime posted;

	public XmlMessage(DBMessage dbMessage) {
		this();
		this.id = dbMessage.getId();
		this.srcId = dbMessage.getSrcId();
		this.destId = dbMessage.getDestId();
		this.message = dbMessage.getMessage();
		this.watched = dbMessage.getWatched();
		this.posted = dbMessage.getPosted();
	}

	public DBMessage createDBMessage() {
		// @formatter:off
		return DBMessage.builder()
				.id(id)
				.srcId(srcId)
				.destId(destId)
				.message(message)
				.watched(watched)
				.posted(posted)
				.build();
		// @formatter:on
	}
}
