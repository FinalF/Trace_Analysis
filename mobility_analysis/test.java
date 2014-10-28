package mobility_analysis;

import java.io.IOException;
import java.util.HashMap;

public class test {

	
	
	public static void main(String args[]) throws IOException{
		double nrRatio=0.8; 		//The requirement for the direct connections

		String dicPath="/home/tue86025/Dropbox/Fall2014_research/MobiHoc15/Code/JAVA/Trace_Analysis/testDataset";
		RawDataReadIn processRaw = new RawDataReadIn(dicPath, 1); //the raw data/coordination processing
		HashMap<String,nodeInfo> matrix = new HashMap<String,nodeInfo>(); //the matrix stores <nodename, information>	
		groupFormation GF = new groupFormation(nrRatio); //The groupFormation process
		
		
		/*--------------1. test the rawdata read in and information updates--------------*/
		/*test read in two files, two lines*/
//		
//		processRaw.infoUpdate(matrix);
//		for(String key:matrix.keySet()){
//			System.out.println("Node: "+key);
//			nodeInfo ni=matrix.get(key);
//			ni.print();
//		}
//		
//		System.out.println("\n---------------\n");
//		
//		processRaw.linesUpdate(2);
//		processRaw.infoUpdate(matrix);
//		for(String key:matrix.keySet()){
//			System.out.println("Node: "+key);
//			nodeInfo ni=matrix.get(key);
//			ni.print();
//		}		
		
		/*--------------1. test the rawdata read in and information updates--------------*/

		/*--------------2. The Go selection based on the signal strength--------------*/


		
		

		matrix=processRaw.infoUpdate(matrix);
		GF.candidatesUpdate(matrix);
		GF.candidatesPrint();
		System.out.println("\n---------------\n");
		GF.formGroup();
		System.out.println("Total connections: "+GF.totalConnection()+"\nCurrent connections: "+GF.currentConnection()
							+"\nRequired reduced ratio: "+nrRatio+"\n Actual reduced ratio: "+GF.actualRatio());
		System.out.println("---------------");
//		
//		processRaw.linesUpdate(2);
//		matrix=processRaw.infoUpdate(matrix);
//		GF.candidatesUpdate(matrix);
//		GF.candidatesPrint();		
		
		
		/*--------------2. The Go selection based on the signal strength--------------*/
		
	}
}
