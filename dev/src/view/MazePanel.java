package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import utils.Config;
import controller.ControllerForView;




public class MazePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*---------------------------------------
	 *-----VARIABILI E COSTANTI STATICHE-----
	 *---------------------------------------*/
	
	private static int[] BACKGROUND_COLOR_RGB;
	private final static Color BACKGROUND_COLOR;
	private static int[] DRILL_COLOR_RGB;
	private final static Color DRILL_COLOR;
	
	private static int[] PIPE_COLOR_RGB;
	private final static Color PIPE_COLOR;
	
	private final static int ROWS;
	private final static int COLUMNS;
	private int[][] MAP;
	private AbstractAnimation entitiesAnimation;
	
	protected JLabel END_GAME;
	
	private BufferedImage WALLS;
	private final static int[] DownWallLVL1;
	private final static int[] UpperWallLVL1;
	
	private final static int[] DownWallLVL2;
	private final static int[] UpperWallLVL2;
	private final static int[] DownWallLVL3;
	private final static int[] UpperWallLVL3;
	private final static int[] OuterWall;
	
	private Image[] walls = new Image[7];
	
	private Rectangle2D START_PIPELINE;
	private Rectangle2D PIPELINE;
	private Arc2D DRILL;
	private RoundRectangle2D PELLET;

	
	//Blocco statico: assegna alle costanti valori
	static {
		BACKGROUND_COLOR_RGB = Config.getInstance().getMazePanelBackgroundColor();
		BACKGROUND_COLOR = new Color(BACKGROUND_COLOR_RGB[0],BACKGROUND_COLOR_RGB[1],BACKGROUND_COLOR_RGB[2]);
		DRILL_COLOR_RGB = Config.getInstance().getDrillColor();
		DRILL_COLOR = new Color(DRILL_COLOR_RGB[0],DRILL_COLOR_RGB[1],DRILL_COLOR_RGB[2]);
		
		PIPE_COLOR_RGB = Config.getInstance().getPipeColor();
		PIPE_COLOR = new Color(PIPE_COLOR_RGB[0],PIPE_COLOR_RGB[1],PIPE_COLOR_RGB[2]);
		
		DownWallLVL1 = Config.getInstance().getWallsColor(1,0);
		UpperWallLVL1 = Config.getInstance().getWallsColor(1,1);
		
		DownWallLVL2 = Config.getInstance().getWallsColor(2,0);
		UpperWallLVL2 = Config.getInstance().getWallsColor(2,1);
		DownWallLVL3 = Config.getInstance().getWallsColor(3,0);
		UpperWallLVL3 = Config.getInstance().getWallsColor(3,1);
		OuterWall = new int[]{0,24,30,20};
		ROWS = ControllerForView.getInstance().getMap().length;
		COLUMNS = ControllerForView.getInstance().getMap()[1].length;
		
	}
	
	//Costruttore
	public MazePanel(){
		super();
		this.setLayout(null);
		this.setSize(MainGUI.JFRAME_WIDTH,MainGUI.JFRAME_HEIGHT-90);
		this.setBackground(BACKGROUND_COLOR);
		this.setVisible(true);
		try {
		    this.WALLS = ImageIO.read(getClass().getResource("/walls.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		this.walls[0] = WALLS.getSubimage(DownWallLVL1[0], DownWallLVL1[1], DownWallLVL1[2], DownWallLVL1[3]);
		this.walls[1] = WALLS.getSubimage(UpperWallLVL1[0], UpperWallLVL1[1], UpperWallLVL1[2], UpperWallLVL1[3]);
		this.walls[2] = WALLS.getSubimage(DownWallLVL2[0], DownWallLVL2[1], DownWallLVL2[2], DownWallLVL2[3]);
		this.walls[3] = WALLS.getSubimage(UpperWallLVL2[0], UpperWallLVL2[1], UpperWallLVL2[2], UpperWallLVL2[3]);
		this.walls[4] = WALLS.getSubimage(DownWallLVL3[0], DownWallLVL3[1], DownWallLVL3[2], DownWallLVL3[3]);
		this.walls[5] = WALLS.getSubimage(UpperWallLVL3[0], UpperWallLVL3[1], UpperWallLVL3[2], UpperWallLVL3[3]);
		this.walls[6] = WALLS.getSubimage(OuterWall[0], OuterWall[1], OuterWall[2], OuterWall[3]);
		
		this.END_GAME = new JLabel("WIN", SwingConstants.CENTER);
		END_GAME.setToolTipText("");
		this.END_GAME.setHorizontalAlignment(SwingConstants.CENTER);
		this.END_GAME.setBounds(10, 121, 740, 89);
		this.END_GAME.setForeground(new Color(255, 0, 0));
		this.END_GAME.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 60));
		this.add(END_GAME);
		this.END_GAME.setVisible(false);
	}
	
	//Assegna al pannello l'animazione
	public void setAnimation(AbstractAnimation animation) {
		this.entitiesAnimation = animation;
	}
	
	//Disegna un muro in una cella di MAP
	private void paintWall(int TYPE, int i, int j, Graphics2D g2d) {
		int LVL = ControllerForView.getInstance().getLevel();
		switch(TYPE) {
			case 1:
				switch(LVL) {
					case 1:
						g2d.drawImage(this.walls[0], i*30, j*20, i*30+30, j*20+20, 0, 0,
								this.walls[0].getWidth(null), this.walls[0].getHeight(null), null);
						break;
					case 2:
						g2d.drawImage(this.walls[2], i*30, j*20, i*30+30, j*20+20, 0, 0,
								this.walls[2].getWidth(null), this.walls[2].getHeight(null), null);
						break;
					case 3:
						g2d.drawImage(this.walls[4], i*30, j*20, i*30+30, j*20+20, 0, 0,
								this.walls[4].getWidth(null), this.walls[4].getHeight(null), null);
						break;
				}
				break;
			case 2:
				switch(LVL) {
					case 1:
						g2d.drawImage(this.walls[1], i*30, j*20, i*30+30, j*20+20, 0, 0,
								this.walls[1].getWidth(null), this.walls[1].getHeight(null), null);
						break;
					case 2:
						g2d.drawImage(this.walls[3], i*30, j*20, i*30+30, j*20+20, 0, 0,
								this.walls[3].getWidth(null), this.walls[3].getHeight(null), null);
						break;
					case 3:
						g2d.drawImage(this.walls[5], i*30, j*20, i*30+30, j*20+20, 0, 0,
								this.walls[5].getWidth(null), this.walls[5].getHeight(null), null);
						break;
				}
				break;
			case 3:
				g2d.drawImage(this.walls[6], i*30, j*20, i*30+30, j*20+20, 0, 0,
					this.walls[6].getWidth(null), this.walls[6].getHeight(null), null);
				break;
		}
		
		
	}
	
	//Disegna la griglia
	private void paintGrid(Graphics2D g2d) {
		for(int i = 0; i < COLUMNS; i++) {
			g2d.drawLine(i*30, 0, i*30, ROWS*20);
		}
		for(int i = 0; i < ROWS; i++) {
			g2d.drawLine(0, i*20, COLUMNS*30, i*20);
		}
	}
	
	
	//Disegna la mappa (muri, pellet e tubo)
	private void paintMap(Graphics2D g2d) {
		
		//restituisce la matrice della mappa da model
		this.MAP = ControllerForView.getInstance().getMap();
		
		//disegna il tubo iniziale
		this.START_PIPELINE = new Rectangle2D.Double((12.0*30.0)+12.5, 0.0, 5.0, 7.5);
		g2d.setColor(PIPE_COLOR);
		g2d.draw(this.START_PIPELINE);
		g2d.fill(this.START_PIPELINE);

		for(int i = 0; i < MAP[0].length; i++) {
			for(int j = 0; j < MAP.length; j++) {
				if(MAP[j][i] == 1) {  //disegna i muri
					this.paintWall(2, i, j, g2d);
				}
				else if(MAP[j][i] == 2) {
					this.paintWall(1, i, j, g2d);
				}
				else if(MAP[j][i] == 3) {
					this.paintWall(3, i, j, g2d);
				}
				else if(MAP[j][i] == 4) {
					this.paintWall(3, i, j, g2d);
				}
				else if(MAP[j][i] == 5) {  //disegna i pellet
				    this.PELLET = new RoundRectangle2D.Double((i*30)+10.0,(j*20)+10.0,8.0,4.0,4.0,2.0);
					g2d.setColor(Color.WHITE);
					g2d.draw(this.PELLET);
					g2d.fill(this.PELLET);
				}
				else if(MAP[j][i] == 6) {  //disegna il tubo
					g2d.setColor(PIPE_COLOR);
					switch(ControllerForView.getInstance().getPipelineDirection(j, i)) {
					case 0:  //giu
						this.PIPELINE = new Rectangle2D.Double((i*30.0)+12.5, (j*20.0)+7.5, 5.0, 20.0);
						g2d.draw(this.PIPELINE);
						g2d.fill(this.PIPELINE);
						break;
					case 1:  //su
						this.PIPELINE = new Rectangle2D.Double((i*30.0)+12.5, (j*20.0)-7.5, 5.0, 20.0);
						g2d.draw(this.PIPELINE);
						g2d.fill(this.PIPELINE);
						break;
					case 2:  //sinistra
						this.PIPELINE = new Rectangle2D.Double((i*30.0)-12.5, (j*20.0)+7.5, 30.0, 5.0);
						g2d.draw(this.PIPELINE);
						g2d.fill(this.PIPELINE);
						break;
					case 3:  //destra
						this.PIPELINE = new Rectangle2D.Double((i*30.0)+12.5, (j*20.0)+7.5, 30.0, 5.0);
						g2d.draw(this.PIPELINE);
						g2d.fill(this.PIPELINE);
						break;
					}
				}
			}
		}
	}
	
	//Disegna la sonda
	private void paintDrill(Graphics2D g2d) {
		int X = ControllerForView.getInstance().getDrillX();
		int Y = ControllerForView.getInstance().getDrillY();
		this.DRILL = new Arc2D.Double();
		switch(ControllerForView.getInstance().getDrillDirection()) {
		case 0:
			if((X+Y)%2 == 0) {
				this.DRILL = new Arc2D.Double((Y*30)+10.0,(X*20)+7.5,10,10,0,360,Arc2D.PIE);
			}
			else {
				this.DRILL = new Arc2D.Double((Y*30)+10.0,(X*20)+7.5,10,10,-45,270,Arc2D.PIE);
			}
			break;
		case 1:
			if((X+Y)%2 == 0) {
				this.DRILL = new Arc2D.Double((Y*30)+10.0,(X*20)+7.5,10,10,0,360,Arc2D.PIE);
			}
			else {
				this.DRILL = new Arc2D.Double((Y*30)+10.0,(X*20)+7.5,10,10,135,270,Arc2D.PIE);
			}
			break;
		case 2:
			if((X+Y)%2 == 0) {
				this.DRILL = new Arc2D.Double((Y*30)+10.0,(X*20)+5.0,10,10,0,360,Arc2D.PIE);
			}
			else {
				this.DRILL = new Arc2D.Double((Y*30)+10.0,(X*20)+5.0,10,10,225,270,Arc2D.PIE);
			}
			break;
		case 3:
			if((X+Y)%2 == 0) {
				this.DRILL = new Arc2D.Double((Y*30)+10.0,(X*20)+5.0,10,10,0,360,Arc2D.PIE);
			}
			else {
				this.DRILL = new Arc2D.Double((Y*30)+10.0,(X*20)+5.0,10,10,45,270,Arc2D.PIE);
			}
			break;
		}
		g2d.setColor(DRILL_COLOR);
		g2d.draw(this.DRILL);
		g2d.fill(this.DRILL);
	}
	
	//Disegna le entità
	private void paintEntities(Graphics2D g2d) {
		for(int i = 0; i < ControllerForView.getInstance().getMonsters().size(); i++) {
			double X = ControllerForView.getInstance().getEntityX(0, i);
			double Y = ControllerForView.getInstance().getEntityY(0, i);
			this.entitiesAnimation.setAnimFrameArray(ControllerForView.getInstance().getMonsterType(i),
				ControllerForView.getInstance().getEntityDirection(0, i));
			this.entitiesAnimation.show(g2d, X, Y);
		}
		for(int i = 0; i < ControllerForView.getInstance().getMines().size(); i++) {
			double X = ControllerForView.getInstance().getEntityX(1, i);
			double Y = ControllerForView.getInstance().getEntityY(1, i);
			this.entitiesAnimation.setAnimFrameArray(3,
				ControllerForView.getInstance().getEntityDirection(1, i));
			this.entitiesAnimation.show(g2d, X, Y);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		if(Config.getInstance().isGridVisible())
			this.paintGrid(g2d);
		this.paintMap(g2d);
		if(MainGUI.monstersOn)
			this.paintEntities(g2d);
		this.paintDrill(g2d);
		if(!MainGUI.gameOn)
			this.END_GAME.setVisible(true);
	}
}
