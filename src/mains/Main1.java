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
public class Main1 {

	public static void main(String[] args) {		
		//Pruebas de unitarias
		World world= new World(2, 5, "world5x5", "F");
		Player player= world.getPlayer();
		Location locPlayer= player.getLocation();
		
		Location loc= new Location(world, 0, 254, 0);
		System.out.println(loc.getNeighborhood());
		
	}
}