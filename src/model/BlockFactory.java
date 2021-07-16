package model;

import model.exceptions.WrongMaterialException;
/**
 * Clase que se encarga de crear los bloques
 * Comprueba si el material sirve para crear un sólido o líquido
 * Tiene una relación de uso con Block
 * @author rbm61 23900664 F
 *
 */
public class BlockFactory {
	/**
	 * Único método que se encarga de crear bloques
	 * @param type Variable de tipo Material con el cual crearemos los bloques 
	 * @return Devuelve el bloque sólido/líquido correspondiente
	 * @throws WrongMaterialException Excepción que se lanza cuando el type no sirve para crear bloques
	 */
	public static Block createBlock(Material type) throws WrongMaterialException {
		Block b = null;
		
		if(type.isBlock() == true) {
			
			if(type.isLiquid() == true) {
				b= new LiquidBlock(type);
			}
			else {
				b= new SolidBlock(type);
			}
		}
		else {
			throw new WrongMaterialException(type);
		}
		return b;
	}
}
