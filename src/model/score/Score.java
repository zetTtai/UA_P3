package model.score;
/**
 * Clase abstracta que se encarga de gestionar las puntuaciones
 * @author rbm61 23900664 F
 *
 * @param <T> Parámetro que según la clase que herede adquiere un valor disntinto, siempre es un objeto
 */
abstract class Score<T> implements Comparable<Score<T>>{
	/**
	 * Variable String con el nombre del jugador
	 */
	private String name;
	/**
	 * Variable double que almacena el score del player
	 */
	protected double score;
	/**
	 * Constructor sobrecargado de la clase Score
	 * @param namePlayer Nombre del player
	 */
	public Score(String namePlayer) {
		name= namePlayer;
		score= 0.0;
	}
	/**
	 * Devuelve el nombre del player
	 * @return Nombre del player
	 */
	public String getName() {
		return name;
	}
	/**
	 * Devuelve el score actual
	 * @return Score actual
	 */
	public double getScoring() {
		return score;
	}
	/**
	 * Método abstracto para aumentar el score según el valor de T
	 * @param score Variable de tipo T que cambia según la clase que está implementada
	 */
	abstract public void score(T score);
	/**
	 * Método que devuelve un mensaje con la información de Score
	 * @return Mensaje deseado
	 */
	@Override
	public String toString() {
		return  name + ":"+ score;
	}

}
