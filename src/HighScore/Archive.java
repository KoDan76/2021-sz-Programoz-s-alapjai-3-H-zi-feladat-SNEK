package HighScore;

import java.io.Serializable;
import java.util.Date;

public class Archive implements Serializable{
	
	private String playerName;
	private int score;
	private Date date;
	
	public Archive(String name, Date currDate, int  highScore){
		playerName = name;
		date = currDate;
		score = highScore;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
		
}
