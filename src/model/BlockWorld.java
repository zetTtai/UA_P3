package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.entities.Animal;
import model.entities.Monster;
import model.entities.Player;
import model.exceptions.BadInventoryPositionException;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;
import model.exceptions.WrongMaterialException;
import model.score.*;
/**
 * Clase con la que controlaremos las acciones del jugador en un mundo creado por nosotros
 * @author rbm61 23900664 F
 *
 */
public class BlockWorld {
	/**
	 * Única private variable de BlockWorld
	 */
	static private BlockWorld BlockWorld= null;
	/**
	 * Devuelve la instancia o crea la instancia de BlockWorld
	 * @return Devuelve la instancia
	 */
	static public BlockWorld getInstance() {
		if(BlockWorld == null) {
			BlockWorld= new BlockWorld();
		}
		return BlockWorld;
	}
	/**
	 * Relación entre world y BlockWorld
	 */
	private World world;
	//New Práctica 4
	/**
	 * Variable que almacena el score relacionado con los items
	 */
	private CollectedItemsScore itemsScore;
	/**
	 * Variable que almacena el score relacionado con la minería
	 */
	private MiningScore miningScore;
	/**
	 * Variable que almacena el score relacionado con el movimiento del player
	 */
	private PlayerMovementScore movementScore; 
	/**
	 * Constructor de BlockWorld
	 */
	private BlockWorld() {
		this.world= null;
		this.itemsScore= null;
		this.miningScore= null;
		this.movementScore= null;
	}
	/**
	 * Crea un mundo nuevo
	 * @param seed Variable long que representa la seed
	 * @param size Variable int que representa el tamaño del mundo
	 * @param name Variable string que representa el nombre del mundo
	 * @return Devuelve 
	 */
	public World createWorld(long seed, int size, String name) {
		world= new World(seed, size, name);
		return world;
	}
	//New Práctica 4
	/**
	 * Crea un mundo nuevo(Práctica 4)
	 * @param seed Variable long que representa la seed
	 * @param size Variable int que representa el tamaño del mundo
	 * @param name Variable string que representa el nombre del mundo
	 * @param playerName Nombre del jugador
	 * @return Devuelve 
	 */
	public World createWorld(long seed, int size, String name, String playerName) {
		world= new World(seed, size, name, playerName);
		itemsScore= new CollectedItemsScore(playerName);
		miningScore= new MiningScore(playerName);
		movementScore= new PlayerMovementScore(playerName);
		//TODO Tras crear el mundo crea los objetos que representan las puntuaciones.
		return world;
	}
	/**
	 * Muestra la información del player
	 * @param player Variable Player con toda la información
	 * @return Devuelve la información
	 */
	public String showPlayerInfo(Player player) {
		
		String info= "";
		info += player.toString();
		info += "\n";
		try {
			info += world.getNeighbourhoodString(player.getLocation());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		info += "\n";
		info += "Scores: [items: ";
		if(itemsScore != null)
			info += itemsScore.getScoring();
		else
			info += "NULL";
		info += ", blocks: ";
		if(miningScore != null)
			info += miningScore.getScoring();
		else
			info += "NULL";
		info += ", movements: ";
		if(movementScore != null)
			info += movementScore.getScoring();
		else
			info += "NULL";
		info += "]\n";
		return info;
	}
	/**
	 * Método que mueve al player por el mundo
	 * @param player Variable de tipo Player con toda la información
	 * @param dx Valor que aumenta o disminuye la coordenada X
	 * @param dy Valor que aumenta o disminuye la coordenada Y
	 * @param dz Valor que aumenta o disminuye la coordenada Z
	 * @throws BadLocationException Excepción que se lanza cuando la location está mal
	 * @throws EntityIsDeadException Excecpión que se lanza si el playe está muerto
	 */
	public void movePlayer(Player player, int dx, int dy, int dz) throws BadLocationException, EntityIsDeadException {
		Location loc= player.move(dx, dy, dz);
		if(loc != null){ //Se ha podido hacer el movimiento
			if(world.getItemsAt(loc) != null) {//Hay un objeto en la posición a la que se mueve player
				world.getPlayer().addItemsToInventory(world.getItemsAt(loc));
				//New Práctica 4(Antes de eliminarlo)
				itemsScore.score(world.getItemsAt(loc));
				world.removeItemsAt(loc);
			}
			if(world.getBlockAt(loc) != null){
				if(world.getBlockAt(loc).getType().isLiquid() == true) {//Es un líquido
					LiquidBlock lb= (LiquidBlock) world.getBlockAt(loc);
					player.setHealth(player.getHealth() - lb.getDamage());//El agua no resta salud, su value es 0.
				}
			}
			//New Práctica 4
			movementScore.score(loc);
		}
	}
	/**
	 * Método que selecciona un item del inventario
	 * @param player Variable de tipo Player con toda la información
	 * @param slot Posición del inventario
	 * @throws BadInventoryPositionException Excepción que se lanza cuando la posición de inventario no existe
	 */
	public void selectItem(Player player, int slot) throws BadInventoryPositionException {
		player.selectItem(slot);
	}
	/**
	 * Método que usa el item de la mano
	 * @param player Variable de tipo Player con toda la información
	 * @param times Variable int con las veces que hay que usarlo
	 * @throws EntityIsDeadException Excepción que se lanza si el Player esta muerto
	 */
	public void useItem(Player player, int times) throws EntityIsDeadException {
		double dmg= 0;
		ItemStack inHand= new ItemStack(player.useItemInHand(times));//Ya ha comsumido la comida o gastado la energía
		Location orientation= new Location(player.getOrientation());//Es la absoluta
		
		if(Location.check(orientation) != false) {//No está orientado fuera de los límites
			if(inHand.getType() != null) {//Tiene item en la mano
				if(inHand.getType().isEdible()  != true) {
					//1º) Calculamos el daño
					if(inHand.getType().isBlock() == true) {
						dmg= 0.1*times;//Daño de un bloque
					}
					else if(inHand.getType().isTool()  == true) {
						dmg= inHand.getType().getValue()*times;
					}
					else if(inHand.getType().isWeapon() == true) {
						dmg= inHand.getType().getValue()*times;
					}
					//2º) Acciones según la orientación
					try {
						if(world.getCreatureAt(orientation) != null) {//Hay una criatura
							if(world.getCreatureAt(orientation).getSymbol() == 'L') {//Es un animal
								Animal animal= (Animal) world.getCreatureAt(orientation);//DownCasting
								
								if(dmg >= animal.getHealth()) {//Oneshot
									world.killCreature(orientation);
									world.addItems(orientation, animal.getDrops());
								}
								else {//Sobrevive
									animal.setHealth(animal.getHealth() - dmg);
								}
							}
							else {//Es un monstruo
								Monster monster= (Monster) world.getCreatureAt(orientation);
								if(dmg >= monster.getHealth()) {//Oneshot
									world.killCreature(orientation);
								}
								else {//Revenge
									monster.setHealth(monster.getHealth() - dmg);
									player.setHealth(player.getHealth() - (0.5*times));
								}
							}
						}//Primero miramos si hay una criatura
						else if(world.getBlockAt(orientation) != null) {//Hay un bloque
							if(world.getBlockAt(orientation).getType().isLiquid() == false){//Es un bloque sólido
								SolidBlock sb= (SolidBlock) world.getBlockAt(orientation);
								if(sb.breaks(dmg) == true) {
									//New Práctica 4
									miningScore.score(world.getBlockAt(orientation));//Antes de eliminarlo
									world.destroyBlockAt(orientation);//Ya añade los drops al mundo
								}
							}
						}
						else {//O hay item o no hay nada
							if(world.getItemsAt(orientation) == null) {//No hay nada
								if(inHand.getType().isBlock() == true) {//Con bloque en la mano
									Block block= null;
									try {
										block = BlockFactory.createBlock(inHand.getType());//Creamos un bloque del tipo del que tenemos en la mano
									} catch (WrongMaterialException e) {
										throw new RuntimeException(e);
									}
									world.addBlock(orientation, block);
								}	
							}
						}
					} catch (BadLocationException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}
	/**
	 * Método que se encarga de cambiar la orientación del Player.
	 * @param player Player al que queremos cambiar la orientación.
	 * @param dx Variable tipo int con el valor X de la orientación.
	 * @param dy Variable tipo int con el valor Y de la orientación.
	 * @param dz Variable tipo int con el valor Z de la orientación.
	 * @throws EntityIsDeadException Excepción que lanza cuando el Player está muerto
	 * @throws BadLocationException Excepción que lanza cuando la nueva orientación introducida no cumple los requisitos
	 */
	public void orientatePlayer(Player player, int dx, int dy, int dz) throws EntityIsDeadException, BadLocationException {
		player.orientate(dx, dy, dz);
	}
	/**
	 * Método que se encarga de gestionar las ordenes introducidas en PlayFile() y PlayFromConsole()
	 * @param sc Variable tipo Scanner con toda la información necesaria para realizar las ordenes
	 */
	public void play(Scanner sc) {
		
		String orden= sc.next();
		if(orden.equals("move") == true) {
			try {
				movePlayer(world.getPlayer(), sc.nextInt(),sc.nextInt(), sc.nextInt());
			} catch (BadLocationException e) {
				System.err.println("No se ha podido mover a la posición indicada.");	
			} catch (EntityIsDeadException e) {
				System.err.println("Player está muerto, no puede realizar acciones");
			}	
		}
		else if(orden.equals("orientate") == true) {			
			try {
				orientatePlayer(world.getPlayer(), sc.nextInt(), sc.nextInt(), sc.nextInt());
			} catch (EntityIsDeadException e) {
				System.err.println("Player está muerto, no puede realizar acciones");
			} catch (BadLocationException e) {
				System.err.println("La location introducida no es válida");
			}
		}
		else if(orden.equals("useItem") == true) {
			try {
				useItem(world.getPlayer(), sc.nextInt());
			} catch (Exception e) {
				System.err.println("Player está muerto, no puede realizar acciones o el parámetro es erróneo");
			}
		}
		else if(orden.equals("show") == true) {
			System.out.println(showPlayerInfo(world.getPlayer()));
		}
		else if(orden.equals("selectItem") == true) {
			int slot= sc.nextInt();
			try {
				selectItem(world.getPlayer(), slot);
			} catch (BadInventoryPositionException e) {
				System.err.println("La posición del inventario '" + slot + "' no existe.");
			}
		}
		else {
			System.err.println("La orden: " + orden + sc.nextLine());
			System.err.println("No es válida");
		}
	}
	/**
	 * Método que se encarga de leer un fichero con instrucciones y enviarselas a play() para que las ejecute
	 * @param path Dirección/Ruta dónde se encuentra el fichero que hay que abrir
	 * @throws FileNotFoundException Excepción que se lanza cuando no se ha encontrado el archivo
	 */
	public void playFile(String path) throws FileNotFoundException {
		File f= new File(path);
		Scanner sc= new Scanner(f);
		
		long seed= sc.nextLong();
		int size= sc.nextInt();
		String playerName= sc.next();
		String name= sc.next() + sc.nextLine();
		createWorld(seed, size, name, playerName);
		while(sc.hasNext() == true && world.getPlayer().isDead() == false) {//Sigue leyendo mientras quede fichero o el player NO esté muerto
			String new_world = null;
			try {
				new_world= world.getNeighbourhoodString(world.getPlayer().getLocation());
			} catch (BadLocationException e) {
			}
			play(sc);
		}
		sc.close(); 
	}
	/**
	 * Método que se encarga de leer por consola las ordenes del usuario y enviarselas a play() para que ejecute las ordenes
	 */
	public void playFromConsole() {
		Scanner sc= new Scanner(System.in);
		boolean ordenar= true;
		System.out.println("Introduce los datos para crear el mundo: ");
		long seed= sc.nextLong();
		int size= sc.nextInt();
		String name= sc.next();
		String playerName= sc.next();
		createWorld(seed, size, name, playerName);
		while(ordenar == true) {//Como nos gustan los bucles infinitos
			if(world.getPlayer().isDead() == false) {
				System.out.println("Introduce una orden: ");
				play(sc);
			}
			else
				ordenar = false;
		}
	}
	//New Práctica 4
	/**
	 * Método que devuelve el score relacionado con items
	 * @return Devuelve itemsScore
	 */
	public CollectedItemsScore getItemsScore() {
		return itemsScore;
	}
	/**
	 * Método que devuelve el score relacionado con la minería
	 * @return Devuelve MiningScore
	 */
	public MiningScore getMiningScore() {
		return miningScore;
	}
	/**
	 * Método que devuelve el score relacionado con el movimiento
	 * @return Devuelve movementScore
	 */
	public PlayerMovementScore getMovementScore() {
		return movementScore;
	}
}
