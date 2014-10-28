package general_stats;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String args[]){
			String RawDataDicPath="dataset/KAIST-92/";
			String MetaData="dataset/meta.txt";
			try {
				getMeta(RawDataDicPath,MetaData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	static boolean getMeta(String RawDataDicPath,String MetaData) throws IOException{
			File RawDataDic= new File(RawDataDicPath);
			RawData_Process test;
			for(File f:RawDataDic.listFiles()){
				test=new RawData_Process(f.getAbsolutePath(), MetaData);
				test.generateStat();
			}
			System.out.println("Finish scanning all users' data");
			test=new RawData_Process(MetaData);
			test.generateOveralStat();
			System.out.println("Finish Processing data");
			
			return true;
		}
}
