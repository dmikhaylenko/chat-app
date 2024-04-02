package org.github.dmikhaylenko.modules.messages;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class EditMessageRequest implements EditMessageCommand {
	@NotNull
	private final Long messageId;
	@Valid
	@Getter
	@NotNull
	private final MessageContent messageContent;
	
	@Override
	public MessageIdModel getMessageId() {
		return new MessageIdModel(messageId);
	}
}
