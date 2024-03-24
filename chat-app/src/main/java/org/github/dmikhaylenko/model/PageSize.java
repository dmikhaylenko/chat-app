package org.github.dmikhaylenko.model;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class PageSize {
	private final Long pageSize;
	private final long maxValue;
	private final long defaultPageSize;
	
	public PageSize(Long pageSize) {
		this(pageSize, 500L, 500L);
	}
	
	public long getPageSize() {
		return Optional.ofNullable(pageSize).map(val -> {
			if (val.compareTo(1L) < 0) {
				return 1L;
			}

			if (val.compareTo(maxValue) > 0) {
				return maxValue;
			}
			return val;
		}).orElse(defaultPageSize);
	}
	
	public PageSize defaults(long maxValue, long defaultPageSize) {
		return new PageSize(pageSize, maxValue, defaultPageSize);
	}
}
