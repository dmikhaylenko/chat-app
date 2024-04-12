package org.github.dmikhaylenko.dao.contacts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlContact {
	@XmlElement
	private Long whoseId;

	@XmlElement
	private Long whoId;

	public XmlContact(DBContact dbContact) {
		this();
		this.whoseId = dbContact.getWhoseId();
		this.whoId = dbContact.getWhoId();
	}

	public DBContact createDBContact() {
		// @formatter:off
		return DBContact.builder()
				.whoseId(whoseId)
				.whoId(whoId)
				.build();
		// @formatter:on
	}
}
