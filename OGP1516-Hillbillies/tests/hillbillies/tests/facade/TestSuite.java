package hillbillies.tests.facade;
import static hillbillies.tests.util.PositionAsserts.assertDoublePositionEquals;
import static hillbillies.tests.util.PositionAsserts.assertIntegerPositionEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Assert.*;
import ogp.framework.util.*;
import hillbillies.model.Status;
import hillbillies.model.Unit;
import hillbillies.part1.facade.IFacade;


public class TestSuite {
	
	private static Unit Aunit;
	
	private Unit Bunit;
	
	private Unit Cunit;
	
	@BeforeClass
	public static void setUpBeforeClass(){
		Aunit = new Unit("Aunit",new double[] {1.5,4,5,3.5},50,50,50,50,false,25.0,25.0,Math.PI/2);
	}
	
	@Before
	public final void setUpBefore(){
		Bunit = new Unit("Bunit",new double[] {3.5,1.5,4.5},75,25,25,75,false,25.0,25.0,Math.PI/2);
		//unit3 = new Unit();
				
	}
	
	@Test
	public final void getBaseSpeed(){
		Assert.assertEquals(1.5*(50+50)/(200.0*50/100.0), Aunit.getBaseSpeed(), Util.DEFAULT_EPSILON);
	}
	@Test
	public final void startSprinting_Effective(){
		Bunit.status = Status.MOVING;
		Bunit.startSprinting();
		Assert.assertTrue(Bunit.isSprinting);
		
	}
	
	@Test
	public final void startSprinting_NonEffective(){
		Bunit.startSprinting();
		Assert.assertFalse(Bunit.isSprinting);
	}
	
	@Test
	public final void stopSprinting(){
		Bunit.isSprinting = true;
		Bunit.stopSprinting();
		Assert.assertFalse(Bunit.isSprinting);
	}
	
	
	@Test
	public final void getCurrentSpeed_NotMoving(){
		Bunit.status = Status.DONE;
		assert(Util.fuzzyEquals(Bunit.getCurrentSpeed(), 0.0, Util.DEFAULT_EPSILON));
	}
	
	@Test
	public final void getCurrentSpeed_WalkingStraight(){
		Bunit.moveToAdjacent(0, 1, 0);
		assert(Util.fuzzyEquals(Bunit.getBaseSpeed(), Bunit.getCurrentSpeed()));
	}
	
	@Test
	public final void getCurrentSpeed_WalkingUp(){
		Bunit.moveToAdjacent(0, 0, 1);
		assert(Util.fuzzyEquals(1.2*Bunit.getBaseSpeed(), Bunit.getCurrentSpeed()));
	}
	@Test
	public final void getCurrentSpeed_WalkingDown(){
		Bunit.moveToAdjacent(0, 0, -1);
		assert(Util.fuzzyEquals(0.5*Bunit.getBaseSpeed(),Bunit.getCurrentSpeed()));
	}
	
	@Test
	public final void getCurrentSpeed_Sprinting(){
		Bunit.moveToAdjacent(1, 1, 0);
		Bunit.startSprinting();
		assert(Util.fuzzyEquals(2.0*Bunit.getBaseSpeed(),Bunit.getCurrentSpeed()));
		
	}
	
	@Test
	public void moveToAdjacent_LegalCase(){
		Bunit.moveToAdjacent(1,0,-1);
		double speed = Bunit.getCurrentSpeed();
		double distance = Math.sqrt(2);
		double time = distance / speed;
		advanceTimeFor(Bunit,time,0.05);
		Assert.assertArrayEquals(new double[] {4.5,1.5,3.5}, Bunit.getPosition(), Util.DEFAULT_EPSILON);
	}
	
	/**
	 * Helper method to advance time by some time for the given unit. (we based this on the helper method in Part1TestPartial)
	 * 
	 * @param time
	 *            The time, in seconds, to advance.
	 * @param step
	 *            The step size, in seconds, by which to advance.
	 */
	private static void advanceTimeFor(Unit unit, double time, double step) {
		int n = (int) (time / step);
		for (int i = 0; i < n; i++)
			unit.advanceTime((float)step);
		unit.advanceTime((float) (time - n * step));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void moveToAdjacent_IllegalArgument() throws IllegalArgumentException{
		
		Bunit.moveToAdjacent(0,0,2);
	}
	
	@Test(expected =IllegalArgumentException.class)
	public final void moveToAdjacent_MovingToIllegalPosition() throws IllegalArgumentException{
		Bunit.setPosition(new double[]{0.5,4.6,5.0});
		Bunit.moveToAdjacent(-1,0,1);
	}
	
	
	@Test
	public final void getCubeCentre(){
		assertDoublePositionEquals(1.5,2.5,3.5,Unit.getCubeCenter(new double[] {1.0,2.0,3.0}));
	}
	
	
	
	

}
