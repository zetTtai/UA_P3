package model.exceptions;
/**
 * Clase que hereda de Exception y trata la excepción BadInventoryPositionException
 * @author rbm61 23900664F
 *
 */
public class BadInventoryPositionException extends Exception {

	/**
	 * Static de tipo Long para la versión de la excepción
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Excepción que lanza
	 * @param pos Posición errónea
	 */
	public BadInventoryPositionException(int pos) {
		super();
	}

}
