package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import javax.swing.*;

import HighScore.Archive;
import HighScore.HighScores;
import Main_and_Utility.MainFrame;
import Main_and_Utility.PopUp;
import Main_and_Utility.SpecialPanel;
import Options.SnekOptions;


public class GamePanel extends SpecialPanel implements ActionListener, Serializable{
	private int spawner = 5;
	
	private final int SPEED_STEPS = 10;	
	private int SCREEN_WIDTH = 900;
	private int SCREEN_HEIGHT = 600;
	private final int UNIT_SIZE = 30;
	private final int UNIT_NUMBER = (int)((SCREEN_HEIGHT*SCREEN_HEIGHT) / UNIT_SIZE);
	
	private Walls walls;
	private Apples apples;	
	
	final int BASE_DELAY = 150;
	
	private Snek player;	
	private String playerName;
	
	Timer timer;
	Random random;
	
	private boolean endGame = false;
	private boolean stopped = true;
		
	public GamePanel(SnekOptions opt, JFrame thisGame, MainFrame mFrame, boolean newGame){
		super(thisGame, mFrame);
		playerName = opt.getPlayerName();
		spawner = opt.getAppleFrequency();
		opt.setOptionsChanged(false);		
		
		SCREEN_WIDTH = opt.getGameWidth();
		SCREEN_HEIGHT = opt.getGameHeight();
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		random = new Random();			
		
		if (newGame) {
			walls = new Walls(this, opt);
			apples = new Apples(this,opt);			
			player = new Snek(this, opt); 
			
		} else {
			try {
				load();
			} catch (ClassNotFoundException | IOException e) {
				new PopUp().error("Game files not found or corrupted");
				close();
			}
		}
		
		player.setColor(opt.getSnekColor());
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setBackground(Color.black);
		setFocusable(true);
		addKeyListener(new MyKeyAdapter());
	}
	
	private void load() throws IOException, ClassNotFoundException {
		FileInputStream f = new FileInputStream("Walls.kop");
		ObjectInputStream in = new ObjectInputStream(f);
		walls = (Walls)in.readObject();
		in.close();
		
		f = new FileInputStream("Apples.kop");
		in = new ObjectInputStream(f);
		apples = (Apples)in.readObject();
		in.close();
		
		f = new FileInputStream("Snek.kop");
		in = new ObjectInputStream(f);
		player = (Snek)in.readObject();
		in.close();
	}
	
	private void save() throws IOException {
		FileOutputStream f = new FileOutputStream("Walls.kop");
		ObjectOutputStream out = new ObjectOutputStream(f);
		out.writeObject(walls);
		out.close();
		
		f = new FileOutputStream("Apples.kop");
		out = new ObjectOutputStream(f);
		out.writeObject(apples);
		out.close();

		f = new FileOutputStream("Snek.kop");
		out = new ObjectOutputStream(f);
		out.writeObject(player);
		out.close();
	}

	public int getUnitNum() {
		return UNIT_NUMBER;
	}	
	
	public int getUnitSize() {
		return UNIT_SIZE;
	}
	
	public void startGame() {			
		timer = new Timer(BASE_DELAY, this);
		apples.newApple(player, walls, this);   
	}

	public int getSCREEN_HEIGHT() {
		return SCREEN_HEIGHT;
	}

	public int getSCREEN_WIDTH() {
		return SCREEN_WIDTH;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		draw(g);
	}
	
