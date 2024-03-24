package org.github.dmikhaylenko.commons.adapters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JaxbLocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
	private static final DateTimeFormatter JAXB_DT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	@Override
	public LocalDateTime unmarshal(String value) throws Exception {
		return LocalDateTime.parse(value, JAXB_DT_FORMATTER);
	}

	@Override
	public String marshal(LocalDateTime value) throws Exception {
		return value.format(JAXB_DT_FORMATTER);
	}
}
