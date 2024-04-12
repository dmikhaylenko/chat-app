package org.github.dmikhaylenko.modules;

import java.util.Optional;

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
public class XmlOperationContext {
	@XmlElement
	private String authToken;
	
	@XmlElement
	private String timeOffset;
	
	public void initContext(OperationContextConfigurer contextConfigurer) {
		contextConfigurer.initAuthToken(Optional.ofNullable(authToken));
		contextConfigurer.initTimezone(Optional.ofNullable(timeOffset));
	}
}
