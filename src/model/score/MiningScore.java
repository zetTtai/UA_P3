package model.score;

import model.Block;
/**
 * Clase que se encarga de gestionar las puntuaciones relacionadas con la minería
 * @author rbm61 23900664 F
 *
 */
public class MiningScore extends Score<Block>{
	/**
	 * Constructor de MiningScore
	 * @param namePlayer Nombre del jugador
	 */
	public MiningScore(String namePlayer) {
		super(namePlayer);
	}
	/**
	 * Método que compara dos puntuaciones de minería
	 * @param other Score de minería a comparar con el cultural
	 * @return Devuelve 0, positivo o negativo dependiendo del resultado de la comparación
	 */
	public int compareTo(Score<Block> other) {
		int compare=0;
		
		if(score < other.getScoring())
			compare= 1;
		else if(score != other.getScoring())
			compare= -1;
		
		return compare;
	}
	/**
	 * Método que se encarga de acumular la puntuación de minería
	 * @param block Variable Block con todos los datos para aumentar el score
	 */
	@Override
	public void score(Block block) {
		this.score += block.getType().getValue();
	}

}
