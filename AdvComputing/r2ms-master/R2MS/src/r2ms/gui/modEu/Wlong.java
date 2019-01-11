package r2ms.gui.modEu;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import r2ms.common.Graph;
import r2ms.common.IResults;
import r2ms.inputData.modBi.Bi;
//import r2ms.longSim.modC.C;

/**
 * Creates a loading window for the process of the simulations WITH the choice
 * of pausing them.
 * 
 * 
 * @author Guillermo Pascual and Diego Rivera
 * 
 * Version: 20/12/2018
 *
 */
public class Wlong extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Window1 Return;
	WResults results;
	JProgressBar progressBar;
	private JButton btnStart;
	C simulation;
	Bi[] input;
	Re re;
	static double fraction = 0;
	private JButton btnContinue;
	private JButton button;
	private JTextPane txtpnYourSimulationIs;

	public Wlong(final C simulation, final Bi[] input) {

		this.simulation = simulation;
		this.input = input;
		re=new Re();
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
		 * The message "Your simulation is currently being executed. Please wait." is
		 * shown.
		 */

		contentPane.add(gettxt());

		/**
		 * A cancel button is created. It disposes the Wlong screen and calls a new
		 * Window1 object.
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

		/**
		 * A pause button is created. It disposes the Wlong screen and calls a new
		 * Wpause object.
		 */

		button = new JButton("PAUSE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Longterm pause method
				simulation.pauseSimulation();
				button.setVisible(false);
				btnContinue.setVisible(true);
				txtpnYourSimulationIs.setText("Your simulation is paused");

			}
		});
		button.setBounds(176, 216, 97, 23);
		contentPane.add(button);
		contentPane.add(getBtnStart());
		contentPane.add(getBtnContinue());
		btnContinue.setVisible(false);
	}

	public void progress(final C simulation, final Bi[] input) {
		/**
		 * The progress bar is set to show the percentaje of done simulations over the
		 * total number of them. To do so we count the number of needed simulations
		 * imputed by the user, and count one by one the simulations. When all the
		 * simulations are over, the results are shown.
		 */
		// Now we should start the long term simulations. We don''t have the methods
		// yet, but we create a loop to go through all the components
		// of the inArray. Maybe it would be idea to store the results so when we pause
		// the simulations we can acess them.

		// We launch the simulation.
		boolean running = false;
		
		running = simulation.startSimulation(input, input.length, re);
		if (running) {
			while (true) {
				double fraction = simulation.currentSimulationFraction();
				if (fraction == 1)
					break;
				progressBar.setValue((int) fraction * 100);
//				try {
//					Thread.sleep(50);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}

		} else {

		}
	}

	private JButton getBtnStart() {
		if (btnStart == null) {
			btnStart = new JButton("START");
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boolean running = false;
					running = simulation.startSimulation(input, input.length, re);
					btnStart.setVisible(false);
					txtpnYourSimulationIs.setText("Your simulation is currently being executed. Please wait.");
					if (running) {
						new Thread(new Runnable() {
							public void run() {

								while (fraction != 1) {
									try {
										Thread.sleep(200);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									fraction = simulation.currentSimulationFraction();
									progressBar.setValue((int) fraction * 100);
//									Thread t = new Thread(new Runnable() {
//										@Override
//										public void run() {
//											SwingUtilities.invokeLater(new Runnable() {
//												@Override
//												public void run() {
//													// TODO Auto-generated method stub
//													progressBar.setValue((int) fraction * 100);
//												}
//											});
//										}
//
//									});
//									t.start();
//									try {
//										t.join();
//									} catch (InterruptedException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}

								}
								simulation.getSimulationResults();
								re.getPointstT(input[0].latticeLength, input[0].H);
							}
						}).start();
					}
				}
			});
		}
		btnStart.setBounds(323, 215, 97, 25);
		return btnStart;
	}

	private JButton getBtnContinue() {
		if (btnContinue == null) {
			btnContinue = new JButton("CONTINUE");
			btnContinue.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					button.setVisible(true);
					btnContinue.setVisible(false);
					txtpnYourSimulationIs.setText("Your simulation is currently being executed. Please wait.");
				}
			});
			btnContinue.setBounds(176, 215, 97, 25);
		}
		return btnContinue;
	}

	private JTextPane gettxt() {
		if (txtpnYourSimulationIs == null) {
			txtpnYourSimulationIs = new JTextPane();
			txtpnYourSimulationIs.setBackground(SystemColor.control);
			txtpnYourSimulationIs.setText("Start the simulation");
			txtpnYourSimulationIs.setBounds(79, 27, 276, 44);
		}
		return txtpnYourSimulationIs;
	}

}
