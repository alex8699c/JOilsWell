package controller;

import view.View;

import java.util.ArrayList;

import model.Mine;
import model.Model;
import model.Monster;

public class ControllerForView implements IControllerForView{
	
	private static ControllerForView instance = null;
	
	public ControllerForView() {
		//vuoto
	}
	
	public void startGUI() {
		View.getInstance().startGUI();
	}
	
	public void initGame() {
		Model.getInstance().initGame();
	}
	
	public void endGame(int type) {
		Model.getInstance().endGame(type);
	}
	
	public int[][] getMap(){
		return Model.getInstance().getMap();
	}
	
	public boolean moveDrill(int direction) {
		return Model.getInstance().moveDrill(direction);
	}
	
	public void retract() {
		Model.getInstance().retract();
	}
	
	public int getDrillX() {
		return Model.getInstance().getDrillX();
	}
	
	public int getDrillY() {
		return Model.getInstance().getDrillY();
	}
	
	public int getPipelineDirection(int x, int y) {
		return Model.getInstance().getPipelineDirection(x,y);
	}
	
	public int getDrillDirection() {
		return Model.getInstance().getDrillDirection();
	}
	
	public ArrayList<Monster> getMonsters(){
		return Model.getInstance().getMonsters();
	}
	
	public ArrayList<Mine> getMines(){
		return Model.getInstance().getMines();
	}
	
	public int getEntityDirection(int type, int i) {
		return Model.getInstance().getEntityDirection(type, i);
	}
	public int getMonsterType(int i) {
		return Model.getInstance().getMonsterType(i);
	}
	
	public double getEntityX(int type, int i) {
		return Model.getInstance().getEntityX(type, i);
	}
	
	public double getEntityY(int type, int i) {
		return Model.getInstance().getEntityY(type, i);
	}
	
	public int getNumberOfLives() {
		return Model.getInstance().getNumberOfLives();
	}
	
	public int getScore() {
		return Model.getInstance().getScore();
	}
	
	public int getLevel() {
		return Model.getInstance().getLevel();
	}
	
	public void exitGame(int type){
		Model.getInstance().exitGame(type);
	}
	
	public void playSoundForGUI(int sound, int g) {
		Model.getInstance().playSoundEffectForGUI(sound, g);
	}
	
	public void controlTimer(int c) {
		Model.getInstance().controlTimer(c);
	}
	
	public int getRemainingTime() {
		return Model.getInstance().getRemainingTime();
	}
	
	public static IControllerForView getInstance() {
		if (instance == null)
			instance = new ControllerForView();
		return instance;
	}
}
