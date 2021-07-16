package model.persistence;

import model.Location;
import model.entities.Player;
/**
 * Clase que se encarga de implementar los métodos de IPlayer
 * @author rbm61 23900664 F
 *
 */
public class PlayerAdapter implements IPlayer{
	/**
	 * Variable que almacena los datos de Player
	 */
	private Player player;
	/**
	 * Variable que almacena los datos de inventory de Player
	 */
	private IInventory inventory;
	/**
	 * Constructor de PlayerAdapter
	 * @param player Variable player con todos los datos necesarios.
	 */
	public PlayerAdapter(Player player) {
		this.player= player;
		this.inventory= new InventoryAdapter(player.getInventory());
	}
	/**
	 * Devuelve la salud del Player
	 * @return Devuelve una variable double con el valor de la salud
	 */
	@Override
	public double getHealth() {
		return player.getHealth();
	}
	/**
	 * Devuelve el Inventario adaptado del Player adapter
	 * @return Devuelve una variable IInventario con los datos del InventoryAdapter
	 */
	@Override
	public IInventory getInventory() {
		return inventory;//Se supone que ya es una copia del de player
	}
	/**
	 * Método que se encarga de devolver la Location del player
	 * @return Devuelve la location del player
	 */
	@Override
	public Location getLocation() {//Copia defensiva
		Location loc= new Location(player.getLocation());
		return loc;
	}
	/**
	 * Método que devuelve el nombre del player
	 * @return Devuelve el nombre del player
	 */
	@Override
	public String getName() {
		return player.getName();
	}

}
