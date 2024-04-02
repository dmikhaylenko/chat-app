package org.github.dmikhaylenko.modules.messages.history;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.github.dmikhaylenko.dao.messages.history.DBHistory;
import org.github.dmikhaylenko.xml.JaxbLocalDateTimeAdapter;

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

	public HistoryModel(DBHistory message) {
		super();
		this.id = message.getId();
		this.publicName = message.getPublicName();
		this.avatar = message.getAvatar();
		this.online = message.isOnline();
		this.lastAccess = message.getLastAccess();
		this.unwatched = message.getUnwatched();
	}
}
