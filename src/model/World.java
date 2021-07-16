package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.util.noise.CombinedNoiseGenerator;
import org.bukkit.util.noise.OctaveGenerator;
import org.bukkit.util.noise.PerlinOctaveGenerator;

import model.entities.Animal;
import model.entities.Creature;
import model.entities.LivingEntity;
import model.entities.Monster;
import model.entities.Player;
import model.exceptions.BadLocationException;
import model.exceptions.StackSizeException;
import model.exceptions.WrongMaterialException;
/**
 * Clase que gestiona los métodos del World
 * @author rbm61
 *
 */
public class World {
	/**
	 * name of the world
	 */
    private String name;
    
    /**
     * Size of the world in the (x,z) plane.
     */
    private int worldSize;
  
    /**
     * World seed for procedural world generation
     */
	private long seed;

    /**
     * bloques de este mundo
     */
    private Map<Location, Block> blocks;	
    
	/**
	 * Items depositados en algún lugar de este mundo.
	 */ 
    private Map<Location, ItemStack> items;
    /**
     * Criaturas que existen en el mundo
     */
    private Map<Location, Creature> creatures;
    /**
     * El jugador 
     */
    private Player player;
    /**
     * String auxiliar para NeighbourHoodString
     */
    private String fila0;
    /**
     * String auxiliar para NeighbourHoodString
     */
    private String fila1;
    /**
     * String auxiliar para NeighbourHoodString
     */
    private String fila2;
    /**
     * Constructor de la práctica 1 de world
     * @param name Variable tipo String con el nombre del mundo
     */
	public World(String name) {//Práctica 1
		this.name= name;
	}
	/**
	 * Constructor de World
	 * @param seed Semilla del mundo
	 * @param size Tamaño del mundo
	 * @param name Nombre del mundo
	 */
	public World(long seed,int size, String name) {//new Práctica 2
		this.name= name;		
		setSize(size);
		setSeed(seed);
		
		blocks= new HashMap<Location, Block>();
		items= new HashMap<Location, ItemStack>();
		creatures= new HashMap<Location, Creature>();
		generate(this.seed, this.worldSize);
		this.fila0= this.fila1= this.fila2= "";
	}
	//New Práctica 4
	/**
	 * Constructor de World de la práctica 4
	 * @param seed Semilla del mundo
	 * @param size Tamaño del mundo
	 * @param name Nombre del mundo
	 * @param playerName Nombre del player que va a jugar la partida
	 */
	public World(long seed,int size, String name, String playerName) {//new Práctica 2
		this.name= name;		
		setSize(size);
		setSeed(seed);
		
		blocks= new HashMap<Location, Block>();
		items= new HashMap<Location, ItemStack>();
		creatures= new HashMap<Location, Creature>();
		generate(this.seed, this.worldSize);//Crea su propio Player
		player = new Player(playerName, this);//Lo modificamos
		this.fila0= this.fila1= this.fila2= "";
	}
	/**
	 * Usamos esta función para obtener el nombre almacenado.
	 * @return Devuelve el string llamado name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Devuelve el mensaje escrito, en este caso sólo el nombre.
	 * @return Devuelve el mensaje deseado.
	 */
	public String toString() {
		return name;
	}
	//New Practica 2
	/**
	 * Función que sirve para otorgarle un identificador a la clase.
	 * @return Devuelve el resultado
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (seed ^ (seed >>> 32));
		result = prime * result + worldSize;
		return result;
	}
	/**
	 * Función que sirve para comparar dos objetos iguales.
	 * @param obj Objeto predeterminado el cual sirve para obtener los datos que se van a comparar.
	 * @return Devuelve true o false si cumple las condiciones.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		World other = (World) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (seed != other.seed)
			return false;
		if (worldSize != other.worldSize)
			return false;
		return true;
	}
	/**
	 * Devuelve el tamaño del mundo
	 * @return Devuelve el tamaño del mundo
	 */
	public int getSize() {
		return worldSize;
	}
	/**
	 * Actualiza el tamaño del mundo
	 * @param worldSize Nuevo tamaño
	 */
	public void setSize(int worldSize) {
		if(worldSize < 0) {
			IllegalArgumentException e= new IllegalArgumentException();
			throw e;
		}
		else {
			this.worldSize= worldSize;
		}
	}
	/**
	 * Devuelve la semilla del mundo
	 * @return Devuelve seed
	 */
	public long getSeed() {
		return seed;
	}
	/**
	 * Actualiza la semilla del mundo
	 * @param seed Semilla nueva
	 */
	public void setSeed(long seed) {
		this.seed = seed;
	}
	/**
	 * Método que se encarga de devolver el bloque de una cierta posición
	 * @param loc Posición del bloque
	 * @return Devuelve el bloque o null dependiendo si existe o no bloque en esa posición
	 * @throws BadLocationException Excepción que lanza cuando la Location es incorrecta
	 */
	public Block getBlockAt(Location loc) throws BadLocationException {
		
		Block bloque= null;
		if(blocks.get(loc) != null) {//Si es null da error
			bloque= blocks.get(loc);//Copia Bloque en la posición loc
		}
		if(equals(loc.getWorld()) == false || loc == null) {
			BadLocationException e= new BadLocationException("Location: " + loc + " no es de este mundo.");
			throw e;
		}
		return bloque;
	}
	/**
	 * Método que devuelve la location modificada con la altura máxima de esa posición
	 * @param ground Location para modificar
	 * @return Devuelve la Location con la altura máxima, bloque más alto
	 * @throws BadLocationException Se lanza cuando la location no existe o no pertenece al mismo mundo
	 */
	public Location getHighestLocationAt(Location ground) throws BadLocationException {
		
		Location copy= new Location(ground);
		if(equals(ground.getWorld()) == false || ground == null) {//Mismo mundo
			
			BadLocationException e= new BadLocationException("Location: " + ground + " no es de este mundo.");
			throw e;
		}
		else {
			double highest= heightMap.get(copy.getX(), copy.getZ());
			copy.setY(highest);
		}
		return copy;
	}
	/**
	 * Método que se encarga de devolver el item de una cierta posición
	 * @param loc Posición del item
	 * @return Devuelve el item o null dependiendo si hay un item existe o no en esa posición
	 * @throws BadLocationException Se lanza cuando la location no existe o no pertenece al mismo mundo
	 */
	public ItemStack getItemsAt(Location loc) throws BadLocationException {
		
		ItemStack item= items.get(loc);//Item en la posición loc
		if(equals(loc.getWorld()) == false || loc == null) {
			item= null;
			BadLocationException e= new BadLocationException("Location: " + loc + " no es de este mundo.");
			throw e;
		}
		return item;
	}
	/**
	 * Método auxiliar que se encarga de formar las filas
	 * @param symbol Símbolo que hay que añadir
	 * @param z Posición de la fila en la que se tiene que insertar symbol
	 */
	private void stringBuilder(char symbol, int z) {
		switch(z) {
		case 0: this.fila0 += symbol;
			break;
		case 1: this.fila1 += symbol;
			break; 
		case 2: this.fila2 += symbol;
			break;
		}
	}
	/**
	 * Método que genera un string con los bloques, items o límites alrededor de cierta posición
	 * @param loc Posición central a partir de la cual se mostraran objetos, items o límetes del mundo
	 * @return Devuelve un string con los bloques, items o límites adyacentes a la location introducida
	 * @throws BadLocationException 
	 */
	public String getNeighbourhoodString(Location loc) throws BadLocationException {
		String vecinos= "";
		int x,y,z;
		int MAX= 3;
		for(y= 1; y > -2; y--) {
			Location original= new Location(loc.getWorld(), loc.getX() - 1, loc.getY() + y, loc.getZ() - 1);
			for(z=0; z < MAX; z++) {
				for(x=0; x < MAX; x++) {
					Location aux= new Location(original.getWorld(), original.getX() + x, original.getY(), original.getZ());
					if(Location.check(aux)) {//Está dentro de los límites
						if(player.getLocation().equals(aux) == true) {
							char p= player.getSymbol();
							stringBuilder(p, z);
						}
						else {
							if(getBlockAt(aux) != null) {//Hay un bloque
								if(getBlockAt(aux).getType().isLiquid()== true && getCreatureAt(aux) != null) {//Criatura en un líquido
									char creature= getCreatureAt(aux).getSymbol();
									stringBuilder(creature, z);
								}
								else if(getBlockAt(aux).getType().isLiquid()== true && getItemsAt(aux) != null) {//Item en un líquido
									char item = getItemsAt(aux).getType().getSymbol();
									if(getItemsAt(aux).getType().isBlock() != true)//Si su tipo no es de bloque
										stringBuilder(item, z);
									else {//Su tipo es un bloque
										item= Character.toUpperCase(item);//Lo ponemos mayúscula
										stringBuilder(item, z);
									}
								}
								else {
									char block = getBlockAt(aux).getType().getSymbol();
									stringBuilder(block, z);
								}
							}
							else if(getItemsAt(aux) != null) {//Hay un item
								char item = getItemsAt(aux).getType().getSymbol();
								if(getItemsAt(aux).getType().isBlock() != true)//Si su tipo no es de bloque
									stringBuilder(item, z);
								else {//Su tipo es un bloque
									item= Character.toUpperCase(item);//Lo ponemos mayúscula
									stringBuilder(item, z);
								}
							}
							else if(getCreatureAt(aux) != null) {//Hay una criatura
								char creature= getCreatureAt(aux).getSymbol();
								stringBuilder(creature, z);
							}
							else{//No hay bloque, item o criatura en esa posición
								if(aux.getX() == 0 && aux.getY() == 0 && aux.getZ() == 0) {
									try {
										addBlock(aux, BlockFactory.createBlock(Material.BEDROCK));
									} catch (WrongMaterialException e) {
										e.printStackTrace();
									}
									stringBuilder(getBlockAt(aux).getType().getSymbol(),z);
								}
								else
									stringBuilder('.', z);
							}
						}
					}
					else
						stringBuilder('X', z);
				}//X
				original.setZ(original.getZ() + 1);
			}//Z
			this.fila0 += " ";
			this.fila1 += " ";
			this.fila2 += " ";
			vecinos = this.fila0 + "\n" + this.fila1 + "\n" + this.fila2;
		}//Y
		this.fila0= this.fila1= this.fila2= "";//Reseteamos los valores para la próxima vez que llamemos al método
		return vecinos;
	}
	/**
	 * Comprueba si la posición está libre 
	 * @param loc Posición que queremos saber si está libre
	 * @return Devuelve true o false dependiendo si cumple o no las condiciones
	 * @throws BadLocationException BadLocationException Se lanza cuando la location no existe o no pertenece al mismo mundo
	 */
	public boolean isFree(Location loc) throws BadLocationException {
		boolean free= false;
		if(equals(loc.getWorld()) == false || loc == null) {
			BadLocationException e= new BadLocationException("La location " + loc + " no existe en este mundo");
			throw e;
		}
		else {
			if(player.getLocation().equals(loc))//Localización ocupada por player
				free= false;
			else {
				if(getCreatureAt(loc) != null)//new
					free= false;
				else {
					if(getBlockAt(loc) != null) {
						
						if(getBlockAt(loc).getType().isLiquid() != true)//new
							free= false;//El bloque es sólido --> No es líquido
						else
							free= true;
					}
					else
						free= true;
				}
			}
		}
		return free;
	}
	/**
	 * Elimina los items de una posición del mundo
	 * @param loc Localización en la que se encuentra el item que queremos eliminar
	 */
	public void removeItemsAt(Location loc) {
		items.remove(loc);
	}
	/**
	 * Devuelve player
	 * @return Devuelve la información del player
	 */
	public Player getPlayer() {
		return player;
	}
	//New
	/**
	 * Método que se encarga de añadir bloques al mundo
	 * @param loc Location en la que queremos colocar el bloque
	 * @param block Bloque que queremos colocar
	 * @throws BadLocationException Excepción que se la lanza cuando coincide con la location de playr, no pertenece al mundo o está fuera de los límites
	 */
	public void addBlock(Location loc, Block block) throws BadLocationException {
		//Ocupada por player, no pertenece al mundo o está fuera de los límites
		if(player.getLocation().equals(loc) == true || equals(loc.getWorld()) == false || Location.check(loc) == false) {
			throw new BadLocationException("BAD LOCATION");
		}
		else {
			if(getBlockAt(loc) != null) {//Hay un bloque en esa posición
				blocks.remove(loc);
				blocks.put(loc, block);
			}
			else if(getCreatureAt(loc) != null) {//Hay una criatura en esa posición
				killCreature(loc);
				creatures.remove(loc);
				blocks.put(loc, block);
			}
			else if(getItemsAt(loc) != null) {//Hay un objeto en esa posición
				items.remove(loc);
				blocks.put(loc, block);
			}
			else {//No hay nada
				blocks.put(loc,block);
			}
			double highest= heightMap.get(loc.getX(), loc.getZ());//Altura máxima
			if(loc.getY() > highest) {
				heightMap.set(loc.getX(), loc.getZ(), loc.getY());
			}
		}
	}
	/**
	 * Método que se encarga de añadir items al mundo
	 * @param loc Location en la que queremos añadir el item
	 * @param item Item que queremos añadir
	 * @throws BadLocationException Excepción que se la lanza cuando coincide con la location de playr, no pertenece al mundo o está fuera de los límites
	 */
	public void addItems(Location loc, ItemStack item) throws BadLocationException {
		
		if(player.getLocation().equals(loc) == true || equals(loc.getWorld()) == false || Location.check(loc) == false) {
			throw new BadLocationException("Bad Location");
		}
		else {
			if(isFree(loc) == true){//La posición está libre //TODO Se puede añadir en lava o agua?
				if(getItemsAt(loc) != null) {//Hay item en esa posición
					removeItemsAt(loc);//Se elimina
					items.put(loc, item);
				}
				else if(getBlockAt(loc) != null) {//Hay un líquido
					//blocks.remove(loc); TODO Preguntar, no se elimina el bloque líquido?
					items.put(loc, item);
				}
				else//No hay item o bloque líquido en esa posición, no hay que eliminar
					items.put(loc, item);
			}
			else {
				throw new BadLocationException("La posición está ocupada");
			}
		}
	}
	/**
	 * Método que se encarga de añadir criaturas al mundo
	 * @param creature Variable de tipo Creature con toda la información necesaria
	 * @throws BadLocationException Excepción que se la lanza cuando coincide con la location de playr, no pertenece al mundo o está fuera de los límites
	 */
	public void addCreature(Creature creature) throws BadLocationException {
		
		if(player.getLocation().equals(creature.getLocation()) == true || equals(creature.getLocation().getWorld()) == false || 
				Location.check(creature.getLocation()) == false || creature.getLocation()==null) {
			throw new BadLocationException("Bad Location");
		}
		else {
			if(isFree(creature.getLocation()) == true){//La posición está libre
				if(getItemsAt(creature.getLocation()) != null) {//Hay item en esa posición
					removeItemsAt(creature.getLocation());//Se elimina
					creatures.put(creature.getLocation(), creature);
				}
				else if(getBlockAt(creature.getLocation()) != null) {//Hay un líquido
//					blocks.remove(creature.getLocation());
					creatures.put(creature.getLocation(), creature);
				}
				else//No hay item en esa posición, no hay que eliminar
					creatures.put(creature.getLocation(), creature);
			}
			else {
				throw new BadLocationException("La posición está ocupada");
			}
		}
	}
	/**
	 * Método que se encarga de destruir bloques de una posición, no puede destruir líquidos
	 * @param loc Location dónde está el bloque que queremos destruir
	 * @throws BadLocationException Excepción que se lanza cuando la location no pertenece al mundo, no hay bloque o está en la posición Y= 0, último bloque
	 */
	public void destroyBlockAt(Location loc) throws BadLocationException {
		if(loc == null) {
			throw new BadLocationException("Location null");
		}
		else {
			if(equals(loc.getWorld()) == false) {//No pertence a este mundo
				throw new BadLocationException("No pertence a este mundo");
			}
			else {
				if(blocks.get(loc) == null){//No hay bloque
					throw new BadLocationException("No hay bloque en esa posición");
				}
				else {
					if(loc.getY() == 0) {
						throw new BadLocationException("No se puede eliminar un bloque de altura 0");
					}
					else {
						
						if(blocks.get(loc).getType().isLiquid() == true) {//Es un líquido
							//System.err.println("No puedes picar lava");
						}
						else {
							SolidBlock sb= (SolidBlock) blocks.get(loc);
							blocks.remove(loc);//Eliminamos bloque
							addItems(loc, sb.getDrops());
						}
						double highest= heightMap.get(loc.getX(), loc.getZ());//Altura máxima
						if(loc.getY() == highest) {//Eliminas el bloque de altura máxima	
								
							Location aux= new Location(loc);
							while(blocks.get(aux) == null) {//Restar de 1 en uno hasta que encuentra el primer bloque
								aux.setY(aux.getY() - 1);
							}
								
							heightMap.set(aux.getX(), aux.getZ(), aux.getY());
						}
					}
				}
			}
		}

	}
	/**
	 * Método que se encarga de obtener la criatura de una cierta posición
	 * @param loc Location en la que queremos buscar la criatura
	 * @return Devuelve la criatura de esa posición o null 
	 * @throws BadLocationException Excepción que se lanza cuando la posición es null o no pertenece a este mundo
	 */
	public Creature getCreatureAt(Location loc) throws BadLocationException {
		if(loc == null) {
			throw new BadLocationException("Loc es null");
		}
		else {
			Creature creature= creatures.get(loc);//Criatura de la posición
			if(equals(loc.getWorld()) == false) {
				creature= null;
				BadLocationException e= new BadLocationException("Location: " + loc + " no es de este mundo.");
				throw e;
			}
			return creature;
		}
	}
	/**
	 * Método que se encarga de recolectar todas las criaturas alrededor de una posición
	 * @param loc Location central desde cual queremos obtener las criaturas alrededor
	 * @return Devuelve un Collection con todas las criaturas alrededor
	 * @throws BadLocationException Excepción que se lanza cuando loc no pertence a este mundo.
	 */
	public Collection<Creature> getNearbyCreatures(Location loc) throws BadLocationException{
		
		Collection<Creature> Criaturas= new ArrayList<Creature>();
		int MAX= 3;
		
		if(equals(loc.getWorld()) == false) {
			throw new BadLocationException("No pertence a este mundo");
		}
		else {
			try {//NIVEL SUPERIOR
				if(loc.above() != null) {
					Location original= new Location(loc.getWorld(), loc.getX() - 1, loc.getY() + 1, loc.getZ() - 1);//Location Esquina superior izquierda
					for(int i=0; i < MAX; i++) {//Filas
						for(int j=0; j < MAX; j++) {//Columnas
							
							Location aux= new Location(original.getWorld(), original.getX() + j, original.getY(), original.getZ());
							//Hay una criatura en esa posición y es una posición válida
							if(getCreatureAt(aux) != null && Location.check(aux) == true) {
								Criaturas.add(getCreatureAt(aux));//Añadimos a la criatura que existe en esa posición
							}
						}
						original.setZ(original.getZ() + 1);
					}
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			try {//NIVEL INTERMEDIO
				if(loc.above() != null) {
					Location original= new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ() - 1);//Location Esquina superior izquierda
					for(int i=0; i < MAX; i++) {//Filas
						for(int j=0; j < MAX; j++) {//Columnas
							
							Location aux= new Location(original.getWorld(), original.getX() + j, original.getY(), original.getZ());
							//Hay una criatura en esa posición y es una posición válida
							if(getCreatureAt(aux) != null && Location.check(aux) == true) {
								Criaturas.add(getCreatureAt(aux));//Añadimos a la criatura que existe en esa posición
							}
						}
						original.setZ(original.getZ() + 1);
					}
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			try {//NIVEL INFERIOR
				if(loc.above() != null) {
					Location original= new Location(loc.getWorld(), loc.getX() - 1, loc.getY() - 1, loc.getZ() - 1);//Location Esquina superior izquierda
					for(int i=0; i < MAX; i++) {//Filas
						for(int j=0; j < MAX; j++) {//Columnas
							
							Location aux= new Location(original.getWorld(), original.getX() + j, original.getY(), original.getZ());
							//Hay una criatura en esa posición y es una posición válida
							if(getCreatureAt(aux) != null && Location.check(aux) == true) {
								Criaturas.add(getCreatureAt(aux));//Añadimos a la criatura que existe en esa posición
							}
						}
						original.setZ(original.getZ() + 1);
					}
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		return Criaturas;
	}
	/**
	 * Método que se encarga de eliminar la criatura de una posición
	 * @param loc Location dónde está la Criatura
	 * @throws BadLocationException Excepcion que se lanza cuando no hay criatura en esa posición o no pertence al mundo
	 */
	public void killCreature(Location loc) throws BadLocationException {//F PACMA
		if(loc == null) {
			throw new BadLocationException("Loc es null");
		}
		else {
			if(getCreatureAt(loc) == null || equals(loc.getWorld()) == false){
				throw new BadLocationException("No hay criatura en esa posición o no pertence a este mundo");
			}
			else
				creatures.remove(loc);
		}
	}
	
	/** Esta clase interna representa un mapa de alturas bidimiensional
	 * que nos servirÃ¡ para guardar la altura del terreno (coordenada 'y')
	 * en un array bidimensional, e indexarlo con valores 'x' y 'z' positivos o negativos.
	 * 
	 * la localizaciÃ³n x=0,z=0 queda en el centro del mundo. 
	 * Por ejemplo, un mundo de tamaÃ±o 51 tiene su extremo noroeste a nivel del mar en la posiciÃ³n (-25,63,-25) 
	 * y su extremo sureste, tambiÃ©n a nivel del mar, en la posiciÃ³n (25,63,25). 
	 * Para un mundo de tamaÃ±o 50, estos extremos serÃ¡n (-24,63,-24) y (25,63,25), respectivamente.
	 * 
	 * Por ejemplo, para obtener la altura del terreno en estas posiciones, invocarÃ­amos al mÃ©todo get() de esta  clase:
	 *   get(-24,24) y get(25,25)
	 * 
	 * de forma anÃ¡loga, si queremos modificar el valor 'y' almacenado, haremos
	 *   set(-24,24,70)
	 *
	 */
	class HeightMap {
		/**
		 * Variable tipo double[][] donde guardamos la altura
		 */
		double[][] heightMap;
		/**
		 * Limite positivo del mundo
		 */
    	int positiveWorldLimit; 
    	/**
		 * Limite negativo del mundo
		 */
    	int negativeWorldLimit;
    	/**
    	 * Constructor de HeighMap
    	 * @param worldsize Tamaño del mundo
    	 */
		HeightMap(int worldsize) {
			heightMap = new double[worldsize][worldsize];
			positiveWorldLimit  = worldsize/2;
			negativeWorldLimit = (worldsize % 2 == 0) ? -(positiveWorldLimit-1) : -positiveWorldLimit;
		}
		/**
		 * Obtiene la atura del  terreno en la posición (x,z)
		 * @param x coordenada 'x' entre 'positiveWorldLimit' y 'negativeWorldLimit'
		 * @param z coordenada 'z' entre 'positiveWorldLimit' y 'negativeWorldLimit'
		 * @return Devuelve la altura máxima
		 */
		double get(double x, double z) {
			return heightMap[(int)x - negativeWorldLimit][(int)z - negativeWorldLimit];
		}
		/**
		 * Actualiza la altura de la posición
		 * @param x Coordenada X
		 * @param z Coordenada Z
		 * @param y Coordenada Y
		 */
		void set(double x, double z, double y) {
			heightMap[(int)x - negativeWorldLimit][(int)z - negativeWorldLimit] = y;
		}
	}
	
	/**
	 * Coordenadas 'y' de la superficie del mundo. Se inicializa en generate() y debe actualizarse
	 * cada vez que el jugador coloca un nuevo bloque en una posiciÃ³n vacÃ­a
	 * Puedes usarlo para localizar el bloque de la superficie de tu mundo.
	 */
	private HeightMap heightMap;

	//-------------------------------------------------------------
	// ImplementaciÃ³n de World.generate() y sus mÃ©todos auxiliares:
	// fillOblateSpheroid(), floodFill() y getFloodNeighborhood()
	//-------------------------------------------------------------

	    /**
	     * Genera un mundo nuevo del tamaÃ±o size*size en el plano (x,z). Si existÃ­an elementos anteriores en el mundo,  
	     * serÃ¡n eliminados. Usando la misma semilla y el mismo tamaÃ±o podemos generar mundos iguales
	     * @param seed semilla para el algoritmo de generaciÃ³n. 
	     * @param size tamaÃ±o del mundo para las dimensiones x y z
	     */
	    private  void generate(long seed, int size) {
	    	
	    	Random rng = new Random(getSeed());

	    	blocks.clear();
	    	creatures.clear();
	    	items.clear();
	    	
	    	// Paso 1: generar nuevo mapa de alturas del terreno
	    	heightMap = new HeightMap(size);
	    	CombinedNoiseGenerator noise1 = new CombinedNoiseGenerator(this);
	    	CombinedNoiseGenerator noise2 = new CombinedNoiseGenerator(this);
	    	OctaveGenerator noise3 = new PerlinOctaveGenerator(this, 6);
	    	
	    	System.out.println("Generando superficie del mundo...");
	    	for (int x=0; x<size; x++) {
	    		for (int z=0; z<size; z++) {
	    	    	double heightLow = noise1.noise(x*1.3, z*1.3) / 6.0 - 4.0;
	    	    	double heightHigh = noise2.noise(x*1.3, z*1.3) / 5.0 + 6.0;
	    	    	double heightResult = 0.0;
	    	    	if (noise3.noise(x, z, 0.5, 2) / 8.0 > 0.0)
	    	    		heightResult = heightLow;
	    	    	else
	    	    		heightResult = Math.max(heightHigh, heightLow);
	    	    	heightResult /= 2.0;
	    	    	if (heightResult < 0.0)
	    	    		heightResult = heightResult * 8.0 / 10.0;
	    	    	heightMap.heightMap[x][z] = Math.floor(heightResult + Location.SEA_LEVEL);
	    		}
	    	}
	    	
	    	// Paso 2: generar estratos
	    	SolidBlock block = null;
	    	Location location = null;
	    	Material material = null;
	    	OctaveGenerator noise = new PerlinOctaveGenerator(this, 8);
	    	System.out.println("Generando terreno...");
	    	for (int x=0; x<size; x++) {
	    		for (int z=0; z<size; z++) {
	    	    	double dirtThickness = noise.noise(x, z, 0.5, 2.0) / 24 - 4;
	    	    	double dirtTransition = heightMap.heightMap[x][z];
	    	    	double stoneTransition = dirtTransition + dirtThickness;
	    	    	for (int y=0; y<= dirtTransition; y++) {
	    	    		if (y==0) material = Material.BEDROCK;
	    	    		else if (y <= stoneTransition) 
	    	    			material = Material.STONE;
	    	    		else // if (y <= dirtTransition)
	    	    			material = Material.DIRT;
						try {
							location = new Location(this,x+heightMap.negativeWorldLimit,y,z+heightMap.negativeWorldLimit);
							block = new SolidBlock(material);
							if (rng.nextDouble() < 0.5) // los bloques contendrÃ¡n item con un 50% de probabilidad
								block.setDrops(block.getType(), 1);
							blocks.put(location, block);
						} catch (WrongMaterialException | StackSizeException e) {
							// Should never happen
							e.printStackTrace();
						}
	    	    	}

	    		}
	    	}
	    	
	    	// Paso 3: Crear cuevas
	    	int numCuevas = size * size * 256 / 8192;
			double theta = 0.0;
			double deltaTheta = 0.0;
			double phi = 0.0;
			double deltaPhi = 0.0;

			System.out.print("Generando cuevas");
	    	for (int cueva=0; cueva<numCuevas; cueva++) {
	    		System.out.print("."); System.out.flush();
	    		Location cavePos = new Location(this,rng.nextInt(size),rng.nextInt((int)Location.UPPER_Y_VALUE), rng.nextInt(size));
	    		double caveLength = rng.nextDouble() * rng.nextDouble() * 200;
	    		//cave direction is given by two angles and corresponding rate of change in those angles,
	    		//spherical coordinates perhaps?
	    		theta = rng.nextDouble() * Math.PI * 2;
	    		deltaTheta = 0.0;
	    		phi = rng.nextDouble() * Math.PI * 2;
	    		deltaPhi = 0.0;
	    		double caveRadius = rng.nextDouble() * rng.nextDouble();

	    		for (int i=1; i <= (int)caveLength ; i++) {
	    			cavePos.setX(cavePos.getX()+ Math.sin(theta)*Math.cos(phi));
	    			cavePos.setY(cavePos.getY()+ Math.cos(theta)*Math.cos(phi));
	    			cavePos.setZ(cavePos.getZ()+ Math.sin(phi));
	    			theta += deltaTheta*0.2;
	    			deltaTheta *= 0.9;
	    			deltaTheta += rng.nextDouble();
	    			deltaTheta -= rng.nextDouble();
	    			phi /= 2.0;
	    			phi += deltaPhi/4.0;
	    			deltaPhi *= 0.75;
	    			deltaPhi += rng.nextDouble();
	    			deltaPhi -= rng.nextDouble();
	    			if (rng.nextDouble() >= 0.25) {
	    				Location centerPos = new Location(cavePos);
	    				centerPos.setX(centerPos.getX() + (rng.nextDouble()*4.0-2.0)*0.2);
	    				centerPos.setY(centerPos.getY() + (rng.nextDouble()*4.0-2.0)*0.2);
	    				centerPos.setZ(centerPos.getZ() + (rng.nextDouble()*4.0-2.0)*0.2);
	    				double radius = (Location.UPPER_Y_VALUE - centerPos.getY()) / Location.UPPER_Y_VALUE;
	    				radius = 1.2 + (radius * 3.5 + 1) * caveRadius;
	    				radius *= Math.sin(i * Math.PI / caveLength);
	    				try {
	    					fillOblateSpheroid( centerPos, radius, null);
	    				} catch (WrongMaterialException e) {
	    					// Should not occur
	    					e.printStackTrace();
	    				}
	    			}

	    		}
	    	}
	    	System.out.println();
	    	
	    	// Paso 4: crear vetas de minerales
	    	// Abundancia de cada mineral
	    	double abundance[] = new double[2];
	    	abundance[0] = 0.5; // GRANITE
	    	abundance[1] =  0.3; // OBSIDIAN
	    	int numVeins[] = new int[2];
	    	numVeins[0] = (int) (size * size * 256 * abundance[0]) / 16384; // GRANITE
	    	numVeins[1] =  (int) (size * size * 256 * abundance[1]) / 16384; // OBSIDIAN

	    	Material vein = Material.GRANITE;
	    	for (int numVein=0 ; numVein<2 ; numVein++, vein = Material.OBSIDIAN) { 
	    		System.out.print("Generando vetas de "+vein);
	    		for (int v=0; v<numVeins[numVein]; v++) {
	    			System.out.print(vein.getSymbol());
	    			Location veinPos = new Location(this,rng.nextInt(size),rng.nextInt((int)Location.UPPER_Y_VALUE), rng.nextInt(size));
	    			double veinLength = rng.nextDouble() * rng.nextDouble() * 75 * abundance[numVein];
	    			//cave direction is given by two angles and corresponding rate of change in those angles,
	    			//spherical coordinates perhaps?
	    			theta = rng.nextDouble() * Math.PI * 2;
	    			deltaTheta = 0.0;
	    			phi = rng.nextDouble() * Math.PI * 2;
	    			deltaPhi = 0.0;
	    			//double caveRadius = rng.nextDouble() * rng.nextDouble();
	    			for (int len=0; len<(int)veinLength; len++) {
	    				veinPos.setX(veinPos.getX()+ Math.sin(theta)*Math.cos(phi));
	    				veinPos.setY(veinPos.getY()+ Math.cos(theta)*Math.cos(phi));
	    				veinPos.setZ(veinPos.getZ()+ Math.sin(phi));
	    				theta += deltaTheta*0.2;
	    				deltaTheta *= 0.9;
	    				deltaTheta += rng.nextDouble();
	    				deltaTheta -= rng.nextDouble();
	    				phi /= 2.0;
	    				phi += deltaPhi/4.0;
	    				deltaPhi *= 0.9; // 0.9 for veins
	    				deltaPhi += rng.nextDouble();
	    				deltaPhi -= rng.nextDouble();
	    				double radius = abundance[numVein] * Math.sin(len * Math.PI / veinLength) + 1;

	    				try {
	    					fillOblateSpheroid(veinPos, radius, vein);
	    				} catch (WrongMaterialException ex) {
	    					// should not ocuur
	    					ex.printStackTrace();
	    				}
	    			}
	    		}
	    		System.out.println();
	    	}
	    	
	    	System.out.println();

	    	// flood-fill water     	
	    	char water= Material.WATER.getSymbol();

	    	int numWaterSources = size*size/800;
	    	
	    	System.out.print("Creando fuentes de agua subterráneas");
	    	int x = 0;
	    	int z = 0;
	    	int y = 0;
	    	for (int w=0; w<numWaterSources; w++) {
	    		System.out.print(water);
	    		x = rng.nextInt(size)+heightMap.negativeWorldLimit;
	    		z = rng.nextInt(size)+heightMap.negativeWorldLimit;
	    		y = (int)Location.SEA_LEVEL - 1 - rng.nextInt(2);
	    		try {
					floodFill(Material.WATER, new Location(this,x,y,z));
				} catch (WrongMaterialException | BadLocationException e) {
					// no debe suceder
					throw new RuntimeException(e);
				}
	    	}
	    	System.out.println();
	   
	    	System.out.print("Creando erupciones de lava");
	    	char lava = Material.LAVA.getSymbol();
	    	// flood-fill lava
	    	int numLavaSources = size*size/2000;
	    	for (int w=0; w<numLavaSources; w++) {
	    		System.out.print(lava);
	    		x = rng.nextInt(size)+heightMap.negativeWorldLimit;
	    		z = rng.nextInt(size)+heightMap.negativeWorldLimit;
	    		y = (int)((Location.SEA_LEVEL - 3) * rng.nextDouble()* rng.nextDouble());
	    		try {
					floodFill(Material.LAVA, new Location(this,x,y,z));
				} catch (WrongMaterialException  | BadLocationException e) {
					// no debe suceder
					throw new RuntimeException(e);			
				}
	    	}
	    	System.out.println();

	    	// Paso 5. crear superficie, criaturas e items
	    	// Las entidades aparecen sÃ³lo en superficie (no en cuevas, por ejemplo)

	    	OctaveGenerator onoise1 = new PerlinOctaveGenerator(this, 8);
	    	OctaveGenerator onoise2 = new PerlinOctaveGenerator(this, 8);
	    	boolean sandChance = false;
	    	double entitySpawnChance = 0.05;
	    	double itemsSpawnChance = 0.10;
	    	double foodChance = 0.8;
	    	double toolChance = 0.1;
	    	double weaponChance = 0.1;
	    	
	    	System.out.println("Generando superficie del terreno, entidades e items...");
	    	for (x=0; x<size; x++) {    		
	    		for (z=0; z<size; z++) {
	    			sandChance = onoise1.noise(x, z, 0.5, 2.0) > 8.0;
	    			y = (int)heightMap.heightMap[(int)x][(int)z];
	    			Location surface = new Location(this,x+heightMap.negativeWorldLimit,y,z+heightMap.negativeWorldLimit); // la posiciÃ³n (x,y+1,z) no estÃ¡ ocupada (es AIR)
	    			try {
		    			if (sandChance) {
		    				SolidBlock sand = new SolidBlock(Material.SAND);
		    				if (rng.nextDouble() < 0.5)
		    					sand.setDrops(Material.SAND, 1);
		    				blocks.put(surface, sand);
		    			}
		    			else {
		    				SolidBlock grass = new SolidBlock(Material.GRASS);
		    				if (rng.nextDouble() < 0.5)
		    					grass.setDrops(Material.GRASS, 1);
		    				blocks.put(surface, grass);
		    			}
	    			} catch (WrongMaterialException | StackSizeException ex) {
	    				// will never happen
	    				ex.printStackTrace();
	    			}
	    			// intenta crear una entidad en superficie
	    			try {
	    				Location aboveSurface = surface.above();
	    				
	    				if (rng.nextDouble() < entitySpawnChance) {
	    					Creature entity =null;
	    					double entityHealth = rng.nextInt((int)LivingEntity.MAX_HEALTH)+1;
	    					if (rng.nextDouble() < 0.75) // generamos Monster (75%) o Animal (25%) de las veces
	    						entity = new Monster(aboveSurface, entityHealth);
	    					else 
	    						entity = new Animal(aboveSurface, entityHealth);
	    					creatures.put(aboveSurface, entity);
	    				} else { 
	    					// si no, intentamos crear unos items de varios tipos (comida, armas, herramientas)
	    					// dentro de cofres
	    					Material itemMaterial = null;
	    					int amount = 1; // p. def. para herramientas y armas
	    					if (rng.nextDouble() < itemsSpawnChance) {
	    						double rand = rng.nextDouble();
	    						if (rand < foodChance) { // crear comida
	    							// hay cuatro tipos de item de comida, en las posiciones 8 a 11 del array 'materiales'
	    							itemMaterial = Material.getRandomItem(8, 11);
	    							amount = rng.nextInt(5)+1;
	    						}
	    						else if (rand < foodChance+toolChance)
	    							// hay dos tipos de item herramienta, en las posiciones 12 a 13 del array 'materiales'
	    							itemMaterial = Material.getRandomItem(12, 13);
	    						else
	    							// hay dos tipos de item arma, en las posiciones 14 a 15 del array 'materiales'
	    							itemMaterial = Material.getRandomItem(14, 15);
	    						
	    						items.put(aboveSurface, new ItemStack(itemMaterial, amount));
	    					}
	    				}
	    			} catch (BadLocationException | StackSizeException e) {
	    				// BadLocationException : no hay posiciones mÃ¡s arriba, ignoramos creaciÃ³n de entidad/item sin hacer nada 
	    				// StackSizeException : no se producirÃ¡
	    				throw new RuntimeException(e);    			}

	    		}
	    	}

	    	// TODO: Crear plantas
	    	    	
	    	// Generar jugador
	    	player = new Player("Steve",this);
	    	// El jugador se crea en la superficie (posiciÃ³n (0,*,0)). AsegurÃ©monos de que no hay nada mÃ¡s ahÃ­
	    	Location playerLocation = player.getLocation();
	    	creatures.remove(playerLocation);
	    	items.remove(playerLocation);
	    	
	    }
	    
	    /**
	     * Where fillOblateSpheroid() is a method which takes a central point, a radius and a material to fill to use on the block array.
	     * @param centerPos central point
	     * @param radius radius around central point
	     * @param material material to fill with
	     * @throws WrongMaterialException if 'material' is not a block material
	     */
	    private void fillOblateSpheroid(Location centerPos, double radius, Material material) throws WrongMaterialException {
	    	
					for (double x=centerPos.getX() - radius; x< centerPos.getX() + radius; x += 1.0) {					
						for (double y=centerPos.getY() - radius; y< centerPos.getY() + radius; y += 1.0) {
							for (double z=centerPos.getZ() - radius; z< centerPos.getZ() + radius; z += 1.0) {
								double dx = x - centerPos.getX();
								double dy = y - centerPos.getY();
								double dz = z - centerPos.getZ();
								
								if ((dx*dx + 2*dy*dy + dz*dz) < radius*radius) {
									// point (x,y,z) falls within level bounds ?
									// we don't need to check it, just remove or replace that location from the blocks map.
									Location loc = new Location(this,Math.floor(x+heightMap.negativeWorldLimit),Math.floor(y),Math.floor(z+heightMap.negativeWorldLimit));
									if (material==null)
										blocks.remove(loc);
									else try { //if ((Math.abs(x) < worldSize/2.0-1.0) && (Math.abs(z) < worldSize/2.0-1.0) && y>0.0 && y<=Location.UPPER_Y_VALUE)
										SolidBlock veinBlock = new SolidBlock(material);
										// los bloques de veta siempre contienen material
										veinBlock.setDrops(material, 1);
										blocks.replace(loc, veinBlock);
									} catch  (StackSizeException ex) {
										// will never happen
										ex.printStackTrace();
									}
								}
							}
						}
					}
		}
	    /**
	     * FloodFill método
	     * @param liquid Material con el que se hace el líquido
	     * @param from Location dónde se crea
	     * @throws WrongMaterialException Se lanza cuando es un material erróneo
	     * @throws BadLocationException Se lanza cuando no es una location válida
	     */
	    private void floodFill(Material liquid, Location from) throws WrongMaterialException, BadLocationException {
	    	if (!liquid.isLiquid())
	    		throw new WrongMaterialException(liquid);
	    	if (!blocks.containsKey(from))
	    	{
	    		blocks.put(from, BlockFactory.createBlock(liquid));
	    		items.remove(from);
	    		Set<Location> floodArea = getFloodNeighborhood(from);
	    		for (Location loc : floodArea) 
	    			floodFill(liquid, loc);
	    	}
	    }
	    
		/**
		 * Obtiene las posiciones adyacentes a esta que no estÃ¡n por encima y estÃ¡n libres 
		 * @param location Location donde se toma la referencia
		 * @return si esta posiciÃ³n pertenece a un mundo, devuelve sÃ³lo aquellas posiciones adyacentes vÃ¡lidas para ese mundo,  si no, devuelve todas las posiciones adyacentes
		 * @throws BadLocationException cuando la posiciÃ³n es de otro mundo
		 */
		private Set<Location> getFloodNeighborhood(Location location) throws BadLocationException {
			if (location.getWorld() !=null && location.getWorld() != this)
				throw new BadLocationException("Esta posiciÃ³n no es de este mundo");
			Set<Location> neighborhood = location.getNeighborhood();
			Iterator<Location> iter = neighborhood.iterator();
			while (iter.hasNext()) {
				Location loc = iter.next();
				try {
					if ((loc.getY() > location.getY()) || getBlockAt(loc)!=null)
						iter.remove();
				} catch (BadLocationException e) {
					throw new RuntimeException(e);
					// no sucederÃ¡
				}
			}
			return neighborhood;
		}
}