	public void draw(Graphics g) {
		if (!endGame) {			
			if (stopped) {
				timer.stop();
				g.setColor(Color.red);
				g.setFont(new Font("Ink Free", Font.BOLD,75));
				FontMetrics metrics = getFontMetrics(g.getFont());
				g.drawString("Pause", (SCREEN_WIDTH - metrics.stringWidth("Pause"))/2, SCREEN_HEIGHT/2);
			}
			
			g.setColor(player.getColor());
			for(int i = 0; i < player.getBodyParts(); i++) {
				
				g.fillRect(player.getXAt(i), player.getYAt(i), UNIT_SIZE, UNIT_SIZE);
			}	
			
			g.setColor(Color.GRAY);
			for (int i = 0; i < walls.wallsNum(); i++) {
				g.fillRect(walls.getXAt(i), walls.getYAt(i), UNIT_SIZE, UNIT_SIZE);
			}
			
			for (int i = 0; i < apples.getMaxApples() ; i++) {
				if (!apples.isFreeAt(i)) {
					g.setColor(apples.getAppleAt(i).getColor());
					g.fillOval(apples.getXAt(i), apples.getYAt(i), UNIT_SIZE, UNIT_SIZE);	
				}
			}
			
			g.setColor(Color.green);
			g.setFont(new Font("Ink Free", Font.BOLD,23));
			g.drawString(playerName + ": " + player.getApplesEaten(), 10, 25);
			
			g.setColor(Color.blue);
			g.setFont(new Font("Ink Free", Font.BOLD,23));
			FontMetrics speedMetrics = getFontMetrics(g.getFont());
			String speedTitle = "Speed: " + player.getVelocity();
			g.drawString(speedTitle, SCREEN_WIDTH - speedMetrics.stringWidth(speedTitle) - 25, 25);
			
		} else {
			gameOver(g);
		}		
	}
	
	private void gameOver(Graphics g) {		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD,75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}

	public void updateVelocity() {
		timer.setDelay(BASE_DELAY - player.getVelocity()*SPEED_STEPS);
	}
	
	public void checkApple() {
		int appleIndex = apples.findCoordiantes(player.getXAt(0), player.getYAt(0));		
		if ( appleIndex != -1 ) {
			apples.getAppleAt(appleIndex).appleEffect(player);
			apples.destroyApple(appleIndex);
		}
	}
	
	public void checkCollisions() {
		// Checks if Snek touched himself
		for(int i = player.getBodyParts(); i > 0; i--) {
			if(player.getXAt(0) == player.getXAt(i) && player.getYAt(0) == player.getYAt(i)) {
				endGame = true;
			}
		}		
		// Checks if Snek touched the border walls
		if (walls.findCoordiantes(player.getXAt(0), player.getYAt(0)) != -1 )
		{
			endGame = true;
		}
				
		if (endGame) {
			timer.stop();			
		}
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (spawner == 0){
			if (apples.getApplesSpawned() < apples.getMaxApples()) {
				apples.newApple(player, walls, this);
				spawner = 10;
			}
		} else spawner--;
		
		if(!endGame) {
			player.move(UNIT_SIZE);
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	private class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			
			if (!timer.isRunning()) {
				timer.start();
				stopped = false;
			}
			
			if (endGame) {
				HighScores result = new HighScores();
				try {					
					result.load();
				} catch (IOException | ClassNotFoundException e1) {
					new PopUp().error("High Scores file not found or corrupted");
				}				
				result.getArchives().add(new Archive( playerName, new Date(), player.getApplesEaten()));
				try {
					result.save();
				} catch (IOException e1) {
					new PopUp().error("High Scores file not found or corrupted");
				}
				close();
			}
			
			switch(e.getKeyCode()) {			
			
			case KeyEvent.VK_A:
				if(player.getDirection() != (char) player.getDirectionMap().get('D')) {
					player.setDirection((char) player.getDirectionMap().get('A'));
				}
				break;
			case KeyEvent.VK_D:
				if(player.getDirection() != (char) player.getDirectionMap().get('A')) {
					player.setDirection((char) player.getDirectionMap().get('D'));
				}
				break;
			case KeyEvent.VK_W:
				if(player.getDirection() != (char) player.getDirectionMap().get('S')) {
					player.setDirection((char) player.getDirectionMap().get('W'));
				}
				break;
			case KeyEvent.VK_S:
				if(player.getDirection() != (char) player.getDirectionMap().get('W')) {
					player.setDirection((char) player.getDirectionMap().get('S'));
				}
				break;
			case KeyEvent.VK_SPACE:				
				if (timer.isRunning()) stopped = true;								
				break;
				
			case KeyEvent.VK_ESCAPE:
					stopped = true;						
					boolean quit = false;
					JOptionPane opt = new JOptionPane("Are you sure you want to quit?",
					JOptionPane.QUESTION_MESSAGE, 
					JOptionPane.YES_NO_OPTION); 
							
					JDialog jd = opt.createDialog("QUIT S.N.E.K");
					jd.setVisible(true);
					if((int)opt.getValue() == 0) {
						try {
							save();
						} catch (IOException e1) {
							new PopUp().error("Couldn't save game files");
						}close();						
					}									
				break;
			}
		}
	}
}
