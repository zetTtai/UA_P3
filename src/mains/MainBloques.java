package mains;

import java.util.NavigableMap;
import java.util.TreeMap;

import model.*;
import model.exceptions.*;

/**
 * @author pierre
 *
 */
public class MainBloques {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		int CHUNK= 16;
		Inventory inv= new Inventory();
		Inventory copy;
		
		try {
			inv.addItem(new ItemStack(Material.APPLE, 5));
			inv.addItem(new ItemStack(Material.IRON_PICKAXE, 1));
			inv.addItem(new ItemStack(Material.BEDROCK, 4));
			inv.addItem(new ItemStack(Material.BEEF, 41));
			inv.setItemInHand(new ItemStack(Material.WOOD_SWORD, 1));
		} catch (StackSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(inv.toString());
		System.out.println("Copiamos...");
		copy= new Inventory(inv);
		System.out.println(copy.toString());
		System.out.println("---------------------------------------------------");
		BlockWorld bw= BlockWorld.getInstance();	
		World world= bw.createWorld(6, 50, "world50x50", "Steve");
		
		Location loc= new Location(world, -24,0,-24);
		try {
			System.out.println(world.getNeighbourhoodString(loc));
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
		NavigableMap<Location, Block> blocks= new TreeMap<Location, Block>();
		int z;
		for(int y= 0; y < CHUNK; y++) {//Altura
			for(z= 0; z < CHUNK; z++) {//Filas
				for(int x= 0; x < CHUNK; x++) {//Columnas
					Location aux= new Location(world, loc.getX() + x, loc.getY(), loc.getZ());
					if(Location.check(aux)== false) {//Fuera de los límites
						blocks.put(aux, null);
					}
					else {//Dentro de los límites
						try {
							if(world.getBlockAt(aux) != null) {//Hay un bloque
								blocks.put(aux,world.getBlockAt(aux));
							}
							else {//No hay bloque
								Block air= BlockFactory.createBlock(Material.AIR);
								blocks.put(aux, air);
							}
						} catch (BadLocationException e) {
							e.printStackTrace();
						} catch (WrongMaterialException e) {
							e.printStackTrace();
						}
					}
				}
				loc.setZ(loc.getZ() + 1);//Miramos la siguiente fila
			}
			loc.setY(loc.getY() + 1);//Aumentamos la altura	
			loc.setZ(loc.getZ() - z);//Reiniciamos el valor de la coordenada Z
		}
		//Comprobar con diffchecker
		System.out.println(blocks);
		Location loc2= new Location(world, -23,63,-18);
		
		try {
			System.out.println(world.getNeighbourhoodString(loc2));
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		Location loc3= new Location(world, -22,63,-18);
		
		try {
			System.out.println(world.getNeighbourhoodString(loc3));
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		Location loc4= new Location(world, -24,63,-18);
		
		try {
			System.out.println(world.getNeighbourhoodString(loc4));
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
}