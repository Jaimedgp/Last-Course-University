
package r2ms.common;

public interface IFlipAcceptance {
  /**
   * This methode calculates the acceptance of a flip of magnetization.
   * @param xCoord the x coordinate of the position of the element to analyze in the Lattice
   * @param yCoord the y coordinate of the position of the element to analyze in the Lattice
   * @return it returns true if the flip is accepted false otherwise
   */
  //boolean doIAccept(int xCoor, int yCoor) ;
  
  /**
   * This method checks the acceptance of spin change and updates the magnetic momentum
   * and the energy change from initial state.
   */
  
  void checkFlip(int xCoor, int yCoor);

  /**
   * This method constructs and returns a new FlipAcceptance Object 
   */
  IFlipAcceptance constructFlipEvaluator(InputData input, int [][] L) ;
  
  /**
   * This method returns the energy variation from initial state
   */
  
  double getEnergy();
  
  /**
   * This method returns the magnetic moment of lattice
   */
  
  int getMagneticMoment();


/**
 * This methods allows to reset the energy to 0 (We recommend doing it after
 * the thermalization)
 */

  void resetEnergy();

}