package org.github.dmikhaylenko.model;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class PageNumber {
	private final Long pageNumber;
	private final long defaultValue;
	
	public PageNumber(Long pageNumber) {
		this(pageNumber, 1L);
	}
	
	public long getPageNumber() {
		return Optional.ofNullable(pageNumber).map(val -> {
			if (val.compareTo(1L) < 0) {
				return 1L;
			}
			return val;
		}).orElse(defaultValue);
	}
	
	public PageNumber defaults(long defaultPageNumber) {
		return new PageNumber(pageNumber, defaultPageNumber);
	}
}
