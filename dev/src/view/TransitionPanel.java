package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class TransitionPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JLabel LEVEL_LABEL;
	
	//Parole da visualizzare sul pannello di transizione
	protected String WORDS;	
	
	// 0 = bianco, 1 = giallo
	protected int color;
	
	//Costruttore
	public TransitionPanel(String WORDS) {
		super();
		setBackground(SystemColor.BLACK);
		this.setLayout(null);
		this.setSize(MainGUI.JFRAME_WIDTH,MainGUI.JFRAME_HEIGHT);
		this.WORDS = WORDS;
		
		LEVEL_LABEL = new JLabel(WORDS);
		LEVEL_LABEL.setHorizontalAlignment(SwingConstants.CENTER);
		LEVEL_LABEL.setBounds(225, 190, 309, 60);
		LEVEL_LABEL.setForeground(new Color(255, 255, 255));
		this.add(LEVEL_LABEL);
		LEVEL_LABEL.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 40));
		this.color = 0;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	}
}
