package org.github.dmikhaylenko.modules.messages;

public interface EditMessageCommand {
	MessageContent getMessageContent();
	MessageIdModel getMessageId();
}