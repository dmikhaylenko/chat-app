package org.github.dmikhaylenko.modules;

import java.util.Arrays;
import java.util.List;

import org.junit.runners.Parameterized.Parameters;

public class PaginationWithPageSizeDefaultsTest extends PaginationTest {
	@Parameters
	public static List<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] {
			{null, null, 1, 10, 0, 100, 10},
			{-1L, null, 1, 10, 0, 100, 10},
			{null, -1L, 1, 10, 0, 100, 10},
			{null, 1000L, 1, 100, 0, 100, 10},
		});
		// @formatter:on
	}

	private final long maxPageSize;
	private final long defaultPageSize;

	// @formatter:off
	public PaginationWithPageSizeDefaultsTest(
			Long pageNumber, 
			Long pageSize, 
			long expectedPageNumber,
			long expectedPageSize, 
			long expectedOffset, 
			long maxPageSize, 
			long defaultPageSize) {
		// @formatter:on
		super(pageNumber, pageSize, expectedPageNumber, expectedPageSize, expectedOffset);
		this.maxPageSize = maxPageSize;
		this.defaultPageSize = defaultPageSize;
	}

	@Override
	protected Pagination createPagination(Long pageNumber, Long pageSize) {
		return Pagination.of(pageNumber, pageSize).pageSizeDefaults(maxPageSize, defaultPageSize);
	}
}