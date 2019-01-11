
package r2ms.gui.modEu;

import r2ms.common.ILongSimulationManager;
import r2ms.common.IResults;
import r2ms.common.InputData;
import r2ms.simIsing.modHe.HeNew;

public class C implements ILongSimulationManager {
	/**
	 * Enumeration that contains every possible state of the simulation.
	 */
	enum RunningState {
		UNINTERRUPTIBLE,
		/**
		 * This is the state used to indicate non concurrent simulations that will run
		 * to completion and hence are not managed.
		 */

		READY,
		/**
		 * Indicates that the simulation is ready to be launched
		 */

		PAUSE,
		/**
		 * This state indicates that the simulation should be stopped and stay stopped.
		 */

		PAUSED,
		/**
		 * This state indicates that the simulation has been stopped and stay stopped.
		 */

		RUNNING,
		/**
		 * This is the normal state when the simulation runs in a separate thread
		 */

		DONE,/**
				 * This is the state that indicates that the simulation has finished and is
				 * waiting for its results to be recalled. This is necessary when the simulation
				 * runs in a separate thread
				 */
		;
	}

	private RunningState runningState = RunningState.READY; // Actual state of the system. It is initialized to ready,
								// waiting to be launch.
	private int step; // Number of step in which the simulation is in
	private int nstep;// Total number of steps of the simulation
	private Thread simulationThread; // Thread in which the simulation is going to be ran in
	private InputData[] experiment; // Input data needed to run the simulation
	private IResults results; // Results of the simulations
	private HeNew sim; // Simulation to be run

	@Override
	public void run() {
		runningState = RunningState.RUNNING; // The state is changed to running

		// Run simulation for all the values provided
		for (int i = 0; i < experiment.length; i++) {
			sim = new HeNew();  //G80 think about locating this outside the loop...
			sim.run(experiment[i], results);
			stepDone();
		}
		// Set the state to DONE after finishing
		runningState = RunningState.DONE;
	}

	@Override
	public boolean startSimulation(InputData[] experiment, int nSteps, IResults results) {
		// Variable initialization
		this.step = 0; // Put the step to 0 as it is in the initial state
		this.nstep = nSteps; // Set the total number of steps
		this.experiment = experiment; // Set the input data
		simulationThread = new Thread(this); // Set the thread
		this.results = results; // Set the results
		// If the thread is not obtained or the state of the system is different to
		// READY, do not launch and return false
		if (simulationThread == null || runningState != RunningState.READY) {
			return false;
		}
		// Start the simulation
		simulationThread.start();
		return true;
	}

	@Override
	public double currentSimulationFraction() {
		
		if(runningState == RunningState.DONE) {
			return 1.0;
		}
				
		// Fraction of the simulation completed
		return ((double) step) / ((double) nstep);
	}

	@Override
	public boolean pauseSimulation() {
		// If the state of the simulation is not RUNNING, the simulation can not be
		// paused and return false
		if (runningState != RunningState.RUNNING) {
			return false;
		}
		// Put the state to PAUSE
		runningState = RunningState.PAUSE;
		// Keep waiting until the simulation is paused. Check every 200 ms
		while (runningState != RunningState.PAUSED) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {

			}
		}
		return true;
	}

	@Override
	public boolean continueSimulation() {
		// If the state of the simulation is not PAUSED, the simulation can not be
		// continued and return false
		if (runningState != RunningState.PAUSED) {
			return false;
		}
		// Put the state to READY
		runningState = RunningState.READY;
		// Keep waiting until the simulation is running. Check every 200 ms
		while (runningState != RunningState.RUNNING) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {

			}
		}
		return true;
	}

	@Override
	public String saveSimulation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void continueSavedSimulation(String savedSimFile) throws FileNotFound, FileCorrupted {
		// TODO Auto-generated method stub
	}

	@Override
	public IResults getSimulationResults() {
		//G80: this implies the thread has fulfilled its commitments
		runningState = RunningState.READY;
		// Results obtained
		return results;
	}

	@Override
	public String getSimulationIfSaved() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * This method is called in the simulation every time progress is wanted to be
	 * indicated. It can be called either at the end of every simulation or at the
	 * end of every MonteCarlo sweep. The only difference is the total number of
	 * steps. In addition, it checks if a change in the state of the system is
	 * wanted to be made.
	 */
	public void stepDone() {
		step++; // Increase the step in which the simulation is in
		// Check if the simulation is waiting to be paused
		if (runningState == RunningState.PAUSE) {
			// Change the state to PAUSED
			runningState = RunningState.PAUSED;
			// Keep waiting until the state is changed. Check every 200 ms
			while (runningState == RunningState.PAUSED) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					System.out.println("Interrupted Exception");
				}
			}
			// Check if the simulation is waiting to be launched
		} else if (runningState == RunningState.READY) {
			// Change the state to RUNNING
			runningState = RunningState.RUNNING;
		}
	}

}