package model.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import model.Block;
import model.BlockFactory;
import model.ItemStack;
import model.Location;
import model.Material;
import model.World;
import model.entities.Creature;
import model.exceptions.BadLocationException;
/**
 * Método que implementa los métodos de IWorld
 * @author rbm61 23900664 F
 *
 */
public class WorldAdapter implements IWorld{
	/**
	 * Variable tipo World para almacenar los datos del mundo
	 */
	private World world;
	/**
	 * Variable tipo IPlayer para almacenar los datos adaptados de player
	 */
	private IPlayer player;
	/**
	 * Variable global para controlar el tamaño máximo de los bucles
	 */
	private static final int CHUNK= 16;
	/**
	 * Constructor de World Adapter
	 * @param world Variable World con todos los datos que hay que almacenar
	 */
	public WorldAdapter(World world) {
		this.world= world;
		player= new PlayerAdapter(world.getPlayer());
	}
	/**
	 * Método que se encarga de recorrer un cubo 16*16*16 y guardar un mapa de bloques
	 * @param loc Location original de la cual se empieza a recorrer el cubo
	 * @return Devuelve un NavigableMap con las location relativas y bloques de dicho cubo
	 */
	@Override
	public NavigableMap<Location, Block> getMapBlock(Location loc) {
		
		NavigableMap<Location, Block> blocks= new TreeMap<Location, Block>();
		Location original= new Location(loc);
		int z;//Necesitamos que sea declarada fuera para poder reiniciar el valor de Z
		
		for(int y= 0; y < CHUNK; y++) {//Altura
			for(z= 0; z < CHUNK; z++) {//Filas
				for(int x= 0; x < CHUNK; x++) {//Columnas
					Location aux= new Location(world, original.getX() + x, original.getY(), original.getZ());
					Location relative= new Location(world, x, y, z);//Location de forma relativa
					if(Location.check(aux)== false) {//Fuera de los límites
						blocks.put(relative, null);
					}
					else {//Dentro de los límites
						try {
							if(world.getBlockAt(aux) != null) {//Hay un bloque
								blocks.put(relative,world.getBlockAt(aux));
							}
							else {//No hay bloque
								blocks.put(relative, BlockFactory.createBlock(Material.AIR));//Creamos un bloque de aire
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				original.setZ(original.getZ() + 1);//Miramos la siguiente fila
			}
			original.setY(original.getY() + 1);//Aumentamos la altura	
			original.setZ(original.getZ() - z);//Reiniciamos el valor de la coordenada Z
		}
		return blocks;
	}
	/**
	 * Método que se encarga de devolver el limite negativo del mundo
	 * @return Devuelve dicho límite
	 */
	@Override
	public int getNegativeLimit() {
		int min;
		
		if(world.getSize()%2 == 0)
			min= ((world.getSize()/2) - 1)*-1;
		else
			min= ((world.getSize()/2))*-1;
		
		return min;
	}
	/**
	 * Método que devuelve el player adapter del mundo
	 * @return Devuelve una variable tipo IPlayer con toda la información.
	 */
	@Override
	public IPlayer getPlayer() {
		return player;
	}
	/**
	 * Método que se encarga de devolver el limite positivo del mundo
	 * @return Devuelve dicho límite
	 */
	@Override
	public int getPositiveLimit() {
		return world.getSize()/2;
	}
	/**
	 * Método que se encarga de recorrer un cubo 16*16*16 y guardar una List de criaturas
	 * @param loc Posición desde la cual se empieza a recorrer el cubo
	 * @return Devuelve una List con todas las criaturas encontradas en dicho cubo
	 */
	@Override
	public List<Creature> getCreatures(Location loc) {
		List<Creature> creatures= new ArrayList<Creature>();
		Location original= new Location(loc);//IMPORTANTE
		int z;//Necesitamos que sea declarada fuera para poder reiniciar el valor de Z
		
		for(int y= 0; y < CHUNK; y++) {//Altura
			for(z= 0; z < CHUNK; z++) {//Filas
				for(int x= 0; x < CHUNK; x++) {//Columnas
					Location aux= new Location(world, original.getX() + x, original.getY(), original.getZ());
					try {
						if(world.getCreatureAt(aux) != null) {//Hay una criatura
							creatures.add(world.getCreatureAt(aux));
						}
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
				original.setZ(original.getZ() + 1);//Miramos la siguiente fila
			}
			original.setY(original.getY() + 1);//Aumentamos la altura	
			original.setZ(original.getZ() - z);//Reiniciamos el valor de la coordenada Z
		}
		return creatures; 
	}
	/**
	 * Método que se encarga de de recorrer un cubo 16*16*16 y guarda un mapa con todos los items
	 * @param loc Posición desde la cual se empieza a recorrer el cubo
	 * @return Devuelve un mapa con todos los items encontrados en dicho cubo
	 */
	@Override
	public Map<Location, ItemStack> getItems(Location loc) {
		Map<Location, ItemStack> items= new HashMap<Location, ItemStack>();
		Location original= new Location(loc);//IMPORTANTE
		int z;//Necesitamos que sea declarada fuera para poder reiniciar el valor de Z
		
		for(int y= 0; y < CHUNK; y++) {//Altura
			for(z= 0; z < CHUNK; z++) {//Filas
				for(int x= 0; x < CHUNK; x++) {//Columnas
					Location aux= new Location(world, original.getX() + x, original.getY(), original.getZ());
					try {
						if(world.getItemsAt(aux) != null) {//Hay un item
							items.put(aux, world.getItemsAt(aux));
						}
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
				original.setZ(original.getZ() + 1);//Miramos la siguiente fila
			}
			original.setY(original.getY() + 1);//Aumentamos la altura	
			original.setZ(original.getZ() - z);//Reiniciamos el valor de la coordenada Z
		}
		return items;
	}
}
