package org.github.dmikhaylenko.config;

import java.time.ZoneOffset;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.github.dmikhaylenko.dao.Dao;
import org.github.dmikhaylenko.dao.Dao.DaoLocator;
import org.github.dmikhaylenko.i18n.I18n;
import org.github.dmikhaylenko.i18n.I18n.I18nResolver;
import org.github.dmikhaylenko.time.Timezone;
import org.github.dmikhaylenko.validation.Validation;
import org.github.dmikhaylenko.validation.Validation.ObjectValidator;

@ApplicationScoped
public class ResourcesConfig {
	@Inject
	private DaoLocator daoLocator;
	
	@Inject
	private ObjectValidator validator;
	
	@Inject
	private I18nResolver i18nResolver;

	public void registerResources(@Observes @Initialized(ApplicationScoped.class) Object pointless) {
		Dao.initialize(daoLocator);
		Validation.initialize(validator);
		I18n.initialize(i18nResolver);
		Timezone.setDefaultZoneOffset(ZoneOffset.ofHours(3));
	}
}
