package AppleTypes;

import java.awt.Color;

import Game.Snek;

public class SpeedApple extends Apple{
	
	public SpeedApple(){
		color = Color.blue;
		value = 2;
	}

	@Override
	public void appleEffect(Snek snek) {
		super.appleEffect(snek);
		snek.faster(1);		
	}

}
