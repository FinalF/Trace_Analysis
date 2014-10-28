package mobility_analysis;

import java.io.IOException;
import java.util.HashMap;

public class test {

	
	/*1. test the rawdata read in and information updates*/
	public static void main(String args[]) throws IOException{
		/*test read in two files, two lines*/
		String dicPath="/home/tue86025/Dropbox/Fall2014_research/MobiHoc15/Code/JAVA/Trace_Analysis/testDataset";
		HashMap<String,nodeInfo> matrix = new HashMap<String,nodeInfo>();
		
		RawDataReadIn processRaw = new RawDataReadIn(dicPath, 1);
		processRaw.infoUpdate(matrix);
		for(String key:matrix.keySet()){
			System.out.println("Node: "+key);
			nodeInfo ni=matrix.get(key);
			ni.print();
		}
		
		System.out.println("\n---------------\n");
		
		RawDataReadIn processRaw2 = new RawDataReadIn(dicPath, 2);
		processRaw2.infoUpdate(matrix);
		for(String key:matrix.keySet()){
			System.out.println("Node: "+key);
			nodeInfo ni=matrix.get(key);
			ni.print();
		}
		
	}
}
