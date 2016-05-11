package hillbillies.model.expression;

import hillbillies.model.Unit;

public class ThisExpression extends UnitExpression{
	
	public ThisExpression(){
		setValue(thisUnit());
	}

	private Unit thisUnit() {
		return this.getStatement().getTask().getExecutingUnit();
	}

	
	public Unit evaluateExpression() {
		// TODO Auto-generated method stub
		return null;
	}

}
