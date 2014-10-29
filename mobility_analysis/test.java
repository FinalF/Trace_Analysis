package mobility_analysis;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class test {

	
	
	public static void main(String args[]) throws IOException{
		double nrRatio=0.8; 		//The requirement for the direct connections

		String dicPath="/home/tue86025/Dropbox/Fall2014_research/MobiHoc15/Code/JAVA/Trace_Analysis/testDataset";
		String resultRecordPath="/home/tue86025/Dropbox/Fall2014_research/MobiHoc15/Code/JAVA/Trace_Analysis/testRecord.txt";
		
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
//			ni.print();;
//		}		
		
		/*--------------1. test the rawdata read in and information updates--------------*/

		/*--------------2. The Go selection based on the signal strength--------------*/


//		matrix=processRaw.infoUpdate(matrix);
//		GF.candidatesUpdate(matrix);
//		GF.candidatesPrint();
//		System.out.println("\n---------------\n");
//		GF.formGroup();
//		System.out.println("Total connections: "+GF.totalConnection()+"\nCurrent connections: "+GF.currentConnection()
//							+"\nRequired reduced ratio: "+nrRatio+"\n Actual reduced ratio: "+GF.actualRatio());
//		System.out.println("---------------");
		
		
		/*--------------2. The Go selection based on the signal strength--------------*/
		
		/*--------------3. Production test: read in data in n time stamps, output the stats--------------*/
		int timeDuration=10;
		double[][] result = new double[timeDuration][4]; //total connection, actual connection, requiredRatio, actualRatio
		for(int i=0;i<timeDuration;i++){
			processRaw.linesUpdate(i+1);
			matrix=processRaw.infoUpdate(matrix);
			GF.candidatesUpdate(matrix);
			GF.formGroup();
			/*record the result*/
			result[i][0]=GF.totalConnection();
			result[i][1]=GF.currentConnection();
			result[i][2]=GF.nrRatio;
			result[i][3]=GF.actualRatio();
		}
		record(resultRecordPath,result);
		
		/*--------------3. Production test: read in data in n time stamps, output the stats--------------*/
		
		
	}
	
	public static void record(String resultRecordPath, double[][] result) throws IOException{
		OutputStream outF= new FileOutputStream(resultRecordPath,true);
		BufferedWriter out=new BufferedWriter(new OutputStreamWriter(outF));
		out.write("Result Record: \n");
		out.newLine();
		for(int i=0;i<result.length;i++){
			for(int j=0;j<result[i].length;j++){
				out.write(String.valueOf(result[i][j]));
				out.write(" ");
			}
			out.newLine();
		}
		out.write("\n"+result.length+" records have been saved\n");
		out.newLine();
		out.close();
		System.out.println(result.length+" entries of records are written into: "+resultRecordPath);
	}
}
