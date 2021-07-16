package model.entities;

import model.Location;
/**
 * Clase que se encarga de gestionar los datos de Monster
 * @author rbm61 23900664 F
 *
 */
public class Monster extends Creature{
	/**
	 * Símbolo que representa a los Monstruos
	 */
	private static char symbol= 'M';
	/**
	 * Constructor de monster
 	 * @param loc Location en la que se genera
	 * @param health Vida con la que se genera
	 */
	public Monster(Location loc, double health) {
		super(loc, health);
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
		return "Monster [location=" + getLocation() + ", health=" + getHealth() + "]";
	}
}
