package org.github.dmikhaylenko.modules.messages.history;

import org.github.dmikhaylenko.modules.Pagination;
import org.github.dmikhaylenko.modules.Pagination.DefaultPageNumberCalculator;
import org.github.dmikhaylenko.modules.users.UserIdModel;

public interface ShowHistoryCommand {

	UserIdModel getUserId();

	Pagination getPagination(DefaultPageNumberCalculator pageNumberCalculator);

}