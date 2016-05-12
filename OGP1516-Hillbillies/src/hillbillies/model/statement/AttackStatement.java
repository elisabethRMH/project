package hillbillies.model.statement;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.expression.UnitExpression;

public class AttackStatement<E extends UnitExpression> extends ActionStatement<E> {

	public AttackStatement(E e) {
		setExpression(e);
	}

	@Override
	public void executeStatement(List<Object>executionContext) {
		((Unit)executionContext.get(0)).attack(getExpression().getValue());
		
	}
	
//	public AttackStatement(UnitExpression unit){
//		setUnit(unit);
//	}
//	
//	public UnitExpression getUnit() {
//		return unit;
//	}
//
//	public void setUnit(UnitExpression unit) {
//		this.unit = unit;
//	}
//
//	private UnitExpression unit;

}
