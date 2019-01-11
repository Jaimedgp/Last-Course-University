package r2ms.gui.modEu;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import r2ms.inputData.modBi.Bi;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.SystemColor;

import java.io.*;
/**
 * 
 * @author Guillermo Pascual and Diego Rivera
 * 
 * Version: 20/12/2018
 *
 */
public class Window1 extends JFrame {

	private JPanel contentPane;
	public static JTextField Temperature;
	public static JTextField lattice_size;
	public static JTextField h;
	public static JTextField MCSweeps;
	public static JTextField Therma;
	public static JTextField Nskip;
	public static JTextField J;
	public static JTextField Nj;

	FileWriter fichero;
	PrintWriter pw;

	double[] Temp;
	int[] latticeSize;
	double[] H;
	int mcs;
	int Thermalization;
	int nskip;
	int NJ;
	double[] j;
	
	private String Sjstring;
	private String Sns;
	private String Stsweeps;
	private String Scsweeps;
	private String Smagnetic;
	private String Slattice;
	private String Stemperature;
	
	WAsk ask;
	WResults results;
	ReadWindow read;
	public static Bi[] input;
	
	public Window1() {
			setTitle("Ising 2D");
		/**
		 * All windows have been done with WindowBuilder. Most of the code you will find is to create, panels, layouts, test...and their 
		 * respective postions and sizes. The comments for these sections have been omited. 
		 */
			fichero = null;
			pw = null;
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 684, 478);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			JTextPane txtpnWelcomeThisWill = new JTextPane();
			txtpnWelcomeThisWill.setBackground(SystemColor.control);
			txtpnWelcomeThisWill.setText(
					"Welcome. This is where YOU will take control of the Ising's simulation done by the Advanced Computational Techniques students of 2018-2019.");
			txtpnWelcomeThisWill.setBounds(36, 11, 599, 42);
			contentPane.add(txtpnWelcomeThisWill);

			JLabel lblTemperature = new JLabel("Temperature:");
			lblTemperature.setToolTipText("Each temperature will fix this aprameter to launch the MonteCarlo simulation according to the corresponding hamiltonian.");
			lblTemperature.setBounds(85, 107, 77, 14);
			contentPane.add(lblTemperature);

			JLabel lblLatticeSize = new JLabel("Lattice size:");
			lblLatticeSize.setToolTipText("Each lattice size will fix this aprameter to launch the MonteCarlo simulation according to the corresponding hamiltonian (more spins to be sweeped).");
			lblLatticeSize.setBounds(85, 138, 77, 14);
			contentPane.add(lblLatticeSize);

			JLabel lblH = new JLabel("H:");
			lblH.setToolTipText("Each value of H will fix this parameter and launch a MonteCarlo simmulation with the corresponding Hamiltonian.");
			lblH.setBounds(85, 169, 77, 14);
			contentPane.add(lblH);

			JLabel lblJ = new JLabel("J:");
			lblJ.setToolTipText("Values of the neighboring interactions.");
			lblJ.setBounds(85, 231, 77, 14);
			contentPane.add(lblJ);

			JLabel lblMonteCarloSweeps = new JLabel("Monte Carlo Sweeps:");
			lblMonteCarloSweeps.setToolTipText("Total number of Monte Carlo Sweeps.");
			lblMonteCarloSweeps.setBounds(85, 262, 165, 14);
			contentPane.add(lblMonteCarloSweeps);

			JLabel lblThermalization = new JLabel("Thermalization:");
			lblThermalization.setToolTipText("Initial number of MonteCarlo sweeps until thermalization. This sweeps will not be acoounted on the final physical results.");
			lblThermalization.setBounds(85, 293, 116, 14);
			contentPane.add(lblThermalization);

			JLabel lblStepstToSkip = new JLabel("Stepst to skip:");
			lblStepstToSkip.setToolTipText("Physical results will be obteined from valid MonteCarlo sweeps. This is the number of sweeps between two valid Montecarlo sweeps.");
			lblStepstToSkip.setBounds(85, 321, 116, 14);
			contentPane.add(lblStepstToSkip);


			Temperature = new JTextField();
			Temperature.setToolTipText("Introduce the desired temperatures separated by a space and in double format.");
			Temperature.setText("0.9");
			Temperature.setBounds(293, 104, 86, 20);
			contentPane.add(Temperature);
			Temperature.setColumns(10);

			lattice_size = new JTextField();
			lattice_size.setToolTipText("Introduce the lattice sizes separated by spaces.");
			lattice_size.setText("40");
			lattice_size.setColumns(10);
			lattice_size.setBounds(293, 135, 86, 20);
			contentPane.add(lattice_size);

			h = new JTextField();
			h.setToolTipText("Introduce the different values for the magnetic field H separated by spaces and in double format.");
			h.setText("0.0");
			h.setColumns(10);
			h.setBounds(293, 166, 86, 20);
			contentPane.add(h);

			J = new JTextField();
			J.setToolTipText("Introduce the diferent neighbour interactive factors separated by space. be aware that the number of J should be Nj.");
			J.setText("1.0 0.0");
			J.setColumns(10);
			J.setBounds(293, 228, 86, 20);
			contentPane.add(J);

			MCSweeps = new JTextField();
			MCSweeps.setText("100000");
			MCSweeps.setColumns(10);
			MCSweeps.setBounds(293, 259, 86, 20);
			contentPane.add(MCSweeps);

