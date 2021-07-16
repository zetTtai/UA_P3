package model.persistence;

import model.Inventory;
import model.ItemStack;
/**
 * Clase que se encarga de implementar los métodos de IInventory
 * @author rbm61 23900664 F
 */
public class InventoryAdapter implements IInventory{
	/**
	 * Variable tipo Inventory para almacenar los datos del inventario
	 */
	private Inventory inventory;
	/**
	 * Constructor de InventoryAdapter
	 * @param inventory Variable tipo Inventory con todos los datos necesarios
	 */
	public InventoryAdapter(Inventory inventory) {
		//No hay que usar el constructor de copia
		this.inventory= inventory; //new Inventory(inventory);
	}
	/**
	 * Método que se encarga de devolver un Item del inventario
	 * @param pos Posición del item dentro del inventario
	 * @return Devuelve el item de la posición pasada por parámetros 
	 */
	@Override
	public ItemStack getItem(int pos) {
		return inventory.getItem(pos);
	}
	/**
	 * Método que se encarga de devolver el tamaño del inventario
	 * @return Devuelve el tamaño del inventario
	 */
	@Override
	public int getSize() {
		return inventory.getSize();
	}
	/**
	 * Método que se encarga de devolver el item en la mano del player.
	 * @return Devuelve el item de la mano del player.
	 */
	@Override
	public ItemStack inHandItem() {
		return inventory.getItemInHand();
	}

}
