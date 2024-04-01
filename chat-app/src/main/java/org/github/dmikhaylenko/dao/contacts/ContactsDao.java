package org.github.dmikhaylenko.dao.contacts;

public interface ContactsDao {

	boolean existsIntoContactTable(DBContact contact);

	void insertIntoContactTable(DBContact contact);

	void deleteFromContactTable(DBContact contact);

}