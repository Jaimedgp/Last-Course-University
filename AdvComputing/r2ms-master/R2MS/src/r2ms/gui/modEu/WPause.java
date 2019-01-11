package r2ms.gui.modEu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import r2ms.longSim.modC.C;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import r2ms.inputData.modBi.Bi;

public class WPause extends JFrame {

	private JPanel contentPane;
	

	/**
	 * Create a window that allows you to either continue with the simulations or save the results gotten so far.
	 */
	Wlong contin;
	Bi[] bi;
	
	public WPause(final C simulation) {
		setTitle("Pause");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * The message "Simulation is paused" is shown.
		 */
		
		JLabel lblSimulationIsPaused = new JLabel("Simulation is paused");
		lblSimulationIsPaused.setBounds(117, 101, 195, 16);
		contentPane.add(lblSimulationIsPaused);
		
		/**
		 * A continue button is created. It disposes the pause screen and calls a new Wlong object that continues with 
		 * the simulations where they were paused.
		 */
		
		bi=Window1.input; //The aim is to call the initial input data array with all the iput data, and cut all the elements that have already been used on the executuion.
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//continue longterm method
				simulation.continueSimulation();
				contin=new Wlong(simulation,bi);
				dispose();
				contin.setVisible(true);
			}
		});
		btnContinue.setBounds(323, 215, 97, 25);
		contentPane.add(btnContinue);
		
		/**
		 * A save (or maybe save and close) button is created. It saves the simulations done until the pause was done 
		 * and display the results.
		 */
		
		JButton btnSaveResults = new JButton("Save Results");
		btnSaveResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//save longterm method
			}
		});
		btnSaveResults.setBounds(12, 215, 111, 25);
		contentPane.add(btnSaveResults);
	}

}
