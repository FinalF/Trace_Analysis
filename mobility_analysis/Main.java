package mobility_analysis;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class Main {
	
	public static void main(String args[]) throws IOException{
		double nrRatio=0.8; 		//The requirement for the direct connections

		/*read in parameters: # of repeat, time stamps, capacityMax, d2drange, epoch*/
		int repeat=1;
		int timeDuration=1;
		double capacityMax=4;
		double d2drange=50;
		double epoch=30; //the time duration in which update GOs
		
		
		if(args.length==4){
			repeat=Integer.parseInt(args[0]);
			timeDuration=Integer.parseInt(args[1]);
			capacityMax=Double.parseDouble(args[2]);
			d2drange=Double.parseDouble(args[3]);
			epoch=Double.parseDouble(args[4]);
		}else{
			System.err.println(args.length+" Wrong parameters input");
			System.exit(1);
		}
		
		
		System.out.println("Repeat: "+repeat+" | TimeDuration: "+timeDuration+" | capacityMax: "+capacityMax+" | d2dRange: "+d2drange+" | epoch: "+epoch);
		/*Finish getting the parameters*/
		
		String dicPath="/home/tue86025/Dropbox/Fall2014_research/MobiHoc15/Code/JAVA/Trace_Analysis/testDataset";
		String resultRecordPath="/home/tue86025/Dropbox/Fall2014_research/MobiHoc15/Code/JAVA/Trace_Analysis/testRecord/testLog";
		
		RawDataReadIn processRaw = new RawDataReadIn(dicPath,capacityMax,d2drange,epoch); //the raw data/coordination processing
		HashMap<String,nodeInfo> matrix = new HashMap<String,nodeInfo>(); //the matrix stores <nodename, information>	
//		groupFormation GF = new groupFormation(nrRatio); //The groupFormation process
		groupFormation GF = new groupFormation();
		double[][] result = new double[timeDuration][6]; //total connection, actual connection, requiredRatio, actualRatio ,num of GOs, num of GMs
		for(int count=0; count<repeat; count++){
			for(int i=0;i<timeDuration;i++){
				processRaw.linesUpdate(i+1);
				matrix=processRaw.infoUpdate(matrix);
				GF.candidatesUpdate(matrix);
				GF.formGroup();
				/*record the result*/	
				result[i][0]+=GF.totalConnection();
				result[i][1]+=GF.currentConnection();
				result[i][2]+=GF.nrRatio;
				result[i][3]+=GF.actualRatio();
				result[i][4]+=GF.numberOfGO();
				result[i][5]+=GF.numberOfGM();
			}
		}

		/*get the average stats*/
		/*Output the result*/
//		System.out.println("total connection | actual connection | requiredRatio | actualRatio | num of GOs | num of GMs" );	
		for(int i=0;i<timeDuration;i++){
			for(int j=0;j<result[0].length;j++)
				result[i][j]/=repeat;
//			System.out.println(result[i][0]+" "+result[i][1]+" "+result[i][2]+" "+result[i][3]+" "+result[i][4]+" "+result[i][5]);
		}
		record(resultRecordPath,result,capacityMax,d2drange,epoch);	
	}
	
	public static void record(String resultRecordPath, double[][] result, double capacityMax, double d2dRange,double epoch) throws IOException{
//		OutputStream outF= new FileOutputStream(resultRecordPath+result.length+".txt",true); //append
		String filename=resultRecordPath+result.length+"_maxC"+(int)capacityMax+"_d2dR"+(int)d2dRange+"_epoch"+(int)epoch+".txt";
		OutputStream outF= new FileOutputStream(filename); //overwrite
		BufferedWriter out=new BufferedWriter(new OutputStreamWriter(outF));
		out.write("total connection | actual connection | requiredRatio | actualRatio | num of GOs | num of GMs \n");
		for(int i=0;i<result.length;i++){
			for(int j=0;j<result[i].length;j++){
				out.write(String.valueOf(result[i][j]));
				out.write(" ");
			}
			out.newLine();
		}
		out.close();
		System.out.println(result.length+" entries of records are written into: "+filename);
	}
	
	
}
