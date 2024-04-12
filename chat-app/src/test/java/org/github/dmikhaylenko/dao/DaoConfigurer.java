package org.github.dmikhaylenko.dao;

import org.github.dmikhaylenko.dao.contacts.DBContact;
import org.github.dmikhaylenko.dao.messages.DBMessage;
import org.github.dmikhaylenko.dao.users.DBUser;

public interface DaoConfigurer {
	SequenceConfigurer userIdSequenceConfigurer();

	SequenceConfigurer messageIdSequenceConfigurer();

	DataSet<DBAuth> authDataSet();

	DataSet<DBUser> usersDataSet();

	DataSet<DBContact> contactsDataSet();

	DataSet<DBMessage> messagesDataSet();

	void clear();

	void initialize();
}