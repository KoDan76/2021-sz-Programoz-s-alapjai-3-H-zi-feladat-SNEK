package HighScore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


public class HighScores extends AbstractTableModel {

	private ArrayList<Archive> archives = new ArrayList() ;
	
	public HighScores(){
		try {
			load();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("high_scores.kop not found or corrupted");
		}
	}

	void sortByScore() {
		archives.sort((iter1, iter2) -> 
			Integer.compare(iter2.getScore(), iter1.getScore()));		
	}

	public ArrayList<Archive> getArchives() {
		return archives;
	}

	public void setArchives(ArrayList<Archive> archives) {
		this.archives = archives;
	}		
	
	public void save() throws IOException {
		FileOutputStream f = new FileOutputStream("high_scores.kop");
		ObjectOutputStream out = new ObjectOutputStream(f);
		out.writeObject(archives);
		out.close();
	}
	
	public void load() throws IOException, ClassNotFoundException {
		FileInputStream f = new FileInputStream("high_scores.kop");
		ObjectInputStream in = new ObjectInputStream(f);
		archives = (ArrayList<Archive>)in.readObject();
		in.close();
	}

	@Override
	public int getRowCount() {
		return archives.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	public String getColumnName(int columnIndex) {
    	switch(columnIndex) {
			case 0: return "Player Name";
			case 1: return "Date";
			default: return "Score";
    	}    	
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 0: 
				return archives.get(rowIndex).getPlayerName();
			case 1:
				return archives.get(rowIndex).getDate();
			default: 
				return archives.get(rowIndex).getScore();
		}
	}
}
