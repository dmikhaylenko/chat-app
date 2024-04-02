package org.github.dmikhaylenko.modules.messages.history;

import javax.validation.constraints.NotNull;

import org.github.dmikhaylenko.modules.messages.MessageContent;
import org.github.dmikhaylenko.modules.users.UserIdModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class PostMessageRequest implements PostMessageCommand {
	@NotNull
	private final Long receiverId;
	
	@NotNull
	private final MessageContent messageContent;
	
	@Override
	public UserIdModel getReceiverId() {
		return new UserIdModel(receiverId);
	}
}
