package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.exceptions.StackSizeException;

public class LocationInventoryWorldMaterial_PreP4Test {
	World w;
	static ItemStack isApple, isGrass;
	
	@BeforeClass
	public static void  setUpBeforeClass() throws Exception {
		isApple= new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE);
		isGrass = new ItemStack(Material.GRASS, 5);
	}

	@Before
	public void setUp() throws Exception {
		w =  new World(5, 20, "World 10x10","Raul");
	}

	//TESTS P4 PARA Location
	/* Comprueba el método location1.compareTo(location2) en el que location1.x y location2.x son
	 * distintos. Analiza que cuando location1.x>location2.x devuelve un valor >0 y si es
	 * location1.x<location2.x devuelve un valor <0
	 */
	@Test
	public void testCompareToDifferentX() {
		Location loc1 = new Location(w, 7, 5, -3);
		Location loc2 = new Location(w, 6.9, 7, -2);
		assertTrue (loc1.compareTo(loc2)>0);
		assertTrue (loc2.compareTo(loc1)<0);
	}

	/* Comprueba el método location1.compareTo(location2) en el que las 'x' son
	 * iguales pero las 'y' distintas . Analiza que cuando location1.y>location2.y 
	 * devuelve un valor >0 y si es  location1.y<location2.y devuelve un valor <0
	 */
	//TODO
	@Test
	public void testCompareToSameXDifferentY() {
		Location loc1= new Location(w,5,5,4);
		Location loc2= new Location(w,5,10,1);
		Location loc3= new Location(w,5,0,8);
		
		assertTrue(loc1.compareTo(loc3) > 0); //Positivo porque loc1 tiene una Y mayor(5) > 0
		assertTrue(loc1.compareTo(loc2) < 0); //Negativo porque loc1 tiene una Y menor(5) < 10
	}
	
	/* Comprueba el método location1.compareTo(location2) en el que las 'x' y las 'y' son
	 * iguales pero las 'z' distintas . Analiza que cuando location1.z>location2.z 
	 * devuelve un valor >0 y si es  location1.z<location2.z devuelve un valor <0
	 */
	//TODO
	@Test
	public void testCompareToSameXYDifferentZ() {
		
		Location loc1= new Location(w,5,60,5);
		Location loc2= new Location(w,5,60,10);
		Location loc3= new Location(w,5,60,0);
		
		assertTrue(loc1.compareTo(loc3) > 0); //Positivo porque loc1 tiene una Z mayor(5) > 0
		assertTrue(loc1.compareTo(loc2) < 0); //Negativo porque loc1 tiene una Z menor(5) < 10
	}
	
	/* Comprueba el método location1.compareTo(location2) en el que las 'x', 'y' 
	 * y 'z' son iguales. Analiza que en tal caso se devuelve siempre un valor 0
	 */
	@Test
	//TODO
	public void testCompareToSameXYZ() {
		Location loc1= new Location(w,5,5,5);
		Location loc2= new Location(w,5,5,5);
		
		assertTrue(loc1.compareTo(loc2) == 0);
		
	}
	
	//TEST P4 PARA Inventory
	/* Comprueba que el constructor copia de un inventario vacío crea otro igual vacío */
	@Test
	public void testConstructorCopiaInventory1() {
		Inventory inv1 = new Inventory();
		Inventory inv2 = new Inventory(inv1);
		assertEquals(inv1,inv2);
		inv1.addItem(isApple);
		assertNotEquals(inv1,inv2);
	}
	
	/* Crea un inventario y añadele algunos items y pon uno en su mano. Crea un inventario 
	 * nuevo copia del anterior. Comprueba que ambos son iguales
	 */
	//TODO
	@Test
	public void testConstructorCopiaInventory2() {
			Inventory inv1= new Inventory();
			
			inv1.addItem(isApple);
			inv1.addItem(isGrass);
			try {
				inv1.setItemInHand(new ItemStack(Material.IRON_SHOVEL, 1));
			} catch (StackSizeException e) {
			}
			Inventory inv2= new Inventory(inv1);
			assertEquals(inv1, inv2);
			
	}
		
	//TEST P4 PARA World
	//Comprueba que el nombre del jugador del mundo w es "Raul".
	@Test
	public void testConstructorWorld() {
		assertEquals("Raul",w.getPlayer().getName());
	}
	
	//TEST P4 PARA Material
	/* Comprueba que el material AIR está en la posición 18 de los materiales. Que es un bloque 
	 * y es líquido nada más. Que su valor es 0 y su símbolo ' ' */
	@Test
	public void testAIR() {
		Material air = Material.AIR;
		assertEquals(18,air.ordinal());
		assertEquals(18,air.ordinal());
		assertTrue(air.isBlock());
		assertTrue(air.isLiquid());
		assertFalse(air.isEdible());
		assertFalse(air.isTool());
		assertFalse(air.isWeapon());
		assertEquals(0,air.getValue(),0.01);
		assertEquals(' ',air.getSymbol());
	}
}
