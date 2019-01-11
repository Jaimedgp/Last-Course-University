package r2ms.flip.modOg;

import r2ms.common.*;
import java.util.ArrayList;
import java.util.Random;

public class OgNew {
	//Input file with the parameters needed
	InputData in;
	//Lattice
	int[][] L;
	//length of the lattice
	int N;
	// Array of interaction constants
	//The probabilities arrayList is created in the constructor and used in the doIaccpet method
	ArrayList<Double[]> prob;
	//ArrayList with the positions of the N-families of neighbors
	ArrayList<ArrayList<int[]>> neighbors;
	
	
	/**
	 * Constructor of the class
	 * @param in input object
	 * @param L lattice
	 */
	
	public OgNew(InputData in,int[][] L) {
		//We initialize the parameters 
		this.in=in; //input object
		this.L=L; //Lattice
		double red_temp=in.temperature;//Reduced Temperature
		double[] J=in.J; //Array of J (interaction constants)
		int N=in.nJ;//Number of Neighbors
		//We initialize the arrays of positions and probabilities
		neighbors=new ArrayList<ArrayList<int[]>>(N);
		prob=new ArrayList<Double[]>(N);
		//We initialize the neigh-th family of neighbors
		for(int i=0 ; i<N ; i++) {
			neighbors.add(new ArrayList<int[]>());
		}

		//We compute an upper bound of the radius that the first n neighbors occupy in the first octant
		double R=Math.ceil(Math.sqrt(8*(N+1)/Math.PI));
		//We compute the position and square distance of the points of the first octant with distance less than R
		ArrayList<int[]> V=new ArrayList<int[]>();
		for(int j=1 ; j<R/Math.sqrt(2) ; j++) {
			for(int i=1 ; i<Math.sqrt(R*R-j*j) ; i++) {
				int[] v=new int[3];
				//Square distance
				v[0]=i*i+j*j;
				//x position
				v[1]=i;
				//y position
				v[2]=j;

				
				V.add(v);
				
			}	
		}
		//We sort the elements of V. This arraylist is going to be filled with the neighbors sorted by distance.
		ArrayList<int[]> Vord=new ArrayList<int[]>();
		//SORTING ALGORITHM
		int d=N*N+1;
		int ind=0;
		int Vsize=V.size();
		
		for(int n=0 ; n<Vsize ; n++)
			for (int k=0 ; k<V.size() ; k++) {
				if(V.get(k)[0]<=d) {
					ind=k;
					d=V.get(k)[0];
				}
				Vord.add(n,V.remove(ind));
			}
		
		//We assign all the positions to the i-th neighbors		
		int neigh=0;
		for(int l=0 ; l<Vord.size() ; l++) {
			if(neigh<N) {
				// Coordinates of the representative point of the l-th neighbors
				int nx=Vord.get(l)[1];
				int ny=Vord.get(l)[2];
				
				
				if(ny==0) {//By symmetry, if the pint is located on the X, it will have four symmetric points
					int[] v1= {nx,0};
					int[] v2= {-nx,0};
					int[] v3= {0,nx};
					int[] v4= {0,-nx};
					neighbors.get(neigh).add(v1);
					neighbors.get(neigh).add(v2);
					neighbors.get(neigh).add(v3);
					neighbors.get(neigh).add(v4);
				}else if(ny==nx) {//By symmetry, if the pint is located on a main diagonal, it will have four 
								  //symmetric points
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
				
		//We compute the probabilities 
		// First of all we divide the interaction constants by the reduced temperature
		for(int a = 0; a < J.length; a++)
		{
		  J[a] = J[a]/red_temp;
		}
		// We study the different positions of the spins in each family (supposing the center spin is up)
		for(int i=0 ; i<N ; i++) {
			int num=neighbors.get(i).size();
			Double[] w=new Double[num+1];
			// We study the number of down spins in the i-th family
			// If half or less of the spins are down, the probability of flipping is 1
			for(int j=0 ; j<=0.5*num ; j++) {
				w[j]=(double) 1;
			}
			// If more than half of the spins are down, the probability of flipping is given by the 
			//Boltzmann distribution
			for (int j=(int) (0.5*num+1) ; j<num ; j++){
				w[j]=Math.exp(-2*J[i]*j);
			}
			//We add the array of probabilities for the different configurations for the i-th neighbors to the 
			//general probability ArrayList, ordered by i.
				prob.add(i,w);
		}	
		
	}
	
	/**
	 * For a given point, it computes whether the shift should be accepted or not, based on the given 
	 * configuration of the neighbors
	 * @param x horizontal position of the point
	 * @param y vertical position of the point
	 * @return if we should accept the shift
	 */

	
	public boolean doIAccept(int x , int y) {
		// We study whether the spin of the point is up or down
		int ud=L[x][y];
		// We initialize the probability of flipping
		double pr=1;
		// We go through all the families of neighbors		
		for(int i=0 ; i<N ; i++) {
			// We store here the number of down-pointing spins of a certain family
			int ndown=0;
			//We go through all the neighbors of the family
			for(int j=0 ; j<=neighbors.get(i).size() ; j++) {
				// Position of the neighbor taking into account the cyclic boundary conditions
				int LL=in.latticeLength;
				int gx=(x+neighbors.get(i).get(j)[0])%LL;
				int gy=(y+neighbors.get(i).get(j)[1])%LL;
				//If the spin is down, we add it to ndown
				if(L[gx][gy]==-1) {
					ndown++;
				}
			}
			// As the variables are independent, the total probability will be the product of the individual ones
			// The energy difference is different if the spin is up or down (but is symmetric)
			if(ud==1) {
				pr=pr*prob.get(i)[ndown];
			}else {
				pr=pr*prob.get(i)[neighbors.get(i).size()-ndown-1];
			}
		}
		int iseed = -1;//To seed the generator
	    Random random=new Random(iseed); //To obtain always the same 'random' configuration
	    
	    //We use a random number to decide whether we flip or not
	    if(random.nextDouble()<pr) {
	    	return true;
	    }else {
	    	return false;
	    }
	    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    // WARNING: We do not actually flip the spin. You will have to do it based on our ouput
	    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    
	}

}