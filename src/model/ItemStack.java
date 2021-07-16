package model;

import model.exceptions.StackSizeException;
/**
 * Método que se encarga de gestionar los métodos para crear un item del mundo
 * @author rbm61 23900664 F
 *
 */
public class ItemStack {
	/**
	 * Variable tipo Material con el tipo
	 */
	private Material type;
	/**
	 * Variable tipo int con la cantidad
	 */
	private int amount;
	/**
	 * Variable estatica con el tamaño máximo posible de stack
	 */
	public final static int MAX_STACK_SIZE= 64;
	/**
	 * Constructor de ItemStack
	 * @param type Variable con el Material
	 * @param amount Variable con la cantidad a crear
	 * @throws StackSizeException Si la cantidad no es la permitida lanza excepción
	 */
	public ItemStack(Material type, int amount) throws StackSizeException{
		this.type= type;
		setAmount(amount);//Lanza excepción ya comprueba si es negativo o supera el máximo
	}
	/**
	 * Constructor de copia
	 * @param item Variable ItemStack con toda la información necesaria
	 */
	public ItemStack(ItemStack item){
		if(item != null) {
			this.type= item.type;
			this.amount= item.amount;
		}
	}
	/**
	 * Devuelve el tipo del item
	 * @return Devuelve el material
	 */
	public Material getType(){
		return type;
	}
	/**
	 * Devuevlve la cantidad
	 * @return Devuelve el amount
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * Actualiza el amount actual
	 * @param amount Variable tipo int con el nuevo amount
	 * @throws StackSizeException Se lanza cuando el amount es negativo o ha superado el límite
	 */
	public void setAmount(int amount) throws StackSizeException {
		if(getType().isTool() == true || getType().isWeapon() == true) {
			if(amount != 1) {
				StackSizeException e= new StackSizeException();
				throw e;
			}
			else
				this.amount= amount;
		}
		else {
			if(amount < 1 || amount > MAX_STACK_SIZE){
				StackSizeException e= new StackSizeException();
				throw e;
			}
			else	
				this.amount = amount;
		}
	}
	/**
	 * Identificador de la clase
	 * @return Devuelve el id
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	/**
	 * Método que compara una clase con otra 
	 * @param obj Variable tipo Objeto con la información a comparar
	 * @return Devuelve true o false dependiendo si cumple o no las condiciones.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemStack other = (ItemStack) obj;
		if (amount != other.amount)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	/**
	 * Método que se encarga de gestionar el toString
	 * @return Devuelve el mensaje correspondiente
	 */
	@Override
	public String toString() {
		return "(" + type + "," + amount + ")";
	}
}