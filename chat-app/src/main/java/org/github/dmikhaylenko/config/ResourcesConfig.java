package org.github.dmikhaylenko.config;

import java.time.ZoneOffset;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.validation.Validator;

import org.github.dmikhaylenko.commons.DatabaseUtils;
import org.github.dmikhaylenko.commons.time.TimezoneUtils;
import org.github.dmikhaylenko.model.validation.ValidationUtils;

@ApplicationScoped
public class ResourcesConfig {
	@Resource(lookup = "jdbc/Chat", authenticationType = AuthenticationType.CONTAINER)
	private DataSource chatDb;

	@Inject
	private Validator validator;

	public void registerResources(@Observes @Initialized(ApplicationScoped.class) Object pointless) {
		DatabaseUtils.initialize(chatDb);
		ValidationUtils.initialize(validator);
		TimezoneUtils.setDefaultZoneOffset(ZoneOffset.ofHours(3));
	}
}
