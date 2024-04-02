package org.github.dmikhaylenko.i18n;

import java.util.Optional;
import java.util.ResourceBundle;

import org.github.dmikhaylenko.i18n.I18n.I18nResolver;

public class I18nResourceBundleResolver implements I18nResolver {
	public Optional<String> getMessage(String key) {
		return Optional.ofNullable(getBundle().getString(key));
	}

	private ResourceBundle getBundle() {
		return ResourceBundle.getBundle("i18n/messages");
	}
}
