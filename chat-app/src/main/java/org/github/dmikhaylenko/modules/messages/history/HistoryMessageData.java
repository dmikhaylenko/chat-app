package org.github.dmikhaylenko.modules.messages.history;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.modules.messages.history.MessageViewModel.MessageAuthorModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class HistoryMessageData {
	@XmlElement
	private Long id;

	@XmlElement
	private MessageAuthorData author;

	@XmlElement
	private String message;

	@XmlElement
	private boolean watched;

	@XmlElement
	private LocalDateTime posted;
	
	public HistoryMessageData(MessageViewModel messageView) {
		super();
		this.id = messageView.getId();
		this.author = new MessageAuthorData(messageView.getAuthor());
		this.message = messageView.getMessage();
		this.watched = messageView.isWatched();
		this.posted = messageView.getPosted();
	}
	
	@Getter
	@ToString
	@XmlRootElement
	@EqualsAndHashCode
	@NoArgsConstructor
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class MessageAuthorData {
		@XmlElement
		private String avatar;
		
		@XmlElement
		private String publicName;
		
		public MessageAuthorData(MessageAuthorModel author) {
			super();
			this.avatar = author.getAvatar();
			this.publicName = author.getPublicName();
		}
	}
}
