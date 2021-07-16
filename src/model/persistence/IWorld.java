package model.persistence;

import java.util.*;

import model.*;
import model.entities.*;
/**
 * Interfaz de World
 * @author rbm61 23900664 F
 *
 */
public interface IWorld {
	/**
	 * Método que se encarga de recorrer un cubo 16*16*16 y guardar un mapa de bloques
	 * @param loc Location original de la cual se empieza a recorrer el cubo
	 * @return Devuelve un NavigableMap con las location relativas y bloques de dicho cubo
	 */
	public NavigableMap<Location, Block> getMapBlock(Location loc);
	/**
	 * Método que se encarga de devolver el limite negativo del mundo
	 * @return Devuelve dicho límite
	 */
	public int getNegativeLimit();
	/**
	 * Método que devuelve el player adapter del mundo
	 * @return Devuelve una variable tipo IPlayer con toda la información.
	 */
	public IPlayer getPlayer();
	/**
	 * Método que se encarga de devolver el limite positivo del mundo
	 * @return Devuelve dicho límite
	 */
	public int getPositiveLimit();
	/**
	 * Método que se encarga de recorrer un cubo 16*16*16 y guardar una List de criaturas
	 * @param loc Posición desde la cual se empieza a recorrer el cubo
	 * @return Devuelve una List con todas las criaturas encontradas en dicho cubo
	 */
	public List<Creature> getCreatures(Location loc);
	/**
	 * Método que se encarga de de recorrer un cubo 16*16*16 y guarda un mapa con todos los items
	 * @param loc Posición desde la cual se empieza a recorrer el cubo
	 * @return Devuelve un mapa con todos los items encontrados en dicho cubo
	 */
	public Map<Location, ItemStack> getItems(Location loc);
}
