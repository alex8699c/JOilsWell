package model;

public class Entity {
	
	
	/*row = indice riga (tunnel) del labirinto
	 *direction = direzione di movimento entità:
	 *0 = sinistra
	 *1 = destra*/
    private int row, direction;
    
    //Coordinate
    private double x, y;
    
    //Costruttore
    public Entity(int row, int dir) {
    	this.row = row;
    	this.direction = dir;
    }
    
    //Restituisce la coordinata x
    protected double getX() {
    	return this.x;
    }
    
    //Restituisce la coordinata y
    protected double getY() {
    	return this.y;
    }
    
    //Restituisce l'indice della riga
    protected int getRow() {
    	return this.row;
    }
    
    //Restituisce la direzione
    protected int getDirection() {
    	return this.direction;
    }
    
    //Cambia le coordinate dell'entità
    protected void setCoordinates(double x, double y) {
    	this.x = x;
    	this.y = y;
    }
    
    //Muove l'entità orizzontalmente in base alla direzione
    protected void move(double shift) {
    	if(this.direction == 0)
    		this.x = this.x - shift;
    	else
    		this.x = this.x + shift;
    }
    
    
}
