package r2ms.gui.modIr;

import java.awt.event.ActionEvent;
import r2ms.common.*;
import r2ms.inputData.modBi.*;
import r2ms.longSim.modC.C;
import r2ms.simIsing.modHe.*;
import r2ms.results.modRe.*;


import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import fundamentos.Grafica;

/*
 * Author: Inigo Gonzalez Ruiz
 * Version: 08/01/2019
 * Problems:
 * 	-Long simulation thread disables the rest of the functionality while working
 * 	-LongSimulation not fully tested
 * 	-Graphs not working properly
 * To do for next version:
 * 	-handle exception cases for the input
 * Implemented in this version:
 * 	-Long simulation
 * 	-Graph for multiple temperature simulations	
 * 
 * Class that creates the JFrame objects that will allow to perform
 * the simulation and show the results. 
 */
public class GlobalFrame {
	
	Boolean multipleTemps=false;//variable to check if only one simulation is going to be done
	Boolean longSimulation=false;//variable to check if a long simulation is needed
	
	String filePath=null;//contains the name of the file
	InputData inputData;//input data
	ILongSimulationManager longSimManager;
	Double inTemp=0.0;
	Double finTemp=300.0;
	Double temperatureIncrement=30.0;
	
	JFrame inputFrame; 
	JFrame simulationFrame;
	JFrame resultsFrame;
	JFrame temperatureFrame;
	JFrame simOptionsFrame;
	File file;
	JFileChooser jfc;
	IR2MS sim;
	IResults results;
	Result	tempResult;
	
