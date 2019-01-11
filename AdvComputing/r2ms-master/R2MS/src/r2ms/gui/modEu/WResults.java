package r2ms.gui.modEu;

import java.awt.SystemColor;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
/**
 * 
 * @author Guillermo Pascual and Diego Rivera
 * 
 * Version: 20/12/2018
 *
 */
public class WResults extends JFrame {

private JPanel contentPane;
private JRadioButton rdbtnTemperature;
private ButtonGroup btn;

/**
* Create the frame with the parameters needed to fill the combo boxes.
*/
public WResults(double[] temp, int[] lattsiz, double[] h1) {
setTitle("Results");
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setBounds(100, 100, 450, 300);

contentPane = new JPanel();
contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
setContentPane(contentPane);
contentPane.setLayout(null);

//I want to set my combo boxes with the values introduced by the user in window 1. 
JComboBox Temperature = new JComboBox();
for(int i=0;i<temp.length;i++) { 
Temperature.addItem(String.valueOf(temp[i]));//Converts each double value from the Double[] to a String value. 
}

Temperature.setBounds(248, 105, 45, 20);
contentPane.add(Temperature);

JComboBox LattizeSize = new JComboBox();
String[] Lattsiz=new String[lattsiz.length];
for(int i=0;i<lattsiz.length;i++) {  
Lattsiz[i]=String.valueOf(lattsiz[i]); 
}
LattizeSize.setModel(new DefaultComboBoxModel(Lattsiz));
LattizeSize.setBounds(248, 150, 45, 20);
contentPane.add(LattizeSize);

JComboBox H = new JComboBox();
String[] H1=new String[h1.length];
for(int i=0;i<h1.length;i++) {  
H1[i]=String.valueOf(h1[i]); 
}
H.setModel(new DefaultComboBoxModel(H1));
H.setBounds(248, 194, 45, 20);
contentPane.add(H);

JTextPane txtpnPleaseSelectOne = new JTextPane();
txtpnPleaseSelectOne.setBackground(SystemColor.control);
txtpnPleaseSelectOne.setText("Please select one of the following magnitures and fix the values for the other two. ");
txtpnPleaseSelectOne.setBounds(41, 11, 357, 44);
contentPane.add(txtpnPleaseSelectOne);

JRadioButton rdbtnH = new JRadioButton("H");
rdbtnH.setBounds(66, 192, 127, 25);
contentPane.add(rdbtnH);

JRadioButton rdbtnLatticeSize = new JRadioButton("Lattice size");
rdbtnLatticeSize.setBounds(66, 148, 127, 25);
contentPane.add(rdbtnLatticeSize);
contentPane.add(getRdbtnTemperature());

btn=new ButtonGroup();
btn.add(rdbtnTemperature);
btn.add(rdbtnLatticeSize);
btn.add(rdbtnH);
}
	private JRadioButton getRdbtnTemperature() {
		if (rdbtnTemperature == null) {
			rdbtnTemperature = new JRadioButton("Temperature");
			rdbtnTemperature.setBounds(66, 103, 127, 25);
		}
		return rdbtnTemperature;
	}
}
