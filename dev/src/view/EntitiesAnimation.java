package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

public class EntitiesAnimation extends AbstractAnimation{
	
	private JOilsWellSprites jowSprites;
	
	private final static int ZOOM_FACTOR = 1;
	private final static int ANIMATION_WIDTH = 30;
	private final static int ANIMATION_HEIGHT = 20;
	
	//Spazio verticale, utile per centrare i frame di un'entità nei tunnel
	private int Y_SHIFT;
	
	//Costruttore
	public EntitiesAnimation(MazePanel MAZE) throws IOException{
		super(null, null, MAZE);
		jowSprites = new JOilsWellSprites();
		this.animFrameArray = jowSprites.getGoblinLeftWalkFrame();
	}

	@Override
	public int getAnimationWidth() {
		return ANIMATION_WIDTH;
	}

	@Override
	public int getAnimationHeight() {
		return ANIMATION_HEIGHT;
	}
	
	public void setAnimFrameArray(int TYPE, int DIR) {
	    switch(TYPE) {
	    case 0:
		Y_SHIFT = 0;
		if(DIR == 1)
		    this.animFrameArray = jowSprites.getGoblinRightWalkFrame();
		else
		    this.animFrameArray = jowSprites.getGoblinLeftWalkFrame();
		break;
	    case 2:
		Y_SHIFT = 4;
		if(DIR == 1)
		    this.animFrameArray = jowSprites.getSnakeRightWalkFrame();
		else
		    this.animFrameArray = jowSprites.getSnakeLeftWalkFrame();
		break;
	    case 1:
		Y_SHIFT = 0;
		if(DIR == 1)
		    this.animFrameArray = jowSprites.getSpiderRightWalkFrame();
		else
		    this.animFrameArray = jowSprites.getSpiderLeftWalkFrame();
		break;
	    case 3:
	    	Y_SHIFT = 0;
	    	if(DIR == 1)
			    this.animFrameArray = jowSprites.getMineWalkFrame();
			else
			    this.animFrameArray = jowSprites.getMineWalkFrame();
	    	break;
	    }
	}
	
	@Override
	public void showCurrentFrame(Graphics2D g2d, double x, double y) {
		Image img = this.animFrameArray[this.currentFrameIndex];
		int X = (int)x;
		int Y = (int)y;
		g2d.drawImage(img,
				X + ZOOM_FACTOR, Y  + ZOOM_FACTOR + Y_SHIFT, X + ZOOM_FACTOR * (img.getWidth(null)),Y_SHIFT + Y + ZOOM_FACTOR * (img.getHeight(null)),
				0, 0, img.getWidth(null), img.getHeight(null),
				null);
	}
}
