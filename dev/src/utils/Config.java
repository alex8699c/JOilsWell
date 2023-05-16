package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	
	
	/*Coordinate pixel sull'immagine walls.png per i vari muri di colore diverso
	*rispettiavamente {X,Y,larghezza,altezza}*/
	private final static int[] downBlueWall = {0,0,30,20};
	private final static int[] upperBlueWall = {34,0,30,20};
	
	private final static int[] downRedWall = {68,0,30,20};
	private final static int[] upperRedWall = {102,0,30,20};
	
	private final static int[] downVioletWall = {136,0,30,20};
	private final static int[] upperVioletWall = {170,0,30,20};
	

	private static Config instance = null;

	private Properties properties;
	
	
	//Costruttore
	private Config(){
		properties = new Properties();
		 try {
	            InputStream inputStream = getClass().getResourceAsStream("/config.txt");
	            properties.load(inputStream);
	            inputStream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
		
	/*-------------------------
	 *-----METODI PUBBLICI-----
	 *-------------------------*/

	public boolean isGridVisible() {
		return this.properties.getProperty("isGridVisible").toLowerCase().equals("true") ? true : false;
	}
	
	public int[] getMazePanelBackgroundColor() {
		int[] rgb = new int[3];
		rgb[0] = Integer.parseInt(this.properties.getProperty("R_MAZE_BACKGROUND").toLowerCase());
		rgb[1] = Integer.parseInt(this.properties.getProperty("G_MAZE_BACKGROUND").toLowerCase());
		rgb[2] = Integer.parseInt(this.properties.getProperty("B_MAZE_BACKGROUND").toLowerCase());
		return rgb;
	}
	
	public int[] getDrillColor() {
		int[] rgb = new int[3];
		rgb[0] = Integer.parseInt(this.properties.getProperty("R_DRILL").toLowerCase());
		rgb[1] = Integer.parseInt(this.properties.getProperty("G_DRILL").toLowerCase());
		rgb[2] = Integer.parseInt(this.properties.getProperty("B_DRILL").toLowerCase());
		return rgb;
	}
	
	public int[] getPipeColor() {
		int[] rgb = new int[3];
		rgb[0] = Integer.parseInt(this.properties.getProperty("R_PIPE").toLowerCase());
		rgb[1] = Integer.parseInt(this.properties.getProperty("G_PIPE").toLowerCase());
		rgb[2] = Integer.parseInt(this.properties.getProperty("B_PIPE").toLowerCase());
		return rgb;
	}
	
	public double getEntitiesVelocity(int kind) {
		switch(kind) {
			case 0:
				return Double.parseDouble(this.properties.getProperty("ENTITIES_VELOCITY_SLOW").toLowerCase());
			case 1:
				return Double.parseDouble(this.properties.getProperty("ENTITIES_VELOCITY_MEDIUM").toLowerCase());
			case 2:
				return Double.parseDouble(this.properties.getProperty("ENTITIES_VELOCITY_FAST").toLowerCase());
		}
		return 0.5;
	}
	
	public int getSpawnerTime(int kind) {
		switch(kind) {
			case 0:
				return Integer.parseInt(this.properties.getProperty("ENTITIES_SPAWN_TIMER_SLOW").toLowerCase());
			case 1:
				return Integer.parseInt(this.properties.getProperty("ENTITIES_SPAWN_TIMER_MEDIUM").toLowerCase());
			case 2:
				return Integer.parseInt(this.properties.getProperty("ENTITIES_SPAWN_TIMER_FAST").toLowerCase());
		}
		return 3000;
	}
	
	public int getNumberOfPellets() {
		return Integer.parseInt(this.properties.getProperty("numberOfPellets").toLowerCase());
	}
	
	public int getAnimationDelayTime() {
		return Integer.parseInt(this.properties.getProperty("delayTime").toLowerCase());
	}
	
	public int getTransitionTime() {
		return Integer.parseInt(this.properties.getProperty("transitionTime"));
	}
	
	public int getMonsterPoints(int type) {
		switch(type) {
			case 0:
				return Integer.parseInt(this.properties.getProperty("goblinPoints").toLowerCase());
			case 1:
				return Integer.parseInt(this.properties.getProperty("spiderPoints").toLowerCase());
		}
		return Integer.parseInt(this.properties.getProperty("snakePoints").toLowerCase());
	}
	
	public int getPelletPoints() {
		return Integer.parseInt(this.properties.getProperty("pelletPoints").toLowerCase());
	}
	
	public double getEntityProbability(int type) {
		if(type == 0)
			return Double.parseDouble(this.properties.getProperty("monstersProbability").toLowerCase());
		return Double.parseDouble(this.properties.getProperty("minesProbability").toLowerCase());
	}
	
	public double getMonsterTypeProbability(int type) {
		switch(type) {
			case 0:
				return Double.parseDouble(this.properties.getProperty("goblinProbability").toLowerCase());
			case 1:
				return Double.parseDouble(this.properties.getProperty("spiderProbability").toLowerCase());
		}
		return Double.parseDouble(this.properties.getProperty("snakeProbability").toLowerCase());
	}
	
	public int getRemainingTime(int difficulty) {
		switch(difficulty) {
			case 1:
				return Integer.parseInt(this.properties.getProperty("timeSimple").toLowerCase());
			case 2:
				return Integer.parseInt(this.properties.getProperty("timeMedium").toLowerCase());
		}
		return Integer.parseInt(this.properties.getProperty("timeDifficult").toLowerCase());
	}
	
	public int[] getWallsColor(int level, int position) {
		int[] image = new int[4];
		switch(level) {
			case 1:
				switch(this.properties.getProperty("wallsColor1").toLowerCase()) {
					case "blue":
						//downWall
						if(position == 0)
							image = downBlueWall;
						//upperWall
						else
							image = upperBlueWall;
						break;
					case "red":
						if(position == 0)
							image = downRedWall;
						else
							image = upperRedWall;
						break;
					case "violet":
						if(position == 0)
							image = downVioletWall;
						else
							image = upperVioletWall;
						break;
				}
				break;
			case 2:
				switch(this.properties.getProperty("wallsColor2").toLowerCase()) {
					case "blue":
						//downWall
						if(position == 0)
							image = downBlueWall;
						//upperWall
						else
							image = upperBlueWall;
						break;
					case "red":
						if(position == 0)
							image = downRedWall;
						else
							image = upperRedWall;
						break;
					case "violet":
						if(position == 0)
							image = downVioletWall;
						else
							image = upperVioletWall;
						break;
				}
				break;
			case 3:
				switch(this.properties.getProperty("wallsColor3").toLowerCase()) {
					case "blue":
						//downWall
						if(position == 0)
							image = downBlueWall;
						//upperWall
						else
							image = upperBlueWall;
						break;
					case "red":
						if(position == 0)
							image = downRedWall;
						else
							image = upperRedWall;
						break;
					case "violet":
						if(position == 0)
							image = downVioletWall;
						else
							image = upperVioletWall;
						break;
				}
				break;
		}
		return image;
	}
	
	public static Config getInstance(){
		if (instance == null)
			instance = new Config();
		return instance;
	}

}
