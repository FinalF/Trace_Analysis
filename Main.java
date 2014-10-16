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
			for(File f:RawDataDic.listFiles()){
				RawData_Process test=new RawData_Process(f.getAbsolutePath(), MetaData);
				test.generateStat();
			}
			return true;
		}
}
