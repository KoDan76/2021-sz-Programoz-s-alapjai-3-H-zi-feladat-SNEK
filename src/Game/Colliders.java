package Game;

import java.awt.*;
import java.io.*;

public class Colliders implements Serializable {
	protected int x[]; 
	protected int y[]; 	
	
	public Colliders(GamePanel game){
		initialize (game);
	}
	
	protected void initialize(GamePanel game) {
		x = new int[game.getUnitNum()];
		y = new int[game.getUnitNum()];
	}

	public int getXAt(int index) {
		return x[index];
	}	

	public int getYAt(int index) {
		return y[index];
	}
	
	public int findCoordiantes(int searchX, int searchY) {
		int i = 0;
		while (i < x.length) {
			if (x[i] == searchX) {
				if (y[i] == searchY) {
					return i;
				} 
			}
			i++;
		}
		return -1;		
	}
	
}
