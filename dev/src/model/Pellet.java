package model;

public class Pellet {
	
	private int x, y;
	
	/* x = coordinata x su MAP
	 * y = coordinata Y su MAP
	 * */
	public Pellet(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//Restituisce x
	protected int getX() {
		return this.x;
	}
	
	//Restituisce y
	protected int getY() {
		return this.y;
	}
}
