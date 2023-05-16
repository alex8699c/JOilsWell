package view;


public class View implements IView{
	
	private static View instance = null;
	
	protected MainGUI GUI = null;
	
	public View() {
		//vuoto
	}
	
	//Avvia la GUI
	public void startGUI() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (GUI == null) {
					GUI = new MainGUI();
				}
				GUI.setVisible(true);
			}
		});
	}
	
	//Aggiorna MazePanel (repaint)
	public void updateMazePanel() {
		this.GUI.MAZE_PANEL.repaint();
	}
	
	//Aggiorna BoardPanel (repaint)
	public void updateBoardPanel() {
		this.GUI.BOARD_PANEL.repaint();
	}
	
	//Restituisce il numero di livelli da giocare
	public int getNumberOfLevels() {
		return this.GUI.MENU_PANEL.getNumberOfLevels();
	}
	
	//Restituisce la difficoltà di preferenza
	public int getDifficulty() {
		return this.GUI.MENU_PANEL.getDifficulty();
	}
	
	//Fine partita su GUI
	public void endGameGui(int type) {
		GUI.endGameGui(type);
	}
	
	//Restituisce true se gli effetti sono ON
	public boolean effectsOn() {
		if(GUI.MENU_PANEL.SOUND.getText() == "YES")
			return true;
		else
			return false;
	}
	
	//Restituisce true se la musica è ON
	public boolean musicOn() {
		if(GUI.MENU_PANEL.MUSIC.getText() == "YES")
			return true;
		else
			return false;
	}
	
	//Nuovo livello su GUI
	public void newLevel(int level) {
		GUI.newLevel(level);
	}
	
	public static IView getInstance() {
		if (instance == null)
			instance = new View();
		return instance;
	}
}
