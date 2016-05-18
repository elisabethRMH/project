package hillbillies.model.expression;

import hillbillies.model.ExecutionContext;
import hillbillies.model.Unit;

public class IsAliveExpression<E extends Expression<Unit>> extends UnaryBooleanExpression<E> {

	public IsAliveExpression(E e) {
		setExpression(e);
	}

	@Override
	public Boolean evaluateExpression(ExecutionContext context) {
		setValue(!context.getExecutingUnit().isTerminated());
		return getValue();
	}

}
