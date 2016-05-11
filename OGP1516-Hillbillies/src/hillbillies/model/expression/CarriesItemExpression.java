package hillbillies.model.expression;
import hillbillies.model.Unit;

public class CarriesItemExpression<E extends UnitExpression> extends UnaryBooleanExpression<E> {

	public CarriesItemExpression(E e) {
		setExpression(e);
		setValue(((Unit) e.getValue()).getLog() !=null ||((Unit) e.getValue()).getBoulder() !=null);

	}
	public Boolean evaluateExpression(Unit unit){
		return (unit.getBoulder()!= null || unit.getLog()!=null);
	}

	public Boolean evaluateExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
