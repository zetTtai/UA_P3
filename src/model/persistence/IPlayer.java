package model.persistence;

import model.Location;
/**
 * Interfaz de Player
 * @author rbm61 23900664 F
 *
 */
public interface IPlayer {
	/**
	 * Devuelve la salud del Player
	 * @return Devuelve una variable double con el valor de la salud
	 */
	public double getHealth();
	/**
	 * Devuelve el Inventario adaptado del Player adapter
	 * @return Devuelve una variable IInventario con los datos del InventoryAdapter
	 */
	public IInventory getInventory();
	/**
	 * Método que se encarga de devolver la Location del player
	 * @return Devuelve la location del player
	 */
	public Location getLocation();
	/**
	 * Método que devuelve el nombre del player
	 * @return Devuelve el nombre del player
	 */
	public String getName();
}
