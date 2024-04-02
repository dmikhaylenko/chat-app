package org.github.dmikhaylenko.dao;

import org.github.dmikhaylenko.dao.contacts.ContactsDao;
import org.github.dmikhaylenko.dao.messages.MessageIdSequence;
import org.github.dmikhaylenko.dao.messages.MessagesDao;
import org.github.dmikhaylenko.dao.messages.history.HistoriesDao;
import org.github.dmikhaylenko.dao.users.UserIdSequence;
import org.github.dmikhaylenko.dao.users.UsersDao;
import org.github.dmikhaylenko.dao.users.auth.AuthDao;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Dao {
	private DaoLocator daoLocator = null;

	public void initialize(DaoLocator daoLocator) {
		Dao.daoLocator = daoLocator;
	}
	
	public UserIdSequence userIdSequence() {
		return daoLocator.userIdSequence();
	}

	public UsersDao userDao() {
		return daoLocator.userDao();
	}

	public AuthDao authDao() {
		return daoLocator.authDao();
	}

	public ContactsDao contactsDao() {
		return daoLocator.contactsDao();
	}
	
	public MessageIdSequence messageIdSequence() {
		return daoLocator.messageIdSequence();
	}

	public MessagesDao messagesDao() {
		return daoLocator.messagesDao();
	}

	public HistoriesDao historiesDao() {
		return daoLocator.historiesDao();
	}

	public static interface DaoLocator {
		UserIdSequence userIdSequence();
		
		UsersDao userDao();

		AuthDao authDao();

		ContactsDao contactsDao();
		
		MessageIdSequence messageIdSequence();

		MessagesDao messagesDao();

		HistoriesDao historiesDao();
	}
}
