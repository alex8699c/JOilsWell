package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import utils.RecordManager;



public class MenuPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Colore del bakground
	private final static Color BACKGROUND_COLOR;
	
	//Componenti AWT
	protected JLabel DIFFICULTY, DIFFICULTY_TITLE, HIGH_SCORE, HIGH_SCORE_TITLE, NUMBER_OF_LEVELS;
	protected JButton LEFT, RIGHT, NEW_GAME, SOUND, MUSIC;
	protected JToggleButton LVL_1, LVL_2, LVL_3;
	protected ButtonGroup LIVELLI;
	protected JLabel SOUND_LABEL;
	protected JLabel MUSIC_LABEL;
	
	private BufferedImage BACKGROUND;
	
	//Blocco statico: assegna valori a costanti
	static {
		BACKGROUND_COLOR = new Color(96,96,96);
	}
	
	public MenuPanel() {
		super();
		this.setLayout(null);
		this.setBackground(BACKGROUND_COLOR);
		this.setSize(MainGUI.JFRAME_WIDTH,MainGUI.JFRAME_HEIGHT);
		
		
		
		try {
		    this.BACKGROUND = ImageIO.read(getClass().getResource("/menu.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
				
				//high score title
				HIGH_SCORE_TITLE = new JLabel("HIGH SCORE");
				HIGH_SCORE_TITLE.setForeground(new Color(0, 0, 0));
				HIGH_SCORE_TITLE.setHorizontalAlignment(SwingConstants.CENTER);
				HIGH_SCORE_TITLE.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
				this.add(HIGH_SCORE_TITLE);
				HIGH_SCORE_TITLE.setBounds(100,346,150,30);
				
				//difficulty title
				DIFFICULTY_TITLE = new JLabel("DIFFICULTY");
				DIFFICULTY_TITLE.setForeground(new Color(0, 0, 0));
				DIFFICULTY_TITLE.setHorizontalAlignment(SwingConstants.CENTER);
				DIFFICULTY_TITLE.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
				this.add(DIFFICULTY_TITLE);
				DIFFICULTY_TITLE.setBounds(108,156,135,30);
				
				//difficolt�
				DIFFICULTY = new JLabel("SIMPLE");
				DIFFICULTY.setForeground(new Color(0, 0, 0));
				DIFFICULTY.setHorizontalAlignment(SwingConstants.CENTER);
				DIFFICULTY.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
				this.add(DIFFICULTY);
				DIFFICULTY.setBounds(108,196,130,30);
				
				
				//sound
				SOUND = new JButton("YES");
				SOUND.setForeground(new Color(0, 0, 0));
				SOUND.setHorizontalAlignment(SwingConstants.CENTER);
				SOUND.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
				this.add(SOUND);
				SOUND.setBounds(576,195,80,30);
				
				//music
				MUSIC = new JButton("YES");
				MUSIC.setForeground(new Color(0, 0, 0));
				MUSIC.setHorizontalAlignment(SwingConstants.CENTER);
				MUSIC.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
				this.add(MUSIC);
				MUSIC.setBounds(576,293,80,30);
				
				//numero di livelli
				NUMBER_OF_LEVELS = new JLabel("NUMBER OF LEVELS TO PLAY");
				NUMBER_OF_LEVELS.setForeground(new Color(0, 0, 0));
				NUMBER_OF_LEVELS.setHorizontalAlignment(SwingConstants.CENTER);
				NUMBER_OF_LEVELS.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
				this.add(NUMBER_OF_LEVELS);
				NUMBER_OF_LEVELS.setBounds(45,257,260,30);
				
				
				//livelli
				LVL_1 = new JToggleButton("1");
				LVL_1.setForeground(new Color(0, 0, 0));
				LVL_1.setHorizontalAlignment(SwingConstants.CENTER);
				LVL_1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
				this.add(LVL_1);
				LVL_1.setBounds(89,297,50,40);
				
				LVL_2 = new JToggleButton("2");
				LVL_2.setForeground(new Color(0, 0, 0));
				LVL_2.setHorizontalAlignment(SwingConstants.CENTER);
				LVL_2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
				this.add(LVL_2);
				LVL_2.setBounds(149,297,50,40);
				
				LVL_3 = new JToggleButton("3");
				LVL_3.setForeground(new Color(0, 0, 0));
				LVL_3.setHorizontalAlignment(SwingConstants.CENTER);
				LVL_3.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
				this.add(LVL_3);
				LVL_3.setBounds(209,297,50,40);
				
				LIVELLI = new ButtonGroup();
				LIVELLI.add(LVL_1);
				LIVELLI.add(LVL_2);
				LIVELLI.add(LVL_3);
				LVL_1.setSelected(true);
				
				
				
				//new game
				RIGHT = new JButton("►");
				RIGHT.setHorizontalAlignment(SwingConstants.CENTER);
				RIGHT.setFont(new Font("Tahoma", Font.BOLD, 10));
				this.add(RIGHT);
				RIGHT.setBounds(238,196,50,30);
				
				//left e right buttons
				LEFT = new JButton("◄");
				LEFT.setHorizontalAlignment(SwingConstants.CENTER);
				LEFT.setFont(new Font("Tahoma", Font.BOLD, 10));
				this.add(LEFT);
				LEFT.setBounds(58,194,50,30);
				
				
				//new game button
				NEW_GAME = new JButton("START NEW GAME");
				NEW_GAME.setForeground(new Color(0, 0, 0));
				NEW_GAME.setHorizontalAlignment(SwingConstants.CENTER);
				NEW_GAME.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
				this.add(NEW_GAME);
				NEW_GAME.setBounds(412,347,287,57);
				
				SOUND_LABEL = new JLabel("SOUND EFFECTS");
				SOUND_LABEL.setForeground(new Color(0, 0, 0));
				SOUND_LABEL.setHorizontalAlignment(SwingConstants.CENTER);
				SOUND_LABEL.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
				SOUND_LABEL.setBounds(520, 135, 180, 73);
				add(SOUND_LABEL);
				
				MUSIC_LABEL = new JLabel("MUSIC");
				MUSIC_LABEL.setForeground(new Color(0, 0, 0));
				MUSIC_LABEL.setHorizontalAlignment(SwingConstants.CENTER);
				MUSIC_LABEL.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
				MUSIC_LABEL.setBounds(525, 232, 180, 73);
				add(MUSIC_LABEL);
				
				//high score
				HIGH_SCORE = new JLabel("");
				try {
					this.HIGH_SCORE.setText(Integer.toString(RecordManager.getRecord(this.getDifficulty(), this.getNumberOfLevels())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				HIGH_SCORE.setForeground(new Color(0, 0, 0));
				HIGH_SCORE.setHorizontalAlignment(SwingConstants.CENTER);
				HIGH_SCORE.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
				this.add(HIGH_SCORE);
				HIGH_SCORE.setBounds(124,374,100,30);
				
	}
	
	//Restituisce la difficoltà selezionata sul menu
	protected int getDifficulty() {
	    int dif = 0;
		if(this.DIFFICULTY.getText() == "SIMPLE") {
		    dif = 1;
		}
		if(this.DIFFICULTY.getText() == "MEDIUM") {
		    dif = 2;
		}
		if(this.DIFFICULTY.getText() == "HARD") {
		    dif = 3;
		}
		return dif;
	}
	
	//Restituisce il numero di livelli da giocare selezionato
	protected int getNumberOfLevels() {
	    int num_lvls = 1;
	    //se selezionato il tasto "1"
		if(this.LVL_1.isSelected()) {
			num_lvls = 1;
		}
		else if(this.LVL_2.isSelected()) {
			num_lvls = 2;
		}
		else{
			num_lvls = 3;
		}
		return num_lvls;
	}
	
	//Aggiorna il label dei record
	private void updateHighScore(Graphics2D g2d) {
		try {
			this.HIGH_SCORE.setText(Integer.toString(RecordManager.getRecord(this.getDifficulty(), this.getNumberOfLevels())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        this.updateHighScore(g2d);
        g2d.drawImage(this.BACKGROUND, 0, 0, 750, 453, 0, 0,
        		this.BACKGROUND.getWidth(null), this.BACKGROUND.getHeight(null), null);
	}
}
