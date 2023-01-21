package AppleTypes;

import java.awt.Color;

import Game.Snek;

public class ConfusedApple extends Apple {
	
	public ConfusedApple(){
		color = Color.pink;
		value = 3;
	}
	
	@Override
	public void appleEffect(Snek snek) {
		super.appleEffect(snek);
		snek.confuse();	
	}

}
