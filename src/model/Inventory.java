package model;
import java.util.*;//Necesario para hacer List<type>

import model.exceptions.BadInventoryPositionException;
import model.exceptions.StackSizeException;
/**
 * Clase que gestiona los métodos de inventario
 * @author rbm61 23900664 F
 *
 */
public class Inventory {
	/**
	 * Variable tipo ItemStack inicializada a null
	 */
	private ItemStack inHand= null;
	/**
	 * Relación de ItemStack con inventory
	 */
	private List<ItemStack> items;//Agregación
	/**
	 * Constructor de inventory
	 */
	public Inventory() {
		this.items= new ArrayList <ItemStack>();
	}
	//New Práctica 4
	/**
	 * Constructor de copia de Inventory
	 * @param inv Parámetro con los datos del Inventory a copiar
	 */
	public Inventory(Inventory inv) {
		this.items= new ArrayList <ItemStack>();//Incializar
		this.setItemInHand(inv.getItemInHand());
		//Recorremos el vector de items pasado por parámetro y vamos añadiendo
		for(ItemStack s : inv.items)
			addItem(s);
	}
	/**
	 * Método que añade items al inventario
	 * @param item Variable ItemStack con toda la información del item que queremos introducir 
	 * @return Devuelve el amount de los items añadidos.
	 */
	public int addItem(ItemStack item) {
		this.items.add(item);
		return item.getAmount();//Devuelve el número de items añadidos
	}
	/**
	 * Limpia todo el inventario y el item de la mano
	 */
	public void clear() {
		this.inHand= null;
		items.clear();
	}
	/**
	 * Método que se encarga de limpiar una posición en específico
	 * @param slot Posición del inventario 
	 * @throws BadInventoryPositionException Excepción que se lanza cuando la posición de inventario no existe
	 */
	public void clear(int slot) throws BadInventoryPositionException {
		if(slot < 0 || slot >= getSize()){
			BadInventoryPositionException e= new BadInventoryPositionException(slot);
			throw e;
		}
		else
			items.remove(slot);
	}
	/**
	 * Busca el primer material del tipo introducido por parámetros
	 * @param type Variable material con todos los datos necesarios
	 * @return Devuelve la posición en la que se encuentra ese material
	 */
	public int first(Material type) {
		int contador= 0;
		boolean encontrado= false;
		int slot= 0;
		
		for(ItemStack s : items){
			
			if(s.getType() == type && encontrado == false)//Encontramos item del mismo Material
			{
				encontrado= true;
				slot= contador;
			}
			contador++;
		}
		if(encontrado == false)
		{
			slot= -1;//No se ha encontrado
			return slot;
		}
		else	
			return slot;
	}
	/**
	 * Método que se encarga de devolver la posición de un Item del inventario
	 * @param slot Posición del Item
	 * @return Devuelve la posición o null si la posición no existe
	 */
	public ItemStack getItem(int slot) {
		
		if(slot < 0 || slot >= items.size())
			return null;
		else {
			int contador= 0;
			for(ItemStack s : items)
			{
				if(contador == slot){//Encontramos su posición
					return s;
				}
				contador++;
			}
		}
		return null;
	}
	/**
	 * Devuelve el item de la mano
	 * @return Devuelve inHand
	 */
	public ItemStack getItemInHand() {
		return inHand;//Está inicializada a null Inventory()
	}
	/**
	 * Devuelve el tamaño del inventario
	 * @return Devuelve el tamaño de list items
	 */
	public int getSize() {
		return items.size();
	}
	/**
	 * Método que se encarga de introducir un item en una posición del inventario
	 * @param slot Posición del inventario
	 * @param items Item que vamos a guardar
	 * @throws BadInventoryPositionException Excepción que se lanza cuando la posición de inventario no existe
	 */
	public void setItem(int slot, ItemStack items) throws BadInventoryPositionException {
		
		if(slot < 0 || slot >= getSize()){
			BadInventoryPositionException e = new BadInventoryPositionException(slot);
			throw e;
		}
		else
			this.items.set(slot, items);//Sustituimos en la posición indicada
	}
	/**
	 * Escoge un item del inventario y lo pone en la mano
	 * @param items Variable ItemStack con toda la información necesaria
	 */
	public void setItemInHand(ItemStack items) {
		if(inHand == null){
			inHand= items;
		}

		else if(items != null){
			if(inHand.getAmount() != 0) {
				int slot= 0;
				try {//Guardamos el item que tenemos en la mano
						ItemStack aux= new ItemStack(inHand.getType(), inHand.getAmount());
					for(ItemStack s: this.items) {
						if(s.equals(items) == true)
						{
							//System.out.println("Posición del objeto: " + slot);
							this.items.set(slot, aux);
						}
						slot++;
					}
					
				} catch (StackSizeException e) {
					throw new RuntimeException(e);
				}
			}
		}
		inHand= items;
	}
	/**
	 * Método que imprime por consola el toString de inventory()
	 * @return Devuelve el mensaje 
	 */
	@Override
	public String toString() {
		String string= "";
		string += "(inHand=" + inHand + ",[";
		String string2 = "";
		int i=0;
		for(ItemStack s : items)
		{
			if(i != getSize() - 1)
				string2= string2 + s + ", ";
			else
				string2= string2 + s;
			i++;
		}
		string= string + string2 + "])";
		return string;
	}
	/**
	 * Identificador de la clase
	 * @return Devuelve el id
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inHand == null) ? 0 : inHand.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}
	/**
	 * Método que se encarga de comparar un objeto con el actual
	 * @param obj Objeto con toda la información para comparar
	 * @return Devuelve true o false dependiendo si cumple o no las condiciones.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		if (inHand == null) {
			if (other.inHand != null)
				return false;
		} else if (!inHand.equals(other.inHand))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}
}