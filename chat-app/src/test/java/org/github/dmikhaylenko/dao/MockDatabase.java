package org.github.dmikhaylenko.dao;

import org.github.dmikhaylenko.dao.Dao.DaoLocator;
import org.github.dmikhaylenko.dao.contacts.DBContact;
import org.github.dmikhaylenko.dao.messages.DBMessage;
import org.github.dmikhaylenko.dao.messages.MockMessageIdSequence;
import org.github.dmikhaylenko.dao.users.DBUser;
import org.github.dmikhaylenko.dao.users.MockUserIdSequence;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class MockDatabase implements DaoLocator, DaoConfigurer {
	private MockDataSet<DBContact> contactsDataSet = new MockDataSet<DBContact>();
	private MockDataSet<DBMessage> messagesDataSet = new MockDataSet<DBMessage>();
	private MockDataSet<DBUser> usersDataSet = new MockDataSet<DBUser>();
	private MockDataSet<DBAuth> authDataSet = new MockDataSet<DBAuth>();

	private MockUserIdSequence userIdSequence = new MockUserIdSequence();
	private MockMessageIdSequence messageIdSequence = new MockMessageIdSequence();
	private MockUsersDao userDao = new MockUsersDao(usersDataSet);
	private MockAuthDao authDao = new MockAuthDao(authDataSet, userDao);
	private MockContactsDao contactsDao = new MockContactsDao(contactsDataSet);
	private MockMessagesDao messagesDao = new MockMessagesDao(messagesDataSet);
	private MockHistoriesDao historiesDao = new MockHistoriesDao(authDao, contactsDao, messagesDao);

	@Override
	public void initialize() {
		Dao.initialize(this);
	}

	@Override
	public void clear() {
		Dao.initialize(null);
	}

	@Override
	public SequenceConfigurer userIdSequenceConfigurer() {
		return userIdSequence;
	}

	@Override
	public SequenceConfigurer messageIdSequenceConfigurer() {
		return messageIdSequence;
	}
	
	
}
