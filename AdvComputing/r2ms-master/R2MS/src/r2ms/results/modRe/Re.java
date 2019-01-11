package r2ms.results.modRe;

//package r2ms.results.modRe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import r2ms.common.Graph;
import r2ms.common.InputData;
import r2ms.common.R2Point;
import r2ms.common.Result;

//@SuppressWarnings("unused") //Suppresses the warning regarding InputData being imported and not used.
//XXX: G80: Just changed the "in" argument type not to use the full name 
//     G80: (or alternatively you can simply remove the import, I think it is better to have it and use it).
//XXX: G80: The class is to be public (then the modifier "public" is added)
public class Re implements r2ms.common.IResults{
	

	/**
	 * TODO
	 * not sure if static
	 * not static because it will go with the simulation, its an object that is used when 
	 * adding new results
	 */
	
	public ArrayList<Result> allResults=new ArrayList<Result>(); //initializes allResults
	public double m;
	public double x;
	public Set<Double> setT= new HashSet<Double>();
	public Set<Double> setH= new HashSet<Double>();
	public Set<Double> setL= new HashSet<Double>();

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
	public r2ms.common.Result saveResult(InputData in, double m, double x) {
		//receives an object of the InputData class, a double m and a double x
			
		Result a = new Result(in,m,x); //creates a Result object with the data
		allResults.add(a);//adds that Result to the array of all results
		
		setT.add(a.getT()); //adds the value of T to the set of values of T.
		setH.add(a.getH()); //adds the value of H to the set of values of H.
		setL.add(a.getL()); //adds the value of L to the set of values of L.

		return a;//returns the Result 
		
		//prior version:
		//in: we can just save L, T; H instead
		//this.m=m;
		//this.x=x;
		//this.in=in;
		//allResults.add(this);
	}
	
	public r2ms.common.Graph plotMvsH(double Lin, double Tin){
		//TODO Tin and Lin are the temperature and size for which we want to plot values of M vs H
		if(setL.contains(Lin)==false){
			System.out.println("The inserted value of Lin does not match any result");
			return null;
		}	else if( setT.contains(Tin)==false){
			System.out.println("The inserted value of Tin does not match any result");
			return null;
		}
		
		//use graph class
		double mvect = 0; 
		double Hvect = 0;
	    //Initializes list of objects of the R2Point class
		List<R2Point> pointArray = new ArrayList<R2Point>();
		
		for (int i=0; i<allResults.size(); i++){
			if(allResults.get(i).inputData.temperature==Tin && allResults.get(i).inputData.latticeLength==Lin){
				mvect=allResults.get(i).m;
//				xvect[n]=allResults.get(i).getx(); //previous version used vectors
				Hvect=allResults.get(i).inputData.H;
				
				//adds a new point (object of the R2Point class) to the array of points.
				pointArray.add(new R2Point(Hvect, mvect));

				}
		} //end loop

		//System.out.println("M"+mvect+ "H"+Hvect); //prints values for testing purposes
			
		//This returns an object from the class Graph, which can be used by the user.
		return new Graph(pointArray);
			
}	
	
	public r2ms.common.Graph plotMvsT(double Lin, double Hin){
		//TODO Hin and Lin are the mag field and size for which we want to plot values of M vs T
		if(setL.contains(Lin)==false){
			System.out.println("The inserted value of Lin does not match any results");
			return null;
		}	else if( setH.contains(Hin)==false){
			System.out.println("The inserted value of Hin does not match any results");
			return null;
		}
		//use graph class
		double mvect = 0; 
		double Tvect = 0;
		//initializes a list of objects of the R2Point class	
		List<R2Point> pointArray = new ArrayList<R2Point>();
		
		for (int i=0; i<allResults.size(); i++){
			if(allResults.get(i).inputData.H==Hin && allResults.get(i).inputData.latticeLength==Lin){
				mvect=allResults.get(i).m;
				Tvect=allResults.get(i).inputData.temperature;

				//adds a new point (object of the R2Point class) to the array of points.
				pointArray.add(new R2Point(mvect, Tvect));

				}
		} 
		//This returns an object from the class Graph, which can be used by the user.
		return new Graph(pointArray);
			
}
	
