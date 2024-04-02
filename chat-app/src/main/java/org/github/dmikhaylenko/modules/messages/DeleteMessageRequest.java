package org.github.dmikhaylenko.modules.messages;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteMessageRequest implements DeleteMessageCommand {
	private final Long messageId;
	
	@Override
	public MessageIdModel getMessageId() {
		return new MessageIdModel(messageId);
	}
}
