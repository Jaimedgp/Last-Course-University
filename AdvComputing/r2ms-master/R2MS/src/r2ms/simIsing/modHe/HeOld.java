package r2ms.simIsing.modHe;
import r2ms.common.*;
import r2ms.results.modH.*;
import r2ms.flip.modOg.*;
import java.util.Scanner;

class HeOld implements IR2MS {
	/**
	 * Constructor of the class
	 */
	public HeOld() {
		
	}
	
  /**
   * 
   * Runs a simulation for the given input data up to completion and add 
   * the output result object to the collector of results object provided.
   * 
   * @param in is the experiment object with the data needed to configure and 
   * run the simulation
   * @param results is a reference to the object that accumulates the results of all simulations
   * 
   * @return returns a Result object with the magnetization and susceptibility of the simulation
   */
	public Result run(InputData in, IResults results) {
		int L=in.latticeLength;
		int[][] lattice=new int[L][L];
		int m=-L*L,nsample=0;
		double totalMag=0,totalMag2=0;
		OgNew flip=new OgNew(in,lattice);
		for (int i=0; i<L;i++) {
			for (int j=0;j<L;j++) {
				lattice[i][j]=-1;//all down
			}
		}
		for (int mcs=1;mcs<=in.mcs;mcs++) {
			for (int i=0;i<L;i++) {
				for (int j=0;j<L;j++) {
					if (flip.doIAccept(i,j)) {
						lattice[i][j]=-lattice[i][j];
						m+=2*lattice[i][j];
					}
				}
			}
			if (mcs>in.therm) {
				if (mcs%in.skip==0) {
					nsample++;
					double mag=(double)m/(double)(L*L);
					System.out.println(mag);
					totalMag+=mag;
					totalMag2+=mag*mag;
				}
			}
		}
		double mag=totalMag/nsample;
		double ji=(totalMag2/nsample-mag*mag)/in.temperature;
		return results.saveResult(in,mag,ji);
	}
	
	/**
	 * Main method of the class. This will be executed from the shell script.
	 * @param args parameter of the main (not used)
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int L=scan.nextInt(); // read the lattice length
		double T=scan.nextDouble();// read the reduced temperature
		int mcsmax=scan.nextInt();// read the number of Montecarlo sweeps
		int average=scan.nextInt();// read the number of steps for average
		int therm=scan.nextInt();// read the number of steps for thermalization
		scan.close();
		double[] J={1.0};// as there is no J but it is needed by InputData, I set it to 1
		InputData in=new InputData(L,T,0.0,1,J,mcsmax,therm,average);// create the InputData object
		IR2MS sim=new HeNew();// create the main object
		IResults ires=new H();// create the results object
		Result res=sim.run(in,ires);// run the simulation
		System.out.println(res.m+" "+res.x);// print the result
	}
}

