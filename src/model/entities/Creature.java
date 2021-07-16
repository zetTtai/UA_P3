package model.entities;

import model.Location;
/**
 * Clase que se encarga de clasificar a las LivingEntity como criaturas
 * Hereda de LivingEntity
 * @author rbm61 23900664 F
 *
 */
public abstract class Creature extends LivingEntity {
	/**
	 * Construcor de Creature
	 * @param loc Location en la que se genera la criatura
	 * @param health Vida con la que se genera
	 */
	public Creature(Location loc, double health) {
		super(loc, health);
	}
}
