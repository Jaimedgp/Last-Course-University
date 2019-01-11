package r2ms.inputData.modLv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.NumberFormatException;

/**
* Container of all the information needed to perform the simulation. It reads the data 
* from the text file passed as parameter.
* 
* @author Jaime Díez González-Pardo
* @author Pablo Lavín Pellón
* @author Inés Sánchez de Movellán Sáiz
* 
*/

public class Lv extends r2ms.common.InputData {   //XXX: G80: public modifier added

    /**
    * Array of strings in which each element is the name of one variable
    */
    private String[] names = {"temperature", "latticeLength", "J", "mcs", "therm", "skip", "H", "nJ"};

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
    public  Lv (int latticeLength, double temperature, double H, int nJ, double[] J, int mcs, int therm, int skip) {
//XXX: G80: The constructor must have the same name that the class has...
//    	public  Input (int latticeLength, double temperature, double H, int nJ, double[] J, int mcs, int therm, int skip) {
    	super(); //XXX: G80: <--This is added just to have default values before the given ones
        this.latticeLength = latticeLength;
        this.temperature = temperature;
        this.H = H;
        this.nJ = nJ;
        this.J = new double[nJ];
        this.J = J;
        this.mcs = mcs;
        this.therm = therm;
        this.skip = skip;
    }
    
    //XXX: G80: Since you used it, after adding the correct constructor we need to add the default one:
    public Lv (){
    	super();
    };
    	
