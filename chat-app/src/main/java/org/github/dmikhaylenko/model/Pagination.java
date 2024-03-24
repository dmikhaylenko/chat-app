package org.github.dmikhaylenko.model;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Pagination {
	private final PageNumber pageNumber;
	private final PageSize pageSize;

	public static Pagination of(Long pageNumber, Long pageSize) {
		return new Pagination(new PageNumber(pageNumber), new PageSize(pageSize));
	}

	public long getPageNumber() {
		return pageNumber.getPageNumber();
	}

	public long getPageSize() {
		return pageSize.getPageSize();
	}

	public long getOffset() {
		return (getPageNumber() - 1) * getPageSize();
	}

	
	public Pagination defaults(long defaultPageNumber, long maxPageSize, long defaultPageSize) {
		return new Pagination(pageNumber.defaults(defaultPageNumber), pageSize.defaults(maxPageSize, defaultPageSize));
	}
	
	public Pagination pageSizeDefaults(long maxPageSize, long defaultPageSize) {
		return new Pagination(pageNumber, pageSize.defaults(maxPageSize, defaultPageSize));
	}

	public Pagination defaultPageNumber(DefaultPageNumberCalculator pageNumberCalculator) {
		return new Pagination(pageNumber.defaults(pageNumberCalculator.calculatePageNumber(pageNumber, pageSize)),
				pageSize);
	}

	public interface DefaultPageNumberCalculator {
		long calculatePageNumber(PageNumber pageNumber, PageSize pageSize);
	}
}
