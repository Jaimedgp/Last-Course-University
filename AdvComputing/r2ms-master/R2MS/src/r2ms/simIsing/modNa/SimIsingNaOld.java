package r2ms.simIsing.modNa;
/**
 * SimIsing Class runs the Ising model simulation by the Monte Carlo Sweeps
 * @author Carmen García and Francisco Javier Manterola
 * @version December 2018
*
**/

import java.io.*;
import java.util.*;

import r2ms.common.IFlipAcceptance;
import r2ms.common.IResults;
import r2ms.common.InputData;
import r2ms.common.Result;
import r2ms.flip.modOg.Og;

public class SimIsingNaOld {

	// Dimension of the lattice
	int[][] lattice;
	// Index + or - in the axis ip=i+1 im=i-1
	int[] ip, im;

	//Temperature of the system
	double temperature;
	
	// weight of J in the hamiltonian
	// Jkt
	double[] J;
	// Number of neighbors
	int nJ;
	// the energy
	float energy;
	//Maximum number of Mc steps
	int mcs, L;
	int nsample, mcsmax;

	// ici spin of the neighbors
	// ien neighbors with the same spin
	int ien, ici;
	// total magnetic momentum sum of all ici
	double m;
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

	// We initialize with an empty constractor
	public SimIsingNaOld() {
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
	public Result run(InputData input, IResults results) {
		// Variables are read from the input object of InputData
		L = input.latticeLength;
		lattice = new int[L][L];
		this.nJ = input.nJ;
		this.J = new double[nJ];
		this.J = J;
		this.temperature = input.temperature;
		this.mcsmax = input.mcs;
		this.therm = input.therm;
		this.skip = input.skip;
		invsize = 1 / (L * L);
		this.H=input.H;

		
		// We create the files where we save the values during the Monte Carlo sweeps
		File SAMPLES = new File(".", "SAMPLES.txt");
		File PHOTO = new File(".", "PHOTO.txt");

		//G80: Create the object used to evaluate the flip
		//acceptance probability
		IFlipAcceptance flip =  (new Og()).constructFlipEvaluator( input, lattice);

		
		// We create an object to write these files
		//To read SAMPLE file
		try {
			FileWriter writes = new FileWriter(SAMPLES, true);
		} catch (IOException e) {
			//The exception is catched if it is launched
			e.printStackTrace();
		}//To read PHOTO file
		try {
			FileWriter writep = new FileWriter(PHOTO, true);
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
		energy = 0;
		acc_mag = 0;
		acc_mag2 = 0;
		m = -L * L;

		//Monte Carlo sweeps 
		for (mcs = 1; mcs < mcsmax; mcs++) {
			// At every single Monte Carlo sweep, run over all the spins in the
			// lattice
			if (mcs >= therm) {
				for (int i = 0; i < L; i++) {
					for (int j = 0; j < L; j++) {
						// For every single point of the lattice visited,
						// Look at the value of the spin for that point

						//ici = lattice[i][j];
						//ien = lattice[ip[i]][j] + lattice[im[i]][j] + lattice[i][ip[j]] + lattice[i][im[j]];
						//ien = ien * ici;
						// writep.write(lattice[i][j]);
						// writep.write("\r\n");

						// Test whether we should flip the spin
						// and in that case update mag and energy
						// For this, we invoque DoIAccept method from
						// FlipAcceptance class
//G80						IFlipAcceptance flip = new IFlipAcceptance(lattice, J, H);
						if (flip.doIaccept(i, j) == true) {  //XXX G80: Observe that the name is wrong in the interface...

							// If the switch is accepted, then revert the sign of the
							// spin in this lattice point
							ici=lattice[i][j];
							lattice[i][j] = -ici;

							// Update the value of the energy
							// The factor of 2 is for the double counting
							//energy=energy + 2 * ien;
//XXX G80: to decide...							energy = energy +flip.getEnergyChange();

							// Update the value of the magnetic moment
							// If initially all the magnetic moments are pointing down,
							// then the magnetic dipole of the "cluster" is -5.
							// If one is reverted, then there are four pointing down
							// and one pointing up, so the new magnetic moment
							// of the cluster is - 4 (down) + 1 (up) = -3,
							// i. e. we have changed the magnetization by a value of +2, // In this
							// particular case, assuming the initial dipole // moment pointing down, then
							// ici = -1 and
							// m = m - 2*ici = m +2

							m = m - 2 * ici;

						}//end if

					}//end for

				}//end for
				
			}//end if

			//If the maximum number of Monte Carlo steps is more than the thermalization
			if (mcs > therm) {
				//If the module of the maximum number of Monte Carlo steps and the Use for average every steps is equal zero
				if (mcs % skip == 0) {
					//We count 
					nsample = nsample + 1;

					mag = invsize * m;
					// writes.write(nsample+" "+invsize*energy+" "+mag);
					// writes.write("\r\n");

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
		//
		x = ((ave_mag2 - (ave_mag * ave_mag)) / (k * temperature));

		// We return an object from the class Result by using saveResult method
		return results.saveResult(input, m, x);
	}

	//We can't 
	public static void main(String[] args) {
//		InputData inp = new InputData();
//		IResults res=new IResults();
//		SimIsingNa ising=new SimIsingNa();
//		ising.run(inp, res);
		// we have to create scanner to read from keybord and read it from the shell
		System.out.println("I'm fine");
		
						
			//By using Scanner, the user is aked about the variables nedeed to run the simulation
			Scanner scanner = new Scanner(System.in);

			//Read the number of spins per cartesian direction
			System.out.println("System size L: ");
			int L = scanner.nextInt();
			
			//Read the reduced temperature	
			System.out.println("Reduced Temperature (T/Tc) ");
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
			
			double[] J = {0.5, 0.02};
			int nJ = J.length;
		
			//This should be to prove our class but we have to develope some things.
			//InputData in = new InputData(L, temperature,  H, nJ, J, mcsmax, therm, skip);
			//SimIsingNa simulator = new SimIsingNa();
			//Result result = new Result(in, m, x);
			//IResults results = simulator.run(in, result)
		
		

	}

}
