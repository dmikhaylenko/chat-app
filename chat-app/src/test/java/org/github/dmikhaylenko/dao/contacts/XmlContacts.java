package org.github.dmikhaylenko.dao.contacts;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.github.dmikhaylenko.dao.DataSet;
import org.junit.Assert;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlContacts {
	@XmlElement(name = "contact")
	private List<XmlContact> contacts = new ArrayList<XmlContact>();

	public XmlContacts(DataSet<DBContact> dataSet) {
		this();
		dataSet.getData().stream().map(XmlContact::new).forEach(contacts::add);
	}

	public void loadDataSet(DataSet<DBContact> dataSet) {
		dataSet.load(contacts.stream().map(XmlContact::createDBContact));
	}

	public void checkStateConsistence(XmlContacts expected) {
		Assert.assertTrue(contacts.containsAll(expected.getContacts()));
		Assert.assertTrue(expected.getContacts().containsAll(contacts));
	}
}
