package org.github.dmikhaylenko.utils;

import java.util.Optional;
import java.util.ResourceBundle;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageUtils {
	public Optional<String> getErrorMessage(Long errorCode) {
		return getMessage("ERROR_" + errorCode);
	}
	
	public Optional<String> getMessage(String key) {
		return Optional.ofNullable(getBundle().getString(key));
	}
	
	private ResourceBundle getBundle() {
		return ResourceBundle.getBundle("i18n/messages");
	}
}
