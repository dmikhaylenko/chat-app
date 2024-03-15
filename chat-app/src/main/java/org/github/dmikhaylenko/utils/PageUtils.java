package org.github.dmikhaylenko.utils;

import java.util.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageUtils {
	public long normalizePage(Long value) {
		return normalizePage(value, 1L);
	}
	
	public long normalizePage(Long value, Long defaultValue) {
		return Optional.ofNullable(value).map(val -> {
			if (val.compareTo(1L) < 0) {
				return 1L;
			}
			return val;
		}).orElse(defaultValue);
	}

	public long normalizePageSize(Long value, long maxValue, long defaultPageSize) {
		return Optional.ofNullable(value).map(val -> {
			if (val.compareTo(1L) < 0) {
				return 1L;
			}

			if (val.compareTo(maxValue) > 0) {
				return maxValue;
			}
			return val;
		}).orElse(defaultPageSize);

	}

	public long calculateOffset(long page, long size) {
		return (page - 1) * size;
	}
}
