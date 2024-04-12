package org.github.dmikhaylenko.modules;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RunWith(Parameterized.class)
public abstract class PaginationTest {
	private final Long pageNumber;
	private final Long pageSize;
	private final long expectedPageNumber;
	private final long expectedPageSize;
	private final long expectedOffset;

	@Test
	public void executeTestCase() {
		Pagination pagination = createPagination(pageNumber, pageSize);
		Assert.assertEquals(expectedPageNumber, pagination.getPageNumber());
		Assert.assertEquals(expectedPageSize, pagination.getPageSize());
		Assert.assertEquals(expectedOffset, pagination.getOffset());
	}

	protected abstract Pagination createPagination(Long pageNumber, Long pageSize);
}