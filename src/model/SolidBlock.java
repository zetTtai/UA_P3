package model;

import model.exceptions.*;
/**
 * Método que se encarga de gestionar los métodos de solidBlock
 * Hereda de Block
 * Es un bloque que no se puede atravesar
 * @author rbm61 23900664F
 *
 */
public class SolidBlock extends Block{
	/**
	 * Variable de tipo ItemStack que guarda los drops del bloque
	 */
	private ItemStack drops;
	/**
	 * Constructor de SolidBlock
	 * @param type Material con el que se va a crear el bloque
	 * @throws WrongMaterialException Excepción que lanza el constructor sobrecargado[super(type)] cuando es un material erróneo
	 */
	public SolidBlock(Material type) throws WrongMaterialException {
		super(type);//Constructor de Block
		if(type.isLiquid() != true) {
			drops= null;
		}
		else {
			throw new WrongMaterialException(type);
		}
	}
	/**
	 * Constructor de copia
	 * @param sb Variable SolidBlock con toda la información que queremos copiar
	 * @throws WrongMaterialException Excepción que lanza el constructor sobrecargado[super(type)] cuando es un material erróneo
	 */
	protected SolidBlock(SolidBlock sb) throws WrongMaterialException {
		super(sb.getType());
		this.drops= sb.getDrops();
	}
	/**
	 * Método que se encarga de comprobar si el bloque se rompe o no
	 * @param dmg Daño que se le realiza al bloque
	 * @return Devuelve true o false y el bloque ha llegado a romperse
	 */
	public boolean breaks(double dmg) {
		
		boolean breaker= false;
		if(dmg >= this.getType().getValue()) {
			breaker= true;
		}
		return breaker;
	}
	/**
	 * Método abstracto que se gestiona en LiquidBlock, pero se declara en Block
	 * @return Devuelve el bloque actual
	 */
	@Override
	public Block clone() {
		SolidBlock sb= null;
		try {
			sb = new SolidBlock(this);
		} catch (WrongMaterialException e) {
			e.printStackTrace();
		}
		return sb;
	}
	/**
	 * Devuelve drops
	 * @return Devuelve el numero de items del bloque
	 */
	public ItemStack getDrops() {
			return drops;
	}
	/**
	 * Se encarga de colocar drops
	 * @param type Material variable
	 * @param amount Int variable
	 * @throws StackSizeException Excepcion que lanza cuando no se puede crear el item
	 */
	public void setDrops(Material type, int amount) throws StackSizeException {//No modifica el type
		ItemStack drops = new ItemStack(type, amount);//Creamos un nuevo ItemStack

		if(super.getType().isBlock() == true) {//Sólo para bloques
			if(super.getType().getSymbol() == 'C')//Si es un chest
				this.drops= drops;
			else {
				if(drops.getAmount() != 1) { //Compruebas los amount del nuevo itemStack
					StackSizeException e= new StackSizeException();
					throw e;
				}
				else
					this.drops= drops;
			}	
		}
		else
			this.drops= drops;
	}
	/**
	 * Método que identifica la clase
	 * @return Devuelve la id
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((drops == null) ? 0 : drops.hashCode());
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
		SolidBlock other = (SolidBlock) obj;
		if (drops == null) {
			if (other.drops != null)
				return false;
		} else if (!drops.equals(other.drops))
			return false;
		return true;
	}
}
