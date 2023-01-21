package Game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import AppleTypes.*;
import Options.SnekOptions;

// Apple type 0normal, 1poison, 2slow, 3speed, 4confusion 

public class Apples extends Colliders {
	private Apple[] types;
	private int APPLES_AT_ONCE = 10 ;
	private int applesSpawned = 0;
	private boolean[] freeIndex;
	private boolean allowSpecials = false;
	
	public int getApplesSpawned() {
		return applesSpawned;
	}

	public Apples(GamePanel game, SnekOptions opt) {
		super(game);
		APPLES_AT_ONCE = opt.getApplesAtOnce();
		allowSpecials = opt.isEnableSpecials();
		initialize(game);
	}	
	
	protected void initialize(GamePanel game) {
		x = new int[APPLES_AT_ONCE];
		y = new int[APPLES_AT_ONCE];
		freeIndex = new boolean[APPLES_AT_ONCE];
		types = new Apple[APPLES_AT_ONCE];
		for(int i = 0; i < APPLES_AT_ONCE; i++) {
			freeIndex[i] = true;
		}
	}
	
	public void newApple(Snek snek, Walls walls, GamePanel game) {
		int I = getFirstFreeIndex();
		Random random = new Random();
		if (applesSpawned <= APPLES_AT_ONCE && I != -1) {
			do {
				x[I] = random.nextInt((int)(game.getSCREEN_WIDTH()/game.getUnitSize()))*game.getUnitSize();
				y[I] = random.nextInt((int)(game.getSCREEN_HEIGHT()/game.getUnitSize()))*game.getUnitSize();
				
				int type = random.nextInt(4);				
				if (type < (allowSpecials ? 3 : 4)){
					types[I] = new NormalApple();
				} else {
					type = random.nextInt(4);
					switch (type) {					
					case 0:
						//to be changed
						types[I] =new PoisonApple();
						break;
					case 1:
						types[I] = new SlowApple();
						break;
					case 2:
						types[I] = new SpeedApple();
						break;
					case 3:
						types[I] = new ConfusedApple();
						break;
					}
				}	
				
			} while((walls.findCoordiantes(x[I], y[I]) != -1) || (snek.findCoordiantes(x[I], y[I]) != -1));
			freeIndex[I] = false;
		}
		
		applesSpawned++;
	}
	
	public int getFirstFreeIndex() {
		for (int i = 0; i< APPLES_AT_ONCE; i++) {
			if (freeIndex[i]) return i; 
		}
		return -1;
	}
	
	public Apple getAppleAt(int index) {
		return types[index];
	}

	public void destroyApple(int appleIndex) {
		freeIndex[appleIndex] = true;
		applesSpawned--;
	}

	public int getMaxApples() {		
		return APPLES_AT_ONCE;
	}

	public boolean isFreeAt(int i) {
		return freeIndex[i];
	};
	
}
