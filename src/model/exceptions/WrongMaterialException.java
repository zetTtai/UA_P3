package model.exceptions;

import model.Material;
/**
 * Clase que hereda de Exception y trata la excepción WrongMaterialException
 * @author rbm61 23900664 F
 *
 */
public class WrongMaterialException extends Exception {

	/**
	 * Static de tipo Long para la versión de la excepción
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Excepción que se lanza
	 * @param m Variable tipo Material errónea
	 */
	public WrongMaterialException(Material m) {
		super("WrongMaterialException" + m);
		// TODO Auto-generated constructor stub
	}

}
