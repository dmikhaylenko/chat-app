package org.github.dmikhaylenko.dao.users;

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
public class XmlUsers {
	@XmlElement(name = "user")
	private List<XmlUser> users = new ArrayList<XmlUser>();

	public XmlUsers(DataSet<DBUser> dataSet) {
		this();
		dataSet.getData().stream().map(XmlUser::new).forEach(users::add);
	}

	public void loadDataSet(DataSet<DBUser> dataSet) {
		dataSet.load(users.stream().map(XmlUser::createDBUser));
	}

	public void checkStateConsistence(XmlUsers expected) {
		Assert.assertTrue(users.containsAll(expected.getUsers()));
		Assert.assertTrue(expected.getUsers().containsAll(users));
	}
}
