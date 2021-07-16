package model.score;

import model.Location;
/**
 * Clase que se encarga de gestionar la puntuación sobre la distancia recorrida por el player
 * @author rbm61 23900664 F
 *
 */
public class PlayerMovementScore extends Score<Location>{
	/**
	 * Variable que guarda la posición previa de player
	 */
	private Location previousLocation;
	/**
	 * Constructor de PlayerMovementScore
	 * @param namePlayer Nombre del jugador
	 */
	public PlayerMovementScore(String namePlayer) {
		super(namePlayer);
		this.previousLocation= null;
	}
	/**
	 * Método que se encarga de comparar dos puntuaciones de PlayerMovementScore
	 * @param other Score a comprobar con el actual
	 * @return Devuelve 0, positivo o negativo dependiendo del resultado de la comparación
	 */
	public int compareTo(Score<Location> other) {
		int pos=0;

		if(score < other.getScoring())
			pos= -1;//Porque actual es menor que other
		else if(score != other.getScoring())
			pos= 1;
		
		return pos;
	}
	/**
	 * Método que se encarga de aumentar la puntuación
	 * @param loc Location con los datos para aumentar la puntuación.
	 */
	@Override
	public void score(Location loc) {
		
		double distance= 0;
		Location aux= new Location(loc);
		if(previousLocation != null) {
			distance= previousLocation.distance(loc);
			previousLocation= aux;
		}
		else
			previousLocation= aux;
		
		score += distance;
	}
}
