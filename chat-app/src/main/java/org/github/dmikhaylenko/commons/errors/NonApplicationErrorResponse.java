package org.github.dmikhaylenko.commons.errors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.github.dmikhaylenko.model.ResponseModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@XmlRootElement
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NonApplicationErrorResponse")
public class NonApplicationErrorResponse extends ResponseModel {
	public NonApplicationErrorResponse() {
		super(-1L);
	}
}
