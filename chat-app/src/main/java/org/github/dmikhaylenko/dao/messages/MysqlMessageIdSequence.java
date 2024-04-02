package org.github.dmikhaylenko.dao.messages;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.github.dmikhaylenko.dao.Database;
import org.github.dmikhaylenko.dao.MysqlSequence;

@ApplicationScoped
public class MysqlMessageIdSequence extends MysqlSequence implements MessageIdSequence {
	@Inject
	public MysqlMessageIdSequence(Database database) {
		super(database, "SEQ__MESSAGE_ID");
	}
}
