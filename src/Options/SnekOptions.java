package Options;

import java.awt.Color;
import java.io.*;

public class SnekOptions implements Serializable {

	private boolean optionsChanged = false;
	private int  applesAtOnce = 10;
	private Color snekColor = Color.green;
	private int wallNum = 15;
	private String playerName = "GeckoNoob";
	private boolean enableSpecials = true;
	private int appleFrequency = 30;
	private int gameHeight = 900;
	private int gameWidth = 600;

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public SnekOptions(){		
	}
	
	public void setApplesAtonce(int a) {
		applesAtOnce = a;
	}
	
	public int getApplesAtOnce() {
		return applesAtOnce;
	}
	
	public void setSnekColor(Color color) {
		snekColor = color;
	}

	public Color getSnekColor() {
		return snekColor;
	}
	
	public void setWallNum(int wallNum) {
		this.wallNum = wallNum;
	}
	
	public int getWallNum() {
		return wallNum;
	}

	public String getPlayerName() {
		return playerName ;
	}

	public boolean isEnableSpecials() {
		return enableSpecials;
	}

	public void setEnableSpecials(boolean enableSpecials) {
		this.enableSpecials = enableSpecials;
	}

	public void setAppleFrequency(int appleFrequency) {
		this.appleFrequency = appleFrequency;
	}
	
	public int getAppleFrequency() {		
		return appleFrequency ;
	}

	public boolean isOptionsChanged() {
		return optionsChanged;
	}

	public void setOptionsChanged(boolean optionsChanged) {
		this.optionsChanged = optionsChanged;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public void setGameHeight(int gameHeight) {
		this.gameHeight = gameHeight;
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public void setGameWidth(int gameWidth) {
		this.gameWidth = gameWidth;
	}
}
