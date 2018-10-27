/**
 * Script to study the microstates' probability of flip 100 coins 
 *
 * @author Jaimedgp
 */

import fundamentos.Grafica;

public class Randoms {
	
	final static int experimentLengh = 1000000;

	static double x;
	static int numCoins = 100;

	// number of events by their sum
	static int[] S= new int[numCoins+1];
	
	public static void main(String[] args) {
		
		for (int i=0; i<S.length; i++) {
			S[i] = 0;
		}

		for (int i=0; i<experimentLengh; i++) {
			int sum=0;
			
			// Flip coins
			for (int a=0; a<S.length; a++) {
				x = Math.random();
			
				if (x >= 0.5) sum++;
			}
			
			S[sum]++;
			
		}
		
		for (int i=0; i<numCoins; i++) {
			System.out.println("S="+ i + ": "+ S[i]);
		}
		
		Grafica Hist = new Grafica ("Flip a coin", "Sum", "Events");
		for (int i=0; i<S.length; i++) {
			Hist.inserta(i, S[i]);
		}

		Hist.ponLineas(false);
		Hist.pinta();
	}

}
