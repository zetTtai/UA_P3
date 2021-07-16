package model.exceptions;
/**
 * Clase que hereda de Exception y trata la excepción BadLocationException
 * @author rbm61 23900664 F
 *
 */
public class BadLocationException extends Exception {

	/**
	 * Static de tipo Long para la versión de la excepción
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Excepción que lanza
	 * @param message Mensaje que lanza para informars
	 */
	public BadLocationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
