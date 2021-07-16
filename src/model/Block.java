package model;

import model.exceptions.*;
/**
 * Clase con la que crearemos los bloques del juego
 * @author rbm61 23900664 F
 *
 */
public abstract class Block{
	/**
	 * Variable de tipo Material que guarda el material del que está hecho el bloque
	 */
	private Material type;
	/**
	 * Constructor sobrecargado
	 * @param type Variable de tipo Material con la que construiremos el bloque
	 * @throws WrongMaterialException Excepción que lanza cuando no se puede crear un bloque con el Material type dado.
	 */
	public Block(Material type) throws WrongMaterialException {	
		if(type.isBlock() == true) {
			this.type= type;
		}
		else {
			WrongMaterialException e= new WrongMaterialException(type);
			throw e;
		}
	}
	/**
	 * Constructor de copia
	 * @param b Variable de tipo Block con toda la información que queremos copiar
	 */
	protected Block(Block b) {
		this.type= b.getType();
	}
	/**
	 * Devuelve el tipo del bloque
	 * @return Devuelve type
	 */
	public Material getType() {
		return type;
	}
	/**
	 * Método abstracto que se declara en la superclase, pero que luego se implementa en las subclases.
	 * @return Clona el Bloque
	 */
	public abstract Block clone();
	/**
	 * Método que se encarga de mostrar los datos del bloque
	 * @return Devuelve el mensaje deseado
	 */
	@Override
	public String toString() {
		return "[" + type + "]";
	}
	/**
	 * Identificador de la clase
	 * @return Devuelve la id de la clase
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (type != other.type)
			return false;
		return true;
	}

}