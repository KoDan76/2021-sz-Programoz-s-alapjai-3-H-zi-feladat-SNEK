package AppleTypes;

import java.awt.Color;

import Game.Snek;

public class PoisonApple extends Apple {
	
	public PoisonApple() {
		color = Color.yellow;
		value = 30;
	}
	@Override
	public void appleEffect(Snek snek) {
		super.appleEffect(snek);
		snek.poison();		
	}

}
