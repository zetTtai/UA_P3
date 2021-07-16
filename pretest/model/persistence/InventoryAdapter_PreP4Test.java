package model.persistence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Inventory;
import model.ItemStack;
import model.Material;
import model.exceptions.StackSizeException;

public class InventoryAdapter_PreP4Test {
	ItemStack isApple, isGrass, isIronSword;
	
	@Before
	public void setUp() throws Exception {
		isApple= new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE);
		isGrass = new ItemStack(Material.GRASS, 5);
		isIronSword = new ItemStack(Material.IRON_SWORD, 1);
		
	}

	/* Crea un Inventory vacío y a partir de el un InventoryAdapter. 
	 * Comprueba que el InventoryAdapter tiene tamaño 0 y no tiene nada en la mano
	 */
	@Test
	public void testInventoryAdapter1() {
		Inventory inv = new Inventory();
		IInventory ia = new InventoryAdapter(inv);
		assertEquals (inv.getSize(), ia.getSize());
		assertEquals (inv.getItemInHand(), ia.inHandItem());
		assertNull (ia.inHandItem());
	}

	/* Crea un Inventory y añádele algunos items. Pon uno en la mano.
	 * Crea a partir de él un InventoryAdapter y comprueba que tienen el 
	 * mismo número de items.	
	 */
	//TODO
	@Test
	public void testGetSize1() {
		Inventory inv = new Inventory();
		inv.addItem(isApple);
		inv.setItemInHand(isIronSword);
		inv.addItem(isApple);
		IInventory ia= new InventoryAdapter(inv);
		assertEquals (inv.getSize(), ia.getSize());
		
	}

	/* Crea dos Inventory con los mismos items. 
	 * Pon el mismo item en la mano a ambos. 
	 * Crea dos InventoryAdapter a partir de los dos Inventory. 
	 * Comprueba que los dos IInventory tienen el mismo número de items.	
	 * Añade un item nuevo a uno de los Inventory y comprueba que los 
	 * IInventory ya no tienen el mismo tamaño
	 */
	//TODO
	@Test
	public void testGetSize2() {
		Inventory inv1= new Inventory();
		Inventory inv2= new Inventory();
		//-----------------------
		inv1.setItemInHand(isIronSword);
		inv1.addItem(isApple);
		inv1.addItem(isGrass);
		//-----------------------
		inv2.setItemInHand(isIronSword);
		inv2.addItem(isApple);
		inv2.addItem(isGrass);
		//-----------------------
		IInventory ia1= new InventoryAdapter(inv1);
		IInventory ia2= new InventoryAdapter(inv2);
		//-----------------------
		assertEquals(ia1.getSize(), ia2.getSize());
		inv2.addItem(isApple);//TODO COMO SE ACTUALIA IInventory???
		assertNotEquals(ia1.getSize(), ia2.getSize());
	}
	
	/* Prueba de retorno null de IInventory.getItem(int) para
	 * int<0 o int>IInventory.getItem(IInventory.getSize()) 
	 */
	//TODO
	@Test
	public void testGetItemNull() {
		Inventory inv = new Inventory();
		try {
			inv.addItem(new ItemStack(Material.APPLE,5));
		} catch (StackSizeException e) {
		}
		IInventory ia = new InventoryAdapter(inv);
		assertNull(ia.getItem(-1));
		try {
			assertEquals(new ItemStack(Material.APPLE,5),ia.getItem(0));
		} catch (StackSizeException e) {
		}
		assertNull(ia.getItem(1));
		assertNull(ia.getItem(99));
		
	}
	
	/* Crea un Inventory y añádele algunos items.
	 * Crea a partir de él un InventoryAdapter y comprueba que tienen los 
	 * mismos items en las mismas posiciones. 
	 * Añade un item nuevo al Inventory y comprueba que también lo tiene
	 * IInventory	
	 */
	//TODO
	@Test
	public void testGetItem1() {
		Inventory inv = new Inventory();
		ItemStack isApple2 = new ItemStack(isApple);
		ItemStack isGrass2 = new ItemStack(isGrass);
		ItemStack isIronSword2 = new ItemStack (isIronSword);
		ItemStack vis[]= {isApple, isGrass, isIronSword, isApple2,isGrass2, isIronSword2};
		inv.addItem(isApple);
		inv.addItem(isGrass);
		inv.addItem(isIronSword);
		inv.addItem(isApple2);
		inv.addItem(isGrass2);
		inv.addItem(isIronSword2);
		inv.setItemInHand(new ItemStack(isIronSword));
		IInventory ia = new InventoryAdapter(inv);
		int i=0;
		for (ItemStack is: vis) {
			assertEquals(is,ia.getItem(i));
			assertSame(is, ia.getItem(i));
			i++;
		}
		try {
			inv.addItem(new ItemStack(Material.IRON_SHOVEL,1));//TODO Añadimos a inventory, se actualiza IInventory?
		} catch (StackSizeException e) {
			e.printStackTrace();
		}
		assertEquals(inv.getItem(6), ia.getItem(6));
	}

	/* Crea dos Inventory con los mismos items. 
	 * Crea dos InventoryAdapter a partir de los dos Inventory. 
	 * Comprueba que los dos IInventory tienen los mismos elementos
	 * en las mismas posiciones.	
	 * Añade un item nuevo a uno de los Inventory y comprueba que su IInventory
	 * asociado tiene también el item añadido.
	 * Accede a esa posición con getItem sobre el otro IInventory y comprueba
	 * que devuelve null.
	 */
	//TODO
	@Test
	public void testGetItem2() {
		Inventory inv1= new Inventory();
		Inventory inv2= new Inventory();
		//-----------------------
		inv1.addItem(isApple);
		inv1.addItem(isGrass);
		ItemStack vis1[]= {isApple, isGrass};
		//-----------------------
		inv2.addItem(isApple);
		inv2.addItem(isGrass);
		ItemStack vis2[]= {isApple, isGrass};
		//-----------------------
		IInventory ia1= new InventoryAdapter(inv1);
		IInventory ia2= new InventoryAdapter(inv2);
		
		int i=0;
		for (ItemStack is: vis1) {
			assertEquals(is,ia1.getItem(i));
			assertSame(is, ia1.getItem(i));
			i++;
		}
		int j=0;
		for (ItemStack is: vis2) {
			assertEquals(is,ia2.getItem(j));
			assertSame(is, ia2.getItem(j));
			j++;
		}
		inv1.addItem(isIronSword);
		int newPos= inv1.getSize() - 1;
		assertEquals(inv1.getItem(newPos),ia1.getItem(newPos));
		assertNull(ia2.getItem(newPos));
		
	}
	
	/* Crea un inventario y añádele algunos items. 
	 * Crea a partir de él un InventoryAdapter y comprueba que en la mano
	 * tiene null.
	 */
	@Test
	public void testInHandItemNull() {
		Inventory inv = new Inventory();
		inv.addItem(isApple);
		inv.addItem(isGrass);
		
		InventoryAdapter ia = new InventoryAdapter(inv);
		assertNull(ia.inHandItem());
	}

	/* Crea un Inventary y añádele algunos items. Pon uno en la mano.
	 * Crea a partir de él un InventoryAdapter y comprueba que tiene en la
	 * mano el mismo item que el del Inventory
	 */
	//TODO
	@Test
	public void testInHandItem1() {
		Inventory inv= new Inventory();
		inv.addItem(isApple);
		inv.addItem(isGrass);
		inv.setItemInHand(isIronSword);
		IInventory ia= new InventoryAdapter(inv);
		assertEquals(inv.getItemInHand(), ia.inHandItem());
	}

	/* Crea dos Inventary y añádeles algunos items. Pon el mismo en la mano a ambos.
	 * Crea dos InventoryAdapter a partir de los dos Inventory. Comprueba que
	 * ambos IInventory tienen el mismo item en la mano. Cambia en uno de los Inventory el
	 * itemInHand. Comprueba que ahora los IInventory no tienen el mismo itemInHand.
	 */
	//TODO
	@Test
	public void testInHandItem2() {
		Inventory inv1= new Inventory();
		Inventory inv2= new Inventory();
		//-----------------------
		inv1.setItemInHand(isIronSword);
		inv1.addItem(isApple);
		inv1.addItem(isGrass);
		//-----------------------
		inv2.setItemInHand(isIronSword);
		inv2.addItem(isApple);
		inv2.addItem(isGrass);
		//-----------------------
		IInventory ia1= new InventoryAdapter(inv1);
		IInventory ia2= new InventoryAdapter(inv2);
		assertEquals(ia1.inHandItem(), ia2.inHandItem());
		inv1.setItemInHand(isApple);
		assertNotEquals(ia1.inHandItem(), ia2.inHandItem());
		
	}
}
