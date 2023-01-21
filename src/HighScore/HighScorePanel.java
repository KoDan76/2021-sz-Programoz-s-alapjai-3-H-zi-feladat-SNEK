package HighScore;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Main_and_Utility.MainFrame;
import Main_and_Utility.SpecialPanel;

public class HighScorePanel extends SpecialPanel {

	private HighScores data = new HighScores();
	
	public HighScorePanel(JFrame tFrame, MainFrame mFrame) {
		super(tFrame, mFrame);	
		
		data.sortByScore();
		
		this.setLayout(new BorderLayout());
        JTable table = new JTable(data);
        JScrollPane plane = new JScrollPane(table);       
		add(plane, BorderLayout.CENTER);
		
		JPanel backPanel = new JPanel();
		add(backPanel, BorderLayout.SOUTH);
		
		JButton bBack = new JButton("BACK");
		bBack.addActionListener( e -> close());
		backPanel.add(bBack);
	}
}

