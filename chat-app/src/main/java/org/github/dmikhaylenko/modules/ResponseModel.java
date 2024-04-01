package org.github.dmikhaylenko.modules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.github.dmikhaylenko.i18n.I18n;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@XmlRootElement
@NoArgsConstructor
@EqualsAndHashCode
@XmlType(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
// @formatter:off
@XmlSeeAlso({
	SuccessResponse.class,
	ApplicationErrorResponse.class
})
//@formatter:on
public class ResponseModel {
	@XmlElement
	private Long responseCode;

	@XmlElement
	private String responseMessage;

	public ResponseModel(Long responseCode) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = I18n.getErrorMessage(responseCode).get();
	}
	
	
	
}
