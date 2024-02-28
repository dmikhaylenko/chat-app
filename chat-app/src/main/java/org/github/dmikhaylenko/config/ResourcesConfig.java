package org.github.dmikhaylenko.config;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.validation.Validator;

import org.github.dmikhaylenko.utils.Resources;

@ApplicationScoped
public class ResourcesConfig {
	@Resource(lookup = "jdbc/Chat", authenticationType = AuthenticationType.CONTAINER)
	private DataSource chatDb;
	
	@Inject
	private Validator validator;
	
	public void registerResources(@Observes @Initialized(ApplicationScoped.class) Object pointless) {
		Resources.setChatDb(chatDb);
		Resources.setValidator(validator);
	}
}
