package org.github.dmikhaylenko.modules.messages.history;

import org.github.dmikhaylenko.modules.users.UserIdModel;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class ClearHistoryRequest implements ClearHistoryCommand {
	private final Long userId;

	@Override
	public UserIdModel getUserId() {
		return new UserIdModel(userId);
	}
}
