package r2ms.results.modRe;

import r2ms.common.*;
import java.util.Vector;

import r2ms.common.InputData;

public class TestRe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] J=new double[3];
		J[0]=1.5;
		J[1]=3.5;
		J[2]=2.5;
		InputData in=new InputData(3, 300.0, 0, 5, J, 10, 50, 5);
		IResults resultado=new Re();
		resultado.saveResult(in, 2.5, 0.5);
		resultado.saveResult(in, 3.5, 01.5);
		resultado.saveResult(in, 24.5, 03.5);
		resultado.saveResult(in, 22.5, 04.5);
        resultado.plotMvsL(300, 0);
        resultado.plotMvsT(0, 3);
	}

}
