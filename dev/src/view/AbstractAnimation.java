package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import utils.Config;


public abstract class AbstractAnimation implements ActionListener{
	
	private final static int ANIMATION_DELAY; //millisecondi
	
	private MazePanel animPanel;
	private Timer animTimer;
	protected Image[] animFrameArray;
	protected int[][] animShiftMatrix;
	protected int currentFrameIndex;
	
	static {
		ANIMATION_DELAY = Config.getInstance().getAnimationDelayTime();
	}
	
	protected AbstractAnimation(Image[] animFrameArray, int[][] animShiftMatrix, MazePanel animPanel) {
		this.animFrameArray = animFrameArray;
		this.animShiftMatrix = animShiftMatrix;
		this.animPanel = animPanel;
		this.currentFrameIndex = 0;
		this.animTimer = new Timer(ANIMATION_DELAY, this);
	} // end constructor

	//Avvia animazione
	public void start() {
		this.animTimer.start();
	}
	
	//Ferma animazione
	public void stop() {
		this.animTimer.stop();
	}
	
	//Disegna animazione
	public void show(Graphics2D g2d, double x, double y) {
		this.showCurrentFrame(g2d, x, y);
	}
	
	//Seleziona un frame
	public abstract void setAnimFrameArray(int TYPE, int DIR);
	
	public void actionPerformed(ActionEvent evt) {
		this.animPanel.repaint();
		this.currentFrameIndex++;
		this.currentFrameIndex %= this.animFrameArray.length;
	}

	public abstract int getAnimationWidth();

	public abstract int getAnimationHeight();

	public abstract void showCurrentFrame(Graphics2D g2d, double x, double y);
}
