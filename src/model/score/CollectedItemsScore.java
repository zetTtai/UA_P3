package model.score;

import model.ItemStack;
/**
 * Clase que se encarga de gestionar las puntuaciones de los items 
 * @author rbm61 23900664 F
 *
 */
public class CollectedItemsScore extends Score<ItemStack>{
	/**
	 * Constructor de CollectedItemsScore 
	 * @param namePlayer Nombre del player
	 */
	public CollectedItemsScore(String namePlayer) {
		super(namePlayer);
	}
	/**
	 * Método que compara dos score distintos 
	 * @param other Score a comparar con el actual
	 * @return Devuelve un valor positivo, negativo o 0 dependiendo del resultado de la comparación
	 */
	public int compareTo(Score<ItemStack> other) {
		int pos= 0;
		
		if(score < other.getScoring())
			pos= 1;
		else if(score != other.getScoring())
			pos= -1;
		
		return pos;
	}
	/**
	 * Método que se encarga de acumular score
	 * @param item ItemStack con todos los datos para aumentar el score
	 */
	@Override
	public void score(ItemStack item) {//T es ItemStack
		score += item.getAmount()*item.getType().getValue();
	}

}
