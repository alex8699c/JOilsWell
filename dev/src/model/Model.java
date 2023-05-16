package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import javax.swing.Timer;
import utils.Config;
import utils.RecordManager;
import controller.ControllerForModel;

public class Model implements IModel, ActionListener{
	
	/*-------------------
	 *-----ATTRIBUTI-----
	 *-------------------*/
	
	protected final static int ROWS = 18;
	protected final static int COLUMNS = 25;
	
	private final static double ENTITIES_VELOCITY_SLOW;
	private final static double ENTITIES_VELOCITY_MEDIUM;
	private final static double ENTITIES_VELOCITY_FAST;
	
	private final static int ENTITIES_SPAWN_TIMER_SLOW;
	private final static int ENTITIES_SPAWN_TIMER_MEDIUM;
	private final static int ENTITIES_SPAWN_TIMER_FAST;
	
	private final static int NUMBER_OF_PELLETS;
	
	private final static int GOBLIN_POINTS;
	private final static int SPIDER_POINTS;
	private final static int SNAKE_POINTS;
	
	private final static int PELLET_POINTS;
	
	private final static int[] ENTITIES_ID = {0,1};
	private final static double[] ENTITIES_PROBABILITY;
	
	private final static int[] MONSTERS_TYPE_ID = {0,1,2};
	private final static double[] MONSTERS_TYPE_PROBABILITY;
	
	private final static int REMAINING_TIME_SIMPLE;
	private final static int REMAINING_TIME_MEDIUM;
	private final static int REMAINING_TIME_DIFFICULT;
	
	private int[][] MAP;
	
	private ArrayList<Pellet> PELLETS;
	private ArrayList<Monster> MONSTERS;
	private ArrayList<Mine> MINES;
	private Stack<Pipeline> PIPELINES;
	
	private Drill DRILL;
	
	private int SCORE;
	private int NUMBER_OF_LIVES;
	private int LEVEL;
	private int NUMBER_OF_LEVELS;
	private int DIFFICULTY;
	
	private SoundPlayer clip;
	
	private boolean EFFECTS, MUSIC;

	private static Random rand = new Random();
	
	private int lastRow = 1;
	private int lastDirection = 0;
	
	private int time_remaining;
	private static int currentRecord;
	
	private double entitiesShift;
	
	private boolean gameOn = false;
	private boolean gameOver = false;
	
	private Timer ENTITIES_SPAWN_TIMER, ENTITIES_MOVING_TIMER, DRILL_RETRACTION_TIMER, TIMER;
	
	private static Model instance = null;
	
	//Blocco statico: assegna alle costanti statiche valori
	static {
		ENTITIES_VELOCITY_SLOW = Config.getInstance().getEntitiesVelocity(0);
		ENTITIES_VELOCITY_MEDIUM = Config.getInstance().getEntitiesVelocity(1);
		ENTITIES_VELOCITY_FAST = Config.getInstance().getEntitiesVelocity(2);
		
		ENTITIES_SPAWN_TIMER_SLOW = Config.getInstance().getSpawnerTime(0);
		ENTITIES_SPAWN_TIMER_MEDIUM = Config.getInstance().getSpawnerTime(1);
		ENTITIES_SPAWN_TIMER_FAST = Config.getInstance().getSpawnerTime(2);
		
		NUMBER_OF_PELLETS = Config.getInstance().getNumberOfPellets();
		
		GOBLIN_POINTS = Config.getInstance().getMonsterPoints(0);
		SPIDER_POINTS = Config.getInstance().getMonsterPoints(1);
		SNAKE_POINTS = Config.getInstance().getMonsterPoints(2);
		
		PELLET_POINTS = Config.getInstance().getPelletPoints();
		
		ENTITIES_PROBABILITY = new double[]{Config.getInstance().getEntityProbability(0),
				Config.getInstance().getEntityProbability(1)};
	
		MONSTERS_TYPE_PROBABILITY = new double[]{Config.getInstance().getMonsterTypeProbability(0),
				Config.getInstance().getMonsterTypeProbability(1),Config.getInstance().getMonsterTypeProbability(2)};
		
		REMAINING_TIME_SIMPLE = Config.getInstance().getRemainingTime(1);
		REMAINING_TIME_MEDIUM = Config.getInstance().getRemainingTime(2);
		REMAINING_TIME_DIFFICULT = Config.getInstance().getRemainingTime(3);
	}
	
