package model.entities;

import model.ItemStack;
import model.Location;
import model.Material;
import model.exceptions.StackSizeException;
/**
 * Clase la cual se encarga de gestionar los métodos de animal
 * @author rbm61 23900664F
 *
 */
public class Animal extends Creature {
	/**
	 * Símbolo que representa al animal
	 */
	private static char symbol= 'L';
	/**
	 * Constructor de animal
 	 * @param loc Location en la que se genera
	 * @param health Vida con la que se genera
	 */
	public Animal(Location loc, double health) {
		super(loc, health);
	}
	/**
	 * Devuelve los drops de animal, siempre es el mismo
	 * @return Devuelve los drops del animal
	 */
	public ItemStack getDrops() {
		ItemStack drop;
		try {
			drop = new ItemStack(Material.BEEF,1);
		} catch (StackSizeException e) {
			throw new RuntimeException(e);
		}
		return drop;
	}
	/**
	 * Método abstracto declarado en LivingEntity e implementado en esta subclase
	 * @return Devuelve el símbolo
	 */
	@Override
	public char getSymbol() {
		return symbol;
	}
	/**
	 * Método que muestra los datos de Monster
	 * @return Devuelve el mensaje deseado
	 */
	@Override
	public String toString() {
		return "Animal [location=" + getLocation() + ", health=" + getHealth() + "]";
	}

}
