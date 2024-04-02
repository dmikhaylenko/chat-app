package org.github.dmikhaylenko.modules.messages.history;

import java.time.LocalDateTime;

import org.github.dmikhaylenko.dao.messages.DBMessageView;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public class MessageViewModel {
	private Long id;
	private MessageAuthorModel author;
	private String message;
	private boolean watched;
	private LocalDateTime posted;
	
	public MessageViewModel(DBMessageView message) {
		super();
		this.id = message.getId();
		this.author = new MessageAuthorModel(message);
		this.message = message.getMessage();
		this.watched = message.isWatched();
		this.posted = message.getPosted();
	}
	
	
	@Getter
	@ToString
	@SuperBuilder
	@EqualsAndHashCode
	public static class MessageAuthorModel {
		private String avatar;
		private String publicName;
		
		public MessageAuthorModel(DBMessageView message) {
			super();
			this.avatar = message.getAvatar();
			this.publicName = message.getPublicName();
		}
	}
}