	/**
	 * Class constructor, creates three frames: input, temperatures and simulation/results. Only the input 
	 * is visible at the start
	 */
	public GlobalFrame() {
		inputFrame=new JFrame("Data Input");//creating instance of JFrame 
		inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JTextField tf=new JTextField();//creating text box  
	    tf.setBounds(100,100,150,20);
	    tf.setText("File path");
	    
	    
	    //Creating a button to get the file path
	     
			JButton filePathButton=new JButton("Select file directory");//creating instance of JButton  
			filePathButton.setBounds(100,150,200, 40);//x axis, y axis, width, height  
		
			filePathButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(null);
				// int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					filePath=(selectedFile.getAbsolutePath());
					tf.setText(filePath);
				}           
			           
			}
			});
			inputFrame.add(filePathButton);//adding button  
	    
	    
			//Creating a button to get the input from the textbox
	     
			JButton fileReader=new JButton("Read from file");//creating instance of JButton  
			fileReader.setBounds(100,200,200, 40);//x axis, y axis, width, height  
		
			fileReader.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
			           filePath=tf.getText();  //getting the file name
			           inputData=new Bi(); //Creating input object TODO choose preferred class for InputData
			           inputData.read(filePath);; //Reading file 
			           inputFrame.setVisible(false); //Closing frame
			           simOptionsFrame.setVisible(true); //Opening new frame
			           
			           
			}
			});
		inputFrame.add(fileReader);//adding button  
		inputFrame.add(tf);//adding file path slot

		inputFrame.setSize(400,500);//400 width and 500 height  
		inputFrame.setLayout(null);//using no layout managers  
		inputFrame.setVisible(true);//making the frame visible  
		
		/**
		 * Creating simulation options frame
		 */
		simOptionsFrame=new JFrame("Simulation type");
		simOptionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Button to choose only one simulation
			JButton oneSim=new JButton("One temperature");//creating instance of JButton  
			oneSim.setBounds(100,100,200, 40);//x axis, y axis, width, height  
	
			oneSim.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
					simOptionsFrame.setVisible(false);
		          	simulationFrame.setVisible(true);  
			}
			});
			
			//Button for multiple temperature simulations
			JButton multSim=new JButton("Various temperatures");//creating instance of JButton  
			multSim.setBounds(100,200,200, 40);//x axis, y axis, width, height  
	
			multSim.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
					multipleTemps=true;
					simOptionsFrame.setVisible(false);
					temperatureFrame.setVisible(true);  
			}
			});
			
			//Button for long simulation
			JButton longSim=new JButton("Long Simulation");//creating instance of JButton  
			longSim.setBounds(100,300,200, 40);//x axis, y axis, width, height  
	
			longSim.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
					longSimulation=true;
					multipleTemps=true;
					simOptionsFrame.setVisible(false);
					temperatureFrame.setVisible(true);  
			}
			});
			
		simOptionsFrame.add(oneSim);
		simOptionsFrame.add(multSim);
		simOptionsFrame.add(longSim);
		
		simOptionsFrame.setSize(400,500);//400 width and 500 height  
		simOptionsFrame.setLayout(null);//using no layout managers  
		simOptionsFrame.setVisible(false);//making the frame visible 
		
		
		/**
		 * Creating temperatures frame
		 */
		
		temperatureFrame=new JFrame("Simulation temperatures");//creating instance of JFrame  
		temperatureFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			final JTextField initialTemp=new JTextField();//creating text box for the initial temperature
			final JTextField finalTemp=new JTextField();//text box for final temperature
			final JTextField tempIncrement=new JTextField();//text box for the temperature increment
			initialTemp.setBounds(100,100,200,20);
			finalTemp.setBounds(100, 130, 200, 20);
			tempIncrement.setBounds(100,160,200,20);
	    
			initialTemp.setText("Initial Temperature");
			finalTemp.setText("Final Temperature");
			tempIncrement.setText("Temperature Increment");
			
			//Creating a button to get the input for the temperature
		     
			JButton readTemps=new JButton("Read temperatures");//creating instance of JButton  
			readTemps.setBounds(100,200,200, 40);//x axis, y axis, width, height  
		
			readTemps.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				if(!initialTemp.getText().equals("Initial Temperature")) {//checking for input, if there is no input use default values
			          inTemp=Double.parseDouble(initialTemp.getText());
			          finTemp=Double.parseDouble(finalTemp.getText());
			          temperatureIncrement=Double.parseDouble(tempIncrement.getText());
				}
			          temperatureFrame.setVisible(false);    
			          simulationFrame.setVisible(true);          
			}
			});
			temperatureFrame.add(readTemps);//adding button
			temperatureFrame.add(initialTemp);//adding text boxes
			temperatureFrame.add(finalTemp);
			temperatureFrame.add(tempIncrement);
			
			
			temperatureFrame.setSize(400,500);//400 width and 500 height  
			temperatureFrame.setLayout(null);//using no layout managers
			temperatureFrame.setVisible(false);//hiding frame
		
		/**
		 * Creating simulation frame
		 */
		
		simulationFrame = new JFrame("Simulation screen");// creating simulation option frame
		simulationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			//Creating button to show results
		 
		
			final JButton resultHandler = new JButton("Show results");// creating result button
			resultHandler.setBounds(100, 150, 150, 20);// result x axis, y axis, width, height

			resultHandler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//////////////////////////////
				//Result button actions///////	
				//////////////////////////////
				if(multipleTemps) {
					Graph xvsT=results.plotXvsT(inputData.latticeLength, inputData.H);//produce a graph object
					Grafica graph=new Grafica("Resultados", "T / K", "X");//create a Grafica object"
							
					for(int i=0; i<xvsT.points.size(); i++) {
						graph.inserta(xvsT.points.get(i).x, xvsT.points.get(i).y);
					}
					graph.pinta();
				}
				else {
					System.out.println("m: "+tempResult.m+"  "+"x: "+ tempResult.x);
				}	
			}
			});
			
			//Button to pause simulation
			final JButton pause=new JButton("Pause");//creating instance of JButton  
			//Button to continue simulation
			final JButton unpause=new JButton("Continue");//creating instance of JButton
			
			pause.setBounds(100,300,200, 40);//x axis, y axis, width, height  
	
			pause.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
					longSimManager.pauseSimulation();  
					unpause.setVisible(true);
					pause.setVisible(false);
			}
			});
			
			  
			unpause.setBounds(100,300,200, 40);//x axis, y axis, width, height  
	
			unpause.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
					longSimManager.continueSimulation();  
					pause.setVisible(true);
					unpause.setVisible(false);
			}
			});
		
			//Creating button to perform the simulation

			JButton simulationStart = new JButton("Start simulation");// creating simulation starter button
			simulationStart.setBounds(100, 100, 200, 40);// simulation x axis, y axis, width, height

			simulationStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//////////////////////////////
				//Simulation button actions///	
				//////////////////////////////
				sim=new He();//TODO choose preferred class for Simulation
				results=new Re();//TODO choose preferred class for Results
				if(multipleTemps) {
					//Long simulation
					if(longSimulation) {
						pause.setVisible(true);//Showing pause button
						new Thread(new Runnable() {
							public void run() {
								int expLength=(int)((finTemp-inTemp)/temperatureIncrement);//length of the experiment  (steps)
								InputData[] inDatas=new InputData[expLength];//Array of input data
								int simLength=inputData.mcs*inputData.latticeLength*inputData.latticeLength*expLength;
								longSimManager=new C();
								longSimManager.startSimulation(inDatas, simLength, results);//starting simulation	
							}
						}).start();
					}
				
							
					//Multiple temps but no long simulation
					else {
				for(double T=inTemp; T<=finTemp; T=T+temperatureIncrement) {
					//Creating an inputData with a different temperature for every simulation
					//TODO choose preferred class for InputData
					InputData inputDataVar=new Bi(inputData.latticeLength, T, inputData.H, inputData.nJ, inputData.J, inputData.mcs, inputData.therm, inputData.skip);
					tempResult=sim.run(inputDataVar,results);// run the simulation multiple times
				}
					}
				
				resultHandler.setVisible(true);	
				}
				else {
					tempResult=sim.run(inputData, results);
					resultHandler.setVisible(true);
				}
			}
			});
			
		simulationFrame.add(pause);
		simulationFrame.add(unpause);
		simulationFrame.add(simulationStart);
		simulationFrame.add(resultHandler);
		unpause.setVisible(false);//hiding until longsimulation is paused
		pause.setVisible(false);//hiding until longsimulation is started
		resultHandler.setVisible(false);//hiding results button
		
		
		simulationFrame.setSize(400,500);//400 width and 500 height  
		simulationFrame.setLayout(null);//using no layout managers
		simulationFrame.setVisible(false);//hiding frame
	}

}