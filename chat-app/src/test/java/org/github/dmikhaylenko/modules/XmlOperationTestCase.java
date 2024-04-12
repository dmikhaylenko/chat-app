package org.github.dmikhaylenko.modules;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.github.dmikhaylenko.dao.XmlDatabase;
import org.github.dmikhaylenko.operations.Operation;
import org.github.dmikhaylenko.operations.OperationContext;
import org.github.dmikhaylenko.time.Time;
import org.github.dmikhaylenko.validation.Validation;
import org.github.dmikhaylenko.xml.JaxbLocalDateTimeAdapter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class XmlOperationTestCase<C extends OperationTestContext, Q, S> {
	@XmlElement
	@XmlJavaTypeAdapter(value = JaxbLocalDateTimeAdapter.class)
	private LocalDateTime currentTime;

	@XmlElement
	private XmlOperationContext operationContext = new XmlOperationContext();

	@XmlElement
	private XmlDatabase initialDatabaseState = new XmlDatabase();

	@XmlElement
	private XmlDatabase resultDatabaseState = new XmlDatabase();

	@XmlTransient
	private MockOperationContext mockOperationContext = new MockOperationContext();

	public void run(C context) {
		// setup
		setup(context);

		// Given
		Q request = createRequest();
		Operation<Q, S> operation = createOperation();
		initialDatabaseState.initDatabase(context.daoConfigurer());

		// When
		run(context, operation, mockOperationContext, request);

		// Then
		resultDatabaseState.checkDatabaseStateConsistence(context.daoConfigurer());

		// cleanup
		cleanup(context);
	}

	protected abstract Q createRequest();

	protected abstract Operation<Q, S> createOperation();

	protected abstract void run(C testContext, Operation<Q, S> operation, OperationContext context, Q request);

	protected void setup(C context) {
		Time.initialize(new MockCurrentTimeProvider(mockOperationContext, currentTime));
		Validation.initialize(context.objectValidator());
		context.daoConfigurer().initialize();
		operationContext.initContext(mockOperationContext);
	}

	protected void cleanup(C context) {
		context.daoConfigurer().clear();
		Validation.initialize(null);
		Time.reset();
	}
}
