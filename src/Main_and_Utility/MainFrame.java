package Main_and_Utility;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.JPanel;

import Game.GamePanel;
import HighScore.HighScorePanel;
import Options.OptionsPanel;
import Options.SnekOptions;

public  class MainFrame extends JFrame implements Serializable{
	private JPanel mainPanel;
	private GamePanel gamePanel;
	private SnekOptions snekOpt = new SnekOptions();
	private OptionsPanel optPanel;
	JButton bPlay;
	JButton bOpt;
	
	MainFrame(){
		initialize();
	}
	
	public void initialize() {
		
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(900, 600));
		mainPanel.setLayout( new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		
		bPlay = new JButton("PLAY");
        bPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		bPlay.addActionListener( e -> startGame());
		bPlay.setPreferredSize(new Dimension(50,50));
		mainPanel.add(bPlay);
		
		bOpt = new JButton("OPTIONS");
        bOpt.setAlignmentX(Component.CENTER_ALIGNMENT);
		bOpt.addActionListener( e -> startOptions());
		bOpt.setPreferredSize(new Dimension(50,50));
		mainPanel.add(bOpt);
		
		JButton bHighScores = new JButton("HIGH SCORES");
		bHighScores.setAlignmentX(Component.CENTER_ALIGNMENT);
		bHighScores.addActionListener(e -> startHighScores());
		bHighScores.setPreferredSize(new Dimension(100,100));
		mainPanel.add(bHighScores);
		
		JButton bExit = new JButton("EXIT");
        bExit.setAlignmentX(Component.CENTER_ALIGNMENT);
		bExit.addActionListener( e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
		mainPanel.add(bExit);
		
		
		
		add(mainPanel);
		pack();	
		
		try {
			loadOptions();
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			new PopUp().error("Option file not found or corrupted");
		}
		
		setTitle("S.N.E.K.");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);		
		setVisible(true);
		setLocationRelativeTo(null);
	}
	


	private void startOptions() {
		this.setVisible(false);
		JFrame newFrame = new JFrame();
		optPanel = new OptionsPanel(snekOpt,newFrame, this);
		newFrame.add(optPanel); 		
		newFrame.pack();
		newFrame.setVisible(true);
 		newFrame.setLocationRelativeTo(null); 	
	}

	private void startGame() {
		boolean newGame = true;
		if (!snekOpt.isOptionsChanged()) {
			JOptionPane opt = new JOptionPane("Do you want to continue your unfinished game?",
			JOptionPane.QUESTION_MESSAGE, 
			JOptionPane.YES_NO_OPTION); 
			
			JDialog jd = opt.createDialog("PLAY S.N.E.K");
			jd.setVisible(true);
			if((int)opt.getValue() == 0) {
				newGame = false;
			}
		}
		
		this.setVisible(false);
		JFrame newFrame = new JFrame();
		gamePanel = new GamePanel(snekOpt, newFrame, this, newGame);
		newFrame.add(gamePanel);
 		newFrame.pack();
 		newFrame.setVisible(true);
 		newFrame.setLocationRelativeTo(null);
 		gamePanel.startGame(); 	  
	}

	private void startHighScores(){
		this.setVisible(false);
		JFrame newFrame = new JFrame();
		HighScorePanel highScores = new HighScorePanel(newFrame, this);
		newFrame.add(highScores); 		
		newFrame.pack();
		newFrame.setVisible(true);
 		newFrame.setLocationRelativeTo(null); 	
	}
	
	public void loadOptions() throws IOException, ClassNotFoundException {
		FileInputStream f = new FileInputStream("Options.kop");
		ObjectInputStream in = new ObjectInputStream(f);
		snekOpt = (SnekOptions)in.readObject();
		in.close();
	}

	public void saveOptions() throws IOException {
		FileOutputStream f = new FileOutputStream("Options.kop");
		ObjectOutputStream out =
		new ObjectOutputStream(f);
		out.writeObject(snekOpt);
		out.close();		
	}	
}