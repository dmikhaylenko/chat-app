package org.github.dmikhaylenko.modules.users;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.github.dmikhaylenko.modules.SuccessResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@XmlRootElement
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchUsersResponse")
public class SearchUsersResponse extends SuccessResponse {
	@XmlElement
	private List<FoundUser> users = new ArrayList<>();

	@XmlElement
	private Long total;
	
	public SearchUsersResponse(SearchUserQueryResult queryResult) {
		super();
		this.total = queryResult.getTotal();
		queryResult.getUsers().stream().map(FoundUser::new).forEach(this.users::add);
	}	
}
