package org.github.dmikhaylenko.modules;

import java.util.Arrays;
import java.util.List;

import org.junit.runners.Parameterized.Parameters;

public class PaginationWithDefaultsTest extends PaginationTest {
	@Parameters
	public static List<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] {
			{null, null, 1, 10, 0, 1, 100, 10},
			{-1L, null, 1, 10, 0, 1, 100, 10},
			{null, -1L, 1, 10, 0, 1, 100, 10},
			{-1L, -1L, 1, 10, 0, 1, 100, 10},
			{0L, -1L, 1, 10, 0, 1, 100, 10},
			{10L, 50L, 10, 50, 450, 1, 100, 10},
			{10L, 1000L, 10, 100, 900, 1, 100, 10},
			{null, 1000L, 10, 100, 900, 10, 100, 10},
		});
		// @formatter:on
	}

	private final long defaultPageNumber;
	private final long maxPageSize;
	private final long defaultPageSize;

	// @formatter:off
	public PaginationWithDefaultsTest(
			Long pageNumber, 
			Long pageSize, 
			long expectedPageNumber,
			long expectedPageSize, 
			long expectedOffset, 
			long defaultPageNumber, 
			long maxPageSize,
			long defaultPageSize) {
		// @formatter:on
		super(pageNumber, pageSize, expectedPageNumber, expectedPageSize, expectedOffset);
		this.defaultPageNumber = defaultPageNumber;
		this.maxPageSize = maxPageSize;
		this.defaultPageSize = defaultPageSize;
	}

	@Override
	protected Pagination createPagination(Long pageNumber, Long pageSize) {
		return Pagination.of(pageNumber, pageSize).defaults(defaultPageNumber, maxPageSize, defaultPageSize);
	}
}