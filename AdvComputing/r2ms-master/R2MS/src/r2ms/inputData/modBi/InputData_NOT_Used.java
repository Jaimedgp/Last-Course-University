package r2ms.inputData.modBi;

import java.io.*;
import java.util.*;

/**
 * Container of all the information needed to perform the simulation. It reads the data 
 * from the text file passed as parameter.
 * 
 * @author Cuesta Sierra, Marina
 * @author Escobedo Corral, Mónica
 * @author García de la Santa Viñuela, María
 * 
 * @version 5-dic-2018
 * 
 * THIS IS A FIRST DRAFT: NONE OF THE PARAMETERS HAVE UNITS
 */

public class InputData_NOT_Used {
	
	/**Arguments of the class: they are inicialiced with some values given so we can check if 
	 * the class works fine.*/
	
	/*Temperature of the system expressed in reduced units.*/
	public double temperature=0.9; 
	
	/*An integer value with the length of the side of the lattice square.*/
	public int latticeLength=40;//Dimension of the problem
	
	/*Number of different interaction parameters that correspond to the levels (numer of distances) 
	 * at which the magnetization of the spins of the particles there located will be considered as 
	 * able to interact which the one of the position under analysis. */
	public int nJ=2;//number of jails
	
	/*An array with as many interaction constants as levels of neighbors to 
	  * consider, as indicated by the nJ attribute.*/
	public double [] J= {1.0, 0.0};
	
	 /* Number of "Montecarlo" sweeps to do, i.e. the number of iterations.*/
	public int mcs=100000; 
	
	/* Number of iteration that will be considered as needed for the system to 
     * be in thermal equilibrium. Also called number of thermalization sweeps.
     * It is the number of sweeps to perform before starting taking samples.*/
	public int therm=25000;

	
	/*This number minus one corresponds to the number of Montecarlo 
	 * sweeps to skip from sample to sample. The simulation will take 
	 * one sample every this number of  sweeps.*/
	public int skip=1000;
	
	/* This is the external magnetic field that will affect the lattice. It is 
	 * unidimentional since it is considered to be in the direction of the spins analyzed. */
	public double H=0.0; 
	
	
	//Strings used in the parse method to read the given line. Need to declare them as atributes
	//so the data can be stored on them and called on the whole class.
	private String nameVar;
	private String valueVar;
	private String unitsVar;
	
	 /**
	   * A constructor that creates an InputData object with the default values.
	   * 
	   * (It is empty as the initial or test values have been already declared at the same time
	   * as the atributes). 
	   */
	  public InputData_NOT_Used() {

	  }//End empty constructor
	  
	  /**
	   * This constructor creates an InputData object with the given parameters.
	   * 
	   * @param latticeSize is the number of spins that are in each of the sides of the 
	   * square that will hold the magnetic surface to simulate
	   * @param temperature the value of the temperature in reduced units
	   * @param externalMagneticField the external magnetic field that will affect the surface
	   * @param nJ the number of concentric levels of neighbors to consider
	   * @param J a vector with as many interaction constants as levels of neighbors to consider
	   * @param mcs number of montecarlo sweeps to do
	   * @param therm the number of sweeps to perform before starting taking samples
	   * @param skip number of sweeps to do in order to take one sample 
	   */
	  public  InputData_NOT_Used(int latticeLength, double temperature, double H, int nJ, double[] J, int mcs, int therm, int skip) {
		  //Assings each atribute with the given value
		  
		  this.temperature=temperature;
		  this.latticeLength=latticeLength;
		  this.mcs=mcs;
		  this.therm=therm;
		  this.skip=skip;
		  this.H=H;
		  this.nJ=nJ;
		  this.J=new double[nJ];
		  //Loop to inicialize the J array
			for (int i=0; i<nJ;i++) {
				this.J[i]=J[i];
			}//end for
	  }//End constructor
	  
