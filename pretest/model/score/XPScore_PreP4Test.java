package model.score;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.BlockFactory;
import model.ItemStack;
import model.Location;
import model.Material;
import model.World;
import model.entities.Player;
import model.exceptions.StackSizeException;
import model.exceptions.score.ScoreException;

public class XPScore_PreP4Test {

	XPScore xpJulia, xpCharles;
	Player pJulia, pCharles;
	World world;
	CollectedItemsScore cis;
	MiningScore ms;
	PlayerMovementScore pms;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		world = new World(3,10,"A Little World", "Joan");
		pJulia = new Player("Julia",world);
		pCharles = new Player("Charles",world);
		xpJulia = new XPScore(pJulia);
		xpCharles = new XPScore(pCharles);
	}

	@Test
	public void testXPScoreAndGetName() {
		Score<Player> score = xpJulia; //Implementas la herencia ???
		assertEquals("Julia",score.getName());	
		
		assertEquals("Julia",xpJulia.getName());
		assertEquals("Charles", xpCharles.getName());
	}

	/* Comparar las puntuaciones de xpJulia y de xpCharles modificando solo
	 * el health y el foodLevel
	 */	 
	//TODO
	@Test
	public void testCompareTo1() {
			//Inicialmente ambas puntuaciones son iguales 
			assertTrue(xpJulia.compareTo(xpCharles)==0);
			
//			xpCharles.score(pCharles);
//			assertTrue(xpJulia.compareTo(xpCharles)==0);//TODO ????
			
			/* Modifica el health de Julia y comprueba que
			 * si xpJulia invoca a compareTo, devuelve un valor >0
			 * al ser el score de xpJulia menor
			 */	
			pJulia.setHealth(10);
			xpJulia.score(pJulia);
			xpCharles.score(pCharles);
			assertTrue(xpJulia.compareTo(xpCharles) > 0);
			
			/* Modifica el foodLevel de Charles al mismo valor que
			 * el health de Julia y comprueba que compareTo devuelve 0
			 */
			pCharles.setFoodLevel(10);
			xpCharles.score(pCharles);
			xpJulia.score(pJulia);
			assertTrue(xpJulia.compareTo(xpCharles)==0);
	}
	
	/* Comparar los XPScore de xpJulia y de xpCharles sin modificar
	 * health y foodLevel pero sí añadiendo a la lista de XPScore de xpJulia 
	 * y xpCharles: un CollectedItemsScore, un MiningScore y un PlayerMovementScore 
	 * sucesivamente.
	 */
	//TODO
	@Test
	public void testCompareTo2() {
		try {
			//Añadimos un ItemScore al marcador de Julia
			cis = new CollectedItemsScore("Julia");
			ms= new MiningScore("Julia");
			pms= new PlayerMovementScore("Julia");
			
			cis.score(new ItemStack(Material.BREAD,5));
			xpJulia.addScore(cis);
			assertTrue(xpJulia.compareTo(xpCharles)<0);	
			//Añadimos el mismo ItemScore al marcador de Charles
			xpCharles.addScore(cis);
			assertTrue(xpCharles.compareTo(xpJulia)==0);
			//---------------------------------------------
			ms.score(BlockFactory.createBlock(Material.STONE));
			xpJulia.addScore(ms);
			assertTrue(xpJulia.compareTo(xpCharles) > 0);//Es positivo porque xpJulia= 53.25 y xpCharles= 65
			//Añadimos el mismo Block al marcador de Charles
			xpCharles.addScore(ms);
			assertTrue(xpCharles.compareTo(xpJulia)==0);
			//---------------------------------------------
			pms.score(new Location(world, 0,90,0));
			xpJulia.addScore(pms);
			assertTrue(xpJulia.compareTo(xpCharles) > 0);	//Positivo, xpJulia= 48.83 y xpCharles= 53.25
			//Añadimos el mismo Location al marcador de Charles
			xpCharles.addScore(pms);
			assertTrue(xpCharles.compareTo(xpJulia)==0);
			
		} catch (Exception e) {
			fail ("Error, no debió lanzar la excepcion "+e.getClass().getName());
		}
	}

	/* Comprobar que inicialmente, sin calcular, xpJulia tiene el marcador a 0. 	 
	 * Calcular el Score inicial de xpJulia, y comprobar que es 40. Modificar
	 * el health y calcular y comprobar el nuevo resultado. Hacer lo mismo con
	 * foodLevel.
	 */
	//TODO
	@Test
	public void testScorePlayer() {
		assertEquals(0, xpJulia.score,0.01);
		
		xpJulia.score(pJulia);//40
		assertEquals(40, xpJulia.score,0.01);
		
		pJulia.setHealth(15.0);
		xpJulia.score(pJulia);//35
		assertEquals(35, xpJulia.score,0.01);
		
		pJulia.setFoodLevel(5.0);//15 de vida y 5 de comida
		xpJulia.score(pJulia);//20
		assertEquals(20, xpJulia.score,0.01);
	}	
	
	//Se comprueba la excepción ScoreException en el método score
	@Test(expected=ScoreException.class)
	public void testScorePlayerException() {
		Player p = new Player("Marta",world);
		xpJulia.score(p);
	}
	
	
	/* Añadir un CollectedIntemsScore a xpJulia, y comprobar
	 * que lo que obteneis es lo esperado.
	 * Hacer lo mismo con un MininScore y un PlayerMovementScore
	 */
	//TODO
	@Test
	public void testAddScorePlayer() {
		
		try {
			cis = new CollectedItemsScore("Julia");
			ms= new MiningScore("Julia");
			pms= new PlayerMovementScore("Julia");
			
			cis.score(new ItemStack(Material.BREAD,5));
			xpJulia.addScore(cis);
			
			assertEquals(65.0, xpJulia.score, 0.01);
			
			ms.score(BlockFactory.createBlock(Material.STONE));
			xpJulia.addScore(ms);
			
			assertEquals(53.25, xpJulia.score, 0.01);
			
			pms.score(new Location(world, 0,90,0));
			xpJulia.addScore(pms);
			
			assertEquals(48.83, xpJulia.score, 0.01);
		} catch (Exception e) {
			fail("Alexa play sad despacito");
		}
		
	}

	/* Comprobar lo mismo que en el testScorePlayer1 pero con 
	 * getScore()
	 */
	//TODO
	@Test
	public void testGetScoring() {
		assertEquals(0, xpJulia.score,0.01);
		//xpJulia.score(pJulia);//40
		assertEquals(40, xpJulia.getScoring(),0.01);
		pJulia.setHealth(15.0);
		//xpJulia.score(pJulia);//35
		assertEquals(35, xpJulia.getScoring(),0.01);
		pJulia.setFoodLevel(5.0);//15 de vida y 5 de comida
		//xpJulia.score(pJulia);//20
		assertEquals(20, xpJulia.getScoring(),0.01);
	}
	
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

	/* Repite lo mismo que en testScorePlayer() y testAddScorePlayer pero usando
	 * toString() en los asseretEquals
	 */
	//TODO
	@Test
	public void testToString() {
		
		compareScores("Julia:0.0", xpJulia.toString());
		
		xpJulia.score(pJulia);
		assertEquals("Julia:40.0", xpJulia.toString());
		pJulia.setHealth(5.0);
		
		xpJulia.score(pJulia);
		assertEquals("Julia:25.0", xpJulia.toString());
		
		pJulia.setFoodLevel(15.0);
		xpJulia.score(pJulia);
		assertEquals("Julia:20.0", xpJulia.toString());
		pJulia.setHealth(20.0);
		pJulia.setFoodLevel(20.0);
		xpJulia.score(pJulia);
		
		try {
			cis = new CollectedItemsScore("Julia");
			ms= new MiningScore("Julia");
			pms= new PlayerMovementScore("Julia");
			
			cis.score(new ItemStack(Material.BREAD,5));
			xpJulia.addScore(cis);
			
			assertEquals("Julia:65.0", xpJulia.toString());
			
			ms.score(BlockFactory.createBlock(Material.STONE));
			xpJulia.addScore(ms);
			
			assertEquals("Julia:53.25", xpJulia.toString());
			
			pms.score(new Location(world, 0,90,0));
			xpJulia.addScore(pms);
			
			assertEquals("Julia:48.833333333333336", xpJulia.toString());
			
		} catch (Exception e) {
			fail ("Error, no debió lanzar la excepcion "+e.getClass().getName());
		}
		
	}	
}