	public r2ms.common.Graph plotMvsL(double Tin, double Hin){
		//TODO Hin and Tin are the mag field and temperature for which we want to plot values of M vs L
		
		if(setH.contains(Hin)==false){
			System.out.println("The inserted value of Hin does not match any results");
			return null;
		}	else if( setT.contains(Tin)==false){
			System.out.println("The inserted value of Tin does not match any results");
			return null;
		}
		
		//use graph class
		double mvect = 0; 
		double Lvect = 0;
		//initializes a list of objects of the R2Point class		
		List<R2Point> pointArray = new ArrayList<R2Point>();
		
		for (int i=0; i<allResults.size(); i++){
			if(allResults.get(i).inputData.H==Hin && allResults.get(i).inputData.temperature==Tin){
				mvect=allResults.get(i).m;
				Lvect=allResults.get(i).inputData.latticeLength;
				
				//adds a new point (object of the R2Point class) to the array of points.
				pointArray.add(new R2Point(Lvect, mvect));
								
				}
		} 
		//This returns an object from the class Graph, which can be used by the user.
		return new Graph(pointArray);
			
}
	
	public r2ms.common.Graph plotXvsH(double Lin, double Tin){
		//TODO Tin and Lin are the temperature and size for which we want to plot values of X vs H

		if(setL.contains(Lin)==false){
			System.out.println("The inserted value of Lin does not match any results");
			return null;
		}	else if( setT.contains(Tin)==false){
			System.out.println("The inserted value of Tin does not match any results");
			return null;
		}

		//use graph class
		double xvect = 0; 
		double Hvect = 0;
		
		//initializes a list of objects of the R2Point class	
		List<R2Point> pointArray = new ArrayList<R2Point>();
		
		for (int i=0; i<allResults.size(); i++){
			if(allResults.get(i).inputData.temperature==Tin && allResults.get(i).inputData.latticeLength==Lin){
				xvect=allResults.get(i).x;
				Hvect=allResults.get(i).inputData.H;
				
				//adds a new point (object of the R2Point class) to the array of points.
				pointArray.add(new R2Point(Hvect, xvect));
				
				}
		} //end loop
		
		//This returns an object from the class Graph, which can be used by the user.
		return new Graph(pointArray);
			
}	
	
	public r2ms.common.Graph plotXvsT(double Lin, double Hin){
		//TODO Hin and Lin are the mag field and size for which we want to plot values of X vs T
		if(setL.contains(Lin)==false){
			System.out.println("The inserted value of Lin does not match any results");
			return null;
		}	else if( setH.contains(Hin)==false){
			System.out.println("The inserted value of Hin does not match any results");
			return null;
		}

		//use graph class
		double xvect = 0; 
		double Tvect = 0;
		
		//initializes a list of objects of the R2Point class	
		List<R2Point> pointArray = new ArrayList<R2Point>();
		
		for (int i=0; i<allResults.size(); i++){
			if(allResults.get(i).getH()==Hin && allResults.get(i).getL()==Lin){
				xvect=allResults.get(i).x;
				Tvect=allResults.get(i).inputData.temperature;
				
				//adds a new point (object of the R2Point class) to the array of points.
				pointArray.add(new R2Point(Tvect, xvect));
				

				}
		} 
		//This returns an object from the class Graph, which can be used by the user.
		return new Graph(pointArray);
			
}
	
	public r2ms.common.Graph plotXvsL(double Tin, double Hin){
		//TODO Hin and Tin are the mag field and temperature for which we want to plot values of X vs L
		if(setT.contains(Tin)==false){
			System.out.println("The inserted value of Tin does not match any results");
			return null;
		}	else if( setH.contains(Hin)==false){
			System.out.println("The inserted value of Hin does not match any results");
			return null;
		}

		//use graph class
		double xvect = 0; 
		double Lvect = 0;
		
		//initializes a list of objects of the R2Point class	
		List<R2Point> pointArray = new ArrayList<R2Point>();
		
		for (int i=0; i<allResults.size(); i++){
			if(allResults.get(i).getH()==Hin && allResults.get(i).getT()==Tin){
				xvect=allResults.get(i).x;
				Lvect=allResults.get(i).inputData.latticeLength;
				
				//adds a new point (object of the R2Point class) to the array of points.
				pointArray.add(new R2Point(Lvect, xvect));
				
				
				}
		} 
		//This returns an object from the class Graph, which can be used by the user.
		return new Graph(pointArray);
			
}
	//end of plot methods

	
	@Override
	public ArrayList<r2ms.common.Result> getAll() {
		// simple method that returns the whole ArrayList with all the Results
		
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

}