    /**
    * This method reads the string provided and add the values that can be 
    * understood from it into the object used to invoke it.
    * 
    * @param line a string to be analyzed, read and the values written in it stored
    * 
    * @returns boolean true if the value can be understood, false in other case
    */
    public boolean lvModifiedParse (String line){  
        // It creates an array of two string, one for the name of the variable (temperature, 
        // latticeLength etc.) and the other one for the value of the this variable)
        //
        // For this version we suppose that every variable have the same units

        String lineArray[] = new String[2];
        // It separate the strings of the line by tabulator
        lineArray = line.split("\t");

        String nameVar = lineArray[0];
        String valueVar = lineArray[1];
        String unitsVar;

        int variable;
        for (variable = 0; variable < names.length; variable++){
            if (nameVar.equalsIgnoreCase(names[variable])) break; 
        } 

        // With switch-case loop, it assigns the value of the variable to the appropriate attribute

        // For this version we suppose that there are not errors in the input file
        switch(variable) {

            case 0:
                unitsVar = lineArray[2];
                this.temperature = Double.parseDouble(valueVar);
                if (!parseTempUnits(unitsVar)) {
                    return false;
                }

                break;

            case 1:
                this.latticeLength = Integer.parseInt(valueVar);
                if(latticeLength < 0) return false;
                break;

            case 2:
                try {

                    unitsVar = lineArray[2];

                    String[] valuesJ = new String[J.length];
                    valuesJ = valueVar.split(" ");

                    // The first value of array of interaction parameters is an integer which is the 
                    // number of J, nJ
                    //
                    // For this version we suppose that the interactions are only for first neighbours
                    this.nJ = (int) Double.parseDouble(valuesJ[0]);

                    // create the new J array with the new dimension
                    this.J = new double[nJ];

                    // read all J values
                    J[0] = Double.parseDouble(valuesJ[1]);
                    if(J[0] < 0) return false;
                    for(int k = 1; k < J.length; k++) {
                        this.J[k] = Double.parseDouble(valuesJ[k+1]);
                        if(J[k] < 0) return false;
                        if(J[k-1] < J[k]) return false;
                    }

                } catch (NumberFormatException nfe) {
                    return false;
                }
                
                if (!parseEnergyUnits(unitsVar)) {
                    return false;
                }

                break;

            case 3:
                this.mcs = Integer.parseInt(valueVar);
                if(mcs < 0) return false;
                break;

            case 4:
                this.therm = Integer.parseInt(valueVar);
                if(therm < 0) return false;
                break;

            case 5:
                this.skip = Integer.parseInt(valueVar);
                if(skip < 0) return false;
                break;

            case 6:
                unitsVar = lineArray[2];
                this.H = Double.parseDouble(valueVar);
                if (!parseFieldUnits(unitsVar)) {
                    return false;
                }
            break;

            case 7:
                this.nJ = Integer.parseInt(valueVar);
                if(nJ < 0) return false;
                this.J = new double[nJ];

            default: return false; 
        }

        return true;
    }

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
      //returns 1 if true, 0 if false:
      return ((lvModifiedParse(line))?1:0);
    }

    
    
    /**
    * Parse temperature units
    */

    private boolean parseTempUnits (String unitsVar) {

        String[] unitsTemp = {"K", "C", "F"};

        int tempUnits;
        for (tempUnits = 0; tempUnits < unitsTemp.length; tempUnits++){
            if (unitsVar.equalsIgnoreCase(unitsTemp[tempUnits])) break; 
        }

        switch (tempUnits){

            case 0:
                break;

            case 1:
                temperature += 273.15;
                break;

            case 2:
                temperature = (temperature-32)*(5/9.0) + 273.15;
                break;

            default: 
                return false;
        }

        if (temperature < 0) return false;

        return true;
    }

    /**
    * Parse energy units
    */
    private boolean parseEnergyUnits (String unitsVar) {

        String[] unitsEnergy = {"J", "erg", "eV", "Hartree"};

        int energyUnits;
        for (energyUnits = 0; energyUnits < unitsEnergy.length; energyUnits++){
            if (unitsVar.equalsIgnoreCase(unitsEnergy[energyUnits])) break; 
        }

        switch (energyUnits){

            case 0:
                // Julios
                break;
                
            case 1:
                for(int k = 0; k < J.length; k++) {
                    // Ergios
                    J[k] = J[k] * Math.pow(10,-7);
                }
                break;

            case 2:
                for(int k = 0; k < J.length; k++) {
                    // electronVoltios
                    J[k] = J[k] * 1.60218 * Math.pow(10,-19);
                }
                break;

            case 3: 
                for(int k = 0; k < J.length; k++) {
                    // Hartree
                    J[k] = J[k] * 4.35974 * Math.pow(10,-18);
                }
                break;

            default: 
                return false;
        }

        return true;
    }


    /**
    * Parse temperature units
    */
    private boolean parseFieldUnits (String unitsVar) {

        String[] unitsField = {"A/m", "Oe"};

        int fieldUnits;
        for (fieldUnits = 0; fieldUnits < unitsField.length; fieldUnits++){
            if (unitsVar.equalsIgnoreCase(unitsField[fieldUnits])) break; 
        }

        switch (fieldUnits){

            case 0:
                break;

            case 1:
                H = H * 79.5774715459424;
                break;

            default: 
                return false;
        }

        return true;
    }

    /**
    * Fill the attributes of the InputData object with those read from the file given as 
    * argument.  It returns zero if it could read every data from the file, the number of
    * the line that it could not read if there is any, or a negative number if the file does not 
    * exist or could not be open for reading.
    * 
    * @param fileFullName a string with the complete path an name of a file to be read.
    * @return it returns zero if it could read every data from the file, the number of
    * the line that it could not read if there is any, or a negative number if the file does not 
    * exist or could not be open for reading.
    */
    public int read(String fileFullName) {

        try { 
            // It uses the class BufferedReader to read the input data file
            FileReader file = new FileReader(fileFullName);
            BufferedReader fr = new BufferedReader(file);

            // It defines the line, a boolean which indicates the result of applied the parse 
            // method and the number of the line what is reading
            String line;
            boolean isCorrect;
            int numLine = 0;

            while ((line = fr.readLine()) != null) {
                numLine++;
                isCorrect = lvModifiedParse(line);
                // It returns the number of the line which has a mistake (in this case 
                // isCorrect = false)
                if (!isCorrect) return numLine;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        return 0;
    }

    /**
    * It writes the values stored in the input data object into the file given as argument
    * 
    * @param fileFullName a string with the name of the file in which the data object will be printed
    */
    public void write(String fileFullName) {

        try {
            // It uses the class BufferedWriter to write the values of the input data objects
            // in a file 
            BufferedWriter fw = new BufferedWriter(new FileWriter(fileFullName, true));

            fw.write("Temperature: " + temperature + " K"+ "\n");
            fw.write("Lattice Length: " + latticeLength + "\n");
            fw.write("Number of interaction parameters: " + nJ + "\n");

            for(int k = 0; k < J.length; k++){
                fw.write("J["+(k+1)+"]: "+J[k] + " J"+ "\n");
            }

            fw.write("MCS: " + mcs + "\n");
            fw.write("Therm: " + therm + "\n");
            fw.write("Skip: " + skip + "\n");
            fw.write("H: " + H +  " A/m" +"\n");

            fw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("The file has not been found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("The input/output operation has failed");
        }
    }
}