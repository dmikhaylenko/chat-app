package org.github.dmikhaylenko.dao.messages;

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
public class XmlMessages {
	@XmlElement(name = "message")
	private List<XmlMessage> messages = new ArrayList<XmlMessage>();

	public XmlMessages(DataSet<DBMessage> dataSet) {
		this();
		dataSet.getData().stream().map(XmlMessage::new).forEach(messages::add);
	}

	public void loadDataSet(DataSet<DBMessage> dataSet) {
		dataSet.load(messages.stream().map(XmlMessage::createDBMessage));
	}

	public void checkStateConsistence(XmlMessages expected) {
		Assert.assertTrue(messages.containsAll(expected.getMessages()));
		Assert.assertTrue(expected.getMessages().containsAll(messages));
	}
}
