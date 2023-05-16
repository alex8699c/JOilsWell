package controller;

public interface IControllerForModel {
	
	public void updateMazePanel();
	
	public void updateBoardPanel();
	
	public int getNumberOfLevels();
	
	public int getDifficulty();
	
	public void endGameGui(int TYPE);

	public boolean effectsOn();
	
	public boolean musicOn();
	
	public void newLevel(int level);
}
