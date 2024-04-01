package org.github.dmikhaylenko.i18n;

import java.util.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
public class I18n {
	private I18nResolver i18nResolver = null;

	public void initialize(I18nResolver i18nResolver) {
		I18n.i18nResolver = i18nResolver;
	}

	public Optional<String> getErrorMessage(Long errorCode) {
		return getMessage("ERROR_" + errorCode);
	}

	public Optional<String> getMessage(String key) {
		return i18nResolver.getMessage(key);
	}

	public static interface I18nResolver {
		Optional<String> getMessage(String key);
	}
}
