
package r2ms.simIsing.modNa;

import r2ms.common.IFlipAcceptance;
import r2ms.common.IR2MS;
import r2ms.common.IResults;
import r2ms.common.InputData;
import r2ms.common.Result;
import r2ms.flip.modOg.Og;
import r2ms.results.modRe.Re;
import r2ms.inputData.modLv.Lv;

import java.io.*;
import java.util.*;


public class Na implements IR2MS{

	// Dimension of the lattice
	int[][] lattice;
	// Index + or - in the axis ip=i+1 im=i-1
	int[] ip, im;

	//Temperature of the system
	double temperature;
	//the energy
	double energy;
	// weight of J in the hamiltonian
	// Jkt
	double[] J;
	// Number of neighbors
	int nJ;
	//Maximum number of Mc steps
	int mcs, L;
	int nsample, mcsmax;

	// ici spin of the neighbors
	// ien neighbors with the same spin
	int ien, ici;
	// total magnetic momentum sum of all ici
	int m;
	//Neglect first steps for thermalization
	int therm;
	//Use for average every steps	
	int skip;

	//mag, magnetization of the system
	//
	double mag, ave_mag, ave_mag2, x, H;
	double acc_mag, acc_mag2;
	double invsize;

	// Boltzmann's constant
	double k = 1.3806488 * Math.pow(10.0, -23);

	// We initialize with an empty constructor
	public Na() {
	}

	
	
	
	/**
	 * Method that runs the current simulation by the Monte Carlo sweeps
	 * 
	 * @param input from InputData, and results from IResults
	 * @returns an object from the class Result
	 **/
	/*
	 * Cambio InputData a Lv, no lo he hecho
	 */
	
	@Override
	public Result run(InputData input, IResults results) {
		// Variables are read from the input object of InputData
		L = input.latticeLength;
		lattice = new int[L][L];
		this.nJ = input.nJ;
		//this.J = new double[nJ];
		this.J = input.J;
		this.temperature = input.temperature;
		this.mcsmax = input.mcs;
		this.therm = input.therm;
		this.skip = input.skip;
		invsize = 1.0 /((double)(L * L));
		this.H=input.H;

		
		// We create the files where we save the values during the Monte Carlo sweeps
		File SAMPLES = new File(".", "SAMPLES.txt");
		File PHOTO = new File(".", "PHOTO.txt");

		// We create an object to write these files
		//To read SAMPLE file
		FileWriter writes=null;
		FileWriter writep=null;
		try {
			 writes = new FileWriter(SAMPLES, true);
		} catch (IOException e) {
			//The exception is catched if it is launched
			e.printStackTrace();
		}//To read PHOTO file
		try {
			 writep = new FileWriter(PHOTO, true);
		} catch (IOException e) {
			//The exception is catched if it is launched
			e.printStackTrace();
		}

		// Initial conditions
		for (int i = 0; i < L; i++) {
			for (int j = 0; j < L; j++) {
				lattice[i][j] = -1;
			}
		}
        
		//Initialized variables
		energy=0;
		acc_mag = 0;
		acc_mag2 = 0;
		m = -L * L;
		nsample=0;
		

		//We create an object from flip acceptance's class
		//IFlipAcceptance flip = new IFlipAcceptance(lattice, J, H);
		IFlipAcceptance flip = (new Og()).constructFlipEvaluator(input, lattice);
		
		
		
		//Monte Carlo sweeps 
		for (mcs = 1; mcs < mcsmax; mcs++) {
			// At every single Monte Carlo sweep, run over all the spins in the
			// lattice
			
				for (int i = 0; i < L; i++) {
					
					for (int j = 0; j < L; j++) {
						
						// For every single point of the lattice visited,
						// Look at the value of the spin for that point
						//Changes the current state of the lattice for each point
						flip.checkFlip(i,j);
					
					}//end for

				}//end for
				
			

			//If the maximum number of Monte Carlo steps is more than the thermalization
			if (mcs > therm) {
				//If the module of the maximum number of Monte Carlo steps and the Use for average every steps is equal zero
				if (mcs % skip == 0) {
					
					//Here we compute 					
					energy=flip.getEnergy();
					m=flip.getMagneticMoment();
					
					//We count 
					nsample = nsample + 1;

					mag = invsize * m;
					
                         try {
							writes.write(nsample +" "+invsize*energy+" "+mag);
							writes.write("\r\n");
						} catch (IOException e) {
						// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
				
					

					acc_mag = acc_mag + mag;
					acc_mag2 = acc_mag2 + mag * mag;
				}
			
			}
		}//end for

		ave_mag = acc_mag / nsample;
		ave_mag2 = acc_mag2 / nsample;

		// Susceptibility
		// This is given by the fluctuation dissipation theorem,
		// see, for instance, Eq. (2.13) of the book by Landau and Binder, page 11
		// $k_{\rm B} T \chi = \langle M^{2} \rangle - \langle M \rangle^{2}
		
		x = ((ave_mag2 - (ave_mag * ave_mag)) / (k * temperature));
		

//I implemented this	---	//Here I print the lattice, it should work
		try {
		for(int j=L-1;j>=0;j--) {
			for(int i=0; i<0;i++) {
				
					writep.write((lattice[i][j]+1)+"");
			}
			writep.write("\r\n");
				
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// We return an object from the class Result by using saveResult method
		return results.saveResult(input, m, x);
	}
	
	//We can't 
	public static void main(String[] args) {

						
			//By using Scanner, the user is aked about the variables nedeed to run the simulation
		    Scanner scanner = new Scanner(System.in);

			//Read the number of spins per cartesian direction
			System.out.println("System size L: ");
			int L = scanner.nextInt();
			
			//Read the reduced temperature	
			System.out.println("Reduced Temperature T ");
			double temperature = scanner.nextDouble();
			//Read the Maximum number of Mc steps			
			System.out.println("Maximum number of Mc steps (Mcsmax): ");
			int mcsmax = scanner.nextInt();
			
			//Read the Use for average every steps	
			System.out.println("Use for average every steps: ");
		    int skip = scanner.nextInt();
			
			//Read the Neglect first steps for thermalization:
     		System.out.println("Neglect first steps for thermalization: ");
			int therm = scanner.nextInt();
			
			//Read the external magnetic field
			System.out.println("External magnetic field, H: ");
			double H= scanner.nextDouble();	
			
			double[] J = {5, 2};
			int nJ = J.length;
		
			//int LL=100;
			//double temp=109;
			//double H=0.3;
			//int mcsmax=1000;
			//int therm=50;
			//int skip=50;
			int a=1;
			double b=0.5;
			//This should be to prove our class but we have to develope some things.
//XXX:G80:	InputData in = new InputData(L, temperature,  H, nJ, J, mcsmax, therm, skip);
			InputData in = new Lv(L, temperature,  H, nJ, J, mcsmax, therm, skip); //XXX: G80: This calls Lv constructor
			Na simulator = new Na();
			IResults results = new Re();  //XXX: G80: IResults changed to Re
			Result result = simulator.run(in, results);
		
		

	}

	
}
