
package r2ms.common;

/**
 * Container of all the information needed to perform the simulation. It reads the data 
 * from the text file passed as parameter.
 */
public class InputData {
  /**
   * A constructor that creates an InputData object with the default values.
   */
  public InputData() {
  }

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
  public  InputData(int latticeLength, double temperature, double H, int nJ, double[] J, int mcs, int therm, int skip) {
  }

  /**
   * This method reads the string provided and add the values that can be 
   * understood from it into the object used to invoke it.
   * 
   * @param line a string to be analyzed, read and the balues written in it stored
   * 
   * @returns the number of values that was possible to retrieve from the line provided
   */
  public int parse(String line) {
    //TODO
    
    return 0;
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
  public int read(String fileFullName) {
    //TODO
    
    return 0;
  }

  /**
   * It writes the values stored in the input data object into the file given as argument
   * 
   * @param fileFullName a string with the name of the file in which the data object will be printed
   */
  public void write(String fileFullName) {
  }

  /**
   * Temperature of the system expressed in reduced units.
   */
  public double temperature = 0.9;

  /**
   * An integer value with the length of the side of the lattice square.
   */
  public int latticeLength = 40;

  /**
   * Number of different interaction parameters that correspond to the levels (numer of distances) 
   * at which the magnetization of the spins of the particles there located will be considered as 
   * able to interact which the one of the position under analysis. 
   */
  public int nJ = 2;

  /**
   * An array with as many interaction constants as levels of neighbors to 
   * consider, as indicated by the nJ attribute.
   */
  public double[] J = { 1.0 , 0.0 };

  /**
   * Number of "Montecarlo" sweeps to do, i.e. the number of iterations
   */
  public int mcs = 100000;

  /**
   * Number of iteration that will be considered as needed for the system to 
   * be in thermal equilibrium. Also called number of thermalization sweeps.
   * It is the number of sweeps to perform before starting taking samples.
   */
  public int therm = 25000;

  /**
   * This number minus one corresponds to the number of Montecarlo 
   * sweeps to skip from sample to sample. The simulation will take 
   * one sample every this number of  sweeps.
   */
  public int skip = 1000;

  /**
   * This is the external magnetic field that will affect the lattice. It is 
   * unidimentional since it is considered to be in the direction of the spins analyzed.
   */
  public double H = 0.0;

}