			Nskip = new JTextField();
			Nskip.setText("25000");
			Nskip.setColumns(10);
			Nskip.setBounds(293, 290, 86, 20);
			contentPane.add(Nskip);

			Therma = new JTextField();
			Therma.setText("1000");
			Therma.setColumns(10);
			Therma.setBounds(293, 318, 86, 20);
			contentPane.add(Therma);
			
			/**
			 * The program will allow you to either right the values of the parameters in the text fields shown above
			 * and run the simulations with them; or read them from a file. It will also allow you to see which, if any, of the parameters introduced is 
			 * incorrect with a Parse button.
			 */

			//Creates the button Read from a file that will take the user to the window Read Window.
			JButton btnRead = new JButton("READ FROM A FILE");
			btnRead.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					read=new ReadWindow();
					dispose(); //closes the current window.
					read.setVisible(true); //open ReadWindow.
				}
			});
			btnRead.setBounds(85, 368, 165, 23);
			contentPane.add(btnRead);

			//in construction
			JButton btnParse = new JButton("PARSE");
			btnParse.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					
					String[] partsJ = J.getText().split(" ");
					String js="";
					j = new double[partsJ.length];
					for (int i = 0; i < partsJ.length; i++) {
						j[i] = Double.parseDouble(partsJ[i]);
						js=j[i]+" ";
					}

					String[] partsT = Temperature.getText().split(" ");
					Temp = new double[partsT.length];
					for (int i = 0; i < partsT.length; i++) {
						Temp[i] = Double.parseDouble(partsT[i]);
					}

					String[] partsH = h.getText().split(" ");
					H = new double[partsH.length];
					for (int i = 0; i < partsH.length; i++) {
						H[i] = Double.parseDouble(partsH[i]);
					}

					String[] partsL = lattice_size.getText().split(" ");
					latticeSize = new int[partsL.length];
					for (int i = 0; i < partsL.length; i++) {
						latticeSize[i] = Integer.parseInt(partsL[i]);
					}


					for (int i = 0; i < latticeSize.length; i++) {
						for (int l = 0; l < Temp.length; l++) {
							for(int k=0; k < H.length; k++) {
								Stemperature = "Temperature\t"+Temp[l]+"\n";
								Slattice = "Latticelength\t"+latticeSize[i]+"\n";
								Smagnetic = "Hfield\t"+H[k]+"\n";
								Scsweeps = "MCS\t"+MCSweeps.getText()+"\n";
								Stsweeps = "Thermalization\t"+Therma.getText()+"\n";
								Sns = "Skip\t"+Nskip.getText()+"\n";
								Sjstring = "J\t"+Nj.getText()+"\t"+js+"\n";
			            
//			     		   	    if (input.parse(String)==0) {
//			     		       	
//			     		  	     }else {
//			      	      	
//			       			     }
					
							}	
						}
					}
				}
			});
			btnParse.setBounds(293, 368, 89, 23);
			contentPane.add(btnParse);

			JButton btnRun = new JButton("RUN");
			btnRun.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					//With the data written on the text fields, we generate the needed parameters to create an object with 
					//the constructor of InputData.
					String[] partsJ = J.getText().split(" ");
					j = new double[partsJ.length];
					for (int i = 0; i < partsJ.length; i++) {
						j[i] = Double.parseDouble(partsJ[i]);
					}

					String[] partsT = Temperature.getText().split(" ");
					Temp = new double[partsT.length];
					for (int i = 0; i < partsT.length; i++) {
						Temp[i] = Double.parseDouble(partsT[i]);
					}

					String[] partsH = h.getText().split(" ");
					H = new double[partsH.length];
					for (int i = 0; i < partsH.length; i++) {
						H[i] = Double.parseDouble(partsH[i]);
					}

					String[] partsL = lattice_size.getText().split(" ");
					latticeSize = new int[partsL.length];
					for (int i = 0; i < partsL.length; i++) {
						latticeSize[i] = Integer.parseInt(partsL[i]);
					}

					mcs = Integer.parseInt(MCSweeps.getText());
					Thermalization = Integer.parseInt(Therma.getText());
					nskip = Integer.parseInt(Nskip.getText());
					NJ = Integer.parseInt(Nj.getText());
					
					int Total = latticeSize.length * Temp.length * H.length;
					
					
					
					//Creates an input data object for every set of parameters from the text fields introduced by the user.
					input=new Bi[Total];
					int count=0;
					for (int i = 0; i < latticeSize.length; i++) {
						for (int l = 0; l < Temp.length; l++) {
							for(int k=0; k < H.length; k++) {
								input[count]= new Bi(latticeSize[i], Temp[l], H[k], NJ, j, mcs, Thermalization, nskip);
								count++;
							}
						}
					
					}
					
					
					//Creates a WAsk window that includes the Imput data object as a parameter.
					
					ask = new WAsk(input);				
					results=new WResults(Temp, latticeSize, H);					
					dispose();
					ask.setVisible(true);	
				}

			});
			btnRun.setBounds(497, 368, 89, 23); 
			contentPane.add(btnRun);

			JLabel lblNj = new JLabel("Nj:");
			lblNj.setToolTipText("Number of neighbour interactions.");
			lblNj.setBounds(85, 200, 77, 14);
			contentPane.add(lblNj);

			Nj = new JTextField();
			Nj.setToolTipText("Introduces the different neighbour interactive factors separated with a space and in format double.");
			Nj.setText("2");
			Nj.setColumns(10);
			Nj.setBounds(293, 197, 86, 20);
			contentPane.add(Nj);
		}
}
