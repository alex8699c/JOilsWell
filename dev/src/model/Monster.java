package model;

public class Monster extends Entity{
	
    	private int type;
    	
	/* TYPE = tipo del mostro
	 * 0 -> Goblin
	 * 1 -> Spider
	 * 2 -> Fish */
	public Monster(int row, int type, int dir) {
	    	super(row, dir);
	    	this.type = type;
	}
	
	//Restituisce il tipo di mostro
	protected int getType() {
	    return this.type;
	}
}
