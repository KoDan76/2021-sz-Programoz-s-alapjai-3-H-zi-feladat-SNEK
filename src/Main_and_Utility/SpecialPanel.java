package Main_and_Utility;

import java.awt.Dimension;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class SpecialPanel extends JPanel{
	
	protected MainFrame mainFrame;
	protected JFrame thisFrame;
	
	protected SpecialPanel(JFrame thisF, MainFrame mainF){
		thisFrame = thisF;
		mainFrame = mainF;
		setPreferredSize(new Dimension(900, 600));
	}
	
	public void close() {
		thisFrame.dispatchEvent(new WindowEvent(thisFrame, WindowEvent.WINDOW_CLOSING));
		mainFrame.setVisible(true);
	}
	
}
