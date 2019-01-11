package r2ms.gui.modEu;

//package r2ms.results.modRe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fundamentos.*;
import r2ms.common.Graph;
import r2ms.common.InputData;
import r2ms.common.R2Point;
import r2ms.common.Result;

public class Re implements r2ms.common.IResults{
	

	/**
	 * TODO
	 * not sure if static
	 * not static because it will go with the simulation, its an object that is used when 
	 * adding new results
	 */
	
	public ArrayList<Result> allResults = new ArrayList<Result>();  //XXX G80;
		public double m;
		public double x;	
		//public double in;
	/**
	* Returns an object with the results of a simulation done for the given experiment. The 
	* operation creates the output result object and stores it in the list of all obtained
	* results. 
	* 
	* @param in is the input data object with the data needed to retrive
	* the simulation results later
	* @param m the magnetization of the complete lattice at the final stage of the simulation
	* @param x the magnetic susceptibility of the complete lattice at the final stage of the simulation
	* 
	* @return returns an IResult object with the given magnetization and susceptibility
	*/
	@Override
	public r2ms.common.Result saveResult(r2ms.common.InputData in, double m, double x) {
		//receives an object of the InputData class, a double m and a double x
			
		Result a = new Result(in,m,x); //creates a Result object with the data
		allResults.add(a);//adds that Result to the array of all results
		return a;//returns the Result 
		
		//prior version:
		//in: we can just save L, T; H instead
		//this.m=m;
		//this.x=x;
		//this.in=in;
		//allResults.add(this);
	}
	
	public r2ms.common.Graph plotMvsT(double Tin, double Ls){
		//TODO
		int n=0;
		//use graph class
		double[] mvect = null; 
//		double[] xvect = null;
		double[] Hvect = null;
		//ArrayList<R2Point> pointArray = null;  //initializes an arraylist of objects of the R2Point class
	    //should be a List instead of an ArrayList
		
//		List<R2Point> pointArray = null; G80
		List<R2Point> pointArray = new ArrayList<R2Point>();
		
		for (int i=0; i<allResults.size(); i++){
//			if(allResults.get(i).getT()==Tin && allResults.get(i).getL()==Ls){      G80
			if(allResults.get(i).in.temperature==Tin && allResults.get(i).in.latticeLength==Ls){
//				mvect[n]=allResults.get(i).getm();   G80
				mvect[n]=allResults.get(i).m;
//				xvect[n]=allResults.get(i).getx();
//				Hvect[n]=allResults.get(i).getH();   G80
				Hvect[n]=allResults.get(i).in.H;
				
				//adds a new point (object of the R2Point class) to the array of points.
				pointArray.add(new R2Point(mvect[n], Hvect[n]));
				
				n++;
				//you have to sort these points properly in ascending order of x, not n
				//create points and add to the arraylist of graphs, and make them comparable 
				//to sort them, or do it manually
				//Analyze the results, if there's no data, problem:
				//have a method that returns all the possible values of H, T, etc that are available
				//and return them as a set, so they can use choose the solutions they want
				//go through allResults and add all the values into a set
				}
		}
		//Set<Double> s= new HashSet<Double>();
		//s.add(10.0);
		//s.
		//System.out.println("M"+mvect+ "X"+xvect + "H"+Hvect); //prints values for testing purposes
			
		//This returns an object from the class Graph, which can be used by the user.
		return new Graph(pointArray);
		
		//Graphs made with fundamentos; they are, for the moment, irrelevant.
		
//		Grafica g1 = new Grafica("m/H","m","H");
//		for (int j=0;j<mvect.length;j++){
		
//		g1.inserta(Hvect[j], mvect[j]);
		
//		}
		
//		g1.pinta();
		
//		Grafica g2 = new Grafica("x/H","x","H");
//		for (int j=0;j<mvect.length;j++){
		
//		g2.inserta(Hvect[j], xvect[j]);
				
//		}
		
//		g2.pinta();	
			
}	
	
	public void getPointsL(int Tin, int H0){
		//TODO
		double[] mvect = new double[allResults.size()];
		double[] xvect = new double[allResults.size()];
		double[] Lvect = new double[allResults.size()];
		int n=0;
		
		for (int i=0; i<allResults.size(); i++){
			if(allResults.get(i).in.temperature==Tin && allResults.get(i).in.H==H0){
				mvect[n]=allResults.get(i).m;
				xvect[n]=allResults.get(i).x;
				Lvect[n]=allResults.get(i).in.latticeLength;
				n++;
				
				}
		}
		System.out.println("M"+mvect+ "X"+xvect + "L"+Lvect); //prints values for testing purposes
				
		Grafica g1 = new Grafica("m/L","m","L");
		for (int j=0;j<mvect.length;j++){
		
		g1.inserta(Lvect[j], mvect[j]);
		
		}
		
		g1.pinta();
				
		Grafica g2 = new Grafica("x/L","x","L");
		for (int j=0;j<mvect.length;j++){
		
		g2.inserta(Lvect[j], xvect[j]);
		
		}
				
		g2.pinta();	
	}
		
	public void getPointstT(int Lin, double H0){
		//TODO
		double[] mvect = new double[allResults.size()];
		double[] xvect = new double[allResults.size()];
		double[] Tvect = new double[allResults.size()];
		int n=0;
		for (int i=0; i<allResults.size(); i++){
			if(allResults.get(i).in.latticeLength==Lin && allResults.get(i).in.H==H0){
				mvect[n]=allResults.get(i).m;
				xvect[n]=allResults.get(i).x;
				Tvect[n]=allResults.get(i).in.temperature;
				n++;
				}
		}
		System.out.println("M"+mvect+ "X"+xvect + "T"+Tvect); //prints values for testing purposes	
		
		Grafica g1 = new Grafica("m/T","m","T");
		for (int j=0;j<mvect.length;j++){
		
		g1.inserta(Tvect[j], mvect[j]);
				
		}
		
		g1.pinta();
		
		Grafica g2 = new Grafica("x/T","x","T");
		for (int j=0;j<mvect.length;j++){
		
		g2.inserta(Tvect[j], xvect[j]);
			
		}
		
		g2.pinta();	
				
	}
	
	@Override
	public ArrayList<r2ms.common.Result> getAll() {
		// TODO Auto-generated method stub
		
		return allResults;
	}
	
	@Override
	public int clearAll() {
		// TODO Auto-generated method stub
		//for(int i=0, i<allResults.size(); i++) {
		//	allResults.remove(0);
		//}
		allResults.clear();
		return 0;
	}
	
	
	
	
	
	
	
	
	
    /**
     * This method shall return a string with the name of the file
	 * all the data has been allocated.
     * @param mag
     * @param T
     * @param ji
     * @param n_samples
     * @param photo
     * @param data
     * @param energy
     * @param avg_mag
     * @param avg_mag2
     * @return outputFile Name of the file with the output data
     */
	//public static String main(double mag[], double T[], double ji[], int n_samples, int photo[][], input data, int energy, double avg_mag, double avg_mag2){
	    //TODO
	
	//}

}