package controller;

import view.View;

public class ControllerForModel implements IControllerForModel{
	
	private static ControllerForModel instance = null;
	
	public ControllerForModel() {
		//vuoto
	}
	
	public void updateMazePanel() {
		View.getInstance().updateMazePanel();
	}
	
	public void updateBoardPanel() {
		View.getInstance().updateBoardPanel();
	}
	
	public int getNumberOfLevels() {
		return View.getInstance().getNumberOfLevels();
	}
	
	public int getDifficulty() {
		return View.getInstance().getDifficulty();
	}
	
	public void endGameGui(int TYPE) {
		View.getInstance().endGameGui(TYPE);
	}
	
	public boolean effectsOn() {
		return View.getInstance().effectsOn();
	}
	
	public boolean musicOn() {
		return View.getInstance().musicOn();
	}
	
	public void newLevel(int level) {
		View.getInstance().newLevel(level);
	}
	
	public static IControllerForModel getInstance() {
		if (instance == null)
			instance = new ControllerForModel();
		return instance;
	}
}
