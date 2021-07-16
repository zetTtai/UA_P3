package model;

import java.util.HashSet;
import java.util.Set;

import model.exceptions.BadLocationException;

/**
 * Este archivo se encarga de gestionar los métodos de la clase Location.
 * @author RAÚL BELTRÁN MARCO 23900664 F
 *
 */
public class Location implements Comparable<Location>{
	/**
	 * Variable estática que se encarga de almacenar el valor 255.0
	 */
	public static final double UPPER_Y_VALUE = 255.0;
	/**
	 * Variable estática que se encarga de almacenar el valor 63.0
	 */
	public static final double SEA_LEVEL = 63.0;
	/**
	 * Esta variable tipo double se encarga de guardar la información de la coordenada 'X'.
	 */
	private double x;
	/**
	 * Esta variable tipo double se encarga de guardar la información de la coordenada 'Y'.
	 */
	private double y;
	/**
	 * Esta variable tipo double se encarga de guardar la información de la coordenada 'Z'.
	 */
	private double z;
	/**
	 * Variable tipo World que usaremos para enlazar las clases World y Location.
	 */
	private World world;

	/**
	 * Método constructor de la clase
	 * @param w Variable tipo World que contiene los datos que introduciremos en nuestra variable private world.
	 * @param x Variable tipo double que guardaremos en nuestra variable privada double que almacena la información de la coordenada X.
	 * @param y Variable tipo double que guardaremos en nuestra variable privada double que almacena la información de la coordenada Y.
	 * @param z Variable tipo double que guardaremos en nuestra variable privada double que almacena la información de la coordenada Z.
	 */
	public Location(World w, double x, double y, double z) {
		world= w;
		setX(x);
		setY(y);
		setZ(z);
		
	}
	/**
	 * Constructor de copia de la clase
	 * @param loc Es una variable tipo Location que contiene los datos que van a ser copiados.
	 */
	public Location(Location loc) {
		world= loc.world;
		x= loc.x;
		y= loc.y;
		z= loc.z;
	}
	/**
	 * Método que se encarga de añadir nuevas Location a la clase.
	 * @param loc Variable de tipo Location que tiene los datos necesarios para actualizar los valores de la clase.
	 * @return Devuelve el objeto, en este caso Location, ya actualizado.
	 */
	public Location add(Location loc) {
		
		if(loc.world != world)
			System.err.println("Cannot add Locations of differing worlds.");
		else{
			x += loc.x;
			setY(y + loc.y);
			z += loc.z;
		}
		return this;
	}
	/**
	 * 
	 * @param loc Variable de tipo Location que tiene los datos necesarios para actualizar los valores de la clase.
	 * @return Devuelve el objeto, en este caso Location, ya actualizado.
	 */
	public Location substract(Location loc) {
		if(loc.world != world)
			System.err.println("Cannot substract Locations of differing worlds.");
		else {
			x -= loc.x;
			setY(y - loc.y);
			z -= loc.z;
		}
		return this;
	}
	/**
	 * Función que se encarga de devolver el valor almacenado en y.
	 * @return devuelve el valor almacenado en y.
	 */
	public double getY() {
		return y;
	}
	/**
	 * Función que se encarga de devolver el valor almacenado en x.
	 * @return devuelve el valor almacenado en x.
	 */
	public double getX() {
		return x;
	}
	/**
	 * Función que se encarga de devolver el valor almacenado en z.
	 * @return devuelve el valor almacenado en z.
	 */
	public double getZ() {
		return z;
	}
	/**
	 * Método que se encarga de calcular la distancia.
	 * @param loc Variable de tipo Location que tiene los datos necesarios para calcular la distancia.
	 * @return Devuelve el cálculo matemático realizado con la operación Math.sqrt, la distancia.
	 */
	public double distance(final Location loc) {
		if(loc.getWorld() == null || getWorld() == null) {
			System.err.println("Cannot measure distance to a null world");
			return -1.0;
		}
		else if(loc.getWorld() != getWorld()) {
			System.err.println("Cannot measure distance between "+ world.getName() + "and" + loc.world.getName());
			return -1.0;
		}
		double dx= x - loc.x;
		double dy= y - loc.y;
		double dz= z - loc.z;
		return Math.sqrt(dx*dx +  dy*dy + dz*dz);
	}
	/**
	 * Método con el cual se obtiene los datos de la clase World.
	 * @return Devuelve el ToString de la clase World.
	 */
	public World getWorld() {
		
		return world;
	}
	/**
	 * Método con el cual podemos introducir un nuevo World pasado por parámetros.
	 * @param w Variable tipo World que contiene los datos necesarios para actualizar la variable privada World.
	 */
	public void setWorld(World w) {
		this.world= w;
	}
	/**
	 * Método con el cual podemos introducir un nuevo valor de X.
	 * @param x Variable tipo double pasada por parámetros con la que actualizaremos el valor de X.
	 */
	public void setX(double x) {
		this.x= x;
	}
	/**
	 * Método con el cual podemos introducir un nuevo valor de Y.
	 * @param y Variable tipo double pasada por parámetros con la que actualizaremos el valor de Y.
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * Método con el cual podemos introducir un nuevo valor de Z.
	 * @param z Variable tipo double pasada por parámetros con la que actualizaremos el valor de Z.
	 */
	public void setZ(double z) {
		this.z= z;
	}
	/**
	 * Método que se encarga de calcular la longitud de la localización.
	 * @return Devuelve el resultado obtenido con la función Math.sqrt la cual equivale a la longitud.
	 */
	public double length() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	/**
	 * Método que se encarga de multiplicar, es el equivalente a *= en C++.
	 * @param factor Variable tipo double que contiene el dato con el cual multiplicaremos las coordenadas.
	 * @return Devuelve el objeto multiplicado
	 */
	public Location multiply(double factor) {
		x *= factor;
		setY(y * factor);
		z *= factor;
		return this;
	}
	/**
	 * Método que se encarga de establecer todas las coordenadas a 0.
	 * @return Devuelve el objeto actualizado, en este caso Location.
	 */
	public Location zero() {
		this.x= 0.0;
		this.y= 0.0;
		this.z= 0.0;
		return this;
	}
	/**
	 * Método que se encarga de otorgarle a la clase un identificador.
	 * @return Devuelve el resultado obtenido.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((world == null) ? 0 : world.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/**
	 * Método que se encarga de comparar dos objetos.
	 * @param obj Variable de un tipo predeterminado para comparar dos objetos.
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
		Location other = (Location) obj;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	/**
	 * Método que se encarga de mostrar el texto escrito en return para mostrarlo en consola.
	 * @return Devuelve el mensaje deseado.
	 */
	@Override
	public String toString() {
		String s;
		
		if(getWorld() != null)
			s= "Location{world=" + world + ",x=" + x + ",y=" + y + ",z=" + z + "}";
		else
			s= "Location{world=NULL,x=" + x + ",y=" + y + ",z=" + z + "}";
		
		return s;
	}
	//New Practica 2
	/**
	 * Método que se encarga de combrobar que la Location introducida cumple con las condiciones.
	 * @param w Variable tipo World que contiene información del mundo creado.
	 * @param x Variable tipo double que contiene la información de la coordenada X.
	 * @param y Variable tipo double que contiene la información de la coordenada Y.
	 * @param z Variable tipo double que contiene la información de la coordenada Z.
	 * @return Devuelve true o false dependiendo si cumple o no las condiciones escritas.
	 */
	public static boolean check(World w, double x, double y, double z) {
		
		int max,min;
		boolean check= false;
		if(w == null){
			check= true;//No tiene mundo asociado
		}
		else {
			if(w.getSize()%2 == 0) {
				max= w.getSize()/2;
				min= ((w.getSize()/2) - 1)*-1;
			}
			else{
				max= w.getSize()/2;
				min= ((w.getSize()/2))*-1;
			}
			if(x < min || x > max)
				check= false;
			else {
				if(z < min || z > max)
					check= false;
				else {
					if(y < 0 || y > UPPER_Y_VALUE)
						check= false;
					else
						check= true;
				}
			}
		}
		return check;
	}
	/**
	 * Método que se encarga de combrobar que la Location introducida cumple con las condiciones.
	 * @param loc Variable tipo location que contiene todos los datos necesarios para realizar las condiciones.
	 * @return Devuelve true o false dependiendo si cumple o no las condiciones escritas.
	 */
	public static boolean check(Location loc) {
		
		int max,min;
		boolean check= false;
		
		if(loc.getWorld() == null){
			check= true;//No tiene mundo asociado
		}
		else {
			if(loc.world.getSize()%2 == 0) {
				max= loc.world.getSize()/2;
				min= ((loc.world.getSize()/2) - 1)*-1;
			}
			else{
				max= loc.world.getSize()/2;
				min= ((loc.world.getSize()/2))*-1;
			}
			if(loc.getX() < min || loc.getX() > max)
				check= false;
			else {
				if(loc.getZ() < min || loc.getZ() > max)
					check= false;
				else {
					if(loc.getY() < 0 || loc.getY() > UPPER_Y_VALUE)
						check= false;
					else
						check= true;
				}
			}
		}
		return check;
	}
	/**
	 * Variable que se encarga de comprobar si la posición actual está libre
	 * @return Devuelve true o false dependiendo si cumple las condiciones
	 */
	public boolean isFree() {
		Location loc= new Location(world, x, y, z);
		boolean free= false;
		if(world != null) {
			try {
				if(world.isFree(loc) == true)
					free= true;
				else
					free= false;
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		else {
			free= false;
		}
		return free;
	}
	/**
	 * Comprueba si existe posición por debajo de la actual
	 * @return Devuelve la location si existe o null si no
	 * @throws BadLocationException Si no existe la location lanza excepción
	 */
	public Location below() throws BadLocationException {
		Location loc = new Location(world,x,y-1,z);
		if(loc.getY() < 0 && loc.getWorld() != null) {
			BadLocationException e= new BadLocationException("Location: " + loc + " no es de este mundo.");
			loc= null;
			throw e;
		}
		return loc;
	}
	/**
	 * Comprueba si existe posición por encima de la actual
	 * @return Devuelve la location si existe o null si no
	 * @throws BadLocationException Si no existe la location lanza excepción
	 */
	public Location above() throws BadLocationException {
		Location loc = new Location(world,x,y+1,z);
		if(loc.getY() > UPPER_Y_VALUE && loc.getWorld() != null) {
			BadLocationException e= new BadLocationException("Location: " + loc + " no es de este mundo.");
			loc= null;
			throw e;
		}
		return loc;
	}
	
	/**
	 * Método que se encarga de comprobar las posiciones adyacentes de la posición actual
	 * @return Devuelve las posiciones adyacentes de la posición actual
	 */
	public Set<Location> getNeighborhood(){
		Set<Location> vecinos= new HashSet<Location>();//Conjunto creado para almacenar las location adyacentes a la actual
		int RENDER= 3;
		
		for(int y= -1; y < 2; y++) {
			
			Location original= new Location(world, this.x - 1, this.y, this.z - 1);
			original.setY(original.getY() + y);
			
			for(int z= 0; z < RENDER; z++) {
				for(int x= 0; x < RENDER; x++) {
					Location aux= new Location(original.getWorld(), original.getX() + x, original.getY(), original.getZ());
					if(Location.check(aux))
						vecinos.add(aux);
				}
				original.setZ(original.getZ() + 1);
			}
		}
		vecinos.remove(this);
		return vecinos;
	}
//	public Set<Location> getNeighborhood() {
//		int MAX= 3;
//		Set<Location> Vecinos = new HashSet<Location>();//Conjunto creado para almacenar todas las location adyacentes
//		
//		try {
//			if(above() != null) {//NIVEL SUPERIOR
//				Location original= new Location(world, x - 1, y + 1, z - 1);//Location Esquina superior izquierda
//				for(int i=0; i < MAX; i++) {//Filas
//					for(int j=0; j < MAX; j++) {//Columnas
//						
//						Location loc= new Location(original.getWorld(), original.getX() + j, original.getY(), original.getZ());
//						
//						if(Location.check(loc) == true) {
//							Vecinos.add(loc);
//						}
//					}
//					original.setZ(original.getZ() + 1);
//				}
//			}
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			if(below() != null) {//NIVEL INFERIOR
//				Location original= new Location(world, x - 1, y - 1, z - 1);//Location Esquina superior izquierda
//				
//				for(int i=0; i < MAX; i++) {//Filas
//					for(int j=0; j < MAX; j++) {//Columnas
//						
//						Location loc= new Location(original.getWorld(), original.getX() + j, original.getY(), original.getZ());
//						
//						if(Location.check(loc) == true) {
//							Vecinos.add(loc);
//						}
//					}
//					original.setZ(original.getZ() + 1);
//				}
//			}
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//		}
//		//NIVEL INTERMEDIO
//		Location original= new Location(world, x - 1, y, z - 1);//Location Esquina superior izquierda
//		
//		for(int i=0; i < MAX; i++) {//Filas
//			for(int j=0; j < MAX; j++) {//Columnas
//				
//				Location loc= new Location(original.getWorld(), original.getX() + j, original.getY(), original.getZ());
//				
//				if(Location.check(loc) == true) {
//					Vecinos.add(loc);
//				}
//			}
//			original.setZ(original.getZ() + 1);
//		}
//		Vecinos.remove(new Location(world,x,y,z));//Eliminamos la coordenada primigénia 
//		return Vecinos;
//	}
	//New Práctica 4
	/**
	 * Método que se encarga de comparar dos location
	 * @param other Location que hay que comparar con la acutal
	 * @return Devuelve un valor positivo, negativo o 0 dependiendo de las diferencias.
	 */
	public int compareTo(Location other) {
		int compare=0;
		//Auxiliares porque sí(?)
		double actualX= this.getX();
		double actualY= this.getY();
		double actualZ= this.getZ();
		
		double otherX= other.getX();
		double otherY= other.getY();
		double otherZ= other.getZ();
		
		if(actualX < otherX || 
		   actualX == otherX && actualY < otherY || 
		   actualX == otherX && actualY == otherY && actualZ < otherZ) {
			compare= -1;//Other es mayor que el original
		}
		else if(actualX == otherX && actualY == otherY && actualZ == otherZ) {
			compare= 0;
		}
		else
			compare= 1;//El original es mayor que other		
		return compare;		
//		if(this.getX() < other.getX() || 
//		   this.getX() == other.getX() && this.getY() < other.getY() || 
//		   this.getX() == other.getX() && this.getY() == other.getY() && this.getZ() < other.getZ()) {
//			compare= -1;//Other es mayor que el original
//		}
//		else if(this.getX() == other.getX() && this.getY() == other.getY() && this.getZ() == other.getZ()) {
//			compare= 0;
//		}
//		else
//			compare= 1;//El original es mayor que other
	}
}