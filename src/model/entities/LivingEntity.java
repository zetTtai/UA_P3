package model.entities;

import model.Location;
/**
 * Clase abstracta que gestiona todas las criaturas vivientes del mundo
 * @author rbm61 23900664 F
 *
 */
public abstract class LivingEntity {
	/**
	 * Variable de tipo double que guarda la salud del ser vivo.
	 */
	private double health;
	/**
	 * Variable de tipo Location que guarda dónde se localiza el ser vivo.
	 */
	protected Location location;
	/**
	 * Variable estática doble que guarda la cantidad máxima de vida de un ser vivo.
	 */
	public final static double MAX_HEALTH= 20;
	/**
	 * Constructor de LivingEntity
	 * @param loc Variable location en la que se genera
	 * @param health Vida con la que se genera
	 */
	public LivingEntity(Location loc, double health) {
		
		if(health <= MAX_HEALTH) {
			this.health= health;
		}
		else if(health > MAX_HEALTH) {
			this.health= MAX_HEALTH;
		}
		this.location= loc;
	}
	/**
	 * Método que resta vida según el daño inflingido
	 * @param amount Cantidad de daño que se realiza
	 */
	public void damage(double amount) {
		this.health= health- amount;
	}
	/**
	 * Método abstracto que se declara en la superclase pero se implementa en las subclases
	 * @return Devuelve el símbolo del ser vivo
	 */
	public abstract char getSymbol();
	/**
	 * Boolean que comprueba si el jugador está muerto
	 * @return Devuelve true o false dependiendo si cumple o no las condicones
	 */
	public boolean isDead() {
		
		if(health <= 0.0) {
			return true;
		}
			return false;
	}
	/**
	 * Devuelve location
	 * @return Location actual del jugador
	 */
	public Location getLocation() {
		Location loc= new Location(location);
		return loc;
	}
	/**
	 * Devuelve la salud
	 * @return Devuelve la salud actual del jugador
	 */
	public double getHealth() {
		return health;
	}
	/**
	 * Cambia la salud del player
	 * @param health Nueva salud del player
	 */
	public void setHealth(double health) {
		if(health > MAX_HEALTH) {
			this.health= MAX_HEALTH;
		}
		else
			this.health = health;
	}
	/**
	 * Método que muestra los datos por pantalla
	 * @return Devuelve el mensaje deseado
	 */
	@Override
	public String toString() {
		return "LivingEntity [location=" + location + ", health=" + health + "]";
	}
	/**
	 * Método que le otorga una id a la clase
	 * @return Devuelve la id
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(health);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}
	/**
	 * Método que compara dos entidades
	 * @param obj Entidad que queremos comparar con la actual
	 * @return Devuelve true o false si son o no iguales
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LivingEntity other = (LivingEntity) obj;
		if (Double.doubleToLongBits(health) != Double.doubleToLongBits(other.health))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}
	
}
