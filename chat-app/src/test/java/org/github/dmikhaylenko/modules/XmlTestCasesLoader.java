package org.github.dmikhaylenko.modules;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.bind.JAXB;

import org.github.dmikhaylenko.errors.CheckedExceptionWrapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "valueOf")
public class XmlTestCasesLoader<T, C> {
	private final Class<T> type;
	private final Function<T, List<C>> testCasesExtractor;
	private final String descriptorPath;

	public Collection<Object[]> load() {
		try (InputStream testCasesStream = type.getClassLoader().getResourceAsStream(descriptorPath)) {
			T testCases = JAXB.unmarshal(testCasesStream, type);
			return testCasesExtractor.apply(testCases).stream().map(testCase -> new Object[] { testCase })
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw CheckedExceptionWrapper.uncheckedOf(e);
		}
	}
}
