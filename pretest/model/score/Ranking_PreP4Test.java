package model.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Block;
import model.BlockFactory;
import model.BlockWorld;
import model.ItemStack;
import model.Location;
import model.Material;
import model.World;
import model.entities.Player;
import model.exceptions.score.EmptyRankingException;
//"Mining ranking = [Sara:5.0, Julia:1.5]\n" + 
public class Ranking_PreP4Test {
	
	final String kRANKING1 = 
			"Collected items ranking = [Julia:25.0]\n" + 
			"Mining ranking = [Julia:1.5]\n" + 
			"Movement ranking = [Julia:14.866068747318506]\n" + 
			"Experience ranking = [Julia:53.7886895824395]";
	
	final String kRANKING2 = "Collected items ranking = [Julia:25.0, Sara:0.5]\n" + 
			"Mining ranking = [Sara:5.0, Julia:1.5]\n"  + 
			"Movement ranking = [Sara:1.4142135623730951, Julia:14.866068747318506]\n" + 
			"Experience ranking = [Julia:53.7886895824395, Sara:42.30473785412436]";
	
	final String kRANKING3 = "Collected items ranking = [Sara:2.0]\n" + 
			"Mining ranking = [Sara:6.199999999999999]\n" + 
			"Movement ranking = [Sara:68.45151661075057]\n" + 
			"Experience ranking = [Sara:65.55050553691686]";
	
	Ranking<CollectedItemsScore> itemsRanking;
	Ranking<MiningScore> miningRanking;
	Ranking<PlayerMovementScore> movementRanking;
	Ranking<XPScore> xpRanking;
	BlockWorld game;
	World world;
	XPScore xpScore;
	
