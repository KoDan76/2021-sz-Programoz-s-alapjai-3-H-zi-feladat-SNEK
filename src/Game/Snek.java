package Game;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

import javax.swing.*;

import Options.SnekOptions;

public class Snek extends Colliders {
	
	private Color color = Color.green;
	private int applesEaten = 0;
	private int velocity = 0;
	private int bodyParts = 4;
	private char direction = 'D';
	private HashMap<Character,Character> directionMap = new HashMap<Character,Character>();
	
	public Snek(GamePanel game, SnekOptions opt) {
		
		super(game);
		color = opt.getSnekColor();
		for (int i = 0; i < bodyParts; i++) {
			x[i] = game.getSCREEN_WIDTH()/2 - i*game.getUnitSize();
			y[i] = game.getSCREEN_HEIGHT()/2;
		}
		
		directionMap.put('X','X'); // invalid
		directionMap.put('D','D');
		directionMap.put('S','S');
		directionMap.put('A','A');
		directionMap.put('W','W');
	}
	
	public int getBodyParts() {
		return bodyParts;
	}

	public HashMap getDirectionMap() {
		return directionMap;		
	}
	
	public void setBodyParts(int bodyParts) {
		this.bodyParts = bodyParts;
	}

	public int getVelocity() {
		return velocity;
	}

	public void faster(int mod) {
		velocity++;
	}
	
	public void slower(int mod) {
		if (velocity > 0) {
			velocity--;
		}
	}	
	
	public char getDirection() {
		return direction;
	}

	
	
	public void move(int unit_size) {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'W': 
			y[0] = y[0] - unit_size;
			break;
		case 'S':
			y[0] = y[0] + unit_size;
			break;
		case 'A':
			x[0] = x[0] - unit_size;
			break;
		case 'D':
			x[0] = x[0] + unit_size;
			break;
		}
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getApplesEaten() {
		return applesEaten;
	}

	public void setApplesEaten(int applesEaten) {
		this.applesEaten = applesEaten;
	}

	public void setDirection(char dir) {		
		direction = dir;
	}
	
	public void poison() {
		Random ranGen =new Random();
		int rounds = ranGen.nextInt(2)+1; 
		rotate(rounds);
	}
	
	public void rotate(int rounds){
		if (rounds == 1) {
			directionMap.put('D','S');
			directionMap.put('S','A');
			directionMap.put('A','W');				
			directionMap.put('W','D');
		} else
			directionMap.put('D','W');
			directionMap.put('S','D');
			directionMap.put('A','S');
			directionMap.put('W','A');			
	}
	
	public void confuse() {
		for (int i = 0; i < bodyParts / 2; i++) { 
            int tempX = x[i]; 
            int tempY = y[i];
            x[i] = x[bodyParts - i-1]; 
            y[i] = y[bodyParts - i-1];
            x[bodyParts - i -1] = tempX; 
            y[bodyParts - i -1] = tempY; 
        }
		if(x[1] > x[0]) direction = 'A';
				
		if(x[1] < x[0])  direction = 'D';
				
		if(y[1] > y[0]) direction = 'W';
				
		if(y[1] < y[0]) direction = 'S';					
	
	}
}
