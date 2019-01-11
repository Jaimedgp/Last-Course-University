
package r2ms.r2msMod;

import r2ms.common.*;
import java.util.*;
import java.math.*;
public class R2MS implements r2ms.common.IR2MS {
  enum RunningState {
    UNINTERRUPTIBLE,/**
     * This is the state used to indicate non concurrent simulations
     * that will run to completion and hence are not managed.
     */

    READY,/**
     * Indicates that the simulation has not been launched yet.
     */

    PAUSE,/**
     * This state indicates that the simulation should be stopped and stay stopped.
     */

    PAUSED,/**
     * This state indicates that the simulation has been stopped and stay stopped.
     */

    RUNNING,/**
     * This is the normal state when the simulation runs in a separate thread
     */

    DONE,/**
     * This is the state that indicates that the simulation has finished and is waiting 
     * for its results to be recalled. This is necessary when  the simulation runs in 
     * a separate thread
     */
;
  }

  /**
   * This is the current step of the simulation
   */
  private int step;

  /**
   * This is the total number of steps of the simulation
   */
  private int nSteps;

  /**
   * This is the handler for the concurrent thread in which the
   * simulation will run if requested.
   */
  private Thread simulationThread;

  /**
   * This static method returns a simulator object that implements the interface IR2MS 
   * 
   * @return an object that implements the interface IR2MS
   */
  public static r2ms.common.IR2MS createSimulator()
  {
    
    	return new R2MS();
  }

  /**
   * Default constructor
   */
  public R2MS() {
  }

  /**
   * Runs a simulation for the given input data up to completion and add 
   * the output result object to the collector of results object provided.
   * 
   * @param inData is the experiment object with the data needed to configure and 
   * run the simulation
   * @param results is a reference to the object that accumulates the results of all simulations
   * 
   * @return returns a Result object with the magnetization and susceptibility of the simulation
   */
  public r2ms.common.Result run(r2ms.common.InputData inData, r2ms.common.IResults results) {
  
	  return null;
  }

  /**
   * Starts a concurrent simulation for the experiment given as parameter.
   * When it returns, the simulation has been already initiated
   * and the steps are advancing.
   * 
   * @param experiment is the experiment object with the data needed to 
   * configure and run the simulation.
   */
  public boolean startSimulation(r2ms.common.InputData experiment) {
	  //TODO

	  return false;
  }

  /**
   * Indicates the fraction of the simulation at which the simulator is currently running.
   * 
   * @retuns a double value from 0 to 1 indicating the fraction of the simulation 
   * already performed. 0 means it has not started, 1 means it has already finished.
   */
  public double currentSimulationFraction() {
    	return ( (double) step ) / ( (double) nSteps );
  }

  /**
   * Stops the advance of the simulation if it is running. 
   * 
   * @return It returns true if it was succesfully paused, and 
   * false in case the simulation was already paused or not 
   * started to run concurrently.
   */
  public boolean pauseSimulation() {
    		if (runningState != RunningState.RUNNING) return false;
    
    		//signal the simulation thread to stop executing 
    		//the simulation and simply wait
    		runningState = RunningState.PAUSE;
    
    		//wait until the simulation is effectively paused
    		while(runningState != RunningState.PAUSED) {
    			try{ Thread.sleep(500);
    			}catch (InterruptedException e){
    				//if an interrupt signal is received there is not guarantee that the simulation could be stopped
    				return false;
    			}
    		}
    		return true;
    
    	  
  }

  /**
   * Continues a simulation previously paused.
   * 
   * @return It returns true if the simulation was succesfully continued, and 
   * false in case the simulation was already running or not 
   * started to run concurrently.
   */
  public boolean continueSimulation() {
    		if (runningState != RunningState.PAUSED) return false;
    
    		int currentStep = step;
    
    		//signal the simulation thread to continue executing 
    		//the simulation
    		runningState = RunningState.RUNNING;
    
    		//wait until the simulation is effectively running
    		while(step == currentStep) {
    			try{ Thread.sleep(500);
    			}catch (InterruptedException e){
    				//if an interrupt signal is received there is not guarantee that the simulation could be put to run again
    				return false;
    			}
    		}
    		return true;
  }

  /**
   * It saves the current status on disk so that in case of need the simulation 
   * can be restarted from the saved point.  
   * If the simulation is running it pauses the advance of the simulation after the 
   * current step is done before saving, and continues running again. 
   * 
   * @return a String with the name of the file holding the status of the simulation. 
   * A not initialized or already finished simulation returns a null String
   */
  public String saveSimulation() {
	return null;
  }

  /**
   * Continues a simulation previously stored to disk. If the simulation is currently running
   * it is stopped,  the experiment is dismissed,  and it gets started from the saved file status.
   * 
   * @param savedSimulationFile a string indicating the full path of a file holding the status of a 
   * previously saved simulation.
   */
  public void continueSavedSimulation(String savedSimFile) throws r2ms.common.ILongSimulationManager.FileNotFound, r2ms.common.ILongSimulationManager.FileCorrupted {
  }

  /**
   * Returns the output results if the simulation has finished or null 
   * if it has not.
   * @return 
   * 
   * @return returns the Results object  
   * 
   *  
   */
  public r2ms.common.IResults getSimulationResults() {
    if (runningState != RunningState.DONE) return null;
    
    return concurrentResults;
  }

  /**
   * This function is used to execute the simulation concurrently
   */
  public void run() {
    	  
    	  //run it concurrently
          concurrentResult = run (concurrentExperiment, concurrentResults);
      	
          //set the state to enable the caller to get results back
          runningState = RunningState.DONE;
  }

  /**
   * This operation runs a simulation of the given experiment up to completion and produces 
   * the output results. If it is running in a concurrent thread it can be
   * paused and continued.
   * 
   * @param experiment is the experiment object with the data needed to configure and 
   * run the simulation
   * @return returns the Results object.
   */
  private r2ms.common.Result simulation(r2ms.common.InputData experiment) {
	  //TODO
	  
	  return null;
  }

  /**
   * 
   * The experiment to run in the concurrent thread.
   * 
   */
  private r2ms.common.InputData concurrentExperiment;

  /**
   * 
   * The results object to store and or return after a concurrent simulation.
   * 
   */
  private r2ms.common.IResults concurrentResults;

  /**
   * 
   * The results object to store and or return after a concurrent simulation.
   * 
   */
  private r2ms.common.Result concurrentResult;

  /**
   * 
   * Internal attribute used to manage a running simulation
   * 
   */
  private R2MS.RunningState runningState =  RunningState.READY;

}
