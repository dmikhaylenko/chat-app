package org.github.dmikhaylenko.dao.messages;

import java.util.Optional;

public interface MessagesDao {
	Optional<DBMessage> findById(Long id);

	void insertIntoMessageTable(DBMessage message);

	void updateIntoMessageTable(DBMessage message);

	void deleteFromMessageTable(Long id);
}