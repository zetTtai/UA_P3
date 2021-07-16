package model.score;

import java.util.ArrayList;
import java.util.List;

import model.entities.Player;
import model.exceptions.score.ScoreException;
/**
 * Clase que se encarga de gestionar la puntuación del jugador en relacion a su experiencia
 * @author rbm61 23900664 F
 *
 */
public class XPScore extends Score<Player>{
	/**
	 * Variable Player en la que guardamos los datos de Player
	 */
	private Player player;
	/**
	 * List en la que se guardan las distintas puntuaciones
	 */
	private List<Score<?>> scores;//Varias puntuaciones de distinto tipo
	/**
	 * Método auxiliar que se encarga de recalcular el score
	 * @return Devuelve un double con el nuevo valor del score
	 */
	private double recalculateScore() {
		double total= 0.0;
		double resultado= 0;
		
		if(scores.size() != 0) {//Hay scores asociados
			for(int i=0; i < scores.size(); i++) {
				total += scores.get(i).getScoring();
			}
			double media= total/scores.size();
			resultado= media + player.getHealth() + player.getFoodLevel();
		}
		else
			resultado= player.getHealth() + player.getFoodLevel();//Media es 0 cuando no hay scores
		return resultado;
	}
	/**
	 * Constructor de XPScore
	 * @param player Variable Player con toda la información necesaria
	 */
	public XPScore(Player player) {
		super(player.getName());
		this.player= player;
		this.scores= new ArrayList<Score<?>>(); 
	}
	/**
	 * Método que se encarga de comparar dos XPScore
	 * @param other Variable Score a comparar con la actual
	 * @return Devuelve 0, positivo o negativo dependiendo del resultado de la comparación
	 */
	public int compareTo(Score<Player> other) {
		int pos=0;
		if(getScoring() < other.getScoring())//other tiene mayor puntuación por tanto es menor que actual
			pos= 1;//actual es mayor que other
		else if(getScoring() != other.getScoring())
			pos= -1;
		
		return pos;
	}
	/**
	 * Método que recalcula el score del Player
	 * @param p Variable Player con toda la información para actualizar el score
	 */
	@Override
	public void score(Player p) {
		if(this.player.equals(p) == true) {//Son el mismo
			super.score= recalculateScore();//Recalculamos score
		}
		else
			throw new ScoreException("No son el mismo Player");
	}
	/**
	 * Método que devuelve el score actual y lo recalcula
	 * @return Devuelve el score 
	 */
	public double getScoring() {
		return super.score= recalculateScore();
	}
	/**
	 * Método que se encarga de añadir scores a la List scores, se recalcula el score
	 * @param score Score que hay que añadir
	 */
	public void addScore(Score<?> score) {
		scores.add(score);
		super.score= recalculateScore();
	}
}
