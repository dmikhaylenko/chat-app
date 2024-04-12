package org.github.dmikhaylenko.dao;

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
public class XmlSequences {
	@XmlElement(name = "user-id")
	private Long userIdSequence = 0L;

	@XmlElement(name = "message-id")
	private Long messageIdSequence = 0L;

	public XmlSequences(DaoConfigurer daoConfigurer) {
		this();
		this.userIdSequence = daoConfigurer.userIdSequenceConfigurer().currentValue();
		this.messageIdSequence = daoConfigurer.messageIdSequenceConfigurer().currentValue();
	}

	public void initSequences(DaoConfigurer daoConfigurer) {
		daoConfigurer.userIdSequenceConfigurer().initializeBy(userIdSequence);
		daoConfigurer.messageIdSequenceConfigurer().initializeBy(messageIdSequence);
	}
	
	public void checkStateConsistence(XmlSequences expectedState) {
		Assert.assertEquals(expectedState, this);
	}
}
