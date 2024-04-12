package org.github.dmikhaylenko.dao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.dao.contacts.XmlContacts;
import org.github.dmikhaylenko.dao.messages.XmlMessages;
import org.github.dmikhaylenko.dao.users.XmlUsers;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlDatabase {
	@XmlElement
	private XmlSequences sequences = new XmlSequences();

	@XmlElement
	private XmlAuths auths = new XmlAuths();

	@XmlElement
	private XmlUsers users = new XmlUsers();

	@XmlElement
	private XmlContacts contacts = new XmlContacts();

	@XmlElement
	private XmlMessages messages = new XmlMessages();

	public XmlDatabase(DaoConfigurer daoConfigurer) {
		this();
		this.sequences = new XmlSequences(daoConfigurer);
		this.auths = new XmlAuths(daoConfigurer.authDataSet());
		this.users = new XmlUsers(daoConfigurer.usersDataSet());
		this.contacts = new XmlContacts(daoConfigurer.contactsDataSet());
		this.messages = new XmlMessages(daoConfigurer.messagesDataSet());
	}

	public void initDatabase(DaoConfigurer daoConfigurer) {
		sequences.initSequences(daoConfigurer);
		users.loadDataSet(daoConfigurer.usersDataSet());
		auths.loadDataSet(daoConfigurer.authDataSet());
		contacts.loadDataSet(daoConfigurer.contactsDataSet());
		messages.loadDataSet(daoConfigurer.messagesDataSet());
	}

	public void checkDatabaseStateConsistence(DaoConfigurer daoConfigurer) {
		XmlDatabase database = new XmlDatabase(daoConfigurer);
		sequences.checkStateConsistence(database.getSequences());
		auths.checkStateConsistence(database.getAuths());
		users.checkStateConsistence(database.getUsers());
		contacts.checkStateConsistence(database.getContacts());
		messages.checkStateConsistence(database.getMessages());
	}
}
