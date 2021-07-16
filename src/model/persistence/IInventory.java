package model.persistence;

import model.ItemStack;
/**
 * Interfaz de Inventario 
 * @author rbm61 23900664 F
 *
 */
public interface IInventory {
	/**
	 * Método que se encarga de devolver un item del inventario
	 * @param pos Posición del item en el inventario
	 * @return Devuelve el ItemStack correspondiente
	 */
	public ItemStack getItem(int pos);
	/**
	 * Método que devuelve el tamaño del inventario
	 * @return Devuelve el tamaño del inventario
	 */
	public int getSize();
	/**
	 * Método que devuelve el item que tiene en la mano
	 * @return Devuelve el item de la mano del player
	 */
	public ItemStack inHandItem();
}
