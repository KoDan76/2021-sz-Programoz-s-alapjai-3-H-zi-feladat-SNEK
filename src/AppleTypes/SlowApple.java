package AppleTypes;

import java.awt.Color;

import Game.Snek;

public class SlowApple extends Apple {
		
	public SlowApple(){
		color = Color.white;
		value = 1;
	}

	@Override
	public void appleEffect(Snek snek) {
		super.appleEffect(snek);
		snek.slower(1);
	}

}