	//Costruttore
	public Model() {
		//vuoto
	}
	
	public static IModel getInstance() {
		if (instance == null)
			instance = new Model();
		return instance;
	}
	
	//Nuova partita
	public void initGame() {
		this.gameOver = false;
		this.gameOn = true;
		this.NUMBER_OF_LIVES = 3;
		this.LEVEL = 1;
		this.SCORE = 0;
		this.NUMBER_OF_LEVELS = ControllerForModel.getInstance().getNumberOfLevels();
		this.DIFFICULTY = ControllerForModel.getInstance().getDifficulty();
		
		this.DRILL = new Drill();
		
		this.PELLETS = new ArrayList<Pellet>();
		this.MONSTERS = new ArrayList<Monster>();
		this.MINES = new ArrayList<Mine>();
		this.PIPELINES = new Stack<Pipeline>();
		
		this.createMap();
		this.distributePellets();
		
		this.DRILL.move(0, 12);
		this.PIPELINES.push(new Pipeline(0,12,0));
		
		this.resetTime();
		
		this.setSpawnDelay();
		this.ENTITIES_MOVING_TIMER = new Timer(10,this);
		this.DRILL_RETRACTION_TIMER = new Timer(50,this);
		this.TIMER = new Timer(1000,this);
		this.ENTITIES_SPAWN_TIMER.setInitialDelay(1000);
		this.ENTITIES_SPAWN_TIMER.start();
		this.ENTITIES_MOVING_TIMER.start();
		this.TIMER.start();
		
		this.entitiesShift = this.getEntitiesShift();
		
		if(ControllerForModel.getInstance().effectsOn()) {
			this.EFFECTS = true;
			this.clip.playSoundEffect("start.wav");
		}
		else
			this.EFFECTS = false;
		
		if(ControllerForModel.getInstance().musicOn()) {
			this.MUSIC = true;
			this.clip.playMusic("soundtrack.mp3");
		}
		else
			this.MUSIC = false;
		
		try {
			currentRecord = RecordManager.getRecord(this.DIFFICULTY, this.NUMBER_OF_LEVELS);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//Fine partita
	public void endGame(int type) {
		this.gameOn = false;
		this.TIMER.stop();
		this.ENTITIES_SPAWN_TIMER.stop();
		this.ENTITIES_MOVING_TIMER.stop();
		switch(type) {
			//vittoria
			case 0:
				if(EFFECTS)
					this.clip.playSoundEffect("win.wav");
				break;
			//vittoria con record
			case 1:
				try {
					if(this.SCORE > RecordManager.getRecord(this.DIFFICULTY, this.NUMBER_OF_LEVELS))
						RecordManager.updateRecord(this.DIFFICULTY, this.NUMBER_OF_LEVELS, this.SCORE);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(EFFECTS)
					this.clip.playSoundEffect("win.wav");
				break;
			//game over
			case 2:
				this.gameOver = true;
				if(EFFECTS)
					this.clip.playSoundEffect("gameOver.wav");
				break;
		}
		if(this.MUSIC)
			this.clip.stopMusic();
		ControllerForModel.getInstance().endGameGui(type);
	}
	
	//Pausa/abbandono partita
	public void exitGame(int type){
		if(type == 0) {
			this.ENTITIES_SPAWN_TIMER.stop();
			this.ENTITIES_MOVING_TIMER.stop();
		}
		else {
			this.ENTITIES_SPAWN_TIMER.start();
			this.ENTITIES_MOVING_TIMER.start();
		}
	}
	
	//Restituisce la mappa
	public int[][] getMap(){
		return this.MAP;
	}
	
	//Restituisce la riga della sonda
	public int getDrillX() {
		return this.DRILL.getX();
	}
	
	//Restituisce la colonna della sonda
	public int getDrillY() {
		return this.DRILL.getY();
	}
	
	//Restituisce il punteggio
	public int getScore() {
		return this.SCORE;
	}
	
	//Restituisce la direzione di un oggetto Pipeline
	public int getPipelineDirection(int x, int y) {
		int DIR = 0;
		for(int k = 0; k < this.PIPELINES.size(); k++) {
			if(this.PIPELINES.get(k).getX() == x && this.PIPELINES.get(k).getY() == y) {
				DIR = this.PIPELINES.get(k).getDirection();
			}
		}
		return DIR;
	}
	
	//Restituisce la direzione della sonda
	public int getDrillDirection() {
		return this.DRILL.getDirection();
	}
	
	//Restituisce i mostri
	public ArrayList<Monster> getMonsters() {
		return this.MONSTERS;
	}
	
	//Restituisce le mine
	public ArrayList<Mine> getMines(){
	    return this.MINES;
	}
	
	//Restituisce la direzione di un'entità (0 = sinistra, 1 = destra)
	public int getEntityDirection(int type, int i) {
		if(type == 0)
			return this.MONSTERS.get(i).getDirection();
		else
			return this.MINES.get(i).getDirection();
	}
	
	//Restituisce la coordinata X di un'entità
	public double getEntityX(int type, int i) {
		if(type == 0)
			return this.MONSTERS.get(i).getX();
		else
			return this.MINES.get(i).getX();
	}
	
	//Restituisce la coordinata Y di un'entità
	public double getEntityY(int type, int i) {
		if(type == 0)
			return this.MONSTERS.get(i).getY();
		else
			return this.MINES.get(i).getY(); 
	}
	
	//Restituisce il tipo di un mostro
	public int getMonsterType(int i) {
		return this.MONSTERS.get(i).getType();
	}
	
	//Restituisce il numero di vite rimanenti
	public int getNumberOfLives() {
		return this.NUMBER_OF_LIVES;
	}
	
	//Restituisce il tempo rimanente
	public int getRemainingTime() {
		return this.time_remaining;
	}
	
	//Restituisce il livello
	public int getLevel() {
		return this.LEVEL;
	}

	//Crea un oggetto della classe SoundPlayer oppure avvia un effetto sonoro del menu
	public void playSoundEffectForGUI(int sound, int g) {
		if(g == 0)
			this.clip = new SoundPlayer();
		else
			this.clip.playSoundEffect("menu.wav");
	}
	
	//Inizia o ferma il timer del tempo rimanente di gioco (serve per GUI)
	public void controlTimer(int c) {
		//start timer
		if(c == 0)
			this.TIMER.start();
		//stop timer
		else
			this.TIMER.stop();
	}
	
	/* Muove la sonda nella mappa in base alla direzione:
	 * 0 -> giù
	 * 1 -> sù
	 * 2 -> sinistra
	 * 3 -> destra
	 * controlla se è possibile muovere la sonda prima di farlo*/
	public boolean moveDrill(int direction) {
		if(this.gameOn == false)
			return false;
		int x = this.DRILL.getX();
		int y = this.DRILL.getY();
		int x_shift = 0;
		int y_shift = 0;
		switch(direction) {
		case 0:
			if(x == ROWS-2)
				return false;
			x_shift = 1;
		    break;
		case 1:
			if(x == 0)
				return false;
			x_shift = -1;
			break;
		case 2:
			if(y == 0)
				return false;
			y_shift = -1;
			break;
		case 3:
			if(y == COLUMNS-1)
				return false;
			y_shift = 1;
			break;
		}	
		if(this.MAP[x+x_shift][y+y_shift] == 0 || this.MAP[x+x_shift][y+y_shift] == 5) {
			if(EFFECTS && (x+y)%2==0)
				this.clip.playSoundEffect("drillMove.wav");
			this.DRILL.move(x+x_shift,y+y_shift);
			this.DRILL.setDirection(direction);
			this.PIPELINES.push(new Pipeline(x, y, direction));
			this.MAP[x][y] = 6;
			if(this.MAP[x+x_shift][y+y_shift] == 5) {
				this.MAP[x+x_shift][y+y_shift] = 0;
				this.eatPellet(x+x_shift,y+y_shift);
			}
			this.checkCollision(direction);
			return true;
		}
		return false;
	}
	
	
	//Ritrae la sonda nella sua posizione di prima
	public void retract() {
		int X = this.DRILL.getX();
		int Y = this.DRILL.getY();
		if(X > 0 && !this.PIPELINES.isEmpty()) {
			if(EFFECTS && (X+Y)%2 == 0)
				this.clip.playSoundEffect("retract.wav");
			this.MAP[X][Y] = 0;
			this.DRILL.move(this.PIPELINES.peek().getX(), this.PIPELINES.peek().getY());
			this.DRILL.setDirection(this.PIPELINES.peek().getDirection());
			this.MAP[this.PIPELINES.peek().getX()][this.PIPELINES.peek().getY()] = 7;
			this.PIPELINES.pop();
			this.DRILL.setDirection(this.PIPELINES.peek().getDirection());
		}
	}
	
	/*------------------------
	 *-----METODI PRIVATI-----
	 *------------------------*/
	
	//Nuovo livello
	private void newLevel() {
		this.LEVEL++;
		ControllerForModel.getInstance().newLevel(this.LEVEL);
		this.PELLETS = new ArrayList<Pellet>();
		this.MONSTERS = new ArrayList<Monster>();
		this.MINES = new ArrayList<Mine>();
		this.PIPELINES = new Stack<Pipeline>();
		this.DRILL.move(0, 12);
		this.PIPELINES.push(new Pipeline(0,12,0));
		this.createMap();
		this.distributePellets();
		this.resetTime();
		this.ENTITIES_MOVING_TIMER.stop();
		this.ENTITIES_SPAWN_TIMER.stop();
		this.setSpawnDelay();
		this.entitiesShift = this.getEntitiesShift();
		this.ENTITIES_MOVING_TIMER.start();
		this.ENTITIES_SPAWN_TIMER.start();
	}
	
	//Crea la mappa
	private void createMap() {
		this.MAP = new int[ROWS][COLUMNS];
		for(int i = 0; i < COLUMNS; i++) { //crea prima riga di muri, lascia aperta l'apertura
			if(i != 12) {
				this.MAP[0][i] = 3;
			}
		}
		for(int i = 0; i < COLUMNS; i++) {	//crea ultima riga di muri (fondo)
			this.MAP[ROWS-1][i] = 4;
		}
		switch(this.LEVEL) {
			case 1:
				this.createRowOfWalls(2,3);
				this.createRowOfWalls(5,3);
				this.createRowOfWalls(8,3);
				this.createRowOfWalls(11,3);
				this.createRowOfWalls(14,3);
				break;
			case 2:
				this.createRowOfWalls(2,2);
				this.createRowOfWalls(5,3);
				this.createRowOfWalls(8,2);
				this.createRowOfWalls(11,3);
				this.createRowOfWalls(14,2);
				break;
			case 3:
				this.createRowOfWalls(2,2);
				this.createRowOfWalls(5,2);
				this.createRowOfWalls(8,2);
				this.createRowOfWalls(11,2);
				this.createRowOfWalls(14,2);
				break;
		}
	}
	
	//Distribuisce i pellet sulla mappa
	private void distributePellets() {
		int X;
		int Y;
		int i = 0;
		while(i < NUMBER_OF_PELLETS) {
			X = rand.nextInt(ROWS);
			Y = rand.nextInt(COLUMNS);
			if(this.MAP[X][Y] == 0 && X != 0) {
				this.MAP[X][Y] = 5;
				this.PELLETS.add(new Pellet(X,Y));
				i++;
			}
		}
	}
	
	/* Crea una riga di muri nella mappa.
	 * ROW = indice riga
	 * TYPE = numero di aperture*/
	private void createRowOfWalls(int row, int type) {
		if(type == 2) {
			int rand1 = rand.nextInt(11);  //primo indice apertura
			int rand2 = 13 + rand.nextInt(10);  //secondo indice apertura
			
			for(int i = 0; i <= rand1; i++) {
				this.MAP[row][i] = 1;
				this.MAP[row+1][i] = 2;
				
			}
			for(int i = (rand1+2); i <= rand2; i++) {
				this.MAP[row][i] = 1;
				this.MAP[row+1][i] = 2;
				
			}
			for(int i = (rand2+2); i < COLUMNS; i++) {
				this.MAP[row][i] = 1;
				this.MAP[row+1][i] = 2;
			}
		}
		else {
			int rand1 = rand.nextInt(7);  //primo indice apertura
			int rand2 = 9 + rand.nextInt(7);  //secondo indice apertura
			int rand3 = 18 + rand.nextInt(5);  //terzo indice apertura

			for(int i = 0; i <= rand1; i++) {
				this.MAP[row][i] = 1;
				this.MAP[row+1][i] = 2;
			}
			for(int i = (rand1+2); i <= rand2; i++) {
				this.MAP[row][i] = 1;
				this.MAP[row+1][i] = 2;
			}
			for(int i = (rand2+2); i <= rand3; i++) {
				this.MAP[row][i] = 1;
				this.MAP[row+1][i] = 2;
			}
			for(int i = (rand3+2); i < COLUMNS; i++) {
				this.MAP[row][i] = 1;
				this.MAP[row+1][i] = 2;
			}
		}
	}
	
	//Gestisce il delay di spawn di entità (con casualità)
	private void setSpawnDelay() {
		switch(this.DIFFICULTY) {
			case 1:
			    this.ENTITIES_SPAWN_TIMER = new Timer(ENTITIES_SPAWN_TIMER_SLOW-rand.nextInt(100-rand.nextInt(LEVEL*10)),this);
			    break;
			case 2:
			    this.ENTITIES_SPAWN_TIMER = new Timer(ENTITIES_SPAWN_TIMER_MEDIUM-rand.nextInt(100-rand.nextInt(LEVEL*10)),this);
			    break;
			case 3:
			    this.ENTITIES_SPAWN_TIMER = new Timer(ENTITIES_SPAWN_TIMER_FAST-rand.nextInt(100-rand.nextInt(LEVEL*10)),this);
			    break;
			}
	}

	//Genera un mostro
	private void spawnMonster(int row, int type, int direction) {
	    Monster M = new Monster(row,type,direction);
		if(direction == 0)
			M.setCoordinates((COLUMNS)*30, row*20);
		else
			M.setCoordinates(-30, row*20);
		this.MONSTERS.add(M);
	}
	
	//Genera una mina
	private void spawnMine(int row, int direction) {
	    Mine M = new Mine(row, direction);
		if(direction == 0)
			M.setCoordinates((COLUMNS)*30, row*20);
		else
			M.setCoordinates(-30, row*20);
		this.MINES.add(M);
	}
	
	/* Toglie il mostro dalla lista perché mangiato dalla sonda.
	 * Aggiorna il punteggio in base a quale tipo*/
	private void eatMonster(int i) {
		if(EFFECTS)
			this.clip.playSoundEffect("monsterEaten.wav");
		switch(this.MONSTERS.get(i).getType()) {
		case 0:
			this.SCORE += GOBLIN_POINTS;
			break;
		case 1:
			this.SCORE += SPIDER_POINTS;
			break;
		case 2:
			this.SCORE += SNAKE_POINTS;
			break;
		}
		ControllerForModel.getInstance().updateBoardPanel();
		this.MONSTERS.remove(i);
	}
	
	//Muove un'entità
	private void moveEntity(int type, int i, double shift) {
		
		//questi due sono indici della matrice MAP (X per le righe, Y per le colonne)
		int drillX = this.DRILL.getX();
		int drillY = this.DRILL.getY();
		
		//questi due sono invece coordinate sul frame
		double entityX, entityY;
		
		int entityDirection;
		
		//spazio tra le coordinate dell'entità e la punta della sua immagine
		double horizontalGap;		
		
		switch(type) {
			case 0:
				//i mostri di tipo 2 sono più veloci
				int monsterType = this.MONSTERS.get(i).getType();
				double Mshift = shift;
				if(monsterType == 2)
					Mshift = shift + 0.25;
				this.MONSTERS.get(i).move(Mshift);
				
				entityDirection = this.MONSTERS.get(i).getDirection();
				if(entityDirection == 0)
					horizontalGap = -10.0;
				else
					horizontalGap = 10.0;
				entityX = this.MONSTERS.get(i).getX();
				entityY = this.MONSTERS.get(i).getY();
				
				//approssimazione intera delle coordinate double
				int monsterXInt = (int)(entityX);
				int monsterYInt = (int)(entityY);
				
				
				
				//se incontra la sonda allora viene mangiato
				if(entityX+horizontalGap >= drillY*30 && entityX+horizontalGap <= drillY*30+10.0
					&& entityY == drillX*20) {
						this.eatMonster(i);
					}
				
				//se incontra il tubo allora c'è una kill
				if((monsterXInt+(int)horizontalGap)/30 > 0 && (monsterXInt+(int)horizontalGap)/30 < 25 &&
						(monsterXInt+(int)horizontalGap)%30 == 0) {
					if(this.MAP[monsterYInt/20][(monsterXInt+(int)horizontalGap)/30] == 6) {
						this.killedByEntity(0, i);
					}
				}
				
				//se il mostro arriva a fine corsa, viene eliminato
				if((entityY < -20 && entityDirection == 0) || (entityY > 740 && entityDirection == 1)) {
					this.deleteEntity(0, i);
				}
				break;
			case 1:
				this.MINES.get(i).move(shift);
				entityDirection = this.MINES.get(i).getDirection();
				if(entityDirection == 0)
					horizontalGap = -10.0;
				else
					horizontalGap = 10.0;
				entityX = this.MINES.get(i).getX();
				entityY = this.MINES.get(i).getY();
				
				//se la mina incontra la sonda allora c'è una kill
				if(entityX+horizontalGap >= drillY*30 && entityX+horizontalGap <= drillY*30+10.0
					&& entityY == drillX*20) {
					this.killedByEntity(1, i);
				}
				
				//se la mina arriva a fine corsa, viene eliminata
				if((entityY < -20 && entityDirection == 0) || (entityY > 740 && entityDirection == 1)) {
					this.deleteEntity(1, i);
				}
				break;
		}
	}
	
	
	/*Se la sonda si sposta a sinistra o destra,
	 * controlla se viene a contatto con qualche entità*/
	private void checkCollision(int direction) {
		
		//coordinate delle entità sul pannello
		double entityX, entityY;
		
		//coordinate della sonda sul pannello
		double drillX = this.DRILL.getY();
		double drillY = this.DRILL.getX();
		
		//controllo per mostri, in caso mangia
		if(this.MONSTERS.size() > 0) {
			for(int i = 0; i < this.MONSTERS.size(); i++) {
				entityX = this.MONSTERS.get(i).getX();
				entityY = this.MONSTERS.get(i).getY();
				switch(direction) {
					case 0:
						if(drillX*30+10.0 >= entityX && drillX*30+20.0 <= entityX+20.0
						&& entityY == drillY*20) {
							this.eatMonster(i);
						}
						break;
					case 1:
						if(drillX*30+10.0 >= entityX && drillX*30+20.0 <= entityX+20.0
						&& entityY == drillY*20) {
							this.eatMonster(i);
						}
						break;
					case 2:
						if(drillX*30 <= entityX && drillX*30+30.0 >= entityX
						&&  entityY == drillY*20) {
							this.eatMonster(i);
						}
						break;
					case 3:
						if(drillX*30+10.0 >= entityX && drillX*30-20.0 <= entityX
						&&  entityY == drillY*20) {
							this.eatMonster(i);
						break;
						}
				}
			}
		}
		
		//controllo per mine, in caso è kill
		if(this.MINES.size() > 0) {
			for(int i = 0; i < this.MINES.size(); i++) {
				entityX = this.MINES.get(i).getX();
				entityY = this.MINES.get(i).getY();
				if(direction == 2) {
					if(drillX*30 <= entityX && drillX*30+30.0 >= entityX
							&&  entityY == drillY*20) {
						this.killedByEntity(1, i);
							}
				}
				else {
					if(drillX*30+10.0 >= entityX && drillX*30-20.0 <= entityX
							&&  entityY == drillY*20) {
						this.killedByEntity(1, i);
							}
				}
			}
		}
	}
	
	/*Toglie l'entità dalla propria lista
	perché è uscita dalla mappa.
	TYPE = tipo di entità
	0 = mostro
	altro = mina*/
	private void deleteEntity(int type, int i) {
	    if(type == 0)
	    	this.MONSTERS.remove(i);
	    else
	    	this.MINES.remove(i);
	}
	
	/* Gestisce le uccisioni.
	 * type = tipo di entità
	 * 0 = mostro
	 * 1 = mina
	 * i = indice lista rispettiva*/
	private void killedByEntity(int type, int i) {
		this.gameOn = false;
		this.ENTITIES_MOVING_TIMER.stop();
		if(EFFECTS)
			this.clip.playSoundEffect("entityHit.wav");
	    if(type == 0)
	    	this.MONSTERS.remove(i);
	    else
	    	this.MINES.remove(i);
	    
		if(this.NUMBER_OF_LIVES > 1)
			this.NUMBER_OF_LIVES--;
		else {
			this.gameOver = true;
			this.endGame(2);
		}
		ControllerForModel.getInstance().updateBoardPanel();
		this.DRILL_RETRACTION_TIMER.start();
	}

	//Mangia un Pellet
	private void eatPellet(int X, int Y) {
		ControllerForModel.getInstance().updateBoardPanel();
		if(EFFECTS)
			this.clip.playSoundEffect("pelletEaten.wav");
		for(int i = 0; i < this.PELLETS.size(); i++) {
			if(this.PELLETS.get(i).getX() == X && this.PELLETS.get(i).getY() == Y) {
				this.PELLETS.remove(i);
				this.SCORE += PELLET_POINTS;
			}
		}
		if(this.PELLETS.size() == 0 && this.LEVEL == this.NUMBER_OF_LEVELS) {
			if(this.SCORE > currentRecord)
				this.endGame(1);
			else
				this.endGame(0);
		}
		else if(this.PELLETS.size() == 0 && this.LEVEL < this.NUMBER_OF_LEVELS) 
			this.newLevel();
	}
	
	//Restituisce l'indice casuale di un tunnel
	private int chooseRow() {
		int[] rows = {1,4,7,10,13,16};
		int row = rows[rand.nextInt(6)];
		while(row == this.lastRow) {
			row = rows[rand.nextInt(6)];
		}
		this.lastRow = row;
		return row;
	}
	
	//Restituisce lo shift in base al livello
	private double getEntitiesShift() {
		double shift = ENTITIES_VELOCITY_SLOW;
		switch(this.LEVEL) {
			case 1:
				shift = ENTITIES_VELOCITY_SLOW;
				break;
			case 2:
				shift = ENTITIES_VELOCITY_MEDIUM;
				break;
			case 3:
				shift = ENTITIES_VELOCITY_FAST;
				break;
		}
		return shift;
	}
	
	//Fissa il tempo a disposizione per ogni livello in base alla difficoltà
	private void resetTime() {
		switch(this.DIFFICULTY) {
		case 1:
			time_remaining = REMAINING_TIME_SIMPLE;
			break;
		case 2:
			time_remaining = REMAINING_TIME_MEDIUM;
			break;
		case 3:
			time_remaining = REMAINING_TIME_DIFFICULT;
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Timer: gestisce lo spawn delle entità
		if(e.getSource() == this.ENTITIES_SPAWN_TIMER) {
			
			//la direzione cambia per ogni spawn
			int direction;
			if(this.lastDirection == 0) {
				direction = 1;
				this.lastDirection = 1;
			}
			else {
				direction = 0;
				this.lastDirection = 0;
			}
			
			//decide quale entità generare
			if(RandomElement.selectRandom(ENTITIES_ID, ENTITIES_PROBABILITY) == 0)
				this.spawnMonster(this.chooseRow(), RandomElement.selectRandom(MONSTERS_TYPE_ID, MONSTERS_TYPE_PROBABILITY), direction);
			else
				this.spawnMine(this.chooseRow(), direction);
		}
		
		//Timer: gestisce il movimento delle entità
		else if(e.getSource() == this.ENTITIES_MOVING_TIMER) {
			if(this.MONSTERS.size() > 0) {
				for(int i = 0; i < this.MONSTERS.size(); i++) {
					this.moveEntity(0, i, this.entitiesShift);
				}
			}
			if(this.MINES.size() > 0) {
				for(int i = 0; i < this.MINES.size(); i++) {
					this.moveEntity(1, i, this.entitiesShift);
				}
			}
			ControllerForModel.getInstance().updateMazePanel();
		}
		
		//Timer: gestisce la ritrazione completa della sonda dopo una kill
		else if(e.getSource() == this.DRILL_RETRACTION_TIMER) {
			if(!gameOver) {
				if(this.DRILL.getX() > 0) {
					this.retract();
				}
				else {
					this.gameOn = true;
					this.DRILL_RETRACTION_TIMER.stop();
					this.ENTITIES_MOVING_TIMER.start();
				}
				ControllerForModel.getInstance().updateMazePanel();
			}
		}
		
		//Timer: gestisce il timer del tempo rimanente di gioco
		else if(e.getSource() == this.TIMER) {
			this.time_remaining--;
			ControllerForModel.getInstance().updateBoardPanel();
			if(this.time_remaining == 0) {
				this.endGame(2);
			}
		}
	}

}
