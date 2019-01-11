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
 * @version 17-dic-2018
 * 
 * On this version, units had been implemented on the code. 
 * The default object has Kelvin units on the magnitudes that required them.
 * 
 */

public class Bi extends r2ms.common.InputData {  //XXX: G80: made public to have it able to be used

	//Strings used in the parse method to read the given line. Need to declare them as atributes
	//so the data can be stored on them and called on the whole class.
	private String nameVar;
	private String valueVar;
	private String unitsVar;


	/**
	 * A constructor that creates an InputData object with the default values and DEFAULT UNITS
	 * (KELVIN FOR EVERY MAGNITUDE THAT HAS UNITS)
	 * 
	 * (It is empty as the initial or test values have been already declared at the same time
	 * as the atributes). 
	 */
	public Bi() {

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
	public  Bi(int latticeLength, double temperature, double H, int nJ, double[] J, int mcs, int therm, int skip) {
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
	 * @param line a string to be analyzed, read and the values written in it stored
	 * 
	 * @returns 0 if line was correctly read and -1 if any error happened (true=0, false=-1)
	 */
	public int biModifiedParse(String line) {

		//We are using a big try-catch block to read the line provided as a parameter
		try (Scanner scr= new Scanner (line)) {    
			//Save the first word on the line. It is going to be the name of the parameter
			//It has been agreed with the other groups. If it's not, it returns -1
			nameVar=scr.next();

			//We use the string comand ".compareToIgnoreCase("string")" to check if the parameter
			//name agrees with the following. It compares without noticing capital letters.
			//Return 0 if the two words are equal.

			//NOTE: On the second draft, the units are added: they are transformed into Kelvins
			//The mangitudes that have units are Temperature, J and externalMagneticField (H)

			//Look if the line has the values and units of temperature
			if(nameVar.compareToIgnoreCase("temperature")==0){
				//If the conditon checks, it reads the next two strings and collects them in the 
				//variables.
				valueVar= scr.next(); //Temperature value
				unitsVar=scr.next(); //Temperature units. Next sring read

				/*
				 * Inside this if loop, we check if the given units are Celsius, Fahrenheit
				 * and Kelvin. If so, we apply the conversion factor to each case.
				 */

				//Case Celsius: T(K)=T(celsius)+273.15
				if(unitsVar.compareToIgnoreCase("ºC")==0) {
					temperature=Double.parseDouble(valueVar)+273.15;
					//As the change is done, units are kelvin. It is written as K

					//Case Kelvin: they stay as they are given	  
				} else if (unitsVar.compareToIgnoreCase("K")==0) {
					temperature=Double.parseDouble(valueVar);//Parses the value on the line into a double
					//No change of units is needed to be done in this case.

					//Case Fahrenheit: T(K)=273.15+(T(F)-32)*5/9
				} else if (unitsVar.compareToIgnoreCase("ºF")==0) {
					temperature=273.15+(Double.parseDouble(valueVar)-32)*5/9;//Parses the value into a double
					//As the change is done, units are kelvin. It is written as K

				}else { 
					System.err.println("WARNING: Wrong format of the temperature. Units can be given in Celsius (ºC), Fahrenheit (ºF) or Kelvin (K).");
					return -1;
				}//end if units

				scr.close();//Closes the scanner
				return 0;//It has worked ok

				//Look if the line has the values and units of externalMagneticField				 
			}else if(nameVar.compareToIgnoreCase("H")==0){
				//If the conditon checks, it reads the next two strings and collects them in the 
				//variables.
				valueVar= scr.next();//externalMagneticField value
				unitsVar=scr.next();//externalMagneticField units. Next string on the line

				/*
				 * Inside this if loop we check and transform the units of the external magnetic field
				 * to Kelvins
				 */
				//Case K: value and units stays as given
				if (unitsVar.compareToIgnoreCase("K")==0) {
					H=Double.parseDouble(valueVar); //externalMagneticField value on Kelvins
					//No change of units is needed. Units are already given in Kelvin

					//Case A/m
				} else if(unitsVar.compareToIgnoreCase("A/m")==0) {
					H=Double.parseDouble(valueVar)*9.27*4*Math.PI*Math.pow(10, -8)/1.38;//Parses the value into a double and transforms them to correct units
					//As the change is done, units are kelvin. It is written as K

				}else {
					System.err.println("WARNING: Wrong format of H units. They should be given in (A/m) or Kelvins (K)");
					return -1;
				}
				scr.close(); //Closes the scanner
				return 0;//It has worked ok

				//Look if the line has values for the monteCarloSteps	 
			}else if(nameVar.compareToIgnoreCase("mcs")==0) {
				//If the conditon checks, it reads the next two strings and collects them in the 
				//variables.
				valueVar= scr.next();//mcs value

				mcs=Integer.parseInt(valueVar); //Parses the value into an Integer

				scr.close();//Closes the scanner
				return 0;//It has worked ok

				//Look if the line has values for the thermalization		 
			}else if(nameVar.compareToIgnoreCase("therm")==0) {
				//If the conditon checks, it reads the next two strings and collects them in the 
				//variables.
				valueVar= scr.next();//therm value

				therm=Integer.parseInt(valueVar);//Parses the value into an integer

				scr.close();//Closes the scanner
				return 0;//It has worked ok

				//Look if the line has the value to the latticeLenght	 
			}else if(nameVar.compareToIgnoreCase("latticeLength")==0) {
				//If the conditon checks, it reads the next two strings and collects them in the 
				//variables.
				valueVar= scr.next();//therm value

				latticeLength=Integer.parseInt(valueVar);//Parses the value into an integer

				scr.close();//Closes the scanner
				return 0;//It has worked ok

				//Look if the line has values to skip	 
			}else if(nameVar.compareToIgnoreCase("skip")==0) {
				//If the conditon checks, it reads the next two strings and collects them in the 
				//variables.
				valueVar= scr.next(); //skip value

				skip=Integer.parseInt(valueVar);//parses the value into an integer

				scr.close(); //Closes the scanner
				return 0;//It has worked ok

				//Look if the line is called J. The first number on the line is nJ, the rest are the 
				// J array values
			}else if(nameVar.compareToIgnoreCase("J")==0) {
				//If the conditon checks, it reads the next string and collect it in the 
				//variable.
				valueVar= scr.next();//nJ value
				nJ=Integer.parseInt(valueVar);//parses the value into an integer


				//Create a new array for reading the given values
				double [] newJ =new double [nJ];

				//Fill the first values and check if it is ok
				newJ[0]=Double.parseDouble(scr.next());
				if(newJ[0]<=0 ) {						  
					System.err.println("J values have no physical sense: the first element can't be 0.0 (interaction does not happen) or smaller than 0.0");
					return -1; //something wrong happens
				}//end if

				//Fill the next values
				for(int j=1; j<nJ; j++) {
					newJ[j]=Double.parseDouble(scr.next());	
				}//end for


				//Loop to check if the J array has the right physical meaning with all the element greater than zero.
				for(int j=1; j<nJ; j++) {

					if( newJ[j]<0) {

						System.err.println("J values have no physical sense: they have to be positive");

						return -1; //something wrong happens

					}//end if

				}//end for


				//Loop to check if the J array has the right physical meaning with the value of the array in a decreasing order
				for(int j=1; j<nJ; j++) {
					if(newJ[j]>newJ[j-1]) {

						System.err.println("J values have no physical sense: values on the array should be "
								+ " ordered from the bigger one to the smaller one.");

						return -1; //something wrong happens

					}//end if
				}//end for

				unitsVar=scr.next(); //Last string on the line

				//Case K: everything stays as given
				if(unitsVar.compareToIgnoreCase("K")==0) {
					//No change is needed: units are kelvin. It is written as K


					//Case J: final units are Kelvin.  A conversion factor is applied on a for loop
					//to every one of the array components
				} else if( unitsVar.compareToIgnoreCase("J")==0) {
					//As the change is done, units are kelvin. It is written as K
					//Loop that applies the conversion factor to obtain kelvins
					for(int j=0; j<nJ; j++) {
						newJ[j]=newJ[j]*(0.25*Math.pow(10, 23)/1.38);
					}//end for


					//Case eV: final units are Kelvin. A conversion factor is applied on a for loop
					//to every one of the array components
				}else if(unitsVar.compareToIgnoreCase("eV")==0) {
					//As the change is done, units are kelvin. It is written as K

					//Loop that applies the conversion factor to obtain kelvins 
					for(int j=0; j<nJ; j++) {
						newJ[j]=newJ[j]*1.602*Math.pow(10, -19)*(0.25*Math.pow(10, 23)/1.38);
					}//end for

					//Case Hartree: final units are Kelvin. A conversion factor is applied on a for loop
					//to every one of the array components	 
				}else if(unitsVar.compareToIgnoreCase("Hartree")==0){
					//As the change is done, units are kelvin. It is written as K

					//Loop that applies the conversion factor to obtain kelvins
					for(int j=0; j<nJ; j++) {
						newJ[j]=newJ[j]*4.36*Math.pow(10, -18)*(0.25*Math.pow(10, 23)/1.38);
					}//end for



				}else {						 
					System.err.println("WARNING: Wrong format of J units. They should be given in Jules (J), electronvolts (eV) or Hartree (Hartree).");
					return -1;
				}


				J=newJ; //Update the value of J with the given values
				scr.close();//Closes the scanner
				return 0; //It has worked ok

			} else { //If the parameter name does not check any of the conditions

				scr.close();//Closes the scanner
				//Prints on the console that the variable is bad written or not needed
				System.out.println("This variable name is wrong or not needed: " +nameVar);
				return -1; //It has some kind of error

			} //End elseif block


		} catch (RuntimeException e) {
			//If an error happens (file is empty, the file does not exist...) it throws an exception
			System.err.println("There is a mistake on: " + line);
			return -1; //It has some kind of error
		}//End try catch block

	}//End parse method

	//XXX: G80: The method you use for parsing is not compatible with the generic one
	//     G80: for that reason we need to add this:
	/**
	 * This method reads the string provided and add the values that can be 
	 * understood from it into the object used to invoke it.
	 * 
	 * @param line a string to be analyzed, read and the balues written in it stored
	 * 
	 * @returns the number of values that was possible to retrieve from the line provided
	 */
	public int parse(String line) {
		//XXX: G80: this is not fully correct but works partially:
		//returns 1 if correct (your method returned 0), 0 if wrong (your method returned -1):
		return ((0==biModifiedParse(line))?1:0);
	}


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

				//Loop to check how the line is written: if its empty or commented (#), the line is skipped
				if(  s.contains("#")==false) { 
					if( s.isEmpty()==false){
						//If the condition checks, it analizes/parses the line

						//In order to analize each line, we are calling the parse method, to save the
						//values on the given atributes 
						if(parse(s)==0) {
							//If the parse method has worked, it adds one line read to the i variable to return
							//later on the whole amount of lines read
							i++;
						} //End if

					}//end if
				}//end if

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
			FileWriter  writeroutFile = new FileWriter(outFile, false);
			PrintWriter printeroutFile = new PrintWriter(writeroutFile);

			//Each line is going to correspond as a variable with its name, value and units, for the following versions
			//.println("string") skips onto the next line. .print("string") continues on the same

			printeroutFile.println("Temperature "+temperature+" "+ "K");//Temperature
			printeroutFile.println("H "+ H +" "+ "K");//externalMagneticField
			printeroutFile.println("mcs "+mcs );//montecarloSteps
			printeroutFile.println("therm "+therm );//Thermalization
			printeroutFile.println("skip "+skip );//skip (steps)
			printeroutFile.println("latticeLength "+ latticeLength); //latticeLength
			printeroutFile.print("J "+ nJ +" ");//J array + nJ value
			for (int i=0; i<J.length; i++) { //Loop to print all the components of the array
				printeroutFile.print(J[i]+" ");
			}//End for
			printeroutFile.println(" "+"K");

			printeroutFile.close();//closes the printer

		} catch (IOException e) {
			//if some error happens with the file, it throws an exception
			e.printStackTrace();
		}//End try catch block

	}//End write method



}//End InputData Bi class
