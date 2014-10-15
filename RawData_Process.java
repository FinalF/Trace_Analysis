/*
 * generate stats we need:
 * States per person:
 * x-min
 * x-max
 * y-min
 * y-max
 * movement-speed-x-min
 * movement-speed-x-max
 * movement-speed-y-min
 * movement-speed-y-max
 * movement-speed-min: sqrt(x^2+y^2)
 * movement-speed-max: sqrt(x^2+y^2)
 */
import java.util.*;
import java.io.*;

public class RawData_Process {
	InputStream raw;
	OutputStream stats;
	
	RawData_Process(String input_location, String output_location) throws FileNotFoundException{
		this.raw=new FileInputStream(input_location);
		this.stats=new FileOutputStream(output_location);
	}
	
	boolean generateStat() throws IOException{
		double xmin=0;
		double xmax=0;
		double ymin=0;
		double ymax=0;
		BufferedReader in=new BufferedReader(new InputStreamReader(raw));
		BufferedWriter out=new BufferedWriter(new OutputStreamWriter(stats));
		String line;
		while((line=in.readLine())!=null){
			line=line.trim();
			System.out.println(line);
			String[] lines = line.split("  ");
			System.out.println(lines[0]);
			xmin=Double.parseDouble(lines[1].substring(0,10))*Math.pow(10,Integer.parseInt(lines[1].substring(lines[1].length()-4)));
		}
		out.write(String.valueOf(xmin));
		out.newLine();
		
		return true;
	}
	
}
