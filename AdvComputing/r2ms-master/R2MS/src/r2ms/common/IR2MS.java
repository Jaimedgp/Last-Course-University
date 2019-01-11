
package r2ms.common;

public interface IR2MS {
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
  Result run(InputData inData, IResults results) ;

}
