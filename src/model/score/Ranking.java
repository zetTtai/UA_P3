package model.score;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import model.exceptions.score.EmptyRankingException;
/**
 * Clase que se encarga de gestionar el ranking de los jugadores
 * @author rbm61 23900664 F
 *
 * @param <ScoreType> Adquiere el valor de la T de Score
 */
public class Ranking<ScoreType extends Score<?>>{
	/**
	 * SortedSet que guarda todas las scores y no se repiten
	 */
	private SortedSet<ScoreType> scores;
	/**
	 * Constructor de Ranking
	 */
	public Ranking() {
		scores= new TreeSet<ScoreType>();
	}
	/**
	 * Método que se encarga de añadir scores a scores
	 * @param score Variable con el score que hay que añadir
	 */
	public void addScore(ScoreType score) {
		scores.add(score);
	}
	/**
	 * Devuelve la sortedSet de Ranking
	 * @return Devuelve scores
	 */
	public SortedSet<ScoreType> getSortedRanking(){
		return scores;
	}
	/**
	 * Método que se encarga de seleccionar al ganador del ranking actual
	 * @return Devuelve el ganador
	 * @throws EmptyRankingException Excepción que se lanza cuando el ranking está vacio
	 */
	public ScoreType getWinner() throws EmptyRankingException {
		ScoreType winner= null;
		ScoreType aux= null;
		
		if(scores.isEmpty() == true) {
			throw new EmptyRankingException();
		}
		else {
			Iterator <ScoreType> iterator= scores.iterator();
			int i=0;
			while(iterator.hasNext()) {
				aux= iterator.next();//El score que leemos lo guardamos en una auxiliar
				if(i == 0)//Primera iteración
					winner = aux;
				else if(aux.getScoring() > winner.getScoring()) {//Obtenemos la score máxima
					winner= aux;
				}
				i++;
			}
		}
		return winner;
	}
}
