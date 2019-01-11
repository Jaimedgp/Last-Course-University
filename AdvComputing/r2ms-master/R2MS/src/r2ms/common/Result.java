package r2ms.common;

public class Result {
  /**
   * magnetization
   */
  public double m;

  /**
   * Susceptibility
   */
  public double x;

  public InputData inputData; //what the model calls "in"
 
  
  //public <inputData> b;
  
  public Result(InputData in, double m, double x){
	  this.m = m;
	  this.inputData=in;
	  this.x=x;
  }
  
  public double getT(){
	  return inputData.temperature;
  }
  
  public double getH(){
	return inputData.H;  
	  	  
  }
  
  public double getL(){
	return inputData.latticeLength;  
	  
  }
  
  public double getm(){
	  return m;
  
  }
  
  public double getx(){
	  return x;
 
  }






  
  
}
  
  