package AppleTypes;

import java.awt.*;
import java.io.Serializable;

import Game.Snek;

public abstract class Apple implements Serializable{
	protected Color color = Color.red;
	protected int value = 1;

	public Color getColor() {
		return color;
	}
	
	public void appleEffect(Snek snek) {
		snek.setApplesEaten(snek.getApplesEaten()+value);	
		snek.setBodyParts(snek.getBodyParts()+1);
	};
	
	public boolean doesDeSpawn() {
		return true;
	}
}
