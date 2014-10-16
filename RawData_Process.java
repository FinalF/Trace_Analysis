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
//	Scanner raw;
	OutputStream stats;
	
	RawData_Process(String input_location, String output_location) throws FileNotFoundException{
		this.raw=new FileInputStream(input_location);
//		this.raw=new Scanner(new File(input_location));
		this.stats=new FileOutputStream(output_location);
	}
	
	boolean generateStat() throws IOException{
		double t=0; //timestamp of last record
		double x=0; //x of last record
		double y=0; //y of last record
		double xmin=0;
		double xmax=0;
		double ymin=0;
		double ymax=0;
		boolean initialed=false;
		BufferedReader in=new BufferedReader(new InputStreamReader(raw));
		BufferedWriter out=new BufferedWriter(new OutputStreamWriter(stats));
		String line;
		while((line=in.readLine())!=null){
//			line=raw.nextLine();
			line=line.trim();
//			System.out.println(line);
			String[] lines = line.split(" ");
			if(initialed==false){
				/*initial values*/
				t=Double.parseDouble(lines[0]);
				x=Double.parseDouble(lines[1]);
				y=Double.parseDouble(lines[2]);
				xmin=Double.parseDouble(lines[1]);
				ymin=Double.parseDouble(lines[2]);
				xmax=Double.parseDouble(lines[1]);
				ymax=Double.parseDouble(lines[2]);
				initialed=true;
			}else{
				xmin=Math.min(xmin, Double.parseDouble(lines[1]));
				xmax=Math.max(xmax, Double.parseDouble(lines[1]));
				ymin=Math.min(ymin, Double.parseDouble(lines[2]));
				ymax=Math.max(ymax, Double.parseDouble(lines[2]));
			}

		}
		out.write(String.valueOf(xmin));
		out.newLine();
		in.close();
		out.close();
		
		return true;
	}
	
	int distance(double time, double x, double y){
		double dis;
		return dis;
	}
	
}
