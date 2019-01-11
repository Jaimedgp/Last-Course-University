package r2ms.gui.modEu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Creates a loading window for the process of the simulations WITHOUT the choice of pausing them. 
 * 
 * 
 * @author Guillermo Pascual and Diego Rivera
 * 
 * Version: 20/12/2018
 *
 */
public class Wnormal extends JFrame {

	private JPanel contentPane;
	Window1 Return;
	WResults results;
	JProgressBar progressBar;

	public Wnormal() {

		setTitle("Loading...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * The progress bar is created.
		 */

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(36, 111, 364, 25);
		contentPane.add(progressBar);

		/**
		 * The message "Your simulation is currently being executed. Please wait." is shown. 
		 */
		
		JTextPane txtpnYourSimulationIs = new JTextPane();
		txtpnYourSimulationIs.setBackground(SystemColor.control);
		txtpnYourSimulationIs.setText("Your simulation is currently being executed. Please wait.");
		txtpnYourSimulationIs.setBounds(79, 27, 276, 44);
		contentPane.add(txtpnYourSimulationIs);
		
		/**
		 * A cancel button is created. It disposes the Wnormal screen and calls a new Window1 object.
		 */

		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Return = new Window1();
				dispose();
				Return.setVisible(true);
			}
		});
		btnCancel.setBounds(36, 216, 89, 23);
		contentPane.add(btnCancel);
		
	}
}
