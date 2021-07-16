package model.exceptions.score;
/**
 * Clase que se encarga de lanzar la excepción ScoreException
 * @author rbm61 23900664 F
 *
 */
public class ScoreException extends RuntimeException {

	/**
	 * Static de tipo Long para la versión de la excepción
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Excepción que lanza
	 * @param excusa Variable tipo String con una explicación del error
	 */
	public ScoreException(String excusa) {
		super();
	}
}
