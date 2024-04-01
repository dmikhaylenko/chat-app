package org.github.dmikhaylenko.modules.contacts;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class DeleteContactRequest implements DeleteContactCommand {
	private Long contactId;
}
