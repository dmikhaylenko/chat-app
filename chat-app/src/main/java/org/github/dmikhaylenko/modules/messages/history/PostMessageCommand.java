package org.github.dmikhaylenko.modules.messages.history;

import org.github.dmikhaylenko.modules.messages.MessageContent;
import org.github.dmikhaylenko.modules.users.UserIdModel;

public interface PostMessageCommand {

	MessageContent getMessageContent();

	UserIdModel getReceiverId();

}