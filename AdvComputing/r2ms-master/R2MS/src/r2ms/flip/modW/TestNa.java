package r2ms.flip.modW;

/**
 * SimIsing Class runs the Ising model simulation by the Monte Carlo Sweeps
 * @author Carmen Garc�a and Francisco Javier Manterola
 * @edited by Lucía Abascal, David Iglesias and Clara Lasaosa.
 * @version 13:54 19-12-2018
*
**/
import java.io.*;
import java.util.*;

import r2ms.common.IFlipAcceptance;
import r2ms.common.InputData;

class ID extends InputData {
	public ID(int latticeLength, double temperature, double H, double[] J, int mcs, int therm, int skip) {
		this.nJ = J.length;
		this.J = J;
		this.temperature = temperature;
		this.latticeLength = latticeLength;
		this.mcs = mcs;
		this.therm = therm;
		this.skip = skip;
	}
}
class TestNa {



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
		double energy;
		
		//Maximum number of Mc steps
		int mcs, L;
		int nsample, mcsmax;

		// ici spin of the neighbors
		// ien neighbors with the same spin
		int ien, ici;
		// total magnetic momentum sum of all ici
		int m; // CHANGED: from double to int
		
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

		public TestNa() {
			
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
		public void run(ID input) {
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
			invsize = 1.0 / (double)(L * L);
			this.H=input.H;

			
			// We create the files where we save the values during the Monte Carlo sweeps
			File SAMPLES = new File(".", "SAMPLES.txt");
			File PHOTO = new File(".", "PHOTO.txt");

			

			
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
			
			//G80: Create the object used to evaluate the flip
			//acceptance probability
			IFlipAcceptance flip =  (new W()).constructFlipEvaluator( input, lattice, (int)m);

			//Monte Carlo sweeps 
			for (mcs = 1; mcs < mcsmax; mcs++) {
				// At every single Monte Carlo sweep, run over all the spins in the
				// lattice
					for (int i = 0; i < L; i++) {
						for (int j = 0; j < L; j++) {

							flip.checkFlip(i, j);

						}//end for

					}//end for
					

				//If the maximum number of Monte Carlo steps is more than the thermalization
				if (mcs > therm) {
					//If the module of the maximum number of Monte Carlo steps and the Use for average every steps is equal zero
					if (mcs % skip == 0) {
						
						// Fetch m and energy from flipAcceptance class
						
						m = flip.getMagneticMoment();
						
						energy = flip.getEnergy();
						// Get the number of sample 
						nsample++;

						mag = invsize * (double) m;
						System.out.println(nsample + "\t" + invsize * energy + "\t" + mag + "\t " + m);

						acc_mag += mag;
						acc_mag2 += mag * mag;
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
			System.out.println("========= END =========");
			System.out.println("mag = " + m * invsize);
			System.out.println("X = " + x + " (wrong value)"); // This value is presumibly wrong...
		}

		//We can't 
		
		public static void main(String[] args) {
			System.out.println("Starting...");
			
							
			//By using Scanner, the user is aked about the variables nedeed to run the simulation
			Scanner scanner = new Scanner(System.in);

			//Read the number of spins per cartesian direction
			System.out.println("System size L: ");
			int L = scanner.nextInt();
			
			//Read the reduced temperature	
			System.out.println("Temperature (K) ");
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
			
			double jktc = 0.5f*(float)Math.log(1.0f+Math.sqrt(2.0f)) * 312.0;
		    
			double[] J = {jktc / temperature};
			
			//This should be to prove our class but we have to develope some things.
			ID in = new ID(L, temperature,  H, J, mcsmax, therm, skip);
			TestNa simulator = new TestNa();
			simulator.run(in);
			

		}

}
