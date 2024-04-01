package org.github.dmikhaylenko.dao.users;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.github.dmikhaylenko.dao.Database;
import org.github.dmikhaylenko.dao.MysqlSequence;

@ApplicationScoped
public class MysqlUserIdSequence extends MysqlSequence implements UserIdSequence {
	@Inject
	public MysqlUserIdSequence(Database database) {
		super(database, "SEQ__USER_ID");
	}
}
