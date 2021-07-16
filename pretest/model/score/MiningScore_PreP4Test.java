package model.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Block;
import model.BlockFactory;
import model.ItemStack;
import model.Material;
import model.exceptions.WrongMaterialException;

public class MiningScore_PreP4Test {

	MiningScore scLaura;
	MiningScore scPeter;
	static Block chest,sand,dirt,grass,stone,granite,obsidian,lava,air,water;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		chest = BlockFactory.createBlock(Material.CHEST);
		sand = BlockFactory.createBlock(Material.SAND);
		dirt = BlockFactory.createBlock(Material.DIRT);
		grass = BlockFactory.createBlock(Material.GRASS);
		stone = BlockFactory.createBlock(Material.STONE);
		granite = BlockFactory.createBlock(Material.GRANITE);
		obsidian = BlockFactory.createBlock(Material.OBSIDIAN);
		lava =  BlockFactory.createBlock(Material.OBSIDIAN);
		air =  BlockFactory.createBlock(Material.AIR);
		water =  BlockFactory.createBlock(Material.WATER);
	}
	
	@Before
	public void setUp() throws Exception {
		scLaura = new MiningScore("Laura");
		scPeter = new MiningScore("Peter");
	}

	/* Comprueba con getName() que los nombres de los jugadores de los dos
	 * MiningScore son correctos
	 */
	//TODO
	@Test
	public void testMiningScoreAndGetName() {
		Score<Block> score = scLaura; //Implementas la herencia ??? Nice copy/Paste btw
		assertEquals("Laura", score.getName());
		Score<Block> score2= scPeter;
		assertEquals("Peter", score2.getName());
	}

	/* Inicialmente aplica score sobre los Scores scLaura y 
	 * scPeter con los mismos bloques. Comprueba que compareTo da 0.
	 * Aplica sobre uno de ellos con score, un bloque que incremente su puntuación.
	 * Comprueba ahora que el que ha aumentado, si es el que invoca a compareTo da un
	 * valor negativo y si es el menor el que lo invoca, da un valor positivo.
	 */
	 //TODO
	@Test
	public void testCompareTo() {
		assertTrue(scPeter.compareTo(scLaura)==0);
		scPeter.score(granite);
		scLaura.score(granite);
		assertTrue(scPeter.compareTo(scLaura)==0);
		scLaura.score(granite);
		assertTrue(scPeter.compareTo(scLaura) > 0);//Peter es menor por lo que devuelve positivo
		scPeter.score(water);
		scPeter.score(granite);
		scPeter.score(stone);
		assertTrue(scPeter.compareTo(scLaura) < 0);
	}

	/* Toma alguno de los bloques estáticos creados al inicio. Con cada uno, 
	 * crea las puntuaciones (score) sobre el MiningScore scLaura y comprueba 
	 * con getScoring() que  los valores que se van obteniendo se van 
	 * acumulando sucesivamente. Por ejemplo:
	 * chest --> 0.1
	 * sand -->0.6
	 * dirt -->1.1
	 * ...
	 * Haz también algo parecido con scPeter
	 */
	//TODO
	@Test
	public void testScoreBlock() {
		assertEquals(0,scLaura.getScoring(),0.01);
		scLaura.score(chest);
		assertEquals(0.1,scLaura.getScoring(),0.01);
		scLaura.score(sand);
		assertEquals(0.6,scLaura.getScoring(),0.01);
		scLaura.score(dirt);
		assertEquals(1.1,scLaura.getScoring(),0.01);
		//-----------------------------------------
		assertEquals(0,scPeter.getScoring(),0.01);
		scPeter.score(air);
		assertEquals(0,scPeter.getScoring(),0.01);
		scPeter.score(lava);
		assertEquals(5.0,scPeter.getScoring(),0.01);
		scPeter.score(water);
		assertEquals(5.0,scPeter.getScoring(),0.01);//El agua y el aire tienen value 0
	}

	/* Comprueba toString sobre scLaura y comprueba que inicialmente es: "Laura:0.0"
	 * Toma algunos bloques del principio y aplica el método score sobre scLaura. 
	 * Comprueba que la salida va cambiando de valor.
	 */
	//TODO
	@Test
	public void testToString() {
		
		assertEquals("Laura:0.0", scLaura.toString());
		try {
			scLaura.score(BlockFactory.createBlock(Material.GRASS));//0,6
			assertEquals("Laura:0.6", scLaura.toString());
			scLaura.score(BlockFactory.createBlock(Material.DIRT));//0,5
			assertEquals("Laura:1.1", scLaura.toString());
			scLaura.score(BlockFactory.createBlock(Material.GRANITE));//1,5
			assertEquals("Laura:2.6", scLaura.toString());
		} catch (WrongMaterialException e) {
			fail("No debería lanzar la excepción " + e.getClass());
		}
	}

	
	/**********************************************/
	/**********************************************/
	//FUNCIONES DE APOYO
	/* Para las salidas Score.toString() compara los valores impresos
	 * de los Scores hasta una precisión de 0.01
	 * 
	 */
	void compareScores(String expected, String result ) {
		String ex[]= expected.split(":");
		String re[]= result.split(":");
		if (ex.length!=re.length) fail("Lineas distintas");
		if (ex.length==2) {
			if (ex[0].trim().equals(re[0].trim())) {
				double ed = Double.parseDouble(ex[1]);
				double rd = Double.parseDouble(re[1]);
		
				assertEquals(ex[0],ed,rd,0.01);
			}
			else fail("Nombres jugadores distintos: esperado=<"+ex[0].trim()+"> obtenido=<"+re[0].trim()+">");
		}
		else
			assertEquals(expected.trim(),result.trim());		
	}	

}
