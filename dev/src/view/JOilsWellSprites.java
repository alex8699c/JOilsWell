package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class JOilsWellSprites {
	
	/*Posizioni delle entità nel file entities.png
	 *gli interi degli array corrispondono al numero di pixel
	 *in ordine {X,Y,larghezza,altezza}*/
	
	private final static int[] GoblinRightWalk1 = {0,0,20,20};
	private final static int[] GoblinRightWalk2 = {25,0,20,20};
	private final static int[] GoblinRightWalk3 = {50,0,20,20};
	private final static int[] GoblinRightWalk4 = {75,0,20,20};
	private final static int[] GoblinLeftWalk1 = {0,24,20,20};
	private final static int[] GoblinLeftWalk2 = {25,24,20,20};
	private final static int[] GoblinLeftWalk3 = {50,24,20,20};
	private final static int[] GoblinLeftWalk4 = {75,24,20,20};
	
	private final static int[] FishRightWalk1 = {0,47,20,13};
	private final static int[] FishRightWalk2 = {24,48,20,13};
	private final static int[] FishRightWalk3 = {48,48,20,13};
	private final static int[] FishRightWalk4 = {72,48,20,13};
	private final static int[] FishLeftWalk1 = {0,64,20,13};
	private final static int[] FishLeftWalk2 = {24,64,20,13};
	private final static int[] FishLeftWalk3 = {48,64,20,13};
	private final static int[] FishLeftWalk4 = {72,63,20,13};
	
	private final static int[] SpiderRightWalk1 = {0,81,20,20};
	private final static int[] SpiderRightWalk2 = {24,81,20,20};
	private final static int[] SpiderRightWalk3 = {48,81,20,20};
	private final static int[] SpiderRightWalk4 = {72,81,20,20};
	private final static int[] SpiderLeftWalk1 = {0,106,20,20};
	private final static int[] SpiderLeftWalk2 = {24,106,20,20};
	private final static int[] SpiderLeftWalk3 = {48,106,20,20};
	private final static int[] SpiderLeftWalk4 = {72,106,20,20};
	
	private final static int[] Mine1 = {0,131,20,19};
	private final static int[] Mine2 = {24,131,20,19};
	
	private BufferedImage JOWbuffImg;
	
	//Costruttore
	public JOilsWellSprites() throws IOException{
		this.JOWbuffImg = ImageIO.read((getClass().getResource("/entities.png")));
	}
	
	//Metodi pubblici che restituiscono i frame delle entità per le animazioni
	
	public Image[] getSpiderRightWalkFrame() {
	    Image[] spiderRightWalkFrame = new Image[4];
	    spiderRightWalkFrame[0] = JOWbuffImg.getSubimage(SpiderRightWalk1[0], SpiderRightWalk1[1], SpiderRightWalk1[2], SpiderRightWalk1[3]);
	    spiderRightWalkFrame[1] = JOWbuffImg.getSubimage(SpiderRightWalk2[0], SpiderRightWalk2[1], SpiderRightWalk2[2], SpiderRightWalk2[3]);
	    spiderRightWalkFrame[2] = JOWbuffImg.getSubimage(SpiderRightWalk3[0], SpiderRightWalk3[1], SpiderRightWalk3[2], SpiderRightWalk3[3]);
	    spiderRightWalkFrame[3] = JOWbuffImg.getSubimage(SpiderRightWalk4[0], SpiderRightWalk4[1], SpiderRightWalk4[2], SpiderRightWalk4[3]);
	    return spiderRightWalkFrame;
	}
	
	public Image[] getSpiderLeftWalkFrame() {
	    Image[] spiderLeftWalkFrame = new Image[4];
	    spiderLeftWalkFrame[0] = JOWbuffImg.getSubimage(SpiderLeftWalk1[0], SpiderLeftWalk1[1], SpiderLeftWalk1[2], SpiderLeftWalk1[3]);
	    spiderLeftWalkFrame[1] = JOWbuffImg.getSubimage(SpiderLeftWalk2[0], SpiderLeftWalk2[1], SpiderLeftWalk2[2], SpiderLeftWalk2[3]);
	    spiderLeftWalkFrame[2] = JOWbuffImg.getSubimage(SpiderLeftWalk3[0], SpiderLeftWalk3[1], SpiderLeftWalk3[2], SpiderLeftWalk3[3]);
	    spiderLeftWalkFrame[3] = JOWbuffImg.getSubimage(SpiderLeftWalk4[0], SpiderLeftWalk4[1], SpiderLeftWalk4[2], SpiderLeftWalk4[3]);
	    return spiderLeftWalkFrame;
	}
	
	public Image[] getGoblinRightWalkFrame() {
		Image[] goblinRightWalkFrame = new Image[4];
		goblinRightWalkFrame[0] = JOWbuffImg.getSubimage(GoblinRightWalk1[0], GoblinRightWalk1[1], GoblinRightWalk1[2], GoblinRightWalk1[3]);
		goblinRightWalkFrame[1] = JOWbuffImg.getSubimage(GoblinRightWalk2[0], GoblinRightWalk2[1], GoblinRightWalk2[2], GoblinRightWalk2[3]);
		goblinRightWalkFrame[2] = JOWbuffImg.getSubimage(GoblinRightWalk3[0], GoblinRightWalk3[1], GoblinRightWalk3[2], GoblinRightWalk3[3]);
		goblinRightWalkFrame[3] = JOWbuffImg.getSubimage(GoblinRightWalk4[0], GoblinRightWalk4[1], GoblinRightWalk4[2], GoblinRightWalk4[3]);
		return goblinRightWalkFrame;
	}
	
	public Image[] getGoblinLeftWalkFrame() {
		Image[] goblinLeftWalkFrame = new Image[4];
		goblinLeftWalkFrame[0] = JOWbuffImg.getSubimage(GoblinLeftWalk1[0], GoblinLeftWalk1[1], GoblinLeftWalk1[2], GoblinLeftWalk1[3]);
		goblinLeftWalkFrame[1] = JOWbuffImg.getSubimage(GoblinLeftWalk2[0], GoblinLeftWalk2[1], GoblinLeftWalk2[2], GoblinLeftWalk2[3]);
		goblinLeftWalkFrame[2] = JOWbuffImg.getSubimage(GoblinLeftWalk3[0], GoblinLeftWalk3[1], GoblinLeftWalk3[2], GoblinLeftWalk3[3]);
		goblinLeftWalkFrame[3] = JOWbuffImg.getSubimage(GoblinLeftWalk4[0], GoblinLeftWalk4[1], GoblinLeftWalk4[2], GoblinLeftWalk4[3]);
		return goblinLeftWalkFrame;
	}
	
	public Image[] getSnakeRightWalkFrame() {
		Image[] fishRightWalkFrame = new Image[4];
		fishRightWalkFrame[0] = JOWbuffImg.getSubimage(FishRightWalk1[0], FishRightWalk1[1], FishRightWalk1[2], FishRightWalk1[3]);
		fishRightWalkFrame[1] = JOWbuffImg.getSubimage(FishRightWalk2[0], FishRightWalk2[1], FishRightWalk2[2], FishRightWalk2[3]);
		fishRightWalkFrame[2] = JOWbuffImg.getSubimage(FishRightWalk3[0], FishRightWalk3[1], FishRightWalk3[2], FishRightWalk3[3]);
		fishRightWalkFrame[3] = JOWbuffImg.getSubimage(FishRightWalk4[0], FishRightWalk4[1], FishRightWalk4[2], FishRightWalk4[3]);
		return fishRightWalkFrame;
	}
	
	public Image[] getSnakeLeftWalkFrame() {
		Image[] fishLeftWalkFrame = new Image[4];
		fishLeftWalkFrame[0] = JOWbuffImg.getSubimage(FishLeftWalk1[0], FishLeftWalk1[1], FishLeftWalk1[2], FishLeftWalk1[3]);
		fishLeftWalkFrame[1] = JOWbuffImg.getSubimage(FishLeftWalk2[0], FishLeftWalk2[1], FishLeftWalk2[2], FishLeftWalk2[3]);
		fishLeftWalkFrame[2] = JOWbuffImg.getSubimage(FishLeftWalk3[0], FishLeftWalk3[1], FishLeftWalk3[2], FishLeftWalk3[3]);
		fishLeftWalkFrame[3] = JOWbuffImg.getSubimage(FishLeftWalk4[0], FishLeftWalk4[1], FishLeftWalk4[2], FishLeftWalk4[3]);
		return fishLeftWalkFrame;
	}
	
	public Image[] getMineWalkFrame() {
		Image[] mineWalkFrame = new Image[4];
		mineWalkFrame[0] = JOWbuffImg.getSubimage(Mine1[0], Mine1[1], Mine1[2], Mine1[3]);
		mineWalkFrame[1] = JOWbuffImg.getSubimage(Mine2[0], Mine2[1], Mine2[2], Mine2[3]);
		mineWalkFrame[2] = JOWbuffImg.getSubimage(Mine1[0], Mine1[1], Mine1[2], Mine1[3]);
		mineWalkFrame[3] = JOWbuffImg.getSubimage(Mine2[0], Mine2[1], Mine2[2], Mine2[3]);
		return mineWalkFrame;
	}
}