	Player pJulia, pSara, pFrank;
	CollectedItemsScore itemScore;
	MiningScore miningScore;
	PlayerMovementScore movementScore;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		itemsRanking = new Ranking<CollectedItemsScore>();
		miningRanking = new Ranking<MiningScore>();
		movementRanking = new Ranking<PlayerMovementScore>();
		xpRanking = new Ranking<XPScore>();
		
		
		game = BlockWorld.getInstance();
		world = new World(3,10,"A Little World", "Joan");
		pJulia = new Player("Julia",world);
		pSara = new Player("Sara",world);	
		pFrank = new Player("Frank",world);
	}

	/* 
	 * Se crea para Julia los 4 tipos de Scores. En el de PlayerMovementScore se a??ade inicialmente 
	 * la ubicaci??n de Julia. Se a??aden a los distintos tipos de Ranking el resto de Scores.  Se 
	 * comprueba que la salida de los ranking (construida en el m??todo auxiliar makeOutputToString())
	 * coincide con kRANKING1.
	 * Realiza lo mismo ahora para Sara, creando los 4 tipo de Scores a partir de los siguientes elementos:
	 * ItemStack(Material.IRON_PICKAXE,1)
	 * Block(Material.OBSIDIAN)
	 * Location(world,0,67,1)
	 * y a??adi??ndolos a los distintos Ranking anteriores.
	 * Comprobar que la salida con makeOutputToString(), coincide con kRANKIG2
	 */
	//TODO
	@Test
	public void testAddScore1() {
		try {
			//SCORES JULIA
			xpScore = new XPScore(pJulia);
			try {
				//A??adimos ItemStack al Score de Julia
				itemScore = new CollectedItemsScore("Julia");
				itemScore.score(new ItemStack(Material.BREAD,5));
				xpScore.addScore(itemScore);
			
				//A??adimos un MiningScore al Score de Julia
				miningScore = new MiningScore("Julia");
				miningScore.score(BlockFactory.createBlock(Material.GRANITE));
				xpScore.addScore(miningScore);
			}catch (Exception e) {
				fail ("Error, no debi?? lanzar la excepcion "+e.getClass().getName());
				}
			//A??adimos un PlayerMovementScore al marcador de Julia
			movementScore = new PlayerMovementScore("Julia");
			movementScore.score(new Location(pJulia.getLocation()));
			System.out.println(movementScore.toString());
			movementScore.score(new Location(world,-3,80,4));
			System.out.println(movementScore.toString());
			xpScore.addScore(movementScore);
			
			//A??adimos los Scores anteriores a los distintos tipos de Ranking
			itemsRanking.addScore(itemScore);
			miningRanking.addScore(miningScore);
			movementRanking.addScore(movementScore);
			xpRanking.addScore(xpScore);
			
			compareRankings(kRANKING1, makeOutputToString());
			
			//SCORES SARA
//			final String kRANKING2 = "Collected items ranking = [Julia:25.0, Sara:0.5]\n" + 
//					"Mining ranking = [Sara:5.0, Julia:1.5]\n"  + 
//					"Movement ranking = [Sara:1.4142135623730951, Julia:14.866068747318506]\n" + 
//					"Experience ranking = [Julia:53.7886895824395, Sara:42.30473785412436]";
			xpScore = new XPScore(pSara);
			try {
				//A??adimos ItemStack al Score de Julia
				itemScore = new CollectedItemsScore("Sara");
				itemScore.score(new ItemStack(Material.IRON_PICKAXE,1));//0,5
				xpScore.addScore(itemScore);
			
				//A??adimos un MiningScore al Score de Julia
				miningScore = new MiningScore("Sara");
				miningScore.score(BlockFactory.createBlock(Material.OBSIDIAN));//5
				xpScore.addScore(miningScore);
			}catch (Exception e) {
				fail ("Error, no debi?? lanzar la excepcion "+e.getClass().getName());
				}
			//A??adimos un PlayerMovementScore al marcador de Julia
			movementScore = new PlayerMovementScore("Sara");
			movementScore.score(new Location(pSara.getLocation()));//0 66 0
			System.out.println(movementScore.toString());
			movementScore.score(new Location(world,-1,67,0));//Lmao a la primera
			System.out.println(movementScore.toString());
			xpScore.addScore(movementScore);
			
			//A??adimos los Scores anteriores a los distintos tipos de Ranking
			itemsRanking.addScore(itemScore);
			miningRanking.addScore(miningScore);
			movementRanking.addScore(movementScore);
			xpRanking.addScore(xpScore);
			
			compareRankings(kRANKING2, makeOutputToString());
			
		} catch (Exception e) {
			fail ("Error, no debi?? lanzar la excepcion "+e.getClass().getName());
		}
	}

	/* Partiendo, por ejemplo del testAddScore1(), puedes a??adir nuevos elementos a los distintos Scores de
	 * los distintos Player. A??adir un nuevo player, pFrank, crear Scores para ??l y comprobar en pantalla
	 * qu?? salida obtienes (con System.out.println(makeOutputToString()); 
	 * Para evitar que te quede un test muy largo, puedes usar los m??todos de ayuda que hay al final
	 * de este test suite, para crear/a??adir nuevos valores a los distintos scores, y para a??adir ??stos
	 * a los distintos Ranking
	 */
	//TODO
	@Test
	public void testAddScore2() {//TODO Objetivo?
		try {
			addScoresGame(pSara,"Sara",null,null,new Location(pSara.getLocation()),true);
			addScoresGame(pSara,"Sara",new ItemStack(Material.IRON_PICKAXE,1),BlockFactory.createBlock(Material.OBSIDIAN),new Location(world,0,67,1),false);
			addScoresGame(pSara,"Sara",new ItemStack(Material.GRANITE,1),BlockFactory.createBlock(Material.GRASS),null,false);
			addScoresGame(pSara,"Sara",null,BlockFactory.createBlock(Material.GRASS),new Location(world,2,0,2),false);
			
			addScoresToRanking ();
			
			addScoresGame(pFrank,"Frank",null,null,new Location(pFrank.getLocation()),true);
			addScoresGame(pFrank,"Frank",new ItemStack(Material.BEEF,5),BlockFactory.createBlock(Material.GRANITE),new Location(world,3,68,1),false);
			addScoresGame(pFrank,"Frank",new ItemStack(Material.GRANITE,1),BlockFactory.createBlock(Material.GRASS),null,false);
			addScoresGame(pFrank,"Frank",null,BlockFactory.createBlock(Material.GRASS),new Location(world,2,0,2),false);
			
			addScoresToRanking ();
			
			addScoresGame(pJulia,"Julia",null,null,new Location(pJulia.getLocation()),true);
			addScoresGame(pJulia,"Julia",new ItemStack(Material.BREAD,5),BlockFactory.createBlock(Material.GRASS),new Location(world,0,80,-1),false);
			addScoresGame(pJulia,"Julia",new ItemStack(Material.GRANITE,1),BlockFactory.createBlock(Material.GRASS),null,false);
			addScoresGame(pJulia,"Julia",null,BlockFactory.createBlock(Material.GRASS),new Location(world,2,0,2),false);
			
			addScoresToRanking ();
			
		} catch (Exception e) {
			fail ("Error, no debi?? lanzar la excepcion "+e.getClass().getName());
		}
	}

	/* Comprobaci??n de la excepci??n EmptyRankingException con getWinner()
	 * cuando no hay valores en los rankings.
	 */
	@Test(expected=EmptyRankingException.class)
	public void testGetWinnerException() throws EmptyRankingException {
		try {
			itemsRanking.getWinner();
		} catch (EmptyRankingException e) {
			try {
				miningRanking.getWinner();
			} catch (EmptyRankingException e1) {
				try {
					movementRanking.getWinner();
				} catch (EmptyRankingException e2) {
					xpRanking.getWinner();
				}
			}
		}
	}

	

	
	
	/******************************************************************/
	//FUNCIONES DE APOYO
	/* Permite, para el Player 'pl', incrementar los distintos Scores si newScores es false, o
	 * iniciar los distintos Scores para el Player 'pl' si newScores es true. Si un determinado score no 
	 * queremos incrementarlo, se pasa null por par??metro 
	 */
	private void addScoresGame (Player pl, String name, ItemStack is, Block b, Location loc, boolean newScores) {
		if (newScores) {
			xpScore = new XPScore(pl);
			itemScore = new CollectedItemsScore(name);
			miningScore = new MiningScore(name);
			movementScore = new PlayerMovementScore(name);
		}
		try {
			//Anadimos el valor del ItemStack al CollectedItemScore 
			if (is!=null) {
				itemScore.score(is);
			}
			//A??adimos un Block al Score 
			if (b!=null) {
				miningScore.score(b);
			}
		}catch (Exception e) {
			fail ("Error, no debi?? lanzar la excepcion "+e.getClass().getName());
			}
		//A??adimos un PlayerMovementScore al marcador del Player pl
		if (loc!=null) {
			movementScore.score(new Location(loc));
			
		}
	}
	
	
	/* A??ade los distintos valores de los Scores a los Ranking*/
	private void addScoresToRanking () {
		xpScore.addScore(itemScore); 
		xpScore.addScore(miningScore); 
		xpScore.addScore(movementScore);
		itemsRanking.addScore(itemScore);
		miningRanking.addScore(miningScore);
		movementRanking.addScore(movementScore);
		xpRanking.addScore(xpScore);
	}
	
	
	
	/* Para las salidas Score.toString() compara los valores impresos
	 * de los Scores hasta una precisi??n de 0.01
	 * 
	 */
	private void compareScores(String expected, String result ) {
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
	
	
	/* Se usa para comparar los valores impresos de los rankings con una precisi??n 
	 * de 0.01
	 * [Sara:1.4142135623730951, Julia:14.866068747318506]"
	 */
	private void compareRankings(String expected, String result) {
		String exp[]=expected.split("\n");
		String res[]=result.split("\n");
		boolean iguales = true;
		for (int i=0; i<exp.length && iguales; i++) {
			
			String exp1[]=exp[i].split(", ");
			String res1[]=res[i].split(", ");
			if (exp1.length!=res1.length) 
				fail("Error: cantidad de jugadores distintos en el ranking "+res[i]);
			for (int k=0; k<exp1.length; k++) { //Para cada Score
				if (k==exp1.length-1) //Es el ??ltimo Score
					compareScores(exp1[k].substring(0, exp1[k].indexOf(']')).trim(),res1[k].substring(0, res1[k].indexOf(']')).trim());
				else 
					compareScores(exp1[k].trim(),res1[k].trim());
				
			}
		
		}
	}
	
	/* Construye la salida como String, con los distintos Ranking */
	private String makeOutputToString () {
		StringBuilder ps = new StringBuilder();
		ps.append("Collected items ranking = "+itemsRanking.getSortedRanking()+"\n");
		ps.append("Mining ranking = "+miningRanking.getSortedRanking()+"\n");//wtf
		ps.append("Movement ranking = "+movementRanking.getSortedRanking()+"\n");
		ps.append("Experience ranking = "+xpRanking.getSortedRanking());
		return ps.toString();
	}
}
