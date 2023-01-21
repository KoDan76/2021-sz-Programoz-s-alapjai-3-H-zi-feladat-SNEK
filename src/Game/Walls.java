package Game;

import java.awt.*;
import java.util.*;

import Options.SnekOptions;

public class Walls extends Colliders{	
	private int num = 0;
	Random random = new Random();	

	public Walls(GamePanel game, SnekOptions opt) {
		super(game);
		num = opt.getWallNum() + 2*game.getSCREEN_HEIGHT()/game.getUnitSize() + 2* game.getSCREEN_WIDTH()/game.getUnitSize();
		generateRandomWalls(num - opt.getWallNum() -4, game);
	}
	
	protected void initialize(GamePanel game) {
		super.initialize(game);		
		int I = 0;	
		for (int i = 0; i < game.getSCREEN_WIDTH(); i += game.getUnitSize()) {
			x[I] = i;
			y[I] = 0;
			I ++;
			x[I] = i;
			y[I] = game.getSCREEN_HEIGHT() - game.getUnitSize();
			I++;
		}
		for (int i = game.getUnitSize() ; i < game.getSCREEN_HEIGHT()-game.getUnitSize(); i+=game.getUnitSize()) {
			x[I] = 0;
			y[I] = i;
			I ++;
			x[I] = game.getSCREEN_WIDTH() - game.getUnitSize();
			y[I] = i;
			I++;
		}		
	}
	
	protected void generateRandomWalls(int I, GamePanel game) {
		while (I < num -4) {
			x[I] = random.nextInt(
				(int)((game.getSCREEN_WIDTH() - 2*game.getUnitSize()) / game.getUnitSize()) 
				) * game.getUnitSize() + game.getUnitSize();
			y[I] = random.nextInt(
				(int)((game.getSCREEN_HEIGHT() - 2*game.getUnitSize()) / game.getUnitSize())
				) *game.getUnitSize() + game.getUnitSize() ;
		I++;
		}
		num -= 4;
	}
	
	public int wallsNum() {
		return num ;
	}
	
}
