package mains;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import model.*;
import model.exceptions.*;
import model.persistence.*;
import model.entities.*;

/**
 * @author pierre
 *
 */
public class MainAdapter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		BlockWorld bw= BlockWorld.getInstance();
		World world= bw.createWorld(5, 50, "world50x50", "Paco");
		Player player= world.getPlayer();
		try {
			world.getPlayer().addItemsToInventory(new ItemStack(Material.APPLE, 5));
			world.getPlayer().addItemsToInventory(new ItemStack(Material.IRON_PICKAXE, 1));
			world.getPlayer().addItemsToInventory(new ItemStack(Material.BEDROCK, 4));
		} catch (StackSizeException e) {
			e.printStackTrace();
		}
		System.out.println(bw.showPlayerInfo(world.getPlayer()));
		WorldAdapter wa= new WorldAdapter(world);
		System.out.println("-------------------------");
		System.out.println(wa.getPlayer().getName());
		System.out.println(wa.getPlayer().getLocation());
		System.out.println(wa.getPlayer().getInventory().inHandItem());
		System.out.println(wa.getPlayer().getInventory().getSize());//3
		System.out.println("-------------------------");
		System.out.println(wa.getPlayer().getInventory().getItem(4));//null
		System.out.println(wa.getPlayer().getInventory().getItem(-1));//null
		System.out.println("-------------------------");
		System.out.println(wa.getPlayer().getInventory().getItem(0));//Apple
		System.out.println(wa.getPlayer().getInventory().getItem(1));//Iron_Pickaxe
		System.out.println("-------------------------");
		System.out.println(wa.getNegativeLimit());
		System.out.println(wa.getPositiveLimit());
		System.out.println(wa.getCreatures(wa.getPlayer().getLocation()));//Esta posición no se modifica
		//Animal [location=Location{world=world50x50,x=5.0,y=59.0,z=0.0}, health=8.0]
		//Buscamos al animal desde 0 58 0
		try {
			bw.movePlayer(player, 1, 1, 0);
			bw.movePlayer(player, 1, 0, 0);
			bw.movePlayer(player, 1, 0, 0);
			bw.movePlayer(player, 1, 0, 0);
//			@@@ @@@ ggg
//			@@@ @PL @@g Clean
//			@@@ @@@ @@@
			System.out.println(bw.showPlayerInfo(world.getPlayer()));
		} catch (BadLocationException | EntityIsDeadException e) {
			e.printStackTrace();
		}
		System.out.println(wa.getItems(wa.getPlayer().getLocation()));//Posición original del player
		//Location{world=world50x50,x=6.0,y=61.0,z=11.0}=(WATER_BUCKET,5)
		//Buscamos el item desde 4 59 0
		try {
			bw.movePlayer(player, 1, 1, 0);
			bw.movePlayer(player, 1, 1, 0);
			bw.movePlayer(player, 0, 0, 1);
			bw.movePlayer(player, 0, 0, 1);
			bw.selectItem(player, 1);
			bw.useItem(player, 3);
			bw.movePlayer(player, 0, 0, 1);
			bw.useItem(player, 3);
			bw.movePlayer(player, 0, 0, 1);
			bw.useItem(player, 3);
			bw.movePlayer(player, 0, 0, 1);
			bw.movePlayer(player, 0, 0, 1);
			bw.movePlayer(player, 0, 0, 1);
			bw.movePlayer(player, 0, 0, 1);
			bw.movePlayer(player, 0, 0, 1);
			bw.movePlayer(player, 0, 0, 1);
//			@@@ @@@ @g@
//			@@@ @P@ ggg Clean
//			@@@ @WF @gg
			//Cogemos el item
			System.out.println(bw.showPlayerInfo(world.getPlayer()));
		} catch (BadLocationException | EntityIsDeadException | BadInventoryPositionException e) {
			e.printStackTrace();
		}
		System.out.println(wa.getMapBlock(wa.getPlayer().getLocation()));
		
	}
}
