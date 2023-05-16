package model;

import java.util.Random;

public class RandomElement {
	
	/*elements = elementi (codificati con interi)
	 *probabilities = probabilità di ogni elemento secondo lo stesso ordine di elements
	 *Restituisce un elemento di elements*/
	public static int selectRandom(int[] elements, double[] probabilities) {
		
	    if (elements.length != probabilities.length) {
	        throw new IllegalArgumentException("Le dimensioni dei due array non combaciano!");
	    }
	    
	    Random rand = new Random();
	    //inizializzo probabilità totale a 0
	    double totalProb = 0;
	    
	    //totalProb deve essere pari ad 1.0 alla fine del for
	    for (double p : probabilities) {
	        totalProb += p;
	    }
	    
	    double randValue = rand.nextDouble() * totalProb;
	    int selectedIndex = -1;
	    while (randValue >= 0) {
	        selectedIndex++;
	        randValue -= probabilities[selectedIndex];
	    }
	    
	    return elements[selectedIndex];
	}
}




