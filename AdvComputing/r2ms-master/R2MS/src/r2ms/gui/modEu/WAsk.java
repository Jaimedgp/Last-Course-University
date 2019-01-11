package r2ms.gui.modEu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import r2ms.inputData.modBi.Bi;
//import r2ms.longSim.modC.C;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * @author Guillermo Pascual and Diego Rivera
 * 
 * Version: 20/12/2018
 *
 */
public class WAsk extends JFrame {

	private JPanel contentPane;
	Wlong longterm;
	Wnormal normal;
	C simulation;
	/**
	 * Creates a window to ask for the lenght of the simmulation. According to the answer it will generate a Wlong class object 
	 * or a Wnormal one.
	 * The basic difference is that one of them, the Wlong one, will give the user the option to pause the simulations.
	 */
	public WAsk(final Bi[] input) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		simulation=new C();
	
		/**
		 * Creates a button Yes that closes the Wask object and generates a Wlong one. Along with the new window it includes a 
		 * progress bar.
		 */	
		JButton btnYes = new JButton("Yes");
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				longterm=new Wlong(simulation,input);
				longterm.setVisible(true);
				
				dispose();
			}
		});
		btnYes.setBounds(281, 215, 97, 25);
		contentPane.add(btnYes);
		
		/**
		 * Creates a button No that closes the Wask object and generates a Wnormal one. Along with the new window it includes 
		 * a progress bar.
		 */	
		JButton btnNo = new JButton("No");
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				normal=new Wnormal();
				dispose();
				normal.setVisible(true);
				
		
		//Now we should start the normal term simulations. We dont have the methods yet, but we create a loop to go through all the components 
		//of the inArray. 
				
				for (int i = 0; i < input.length; i++) {
					//We launch the simulation.
					
				}		
			}
		});
		btnNo.setBounds(47, 215, 97, 25);
		contentPane.add(btnNo);
		
		JTextPane txtpnSimulatiosWillBe = new JTextPane();
		txtpnSimulatiosWillBe.setBackground(SystemColor.control);
		txtpnSimulatiosWillBe.setText(input.length + " simulations will be executed. Do you want to start a long term simulation?");
		txtpnSimulatiosWillBe.setBounds(47, 55, 331, 105);
		contentPane.add(txtpnSimulatiosWillBe);
	}
}
