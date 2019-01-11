
package r2ms.common;

public interface ILongSimulationManager extends Runnable {
  public class FileCorrupted extends Exception {
  }

  public class FileNotFound extends Exception {
  }

  /**
   * Starts a concurrent simulation for the experiment given as parameter.
   * When it returns, the simulation has been already initiated
   * and the steps are concurrently advancing.
   * 
   * @param experiment is the experiment object with the data needed to 
   * configure and run the simulation.
   * @param nSteps is an integer value that informs the controller of 
   * the simulation of the total expected number of steps.
   * 
   * @return It returns true if the simulation starts correctly false otherwise. 
   */
  boolean startSimulation(InputData[] experiment, int nSteps, IResults results) ;

  /**
   * Indicates the fraction of the simulation at which the simulator is currently running.
   * 
   * @retuns a double value from 0 to 1 indicating the fraction of the simulation 
   * already performed. 0 means it has not started, 1 means it has already finished.
   */
  double currentSimulationFraction() ;

  /**
   * Stops the advance of the simulation if it is running. 
   * 
   * @return It returns true if it was succesfully paused, and 
   * false in case the simulation was already paused or not 
   * started to run concurrently.
   */
  boolean pauseSimulation() ;

  /**
   * Continues a simulation previously paused.
   * 
   * @return It returns true if the simulation was succesfully continued, and 
   * false in case the simulation was already running or not 
   * started to run concurrently.
   */
  boolean continueSimulation() ;

  /**
   * It saves the current status on disk so that in case of need the simulation 
   * can be restarted from the saved point.  
   * If the simulation is running it pauses the advance of the simulation after the 
   * current step is done before saving, and continues running again. 
   * 
   * @return a String with the name of the file holding the status of the simulation. 
   * A not initialized or already finished simulation returns a null String
   */
  String saveSimulation() ;

  /**
   * Continues a simulation previously stored to disk. If the simulation is currently running
   * it is stopped,  the experiment is dismissed,  and it gets started from the saved file status.
   * 
   * @param savedSimulationFile a string indicating the full path of a file holding the status of a 
   * previously saved simulation.
   */
  void continueSavedSimulation(String savedSimFile) throws ILongSimulationManager.FileNotFound, ILongSimulationManager.FileCorrupted ;

  /**
   * Returns the output results if the simulation has finished or a null pointer if it has not
   * 
   * @return returns the ResultsFile object with the names of all the requested 
   * resulting images and data files generated. If any of those files could not 
   * be generated the corresponding string would be a null reference.
   *  
   */
  IResults getSimulationResults() ;

  /**
   * This method searchs the persisten repositories looking for the last simulation saved,
   * if any.
   * 
   * @returns it returns as a string the full path name of the file holding the most recent
   * simulation saved. In the case that no simulation has been saved it returns null.
   */
  String getSimulationIfSaved() ;

  /**
   * It serves the simulation to indicate progress and get paused if commended by the controller
   */
  void stepDone() ;

}