	  /**
	   * This method reads the string provided and add the values that can be 
	   * understood from it into the object used to invoke it.
	   * 
	   * @param line a string to be analyzed, read and the balues written in it stored
	   * 
	   * @returns 0 if line was correctly read and -1 if any error happened (true=0, false=-1)
	   */
	  public int parse(String line) {
	    
		  //We are using a big try-catch block to read the line provided as a parameter
		  try (Scanner scr= new Scanner (line)) {    
			  //Save the first word on the line. It is going to be the name of the parameter
			  //It has been agreed with the other groups. If it's not, it returns -1
			  nameVar=scr.next();

			 //We use the string comand ".compareToIgnoreCase("string")" to check if the parameter
			 //name agrees with the following. It compares without noticing capital letters.
			 //Return 0 if the two words are equal.
			  
			 //NOTE: as this is a first draft, the units should be told to be "none" by the GUI or so
			  
			//Look if the line has the values and (units) of temperature
			 if(nameVar.compareToIgnoreCase("temperature")==0){
				 //If the conditon checks, it reads the next two strings and collects them in the 
				 //variables.
				  valueVar= scr.next(); //Temperature value
				  unitsVar=scr.next(); //Temperature units
				  temperature=Double.parseDouble(valueVar);//Parses the value into a double
			      
				  scr.close();//Closes the scanner
				 return 0;//It has worked ok
				  
			//Look if the line has the values and (units) of temperature				 
			 }else if(nameVar.compareToIgnoreCase("H")==0){
				 //If the conditon checks, it reads the next two strings and collects them in the 
				 //variables.
				  valueVar= scr.next();//externalMagneticField value
				  unitsVar=scr.next();//externalMagneticField units
				  H=Double.parseDouble(valueVar);//Parses the value into a double
				
			      scr.close(); //Closes the scanner
				 return 0;//It has worked ok
				 
			//Look if the line has values for the monteCarloSteps	 
			 }else if(nameVar.compareToIgnoreCase("mcs")==0) {
				//If the conditon checks, it reads the next two strings and collects them in the 
				 //variables.
				  valueVar= scr.next();//mcs value
				  unitsVar=scr.next();//null
				mcs=Integer.parseInt(valueVar); //Parses the value into an Integer
			      
				scr.close();//Closes the scanner
				return 0;//It has worked ok

			//Look if the line has values for the thermalization		 
			 }else if(nameVar.compareToIgnoreCase("therm")==0) {
				//If the conditon checks, it reads the next two strings and collects them in the 
				 //variables.
				  valueVar= scr.next();//therm value
				  unitsVar=scr.next(); //null
				 therm=Integer.parseInt(valueVar);//Parses the value into an integer
			     
				 scr.close();//Closes the scanner
				 return 0;//It has worked ok

			//Look if the line has values to skip	 
			 }else if(nameVar.compareToIgnoreCase("skip")==0) {
				//If the conditon checks, it reads the next two strings and collects them in the 
				 //variables.
				  valueVar= scr.next(); //skip value
				  unitsVar=scr.next();//null
				  skip=Integer.parseInt(valueVar);//parses the value into an integer
				  
			      scr.close(); //Closes the scanner
				 return 0;//It has worked ok
				 
			 //Look if the line is called J. The first number on the line is nJ, the rest are the 
			 // J array values
			 }else if(nameVar.compareToIgnoreCase("J")==0) {
				//If the conditon checks, it reads the next two strings and collects them in the 
				 //variables.
				  valueVar= scr.next();//nJ value
				  nJ=Integer.parseInt(valueVar);//parses the value into an integer
				  
				 //Loop to fill the J array with the next values from the line
				 for(int i=0; i<nJ; i++) {
					 J[i]=Double.parseDouble(scr.next());
				 }//end for
				 
				 //unitsVar=scr.next(); //For now, it is null. But its going to have them
				 
			      scr.close();//Closes the scanner
				 return 0; //It has worked ok
				 
			 } else { //If the parameter name does not check any of the conditions
				 
			      scr.close();//Closes the scanner
			      //Prints on the console that the variable is bad written nor not needed
			      System.out.println("This variable name is wrong or not needed: " +nameVar);
				return -1; //It has some kind of error

			 } //End elseif block
			 
			 
		} catch (RuntimeException e) {
		//If an error happens (file is empty, the file does not exist...) it throws an exception
		 System.out.println("There is a mistake on: " + line);
		 return -1; //It has some kind of error
		}//End try catch block
	
	  }//End parse method
	  
