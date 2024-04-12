package org.github.dmikhaylenko.dao;

public interface SequenceConfigurer {
	void initializeBy(Long value);
	
	Long currentValue();
}