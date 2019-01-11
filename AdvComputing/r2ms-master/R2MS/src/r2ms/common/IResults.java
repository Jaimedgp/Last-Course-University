
package r2ms.common;

import java.util.*;
/**
 * The interface for the output class, with the methods to represent each of the results.
 */
public interface IResults {
  /**
   * Returns an object with the results of a simulation done for the given experiment. The 
   * operation creates the output result object and stores it in the list of all obtained
   * results. 
   * 
   * @param in is the input data object with the data needed to retrive
   * the simulation results later
   * @param m the magnetization of the complete lattice at the final stage of the simulation
   * @param x the magetic susceptibility of the complete lattice at the final stage of the simulation
   * 
   * @return returns an IResult object with the given magnetization and susceptibility
   */
  Result saveResult(InputData in, double m, double x) ;

  /**
   * This method returns the list of results accumulated so far.
   */
  ArrayList<Result> getAll() ;

  /**
   * Clear the list of accumulated results. After the call to this method the list of accumulated results is empty.
   * 
   * @returns it returns the number of results accumulated that have been deleted
   */
  int clearAll() ;
  
  
  //add the methods to get info from your list "mvsT", etc do one at least
  /**
   * returns a graph of the m vs H values. There will be 5 similar methods for x vs T, etc 
   * @param Lin fixed value for the lattice size
   * @param Tin fixed value for the temperature
   * @return an object of the class Graph
   */
  Graph plotMvsH(double Lin, double Tin);
  /**
   * returns a graph of the m vs T values. 
   * @param L fixed value for the lattice size
   * @param H fixed value for the external magnetic field
   * @return an object of the class Graph
   */
  Graph plotMvsT(double Lin, double Hin);
  /**
   * returns a graph of the m vs L values.  
   * @param Tin fixed value for the temperature
   * @param H fixed value for the external magnetic field
   * @return an object of the class Graph
   */
  Graph plotMvsL(double Tin, double Hin);
  /**
   * returns a graph of the X vs H values.  
   * @param L fixed value for the lattice size, L
   * @param Tin fixed value for the temperature
   * @return an object of the class Graph
   */
  Graph plotXvsH(double Lin, double Tin);
  /**
   * returns a graph of the X vs T values. 
   * @param L fixed value for the lattice size, L
   * @param H fixed value for the external magnetic field, H
   * @return an object of the class Graph
   */
  Graph plotXvsT(double Lin, double Hin);
  /**
   * returns a graph of the X vs L values. 
   * @param Tin fixed value for the temperature
   * @param H fixed value for the external magnetic field, H
   * @return an object of the class Graph
   */
  Graph plotXvsL(double Tin, double Hin);
}
