package model.entities;

import model.exceptions.*;
import model.*;
/**
 * Clase que gestiona los métodos de Player
 * @author rbm61 23900664 F
 *
 */
public class Player extends LivingEntity {
	/**
	 * Variable tipo string que guarda el nombre del jugador
	 */
	private String name;
	/**
	 * Variable double que guarda el nivel de comida del player
	 */
	private double foodLevel;
	/**
	 * Relación entre inventory-player
	 */
	private Inventory inventory;
	/**
	 * Variable estática y constante de tipo double que almacena la vida máxima posible
	 */
	public final static double MAX_FOODLEVEL= 20;
	/**
	 * Símbolo que representa al Player
	 */
	private static char symbol= 'P';
	/**
	 * Orientación del Player
	 */
	private Location orientation;
	
	/**
	 * Método que se encarga de bajar el nivel de comida o salud del player
	 * @param less Cantidad que hay que restar
	 */
	private void decreaseFoodLevel(double less) {
		double aux= this.foodLevel - less;
		
		if(this.foodLevel != 0.0) {
			if(aux < 0) {//Si es negativo
				this.foodLevel = 0.0;
				double diff= this.foodLevel - aux;//La energía que sobra(en positivo, luego se resta)
				setHealth(getHealth() - diff);
			}
			else {
				this.foodLevel= aux;
			}
		}
		else {//El alimento ya está a 0
			setHealth(getHealth() - less);
		}
	}
	/**
	 * Método que se encarga de aumentar el nivel de comida o salud del player
	 * @param plus Cantidad que hay que aumentar
	 */
	private void increaseFoodLevel(double plus) {
		double aux= this.foodLevel + plus;
		double diff;//Variable en la que guardaremos la cantidad de salud que aumenta.
		
		if(this.foodLevel != MAX_FOODLEVEL){
			if(aux > MAX_FOODLEVEL) {
				
				diff= aux - MAX_FOODLEVEL;
				this.foodLevel= MAX_FOODLEVEL;
				
				if(getHealth() != MAX_HEALTH) {
					
					setHealth(getHealth() + diff);
					
					if(getHealth() > MAX_HEALTH)//No puede superar el máximo de salud
						setHealth(MAX_HEALTH);
				}				
			}
			else
				setFoodLevel(aux);//Ya se encarga de que no supere el máximo
		}
		else {
			diff= aux - MAX_FOODLEVEL;
			if(getHealth() != MAX_HEALTH) {
				
				setHealth(getHealth() + diff);
				
				if(getHealth() > MAX_HEALTH)//No puede superar el máximo de salud
					setHealth(MAX_HEALTH);
			}		
		}
	}
	/**
	 * Constructor de Player
	 * @param name Nombre de Player
	 * @param world Mundo al que pertenece y dónde spawnea
	 */
	public Player(String name, World world) {
		
		super(new Location(world,0.0,0.0,0.0), LivingEntity.MAX_HEALTH);
		
		Location loc= new Location(world, 0.0, 0.0, 0.0);
		
		try {
			loc= world.getHighestLocationAt(loc);
			loc.setY(loc.getY() + 1);//Encima del bloque más alto
			super.location= loc;
		} catch (BadLocationException e1) {
			throw new RuntimeException(e1);
		}
		
		this.orientation= new Location(world, 0, 0, 1);//Cara al sol
		
		this.inventory= new Inventory();
		ItemStack item = null;
		try {
			item = new ItemStack(Material.WOOD_SWORD, 1);
		} catch (StackSizeException e) {
			throw new RuntimeException(e);
		}
		inventory.setItemInHand(item);
		
		this.name= name;
		foodLevel= MAX_FOODLEVEL;
	}
	/**
	 * Devuelve el nivel de comida
	 * @return Devuelve el nivel de comida actual del player
	 */
	public double getFoodLevel() {
		return foodLevel;
	}
	/**
	 * Actualiza el nivel de comida del player
	 * @param foodLevel Nuevo nivel de comida del player
	 */
	public void setFoodLevel(double foodLevel) {
		if(foodLevel > MAX_FOODLEVEL) {
			this.foodLevel= MAX_FOODLEVEL;
		}
		else
			this.foodLevel = foodLevel;
	}
	/**
	 * Devuelve el nombre
	 * @return Devuelve el nombre del player
	 */
	public String getName() {
		return name;
	}
	/**
	 * Devuelve el tamaño del inventario
	 * @return Devuelve el tamaño actual del inventario
	 */
	public int getInventorySize() {
		return inventory.getSize();
	}
	//New Práctica 4
	/**
	 * Método que devuelve el inventory del player como copia defensiva
	 * @return Devuelve copia de inventory de player
	 */
	public Inventory getInventory() {
		return new Inventory(inventory);
	}
	/**
	 * Método que se encarga de hacer que player se mueva
	 * @param dx Valor que aumenta o disminuye la coordenada X
	 * @param dy Valor que aumenta o disminuye la coordenada Y
	 * @param dz Valor que aumenta o disminuye la coordenada Z
	 * @return Devuelve null o la location actualizada si se ha podido mover
	 * @throws BadLocationException Se lanza cuando la location está ocupada, no es adyacente o no es de este mundo
	 * @throws EntityIsDeadException Se lanza cuando el player está muerto
	 */
	public Location move(int dx, int dy, int dz) throws BadLocationException, EntityIsDeadException {
		
		Location loc= new Location(location.getWorld(),location.getX() + dx,location.getY() + dy,location.getZ() + dz);
		//loc, localización con los valores actualizados
		if(isDead() == true) {
			EntityIsDeadException e= new EntityIsDeadException();
			loc= null;
			throw e;
		}
		else {
			if(location.getWorld().isFree(loc) == false) {//Usamos el isFree del location de player
				BadLocationException e= new BadLocationException("Location: " + loc + " no es de este mundo.");
				loc= null;
				throw e;
			}
			else {
				if(location.getNeighborhood().contains(loc) == false) {//No es adyacente
					BadLocationException e= new BadLocationException("Location: " + loc + " no es adyacente.");
					loc= null;
					throw e;
				}
				else {
					decreaseFoodLevel(0.05);//Realiza el movimiento
					this.location= new Location(loc);
				}
			}
		}
		return loc;
	}
	/**
	 * Método que usa el item que tiene en la mano el Player
	 * @param times El número de veces que lo usa
	 * @throws EntityIsDeadException Se lanza cuando el player está muerto
	 * @return Se devuelve el item que lleva en la mano
	 */
	public ItemStack useItemInHand(int times) throws EntityIsDeadException {
		if(isDead() == true) {
			EntityIsDeadException e= new EntityIsDeadException();
			throw e;
		}
		else {
				if(times <= 0) {
					IllegalArgumentException e= new IllegalArgumentException();
					throw e;
				}
				else {
					if(inventory.getItemInHand() != null) {//Tenemos item
						if(inventory.getItemInHand().getType().isEdible() == false) {//No es comida		
							decreaseFoodLevel(0.1*times);
						}
						else {//Tienes comida 
							if(times >= inventory.getItemInHand().getAmount()) {
								//Gasta todo el amount de Item
								increaseFoodLevel(inventory.getItemInHand().getType().getValue()*inventory.getItemInHand().getAmount());
								inventory.setItemInHand(null);
							}
							else {
								increaseFoodLevel(inventory.getItemInHand().getType().getValue()*times);
								//Actualizamos el amount del item usado
								try {
									inventory.getItemInHand().setAmount(inventory.getItemInHand().getAmount() - times);
								} catch (StackSizeException e) {
									throw new RuntimeException(e);//No debería lanzarse nunca
								}
								
								if(inventory.getItemInHand().getAmount() == 0){
									inventory.setItemInHand(null);//El objeto en la mano que no tiene amount se elimina
								}
							}
						}
					}
				}
		}
		return inventory.getItemInHand();//Puede ser null
	}
	/**
	 * Mëtodo que selecciona un item del inventario de player y lo pone en su mano
	 * @param pos Posición del item en el inventario
	 * @throws BadInventoryPositionException Se lanza cuando la posición del inventario no existe
	 */
	public void selectItem(int pos) throws BadInventoryPositionException {
		if(pos < 0 || pos >= getInventorySize()){
			BadInventoryPositionException e= new BadInventoryPositionException(pos);
			throw e;
		}
		else {
			if(inventory.getItemInHand() == null) {
				ItemStack item= inventory.getItem(pos);//Buscamos el item
				inventory.setItemInHand(item);//Lo colocamos en la mano
				inventory.clear(pos);//Eliminamos de la lista
			}
			else {
				ItemStack item= inventory.getItem(pos);//Buscamos el item
				inventory.setItemInHand(item);//Lo colocamos en la mano
			}
		}
	}
	/**
	 * Añade items al inventario del player
	 * @param items Item que se añade
	 */
	public void addItemsToInventory(ItemStack items) {
		inventory.addItem(items);
	}
	//New Practica 3
	/**
	 * Método que devuelve la orientación del player
	 * @return Devuelve orientation, posición absoluta
	 */
	public Location getOrientation() {//Devuelve Posición absoluta
		
		Location loc= new Location(super.location);//Copiamos la de la superclase
		loc.setX(loc.getX() + orientation.getX());
		loc.setY(loc.getY() + orientation.getY());
		loc.setZ(loc.getZ() + orientation.getZ());
		
		return loc;
	}
	/**
	 * Método abstracto declarado en LivingEntity e implementado en esta subclase
	 * @return Devuelve el símbolo del Player
	 */
	public char getSymbol() {
		return symbol;
	}
	/**
	 * Método que se encarga de cambiar la orientación del Player.
	 * @param dx Variable tipo int con el valor X de la orientación.
	 * @param dy Variable tipo int con el valor Y de la orientación.
	 * @param dz Variable tipo int con el valor Z de la orientación.
	 * @return Devuelve la orientación en forma absoluta.
	 * @throws EntityIsDeadException Excepción que lanza cuando el Player está muerto
	 * @throws BadLocationException Excepción que lanza cuando la nueva orientación introducida no cumple los requisitos
	 */
	public Location orientate(int dx, int dy, int dz) throws EntityIsDeadException, BadLocationException {
		
		if(isDead() == true) {
			EntityIsDeadException e= new EntityIsDeadException();
			throw e;
		}
		else {
			if(dx==0 && dy==0 && dz== 0) {//No se orienta sobre sí mismo
				BadLocationException e= new BadLocationException("BadLocation");
				throw e;
			}
			else {
				if(dx != -1 && dx != 0 && dx != 1) {
					BadLocationException e= new BadLocationException("NotAdyacent");
					throw e;
				}
				else if(dy != -1 && dy != 0 && dy != 1) {
					BadLocationException e= new BadLocationException("NotAdyacent");
					throw e;
				}
				else if(dz != -1 && dz != 0 && dz != 1) {
					BadLocationException e= new BadLocationException("NotAdyacent");
					throw e;
				}
				else {//Relativa
					this.orientation.setX(dx);
					this.orientation.setY(dy);
					this.orientation.setZ(dz);
				}
			}
		}
		
		return this.getOrientation();
	}
	/**
	 * Identificador de la clase
	 * @return Devuelve el id
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(foodLevel);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((inventory == null) ? 0 : inventory.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
		return result;
	}
	/**
	 * Método que compara dos objetos
	 * @param obj Objeto que queremos comparar
	 * @return Devuelve true o false si cumple o no las condiciones
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (Double.doubleToLongBits(foodLevel) != Double.doubleToLongBits(other.foodLevel))
			return false;
		if (inventory == null) {
			if (other.inventory != null)
				return false;
		} else if (!inventory.equals(other.inventory))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orientation == null) {
			if (other.orientation != null)
				return false;
		} else if (!orientation.equals(other.orientation))
			return false;
		return true;
	}
	/**
	 * Método que gestiona el toString de Player
	 * @return El mensaje deseado
	 */
	@Override
	public String toString() {
		String s= "";
		s += "Name=" + name + "\n";
		s += location + "\n";
		s += "Orientation=" + orientation + "\n";
		s += "Health=" + getHealth() + "\n";
		s += "Food level=" + foodLevel + "\n";
		s += "Inventory=" + inventory;
		return s;
	}
}