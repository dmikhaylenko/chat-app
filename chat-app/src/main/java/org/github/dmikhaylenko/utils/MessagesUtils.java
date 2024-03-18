package org.github.dmikhaylenko.utils;

import org.github.dmikhaylenko.model.MessageModel;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessagesUtils {
	public void checkMessageEditingAvailabilityForUser(Long userId, MessageModel messageModel) {
		if (!userId.equals(messageModel.getSrcId())) {
			throw ExceptionUtils.createItIsForbiddenToEditForeignUsersMessagesException();
		}
	}
}
