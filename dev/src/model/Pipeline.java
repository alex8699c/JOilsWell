package model;

public class Pipeline {
	
	private int x, y, direction;
	
	/* x = coordinata x
	 * y = coordinata Y
	 * DIRECTION = direzione
	 * 0 -> giù
	 * 1 -> sù
	 * 2 -> sinistra
	 * 3 -> destra
	 * */
	public Pipeline(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.direction = dir;
	}
	
	//Restituisce la riga di MAP
	protected int getX() {
		return this.x;
	}
	
	//Restituisce la colonna di MAP
	protected int getY() {
		return this.y;
	}
	
	//Restituisce la direzione
	protected int getDirection() {
		return this.direction;
	}
}
