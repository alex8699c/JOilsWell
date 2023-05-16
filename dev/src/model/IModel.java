package model;

import java.util.ArrayList;

public interface IModel {
	
	public void initGame();
	
	public void endGame(int type);
	
	public int[][] getMap();
	
	public boolean moveDrill(int direction);
	
	public void retract();
	
	public int getDrillX();
	
	public int getDrillY();
	
	public int getPipelineDirection(int x, int y);
	
	public int getDrillDirection();
	
	public ArrayList<Monster> getMonsters();
	
	public ArrayList<Mine> getMines();
	
	public int getEntityDirection(int type, int i);
	
	public int getMonsterType(int i);
	
	public double getEntityX(int type, int i);
	
	public double getEntityY(int type, int i);
	
	public int getNumberOfLives();
	
	public int getScore();
	
	public int getLevel();
	
	public void exitGame(int type);
	
	public void playSoundEffectForGUI(int sound, int g);
	
	public void controlTimer(int c);
	
	public int getRemainingTime();
}
