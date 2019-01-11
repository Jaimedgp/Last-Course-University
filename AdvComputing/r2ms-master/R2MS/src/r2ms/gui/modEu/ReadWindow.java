package r2ms.gui.modEu;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

import javax.swing.filechooser.FileFilter;

import r2ms.inputData.modBi.Bi;

import javax.swing.JFileChooser;
import java.nio.file.*;

/**
 * 
 * @author Guillermo Pascual and Diego Rivera
 * 
 * Version: 20/12/2018
 *
 */
public class ReadWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	Window1 Return;
	String Directory;
	WResults results;
	Bi input;
	ReadWindow refresh;
	
	/**
	 * Create the Read window. It allows you to read a file when specifying the directory where it is located.
	 */
	public ReadWindow() {
		setTitle("Read a File");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 549, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDirectoryOfThe = new JLabel("Directory of the file:");
		lblDirectoryOfThe.setBounds(24, 147, 162, 16);
		contentPane.add(lblDirectoryOfThe);
		
		textField = new JTextField();
		textField.setBounds(154, 144, 253, 22);
		textField.setText("");
		contentPane.add(textField);
		textField.setColumns(10);
		
		/**
		 * A return button is created. It disposes the reading screen and calls a new Window1 object.
		 */
		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Return= new Window1();
				dispose();
				Return.setVisible(true);
			}
		});
		btnReturn.setBounds(24, 298, 97, 25);
		contentPane.add(btnReturn);
		
		JButton btnBrowse = new JButton("BROWSE");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int seleccion = fileChooser.showOpenDialog(textField);
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					File fichero = fileChooser.getSelectedFile();
					textField.setText(fichero.getAbsolutePath());
					
					}
				}
		});
		
		btnBrowse.setBounds(419, 144, 89, 23);
		contentPane.add(btnBrowse);
		/**
		 * Creates a button Read that generates an input data object from a given directory.
		 */
		JButton btnRead = new JButton("Read and Run");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Directory=textField.getText();
				System.out.println(Directory);
				//Input data read directory file
				//start the simulation with the inputdata object
				input=new Bi();
				input.read(Directory);
				
				//A window 1 object is created.
				Return=new Window1();
				
				//I write the corresponding value from my Input Data object input in string format in my String object.
				String temperature="";
				temperature=String.valueOf(input.temperature);
				//I write it in the corresponding TextField of my window 1 object w1.
				Return.Temperature.setText(temperature);
				
				String H="";
				H=String.valueOf(input.H); 
				Return.h.setText(H);
				
				String latticeLenght="";
				latticeLenght=String.valueOf(input.latticeLength); 
				Return.lattice_size.setText(latticeLenght);
				
				String J="";
				J=String.valueOf(input.J); 
				Return.J.setText(J);
				
				String mcs="";
				mcs=String.valueOf(input.mcs); 
				Return.MCSweeps.setText(mcs);
				
				String skip="";
				skip=String.valueOf(input.skip); 
				Return.Nskip.setText(skip);
				
				String therm="";
				therm=String.valueOf(input.therm); 
				Return.Therma.setText(therm);
				
				String nJ="";
				nJ=String.valueOf(input.nJ); 
				Return.Nj.setText(nJ);

				Return.setVisible(true);//Open Window 1.
				dispose();
			}
		});
		btnRead.setBounds(374, 298, 134, 25);
		contentPane.add(btnRead);
		
		
	}
}
