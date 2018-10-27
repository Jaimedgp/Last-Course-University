package rainPI;

/**
 * a classic program to determine the pi number from the rain
 * 
 * this program determine the number Pi dividing the number of drops fell inside a circle of radious 1 
 * content in a square of side 1
 * 
 * @author jaimedgp
 *
 */

public class rainPI {
	
	static double x, y; // drops' coordinates
	
	static int totalDrops = 100000000; // number of total drops(events)
	static int inCircle = 0; // drops inside the circle

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// rain
		for (int i=0; i<totalDrops; i++) {
			//gt random numbers for drops' coordinates
			x = Math.random();
			y = Math.random();
			
			
			if (Math.pow(x, 2)+Math.pow(y, 2) <= 1) inCircle++;
		}
		
		double pi = 4.0*(double)inCircle/totalDrops;
		
		System.out.println(pi);
	}

}