	  /**
	   * Fill the attributes of the InputData object with those read from the file given as 
	   * argument.  It returns the number of values that could be read from the file, zero 
	   * if no data could be taken from the file, or a negative number if the file does not 
	   * exist or could not be open for reading.
	   * 
	   * @param fileFullName a string with th complete path an name of a file to be read.
	   * @return it returns the number of values that could be read from the file, zero if no 
	   * data could be taken from the file or a negative number if there is no file or t is not 
	   * possible to open it.
	   */
	  public int read(String fileFullName) { //THE COMPLETE PATH AND FORMAT OF THE FILE NEEDS TO BE PROVIDED.
	   
		  int i=0;//Number of values that could be read from the file. TO RETURN.
		  //Initialy, no value has been read
		
		  //The aim of the try catch block is to read the file: if it is empty it throws an exception
		  try  { 
				File f=new File(fileFullName);//Creates a file object of the given as a parameter
				Scanner scr= new Scanner (f); //Opens an scanner object

				//Loop to read the file line by line
					while (scr.hasNextLine()) {//If it has more lines to read it continues doing this
					    String s=scr.nextLine();//Collect the line on a local variable
						System.out.println(s); //Prints the line on the console to check if it is correct (1sr VERSION)
						
						//In order to analize each line, we are calling the parse method, to save the
						//values on the given atributes 
						if(parse(s)==0) {
						//If the parse method has worked, it adds one line read to the i variable to return
						//later on the whole amount of lines read
							i++;
						}//End if
						
					}//End while
					
				   scr.close();//Closes the scanner
				   return i;//Return the amount of read lines
				   
					} catch (FileNotFoundException e) {
					//If an error happens (file is empty, the file does not exist...) it throws an exception
					System.out.println("Could not open " + fileFullName); 
					System.out.println("Exception " + e);//prints the exception
					
				}//end try catch block
			
			   return -1; //something is wrong
			

	  }//End read method
	  
	  /**
	   * It writes the values stored in the input data object into the file given as argument
	   * 
	   * @param fileFullName a string with the name of the file in which the data object will be printed
	   */
	  public void write(String fileFullName) {
	  
			File outFile = new File(fileFullName);//Creates a file object with the parameter name one
	       
			try { //We are using a try catch block to write on this file
				
				//To write and printit into the file
	          	 FileWriter  writeroutFile = new FileWriter(outFile, true);
	          	 PrintWriter printeroutFile = new PrintWriter(writeroutFile);
	          		 
	          	 //Each line is going to correspond as a variable with its name, value and units, for the following versions
	          	 //.println("string") skips onto the next line. .print("string") continues on the same
	          	 
	          	 	printeroutFile.println("Temperature "+temperature);//Temperature
	          		printeroutFile.println("H "+H);//externalMagneticField
	          		printeroutFile.println("mcs "+mcs);//montecarloSteps
	          		printeroutFile.println("therm "+therm);//Thermalization
	          		printeroutFile.println("skip "+skip);//skip (steps)
	          		printeroutFile.println("nJ "+nJ);//nJ
	          		printeroutFile.print("J ");//J array
	          		for (int i=0; i<J.length; i++) { //Loop to print all the components of the array
	              		printeroutFile.print(J[i]+" ");
	          		}//End for
	          		 
	          	    printeroutFile.close();//closes the printer
	          		 
	  	  } catch (IOException e) {
	  		  //if some error happens with the file, it throws an exception
	 		    e.printStackTrace();
	      }//End try catch block
	  
	  }//End write method
	  
	  
}//End InputData Bi class
