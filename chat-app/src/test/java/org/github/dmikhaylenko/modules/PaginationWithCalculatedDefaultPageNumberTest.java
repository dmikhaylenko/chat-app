package org.github.dmikhaylenko.modules;

import java.util.Arrays;
import java.util.List;

import org.github.dmikhaylenko.modules.Pagination.DefaultPageNumberCalculator;
import org.junit.runners.Parameterized.Parameters;

public class PaginationWithCalculatedDefaultPageNumberTest extends PaginationTest {
	private static DefaultPageNumberCalculator PAGE_NUMBER_CALCULATOR = (pageNumber,
			pageSize) -> pageNumber.getPageNumber() + 10;

	@Parameters
	public static List<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] {
			{null, null, 11, 10, 100, PAGE_NUMBER_CALCULATOR}
		});
		// @formatter:on
	}

	private final DefaultPageNumberCalculator pageNumberCalculator;

	// @formatter:off
	public PaginationWithCalculatedDefaultPageNumberTest(
			Long pageNumber, 
			Long pageSize, 
			long expectedPageNumber,
			long expectedPageSize, 
			long expectedOffset, 
			DefaultPageNumberCalculator pageNumberCalculator) {
		// @formatter:on
		super(pageNumber, pageSize, expectedPageNumber, expectedPageSize, expectedOffset);
		this.pageNumberCalculator = pageNumberCalculator;
	}

	@Override
	protected Pagination createPagination(Long pageNumber, Long pageSize) {
		return Pagination.of(pageNumber, pageSize).defaults(1, 10, 10)
				.calculateDefaultPageNumber(pageNumberCalculator);
	}
}