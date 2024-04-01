package org.github.dmikhaylenko.dao;

import javax.inject.Inject;

import org.github.dmikhaylenko.dao.Dao.DaoLocator;
import org.github.dmikhaylenko.dao.contacts.ContactsDao;
import org.github.dmikhaylenko.dao.messages.MessageIdSequence;
import org.github.dmikhaylenko.dao.messages.MessagesDao;
import org.github.dmikhaylenko.dao.messages.history.HistoriesDao;
import org.github.dmikhaylenko.dao.users.UserIdSequence;
import org.github.dmikhaylenko.dao.users.UsersDao;
import org.github.dmikhaylenko.dao.users.auth.AuthDao;

import lombok.Getter;
import lombok.experimental.Accessors;


@Getter
@Accessors(fluent = true)
public class CdiDaoLocator implements DaoLocator {
	@Inject
	private UserIdSequence userIdSequence;
	
	@Inject
	private UsersDao userDao;

	@Inject
	private AuthDao authDao;

	@Inject
	private ContactsDao contactsDao;
	
	@Inject
	private MessageIdSequence messageIdSequence;

	@Inject
	private MessagesDao messagesDao;

	@Inject
	private HistoriesDao historiesDao;
}
