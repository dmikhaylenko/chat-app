package org.github.dmikhaylenko.modules.messages.history;

import java.util.List;

public interface ShowHistoryQueryResult {

	List<MessageViewModel> getMessages();

	Long getPageNumber();

	Long getTotal();

}