package Main_and_Utility;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class PopUp {
	
	public void error(String errorMsg) {
		JOptionPane error = new JOptionPane(errorMsg,
		JOptionPane.ERROR_MESSAGE); 
		JDialog jd = error.createDialog("Error");
		jd.setVisible(true);
	}
}
