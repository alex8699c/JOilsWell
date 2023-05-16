package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import controller.ControllerForView;
import utils.Config;


public class MainGUI extends JFrame implements ActionListener, KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected final static int JFRAME_WIDTH = 760;
	protected final static int JFRAME_HEIGHT = 490;
	
	//10 = 1 secondo
	private final static int transitionTime;
	private static int transitionCounter;
	
	protected MenuPanel MENU_PANEL;
	protected MazePanel MAZE_PANEL;
	protected BoardPanel BOARD_PANEL;
	protected TransitionPanel TRANSITION_PANEL;
	
	protected static boolean gameOn = false;
	protected static boolean monstersOn = false;
	
	private Timer TRANSITION_TIMER, END_GAME_TIMER;

	Random rand = new Random();
	private AbstractAnimation entitesWalkAnimation;
	
	static {
		transitionTime = Config.getInstance().getTransitionTime();
	}
	
	//Costruttore
	public MainGUI() {
		super("JOilsWell");
		
		//Assegna un'icona al JFrame
		try {
			setIconImage(ImageIO.read(getClass().getResource("/appIcon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(JFRAME_WIDTH, JFRAME_HEIGHT);
		this.setBounds(0, 0, JFRAME_WIDTH, JFRAME_HEIGHT);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setFocusable(true);
		this.requestFocusInWindow(true);
		this.addKeyListener(this);
		
		//menu
		this.MENU_PANEL = new MenuPanel();
		this.getContentPane().add(MENU_PANEL);
		this.MENU_PANEL.setVisible(true);
		
		//pulsanti
		this.MENU_PANEL.NEW_GAME.addActionListener(this);
		this.MENU_PANEL.LEFT.addActionListener(this);
		this.MENU_PANEL.RIGHT.addActionListener(this);
		this.MENU_PANEL.LVL_1.addActionListener(this);
		this.MENU_PANEL.LVL_2.addActionListener(this);
		this.MENU_PANEL.LVL_3.addActionListener(this);
		this.MENU_PANEL.SOUND.addActionListener(this);
		this.MENU_PANEL.MUSIC.addActionListener(this);
		ControllerForView.getInstance().playSoundForGUI(8,0);
	}
	
	//Inizializzazione partita
	public void init() {
		gameOn = true;
		
		//avvia Model.initGame()
		ControllerForView.getInstance().initGame();
		
		this.BOARD_PANEL = new BoardPanel();
		this.getContentPane().add(BOARD_PANEL);
		this.BOARD_PANEL.setBounds(0, 0, JFRAME_WIDTH, 90);
		this.BOARD_PANEL.setVisible(true);
		
		this.MAZE_PANEL = new MazePanel();
		this.getContentPane().add(MAZE_PANEL);
		this.MAZE_PANEL.setBounds(0, 90, JFRAME_WIDTH, JFRAME_HEIGHT);
		this.MAZE_PANEL.addKeyListener(this);
		this.MAZE_PANEL.setVisible(true);
		
		try {
			this.entitesWalkAnimation = new EntitiesAnimation(this.MAZE_PANEL);
			this.MAZE_PANEL.setAnimation(this.entitesWalkAnimation);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.entitesWalkAnimation.start();
		
		monstersOn = true;
	}
	
	//Metodo che gestisce l'endGame
	protected void endGameGui(int type) {
		gameOn = false;
		this.END_GAME_TIMER = new Timer(1000,this);
		this.END_GAME_TIMER.setInitialDelay(3000);
		
		switch(type) {
			case 0:
				this.MAZE_PANEL.END_GAME.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 60));
				this.MAZE_PANEL.END_GAME.setText("WIN!");
				this.MAZE_PANEL.repaint();
				this.END_GAME_TIMER.start();
				break;
			case 1:
				this.MAZE_PANEL.END_GAME.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 40));
				this.MAZE_PANEL.END_GAME.setText("WIN! NEW RECORD: " + ControllerForView.getInstance().getScore());
				this.MAZE_PANEL.repaint();
				this.END_GAME_TIMER.start();
				break;
			case 2:
				this.MAZE_PANEL.END_GAME.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 60));
				this.MAZE_PANEL.END_GAME.setText("GAME OVER");
				this.MAZE_PANEL.repaint();
				this.END_GAME_TIMER.start();
				break;
			case 3:
				this.MAZE_PANEL.setVisible(false);
				this.BOARD_PANEL.setVisible(false);
				this.MENU_PANEL.setVisible(true);
				break;
		}
		this.entitesWalkAnimation.stop();
	}
	
	//Nuovo livello
	public void newLevel(int level) {
		this.TRANSITION_PANEL = new TransitionPanel("Level " + level);
		this.MAZE_PANEL.setVisible(false);
		this.BOARD_PANEL.setVisible(false);
		this.getContentPane().add(TRANSITION_PANEL);
		this.TRANSITION_PANEL.setVisible(true);
		this.TRANSITION_TIMER.start();
	}
	
	@SuppressWarnings("static-access")
	private void exitGame(){
		ControllerForView.getInstance().controlTimer(1);
		this.entitesWalkAnimation.stop();
		ControllerForView.getInstance().exitGame(0);
		int a = JOptionPane.showConfirmDialog(this,"Do you really want to end the game?","End game", JOptionPane.YES_NO_OPTION);
		if(a==JOptionPane.YES_OPTION){
			ControllerForView.getInstance().endGame(3);
		} 
		else {
			ControllerForView.getInstance().controlTimer(0);
			this.entitesWalkAnimation.start();
			ControllerForView.getInstance().exitGame(1);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOn) {
			switch (e.getKeyCode()) {	
			case KeyEvent.VK_DOWN:
				ControllerForView.getInstance().moveDrill(0);
				break;
			case KeyEvent.VK_UP:
				ControllerForView.getInstance().moveDrill(1);
				break;
			case KeyEvent.VK_LEFT:
				ControllerForView.getInstance().moveDrill(2);
				break;
			case KeyEvent.VK_RIGHT:
				ControllerForView.getInstance().moveDrill(3);
				break;
			case KeyEvent.VK_R:
				ControllerForView.getInstance().retract();
				break;
			case KeyEvent.VK_ESCAPE:
				this.exitGame();
				break;
				}
			this.MAZE_PANEL.repaint();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//effetto sonoro bottoni menu
		if(e.getSource() != this.TRANSITION_TIMER && e.getSource() != this.END_GAME_TIMER
				&& this.MENU_PANEL.SOUND.getText() == "YES")
				ControllerForView.getInstance().playSoundForGUI(8,1);
		
		//bottone nuova partita
		if(e.getSource() == this.MENU_PANEL.NEW_GAME) {
			this.MENU_PANEL.setVisible(false);
			this.TRANSITION_PANEL = new TransitionPanel("Level 1");
			this.TRANSITION_TIMER = new Timer(100,this);
			this.getContentPane().add(TRANSITION_PANEL);
			this.TRANSITION_PANEL.setVisible(true);
			this.TRANSITION_TIMER.start();
		}
		
		//bottone difficoltà più semplice
		else if(e.getSource() == this.MENU_PANEL.LEFT) {
			if(this.MENU_PANEL.DIFFICULTY.getText() == "MEDIUM") {
				this.MENU_PANEL.DIFFICULTY.setText("SIMPLE");
			}
			else if(this.MENU_PANEL.DIFFICULTY.getText() == "HARD") {
				this.MENU_PANEL.DIFFICULTY.setText("MEDIUM");
			}
			this.MENU_PANEL.repaint();
		}
		
		//bottone difficoltà più difficile
		else if(e.getSource() == this.MENU_PANEL.RIGHT) {
			if(this.MENU_PANEL.DIFFICULTY.getText() == "SIMPLE") {
				this.MENU_PANEL.DIFFICULTY.setText("MEDIUM");
			}
			else if(this.MENU_PANEL.DIFFICULTY.getText() == "MEDIUM") {
				this.MENU_PANEL.DIFFICULTY.setText("HARD");
			}
			this.MENU_PANEL.repaint();
		}
		
		//bottone effetti sonori
		else if(e.getSource() == this.MENU_PANEL.SOUND) {
			if(this.MENU_PANEL.SOUND.getText() == "YES") {
				this.MENU_PANEL.SOUND.setText("NO");
			}
			else {
				this.MENU_PANEL.SOUND.setText("YES");
			}
		}
		
		
		//bottone musica
		else if(e.getSource() == this.MENU_PANEL.MUSIC) {
			if(this.MENU_PANEL.MUSIC.getText() == "YES") {
				this.MENU_PANEL.MUSIC.setText("NO");
			}
			else {
				this.MENU_PANEL.MUSIC.setText("YES");
			}
		}
		
		//cambia numero di livelli da giocare
		else if(e.getSource() == this.MENU_PANEL.LVL_1) {
			this.MENU_PANEL.repaint();
		}
		
		else if(e.getSource() == this.MENU_PANEL.LVL_2) {
			this.MENU_PANEL.repaint();
		}
		
		else if(e.getSource() == this.MENU_PANEL.LVL_3) {
			this.MENU_PANEL.repaint();
		}
		
		else if(e.getSource() == this.TRANSITION_TIMER) {
			if(transitionCounter < transitionTime) {
				if(this.TRANSITION_PANEL.color == 0) {
					this.TRANSITION_PANEL.LEVEL_LABEL.setForeground(new Color(255, 255, 0));
					this.TRANSITION_PANEL.color = 1;
					}
				else {
					this.TRANSITION_PANEL.LEVEL_LABEL.setForeground(new Color(255, 255, 255));
					this.TRANSITION_PANEL.color = 0;
				}
				this.TRANSITION_PANEL.repaint();
				transitionCounter++;
			}
			
			//INIZIO GIOCO
			else if(transitionCounter == transitionTime && gameOn == false){
				this.TRANSITION_PANEL.setVisible(false);
				this.init();
				this.TRANSITION_TIMER.stop();
				transitionCounter = 0;
			}
			else{
				this.TRANSITION_PANEL.setVisible(false);
				this.MAZE_PANEL.setVisible(true);
				this.BOARD_PANEL.setVisible(true);
				transitionCounter = 0;
				this.TRANSITION_TIMER.stop();
			}
		}
		
		//Timer fine partita
		else if(e.getSource() == this.END_GAME_TIMER) {
			this.MAZE_PANEL.setVisible(false);
			this.BOARD_PANEL.setVisible(false);
			this.MENU_PANEL.setVisible(true);
			this.MENU_PANEL.repaint();
			this.END_GAME_TIMER.stop();
		}
	}	
}
