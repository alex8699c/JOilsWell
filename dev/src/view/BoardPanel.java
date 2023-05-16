package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.ControllerForView;

import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

public class BoardPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JLabel TIME, SCORE, FIRST_LIFE, SECOND_LIFE, THIRD_LIFE;
	private BufferedImage BOARD_PANELS;
	private BufferedImage DRILL;
	
	private final static int[] BoardPanelLVL1;
	private final static int[] BoardPanelLVL2;
	private final static int[] BoardPanelLVL3;
	
	private Image[] PANELS = new Image[3];
	
	static {
		BoardPanelLVL1 = new int[]{0,0,750,90};
		BoardPanelLVL2 = new int[]{0,94,750,90};
		BoardPanelLVL3 = new int[]{0,188,750,90};
	}
	
	public BoardPanel() {
		super();
		setBackground(SystemColor.activeCaption);
		this.setLayout(null);
		this.setSize(MainGUI.JFRAME_WIDTH,MainGUI.JFRAME_HEIGHT-400);
		
		try {
		    this.BOARD_PANELS = ImageIO.read(getClass().getResource("/BoardPanel.png"));
		    this.DRILL = ImageIO.read(getClass().getResource("/drill.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		this.PANELS[0] =  BOARD_PANELS.getSubimage(BoardPanelLVL1[0], BoardPanelLVL1[1], BoardPanelLVL1[2], BoardPanelLVL1[3]);
		this.PANELS[1] =  BOARD_PANELS.getSubimage(BoardPanelLVL2[0], BoardPanelLVL2[1], BoardPanelLVL2[2], BoardPanelLVL2[3]);
		this.PANELS[2] =  BOARD_PANELS.getSubimage(BoardPanelLVL3[0], BoardPanelLVL3[1], BoardPanelLVL3[2], BoardPanelLVL3[3]);
		
		//tempo rimanente
		TIME = new JLabel("");
		TIME.setForeground(new Color(0, 0, 0));
		this.add(TIME);
		TIME.setHorizontalAlignment(SwingConstants.CENTER);
		TIME.setBounds(349, 57, 50, 20);
		TIME.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		
		
		//punteggio
		SCORE = new JLabel(""+ControllerForView.getInstance().getScore());
		SCORE.setForeground(new Color(0, 0, 0));
		this.add(SCORE);
		SCORE.setHorizontalAlignment(SwingConstants.RIGHT);
		SCORE.setBounds(640,35,90,20);
		SCORE.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		
		FIRST_LIFE = new JLabel("");
		FIRST_LIFE.setIcon(new ImageIcon(DRILL));
		FIRST_LIFE.setForeground(new Color(0, 0, 0));
		FIRST_LIFE.setHorizontalAlignment(SwingConstants.CENTER);
		FIRST_LIFE.setFont(new Font("Tahoma", Font.PLAIN, 25));
		FIRST_LIFE.setBounds(10, 10, 30, 30);
		add(FIRST_LIFE);
		
		SECOND_LIFE = new JLabel("");
		SECOND_LIFE.setIcon(new ImageIcon(DRILL));
		SECOND_LIFE.setForeground(new Color(0, 0, 0));
		SECOND_LIFE.setHorizontalAlignment(SwingConstants.CENTER);
		SECOND_LIFE.setFont(new Font("Tahoma", Font.PLAIN, 25));
		SECOND_LIFE.setBounds(30, 10, 30, 30);
		add(SECOND_LIFE);
		
		THIRD_LIFE = new JLabel("");
		THIRD_LIFE.setIcon(new ImageIcon(DRILL));
		THIRD_LIFE.setForeground(new Color(0, 0, 0));
		THIRD_LIFE.setHorizontalAlignment(SwingConstants.CENTER);
		THIRD_LIFE.setFont(new Font("Tahoma", Font.PLAIN, 25));
		THIRD_LIFE.setBounds(50, 10, 30, 30);
		add(THIRD_LIFE);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawImage(this.PANELS[1], 0, 0, 750, 90, 0, 0,
        		this.PANELS[1].getWidth(null), this.PANELS[1].getHeight(null), null);
		this.SCORE.setText(""+ControllerForView.getInstance().getScore());
		this.TIME.setText(Integer.toString(ControllerForView.getInstance().getRemainingTime()));
		switch(ControllerForView.getInstance().getNumberOfLives()){
			case 1:
				FIRST_LIFE.setVisible(true);
				SECOND_LIFE.setVisible(false);
				THIRD_LIFE.setVisible(false);
				break;
			case 2:
				FIRST_LIFE.setVisible(true);
				SECOND_LIFE.setVisible(true);
				THIRD_LIFE.setVisible(false);
				break;
			case 3:
				FIRST_LIFE.setVisible(true);
				SECOND_LIFE.setVisible(true);
				THIRD_LIFE.setVisible(true);
				break;
		}
	}

}
