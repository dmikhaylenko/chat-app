package org.github.dmikhaylenko.modules.messages.history;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.github.dmikhaylenko.modules.SuccessResponse;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShowHistoryMessages")
public class ShowHistoryResponse extends SuccessResponse {
	@XmlElement
	private Long pg;

	@XmlElement
	private Long total;

	@XmlElement
	private List<HistoryMessageData> messages = new ArrayList<>();

	public ShowHistoryResponse(ShowHistoryQueryResult result) {
		super();
		this.pg = result.getPageNumber();
		this.total = result.getTotal();
		result.getMessages().stream().map(HistoryMessageData::new).forEach(this.messages::add);
	}
}
