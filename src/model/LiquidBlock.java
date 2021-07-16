package model;

import model.exceptions.WrongMaterialException;
/**
 * Clase que se encarga de gestionar los métodos de LiquidBlock
 * Hereda de Block
 * Es un bloque pero es atravesable
 * @author rbm61 23900664 F
 *
 */
public class LiquidBlock extends Block{
	/**
	 * Variable de tipo double que guarda el daño que produce el bloque al pasar por encima de el
	 */
	private double damage;
	/**
	 * Constructor de LiquidBlock
	 * @param type Variable de tipo Material con el cual se construye el bloque
	 * @throws WrongMaterialException Excepción que lanza el constructor sobrecargado[super(type)] cuando es un material erróneo
	 */
	public LiquidBlock(Material type) throws WrongMaterialException {
		super(type);
		if(type.isLiquid() == true) {
			damage= super.getType().getValue();
		}
		else
			throw new WrongMaterialException(type);
		
	}
	/**
	 * Constructor de Copia
	 * @param lb Variable de tipo LiquidBlock la cual tiene los datos necesarios para copiar los datos
	 * @throws WrongMaterialException  Excepción que lanza el constructor sobrecargado[super(type)] cuando es un material erróneo
	 */
	protected LiquidBlock(LiquidBlock lb) throws WrongMaterialException {
		super(lb.getType());
		damage= lb.getDamage();
	}
	/**
	 * Método abstracto que se gestiona en LiquidBlock, pero se declara en Block
	 * @return Devuelve el bloque actual
	 */
	@Override
	public Block clone() {
		LiquidBlock lb= null;
		try {
			lb = new LiquidBlock(this);
		} catch (WrongMaterialException e) {
			e.printStackTrace();
		}
		return lb;
	}
	/**
	 * Devuelve el daño del bloque
	 * @return Devuelve damage
	 */
	public double getDamage() {
		return damage;
	}
	/**
	 * Identificador de la clase
	 * @return Devuelve la id de la clase
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(damage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/**
	 * Método que comprueba si dos objetos son iguales
	 * @param obj Objeto el cual queremos comparar con el actual
	 * @return Devuelve true o false dependiendo si cumple o no las condiciones
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LiquidBlock other = (LiquidBlock) obj;
		if (Double.doubleToLongBits(damage) != Double.doubleToLongBits(other.damage))
			return false;
		return true;
	}
}
