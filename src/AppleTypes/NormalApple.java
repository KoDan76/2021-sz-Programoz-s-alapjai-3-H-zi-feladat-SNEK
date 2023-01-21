package AppleTypes;

import java.awt.Color;

import Game.Snek;

public class NormalApple extends Apple{
	
	public NormalApple(){
		color = Color.red;
		value = 1;
	}
	@Override
	public void appleEffect(Snek snek) {
		super.appleEffect(snek);
	}
	
	@Override
	public boolean doesDeSpawn() {
		return false ;
	}
}
