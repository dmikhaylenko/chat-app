package org.github.dmikhaylenko.dao;

import java.util.Set;
import java.util.stream.Collectors;

import org.github.dmikhaylenko.dao.contacts.ContactsDao;
import org.github.dmikhaylenko.dao.contacts.DBContact;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockContactsDao implements ContactsDao {
	private final MockDataSet<DBContact> dataSet;

	@Override
	public boolean existsIntoContactTable(DBContact contact) {
		return dataSet.contains(contact);
	}

	@Override
	public void insertIntoContactTable(DBContact contact) {
		dataSet.add(contact);
	}

	@Override
	public void deleteFromContactTable(DBContact contact) {
		dataSet.remove(contact);
	}

	public Set<Long> findAllMyContacts(Long currentUserId) {
		// @formatter:off
		return dataSet.stream()
				.filter(contact -> currentUserId.equals(contact.getWhoseId()))
				.map(DBContact::getWhoId)
				.distinct()
				.collect(Collectors.toSet());
		// @formatter:on
	}
}
