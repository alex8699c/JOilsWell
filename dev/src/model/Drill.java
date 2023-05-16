package model;

public class Drill {
	
	/* x = coordinata x su MAP
	 * y = coordinata Y su MAP
	 * DIRECTION = direzione
	 * 0 -> giù
	 * 1 -> sù
	 * 2 -> sinistra
	 * 3 -> destra
	 * */
	private int x, y, direction;
	
	//Costruttore
	public Drill() {
		//vuoto
	}
	
	//Restituisce x
	protected int getX() {
		return this.x;
	}
	
	//Restituisce y
	protected int getY() {
		return this.y;
	}
	
	//Restituisce la direzione
	protected int getDirection() {
		return this.direction;
	}
	
	//Modifica la direzione
	protected void setDirection(int dir) {
		this.direction = dir;
	}
	
	//Muove la sonda (cambia x ed y)
	protected void move(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
