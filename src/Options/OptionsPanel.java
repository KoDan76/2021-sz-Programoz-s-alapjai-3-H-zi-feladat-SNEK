package Options;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import Main_and_Utility.MainFrame;
import Main_and_Utility.PopUp;
import Main_and_Utility.SpecialPanel;

public class OptionsPanel extends SpecialPanel implements Serializable{
		private SnekOptions opt;
		private JTextField txtPName;
		private JComboBox<Integer> combApplesAtOnce;
		private JComboBox<String>  comboSnekColor;
		private JComboBox<Integer>  comboGameHeight;
		private JComboBox<Integer>  comboGameWidth;
		private JTextField txtWalls;
		private JTextField txtAppleFrequency;
		private JCheckBox checkSpecialsEnabled;
		private HashMap<String, Color > colorMap = new HashMap<String, Color >();
		
		
	
	public OptionsPanel(SnekOptions newOpt, JFrame tFrame, MainFrame mFrame) {
		super(tFrame, mFrame);
		
		
		opt = newOpt;	
		
		fillColorMap();		
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Integer[] gameSizes = new Integer[24];
		gameSizes[0] = 540;
		
		int j = 0;
		while (gameSizes[j] < 1920   ) {
			j++;
			gameSizes[j] = gameSizes[j-1] + 2*30;
		}
		
		Integer[] appleNums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		
		String[] snekColor = {"Green", "Orange", "Cyian", "Red", "Blue" }; 
		
		JPanel[] miniPanels = new JPanel[9];
		JTextArea[] helptexts = {
				new JTextArea("Player Name: "), 
				new JTextArea("Maximum apples spawned at once: "),
				new JTextArea("Snek color: "),
				new JTextArea("Number of random walls: "),
				new JTextArea("Apple Spawn time: "),
				new JTextArea("Allow Specials to spawn "),
				new JTextArea("Screen Width: "),
				new JTextArea("Screen Height: ")
			};
		
		for (int i = 0; i < miniPanels.length; i++) {
			miniPanels[i] = new JPanel();
			if (i < helptexts.length) {
				miniPanels[i].add(helptexts[i]) ;
			}
		}
				
		txtPName = new JTextField("      " + opt.getPlayerName() + "      ");
		miniPanels[0].add(txtPName);
		
		combApplesAtOnce = new JComboBox<Integer>(appleNums);		
		miniPanels[1].add(combApplesAtOnce);
				
		comboSnekColor = new JComboBox<String>(snekColor);		
		miniPanels[2].add(comboSnekColor);		
		
		txtWalls = new JTextField(""+opt.getWallNum());		
		miniPanels[3].add(txtWalls);
		
		txtAppleFrequency = new JTextField(""+opt.getAppleFrequency());		
		miniPanels[4].add(txtAppleFrequency);
		
		checkSpecialsEnabled = new JCheckBox();
		miniPanels[5].add(checkSpecialsEnabled);
		
		comboGameWidth = new JComboBox<Integer>(gameSizes);
		miniPanels[6].add(comboGameWidth);
		
		comboGameHeight = new JComboBox<Integer>(gameSizes);
		miniPanels[7].add(comboGameHeight);
	
		JButton okButton = new JButton("SET OPTIONS");
		okButton.addActionListener(new OkayListener());
		miniPanels[8].add(okButton);
		
		JButton cancelButton = new JButton("CANCEL");
		cancelButton.addActionListener(e -> {
			try {
				 mainFrame.loadOptions();
			} catch (ClassNotFoundException | IOException e1) {
				new PopUp().error("Couldn't Save Options");
			} 
			close(); });		
		miniPanels[8].add(cancelButton);
		
		for (JPanel mp : miniPanels) {
			add(mp, Container.CENTER_ALIGNMENT);
		}
	}
	
	protected void fillColorMap() {
		colorMap.put("Green", Color.green);
		colorMap.put("Orange", Color.orange);
		colorMap.put("Cyian", Color.cyan);
		colorMap.put("Red", Color.red);
		colorMap.put("Blue", Color.blue);		
	}
	
	private class OkayListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			opt.setWallNum(Integer.parseInt(
					txtWalls.getText().replaceAll(" ","")) >= (Integer)50 ?
							50 : 
							(int)Integer.valueOf(txtWalls.getText()));
			
			opt.setPlayerName(txtPName.getText().replaceAll(" ",""));
			
			opt.setSnekColor(colorMap.get(comboSnekColor.getSelectedItem().toString()));
			
			opt.setApplesAtonce((int)combApplesAtOnce.getSelectedItem());
			
			opt.setEnableSpecials(checkSpecialsEnabled.isSelected());
			
			opt.setAppleFrequency(Integer.parseInt(txtAppleFrequency.getText()));
			
			opt.setGameHeight((int)comboGameHeight.getSelectedItem());
			
			opt.setGameWidth((int)comboGameWidth.getSelectedItem());
			
			try {
				mainFrame.saveOptions();
				new PopUp().error("New Options saved, all game progress has been lost.");
				opt.setOptionsChanged(true);
			} catch (IOException e1) {
				new PopUp().error("Couldn't Save Options");
			}	
			
			close();
		}
		
	}
	
}
