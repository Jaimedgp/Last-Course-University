package r2ms.inputData.modLv;
import r2ms.simIsing.modHe.*;
import r2ms.simIsing.modNa.*;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import r2ms.common.*;
import r2ms.results.modRe.*;
import r2ms.flip.modOg.*;
import r2ms.inputData.modLv.Lv;

import fundamentos.Grafica;

public class Main {
	
	static int epoch = 10;
	static double[] T = new double[epoch];
	
	public static Result eachTemperature(String fileName, int index) {
		Lv input = new Lv();
		int numline = input.read(fileName);
		if (numline != 0) {
			System.out.println("Error in file "+ fileName+ " on line "+numline);
			return null;
		}
		T[index] = input.temperature;
		input.write("docs/outputs/output_"+index+".txt");
		
		IR2MS sim=new He();// create the main object
		Re ires=new Re();// create the results object
		Result res=sim.run(input,ires);// run the simulation
		return res;
	}
	
	public static void printGraph(Result[] res) {
		
		Grafica plotMag = new Grafica("M", "Temp", "Mag");
		
		
		for (int i=0; i<res.length; i++){
			plotMag.inserta(T[i], res[i].m);
		}
		
		plotMag.ponColor(1);
		plotMag.pinta();
		
		Grafica plotX = new Grafica("X", "Temp", "X");
		
		for (int i=0; i<res.length; i++){
			plotX.inserta(T[i], res[i].x);
		}
		plotX.ponColor(3);
		plotX.pinta();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Result[] listResults = new Result[10];
		int i;
		for (i=0; i<epoch; i++) {
			String nameFile = "docs/inputs/input_"+i+".txt";
			listResults[i] = eachTemperature(nameFile, i);
			if (listResults[i] == null) {
				break;
			}
		}
		
		if (i == epoch) {
			for (int j=0; j<10; j++) {
				System.out.println("\n"+j+"\t"+listResults[j].m+"\t"+listResults[j].x);// print the result
			}
			
			printGraph(listResults);
		}
	}

}
