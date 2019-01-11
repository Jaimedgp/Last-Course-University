
package r2ms.flip.modW;

import r2ms.common.IFlipAcceptance;
import r2ms.common.InputData;

public class W implements IFlipAcceptance{
	
	WFlipAcceptance wObject;
	
	public W() {
		super();
	}
	
	public W(InputData input, int[][] L, int m) {
		wObject= new WFlipAcceptance(L, input.J, input.H, m);
	}
	  
	public void checkFlip(int xCoor, int yCoor) {
		wObject.checkFlip(xCoor, yCoor);
	}
	  
	public double getEnergy() {
		return wObject.getEnergy();
	}
	  	  
	public int getMagneticMoment() {
		return wObject.getMagneticMoment();
	}

	//XXX: G80: @Override  This annotation is removed for compatibility with the interface
	public boolean doIaccept(int xCoor, int yCoor) {
		// Auto-generated method stub
		return wObject.doIAccept(xCoor, yCoor);
	}

	@Override
	public IFlipAcceptance constructFlipEvaluator(InputData input, int[][] L) {  
	//XXX: G809 public IFlipAcceptance constructFlipEvaluator(InputData input, int[][] L, int m) {  
	//XXX: G80: There is no m in the interface
		// TODO Auto-generated method stub
		return new W(input,L, 0);
	}

	@Override
	public void resetEnergy() {
		// TODO Auto-generated method stub
		
	}
	
}
