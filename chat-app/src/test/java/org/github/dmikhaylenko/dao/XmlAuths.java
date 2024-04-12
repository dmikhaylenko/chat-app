package org.github.dmikhaylenko.dao;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Assert;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlAuths {
	@XmlElement(name = "auth")
	private List<XmlAuth> auths = new ArrayList<XmlAuth>();

	public XmlAuths(DataSet<DBAuth> dataSet) {
		this();
		dataSet.getData().stream().map(XmlAuth::new).forEach(auths::add);
	}

	public void loadDataSet(DataSet<DBAuth> dataSet) {
		dataSet.load(auths.stream().map(XmlAuth::createDBAuth));
	}

	public void checkStateConsistence(XmlAuths expected) {
		Assert.assertTrue(auths.containsAll(expected.getAuths()));
		Assert.assertTrue(expected.getAuths().containsAll(auths));
	}
}
