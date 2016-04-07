package hillbillies.tests.facade;

import static hillbillies.tests.util.PositionAsserts.assertDoublePositionEquals;
import static hillbillies.tests.util.PositionAsserts.assertIntegerPositionEquals;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.*;

import hillbillies.model.*;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.*;


public class TestSuitePart2World {
	
	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	private static final int[][][] types = new int[10][20][30];
	
	private static World world1;
	
	private World world2;
	
	/**
	 * Set up an immutable test fixture
	 */
	@BeforeClass
	public static void setUpBeforeClass(){
		
		world1 = new World(types, new DefaultTerrainChangeListener());
	}
	
	/**
	 * Set up a mutable test fixture.
	 */
	@Before
	public void setUpBefore(){
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_TREE;
		types[1][1][2] = TYPE_WORKSHOP;
		world2 = new World(types, new DefaultTerrainChangeListener());
		
	}
	
	@Test
	public final void Constructor_LegalCase(){
		int[][][] terrainTypes = new int[10][20][30];
		terrainTypes[1][1][0] = TYPE_ROCK;

		World newWorld = new World(terrainTypes, new DefaultTerrainChangeListener());
		Assert.assertEquals(10, newWorld.getxDimension());
		Assert.assertEquals(20, newWorld.getyDimension());
		Assert.assertEquals(30, newWorld.getzDimension());
		Assert.assertEquals(TerrainType.ROCK, newWorld.getTerrain(new int[] {1,1,0}));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void Constructor_IllegalTerrainTypes(){
		int[][][] terrainTypes = new int[20][20][20];
		terrainTypes[1][1][2] = 4;
		World newWorld = new World(terrainTypes, new DefaultTerrainChangeListener());
		
	}
	
	@Test
	public final void getTerrain_LegalCase(){
		Assert.assertEquals(TerrainType.TREE,world2.getTerrain(new int[] {1,1,1}));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void getTerrain_IllegalArgument(){
		world2.getTerrain(new int[] {3,1,1});
	}
	
	@Test
	public final void getTerrain_Double(){
		Assert.assertEquals(TerrainType.TREE,world2.getTerrain(new double[] {1.3,1.8,1.7}));
	}
	
	@Test
	public final void setTerrain_LegalCase(){
		world2.setTerrain(new int[] {1,1,1}, TerrainType.ROCK);
		Assert.assertEquals(TerrainType.ROCK,world2.getTerrain(new int[] {1,1,1}));
	}
	
	@Test
	public final void getTerrainTypes(){
		Assert.assertEquals(types,world1.getTerrainTypes());
	}
	
	@Test
	public final void setTerrainTypes_LegalCase(){
		int[][][] terrainTypes = new int[3][3][3];
		terrainTypes[1][1][2] = 1;
		world2.setTerrainTypes(terrainTypes);
		Assert.assertEquals(TerrainType.ROCK,world2.getTerrain(new int[] {1,1,2}));
		Assert.assertEquals(TerrainType.AIR,world2.getTerrain(new int[] {2,2,2}));

	}
	
	@Test
	public final void isCubeInWorld_TrueCase(){
		Assert.assertTrue(world1.isCubeInWorld(new int[] {9,0,24}));
	}
	
	@Test
	public final void isCubeInWorld_FalseCase(){
		Assert.assertFalse(world1.isCubeInWorld(new int[] {-1,12,24}));
		Assert.assertFalse(world1.isCubeInWorld(new int[] {10,19,24}));

	}
	
	@Test
	public final void spawnUnit(){
		Unit unit = world2.spawnUnit(false);
		Assert.assertTrue(Unit.isValidAgility(unit.getAgility()));
		Assert.assertTrue(Unit.isValidToughness(unit.getToughness()));
		Assert.assertTrue(Unit.isValidStrength(unit.getStrength()));
		Assert.assertTrue(unit.canHaveAsPosition(unit.getPosition()));
		Assert.assertTrue(unit.canHaveAsHitpoints(unit.getHitpoints()));
		Assert.assertTrue(unit.canHaveAsStaminaPoints(unit.getStaminaPoints()));
		Assert.assertTrue(unit.canHaveAsWeight(unit.getWeight()));
		Assert.assertTrue(Unit.isValidName(unit.getName()));
		Assert.assertTrue(unit.getAgility()<=100);
		Assert.assertFalse(unit.mustFall());
		Assert.assertTrue(unit.getWorld()==world2);
		Assert.assertTrue(world2.hasAsUnit(unit));
		//Assert.assertTrue(world2.getUnits(unit.getCubeCoordinate()).contains(unit));

		
	}
	
	public class Position {
		private int[] coords;
		
		public Position(int[] coords){
			this.coords = coords;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + Arrays.hashCode(coords);
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (!Arrays.equals(coords, other.coords))
				return false;
			return true;
		}

		private TestSuitePart2World getOuterType() {
			return TestSuitePart2World.this;
		}
		
	} 
	
	@Test
	public final void spawnUnit2(){
		Map<Position,Set<Unit>> unitsAtCubeMap = new HashMap<Position, Set<Unit>>();
		int[] coord = new int[]{1,1,1};
		Set<Unit> units = new HashSet<>();
		Position p = new Position(coord);
		unitsAtCubeMap.put(p, units);
		if(unitsAtCubeMap.containsKey(p)) {
			System.out.println(coord);
		}
	}
	
	

}