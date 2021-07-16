package model;
import java.util.Random;//Clase random 
/**
 * Clase tipo Enum en la que están todos los materiales del juego
 * @author rbm61 23900664 F
 *
 */
public enum Material {
		/**
		 * Bedrock
		 */
		BEDROCK(-1.0,'*'),
		/**
		 * chest
		 */
		CHEST(0.1,'C'),
		/**
		 * sand
		 */
		SAND(0.5,'n'),//IMPORTANTE, ahora sand es n, su item es N
		/**
		 * dirt
		 */
		DIRT(0.5,'d'),
		/**
		 * grass
		 */
		GRASS(0.6,'g'),
		/**
		 * Stone
		 */
		STONE(1.5,'s'),
		/**
		 * Granite
		 */
		GRANITE(1.5,'r'),
		/**
		 * Obsidian
		 */
		OBSIDIAN(5,'o'),
		/**
		 * Cubo de agua 
		 */
		WATER_BUCKET(1.0,'W'),
		/**
		 * Manzana
		 */
		APPLE(4.0,'A'),
		/**
		 * Pan
		 */
		BREAD(5.0,'B'),
		/**
		 * Chuleta
		 */
		BEEF(8.0,'F'),
		/**
		 * Pala de hierro
		 */
		IRON_SHOVEL(0.2,'>'),
		/**
		 * Pico de hierro
		 */
		IRON_PICKAXE(0.5,'^'),
		/**
		 * Espada de madera
		 */
		WOOD_SWORD(1,'i'),
		/**
		 * Espada de hierro
		 */
		IRON_SWORD(2,'I'),
		//New
		/**
		 * Lava (liquido)
		 */
		LAVA(1.0, '#'),
		/**
		 * Agua (liquido)
		 */
		WATER(0, '@'),
		//New Práctica 4
		/**
		 * Aire
		 */
		AIR(0, ' ');
	/**
	 * Variable tipo doble que almacenará el valor del objeto.
	 */
	private double value;
	/**
	 * Variable tipo char que almacenará el símbolo del objeto
	 */
	private char symbol;
	/**
	 * Variable estática tipo package que se encarga de generar un número aleatorio
	 */
	static Random rng= new Random(1L);
	/**
	 * Constructor de Material
	 * @param v Value
	 * @param s Symbol
	 */
	Material(double v, char s) {
		value= v;
		symbol= s;
	}
	/**
	 * Booleana que comprueba si es un bloque
	 * @return Devuelve true o false si cumple las condiciones
	 */
	public boolean isBlock() {
		boolean isblock= false;
		
		switch(symbol) {
		case '*': isblock= true;
			break;
		case 'C': isblock= true;
			break;
		case 'n': isblock= true;//Cuidado
			break;
		case 'd': isblock= true;
			break;
		case 'g': isblock= true;
			break;
		case 's': isblock= true;
			break;
		case 'r': isblock= true;
			break;
		case 'o': isblock= true;
			break;
		//new Práctica 3
		case '#': isblock= true;
			break;
		case '@': isblock= true;
			break; 
		case ' ': isblock= true;//New
			break;
		default: isblock= false;
			break;	
		}
		return isblock;
	}
	/**
	 * Booleana que comprueba si es comida/comestible
	 * @return Devuelve true o false si cumple las condiciones
	 */
	public boolean isEdible() {
		boolean isedible= false;
		
		switch(symbol) {
		case 'W': isedible= true;
			break;
		case 'A': isedible= true;
			break;
		case 'B': isedible= true;
			break;
		case 'F': isedible= true;
			break;
		default:isedible= false;
			break;	
		}
		return isedible;
	}
	/**
	 * Booleana que comprueba si es un arma
	 * @return Devuelve true o false si cumple las condiciones
	 */
	public boolean isWeapon() {
		boolean isweapon= false;
		
		switch(symbol) {
		case 'i': isweapon= true;
			break;
		case 'I': isweapon= true;
			break;
		default:isweapon= false;
			break;	
		}
		return isweapon;
	}
	/**
	 * Booleana que comprueba si es una herramienta
	 * @return Devuelve true o false si cumple las condiciones
	 */
	public boolean isTool() {
		boolean istool= false;
		
		switch(symbol) {
		case '>': istool= true;
			break;
		case '^': istool= true;
			break;
		default: istool= false;
			break;	
		}
		return istool;
	}
	//New Práctica 3
	/**
	 * Método que comprueba si el Material es líquido
	 * @return Devuelve true o false si el material es líquido o no
	 */
	public boolean isLiquid() {
		boolean isliquid= false;
		
		switch(symbol) {
		case '#': isliquid= true;
			break;
		case '@': isliquid= true;
			break; 
		case ' ': isliquid= true;//New Práctica 4
			break;
		default: isliquid= false;
			break;
		}
		return isliquid;
	}
	/**
	 * Devuelve el valor
	 * @return valor del material
	 */
	public double getValue() {
		return value;
	}
	/**
	 * Devuelve el simbolo
	 * @return simbolo del material
	 */
	public char getSymbol() {
		return symbol;
	}
	/**
	 * Método que se encarga de generar un item aleatorio
	 * @param first Primera posición del intervalo
	 * @param last Última posición del intervalo
	 * @return Devuelve el material aleatorio generado
	 */
    public static Material getRandomItem(int first, int last) {
        int i = rng.nextInt(last-first+1)+first;
        return values()[i];
    }
}


