package org.github.dmikhaylenko.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchUsersResponse")
public class SearchUsersResponse extends ResponseModel {
	@XmlElement
	private List<UserModel> users;

	@XmlElement
	private Long total;
}
