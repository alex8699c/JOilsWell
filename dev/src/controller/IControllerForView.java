package controller;

import java.util.ArrayList;

import model.Mine;
import model.Monster;

public interface IControllerForView {
	
	public void startGUI();
	
	public void initGame();
	
	public void endGame(int type);
	
	public int[][] getMap();
	
	public int getScore();
	
	public boolean moveDrill(int direction);
	
	public int getDrillX();
	
	public int getDrillY();
	
	public void retract();
	
	public int getPipelineDirection(int x, int y);
	
	public int getDrillDirection();
	
	public ArrayList<Monster> getMonsters();
	
	public ArrayList<Mine> getMines();
	
	public int getEntityDirection(int type, int i);
	
	public int getMonsterType(int i);
	
	public double getEntityX(int type, int i);
	
	public double getEntityY(int type, int i);
	
	public int getNumberOfLives();
	
	public int getLevel();
	
	public void exitGame(int type);
	
	public void playSoundForGUI(int sound, int g);
	
	public void controlTimer(int c);
	
	public int getRemainingTime();
}
