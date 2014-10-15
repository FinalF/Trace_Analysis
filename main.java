import java.io.FileNotFoundException;
import java.io.IOException;


public class main {

		public static void main(String args[]) throws IOException{
			RawData_Process test=new RawData_Process("KAIST_30sec_001.txt","record.txt");
			test.generateStat();
		}
}
