/**
* THe Og method is used to compute whether to flip a given spin. It consists on a constructor, which computes the different
* locations of the neighbors, as well as the probabilities of flipping given a certain configuration, as well as a doIAccept
* method, which is the one summoned by the simulation.
*
* @author	Ognanesson Group a.k.a. Og (Pablo Echegoyen Ruiz, Ignacio Ruiz Garc�a, Guillermo Ruiz Laborda)
* @date		21/12/2018
*/


package r2ms.flip.modOg;

import r2ms.common.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Og implements IFlipAcceptance{
	//Input file with the parameters needed 
	private InputData in;
	//Lattice
	private int[][] L;
	//length of the lattice
	private int length;
	//ArrayList with the positions of the N-families of neighbor
	private ArrayList<ArrayList<int[]>> neighbors;
	//External magnetic field (Kelvin)
	private double H;
	//Array of J (interaction constants)(Kelvin)
	double[] J; 	
	//Reduced temperature (Kelvin)
	private double temp;
	//Whole energy of the system (Kelvin)
	private double totalEnergy;
	//Magnetization of the lattice
	private int mag;

	
	/**
	 * Constructor of the class, that is, creates an object with the information about the location of the neighbors
	 * of each family, as well as the probabilities of flipping given each configuration around an arbitrary point
	 * @param in InputData object
	 * @param L lattice
	 */
	
	public Og(InputData in,int[][] L) {
		//We initialize the parameters 
			this.in=in; //input object
			this.L=L; //Lattice
			length=L.length; //Lenght of the lattice
			temp=in.temperature;//Reduced Temperature
			H=in.H;//External magnetic field
			J=in.J; //Array of J (interaction constants)(Kelvin)
			int N=in.nJ;//Number of Neighbors
			//We initialize the arrays of positions
			neighbors=new ArrayList<ArrayList<int[]>>(N);
			//We initialize the neigh-th family of neighbors
			for(int i=0 ; i<N ; i++) {
				neighbors.add(new ArrayList<int[]>());
			}
			//We set the original energy of the system as 0
			totalEnergy=0;
			//We set the initial magnetization according to the state of the lattice
			mag=0;
			for (int i=0; i<length; i++) {
				for (int j=0; j<length; j++) {
					mag=mag+L[i][j];
				}
			}
	
		//We compute an upper bound of the radius that the first n neighbors occupy in the first octant
		double R=Math.ceil(Math.sqrt(8*(N+1)/Math.PI));
		//We compute the position and square distance of the points of the first octant with distance less than R
		ArrayList<int[]> V=new ArrayList<int[]>();
		//for(int j=1 ; j<R/Math.sqrt(2) ; j++) {
		//for(int i=0 ; i<Math.sqrt(R*R-j*j) ; i++) {
		for (int i=1; i<R;i++) {
			for (int j=0; j<=Math.min(i, (int) R);j++) {								
				// We create an array identifying each point with its squared distance to the main point
				int[] v=new int[3];
				v[0]=i*i+j*j;//Square distance
				v[1]=i;//x position
				v[2]=j;//y position	
				// We add the vector to the arraylist
				V.add(v);
				
			}	
		}
		//We sort the elements of V. This arraylist is going to be filled with the neighbors sorted by distance (actually
		// square distance, but as f(x)=x^2 is a monotonous increasing function it preserves the order )
		ArrayList<int[]> Vord=new ArrayList<int[]>();
		// SORTING ALGORITHM
			int d; //The distance that will be used as the sorting parameter
			int[] vm = null; //Element used to store the points with the least square distance in each iteration
			for (int i=0; i<V.size();i++) { //We implement the algorithm as many times as elements we have to short
				d=Integer.MAX_VALUE; //At the beginning of each iteration d is set to infinity so the first element has 
									 // a smaller square distance and is stored at vm
				for(int[] v:V) { // We go through every element in the unordered set every iteration
					if (v[0]<d && !Vord.contains(v)) {// If a certain element has the smallest square distance and is not
													  // already in the ordered set, we set it as vm
						d=v[0]; // We give d the new value
						vm=v; //We set vm as v
					}
				}
				Vord.add(i,vm); // We add vm to the ordered set
			}
		
		//We assign all the positions to the i-th neighbors		
		int neigh=0;
		for(int l=0 ; l<Vord.size() ; l++) {
			if(neigh<N) {
				// Coordinates of the representative point of the l-th neighbors
				int nx=Vord.get(l)[1];
				int ny=Vord.get(l)[2];
				
				if(ny==0) {//By symmetry, if the point is located on the X axis, it will have four symmetric points
					int[] v1= {nx,0};
					int[] v2= {-nx,0};
					int[] v3= {0,nx};
					int[] v4= {0,-nx};
					neighbors.get(neigh).add(v1);
					neighbors.get(neigh).add(v2);
					neighbors.get(neigh).add(v3);
					neighbors.get(neigh).add(v4);
				}else if(ny==nx) {//If the point is located on a main diagonal, it will have four symmetric points
					int[] v1= {nx,ny};
					int[] v2= {-nx,ny};
					int[] v3= {nx,-ny};
					int[] v4= {-nx,-ny};
					neighbors.get(neigh).add(v1);
					neighbors.get(neigh).add(v2);
					neighbors.get(neigh).add(v3);
					neighbors.get(neigh).add(v4);					
				}else {// Otherwise, the symmetry will give us a total of 8 points
					int[] v1= {nx,ny};
					int[] v2= {-nx,ny};
					int[] v3= {nx,-ny};
					int[] v4= {-nx,-ny};
					int[] v5= {ny,nx};
					int[] v6= {-ny,nx};
					int[] v7= {ny,-nx};
					int[] v8= {-ny,-nx};
					neighbors.get(neigh).add(v1);
					neighbors.get(neigh).add(v2);
					neighbors.get(neigh).add(v3);
					neighbors.get(neigh).add(v4);
					neighbors.get(neigh).add(v5);
					neighbors.get(neigh).add(v6);
					neighbors.get(neigh).add(v7);
					neighbors.get(neigh).add(v8);
				}
				// We only change of family of neighbors if the distance changes
				if(Vord.get(l+1)!=Vord.get(l)) {
					neigh++;
				}
				
			}
		}
			
		
	}
	
	
	//We create the random number outside the method (otherwise we will get always the same random number)
	static int iseed = -1;//To seed the generator
    static Random random=new Random(iseed); 
	/**
	 * For a given point, it computes whether the shift should be accepted or not, based on the given 
	 * configuration of the neighbors. It also performs the flip and updates the energy
	 * @param x horizontal position of the point
	 * @param y vertical position of the point
	 */

	public void checkFlip(int x , int y) {
		// Energy shift of the flip
		double energyShift=0;
		// We study whether the spin of the point is up or down
		int ud=L[x][y];
		// We initialize the probability of flipping at 1
		double pr=1;
		// We we'll need the size of the Lattice
		int LL=in.latticeLength;
		// We go through all the families of neighbors		
		for(int i=0 ; i<in.nJ ; i++) {
			// We store here the total spin of a certain family
			int totalSpin=0;
			//We go through all the neighbors of the family
			for(int j=0 ; j<neighbors.get(i).size() ; j++) {
				// Position of the neighbor taking into account the cyclic boundary conditions
				int gx=((x+neighbors.get(i).get(j)[0]+LL)%LL);
				int gy=((y+neighbors.get(i).get(j)[1]+LL)%LL);				
				// We add the spin to the total spin sum
				totalSpin=totalSpin+L[gx][gy];
			}
			// We add the contribution to the energy
			energyShift=energyShift+2*J[i]*totalSpin*ud;
		}
		//We add to the energy shift the external magnetic field term 
		energyShift=energyShift+H*ud;
		// We compute the probability of flipping
		if (energyShift<0) {
			pr=1;
		} else {
			pr=Math.exp(-energyShift/temp);
		}
	    //We use a random number to decide whether we flip or not
		if(random.nextDouble()<=pr) {
			L[x][y]=-L[x][y];//We do the flip
			totalEnergy=totalEnergy+energyShift;// We update the energy
			mag=mag+2*L[x][y];// We update the magnetization
	    }
		
	}
	
	/**
	 * This methods allows to reset the energy to 0 (We recommend doing it after
	 * the thermalization)
	 */
	
	public void resetEnergy() {
		totalEnergy=0;
	}
	
	/**
	 * This methods returns the total energy (compared with the initial state) of the lattice in
	 * the current state
	 */
	@Override
	public double getEnergy() {
		return totalEnergy;
	}
	
	/**
	 * This methods returns the magnetization of the lattice in the current state
	 */
	
		
		
		//This is just a utility constructor
		public Og() {
			super();
		}
		
		@Override
		public IFlipAcceptance constructFlipEvaluator(InputData input, int[][] L) {
			// XXX G80: Used to generate the object
			return new Og(input, L);
		}

		
		@Override
		public int getMagneticMoment() {
			// TODO Auto-generated method stub
			return mag;
		}
		
	    
	}

