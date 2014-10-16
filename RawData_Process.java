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
	String input_location;
	String output_location;
	
	RawData_Process(String input_location, String output_location) throws FileNotFoundException{
		this.raw=new FileInputStream(input_location);
//		this.raw=new Scanner(new File(input_location));
		this.stats=new FileOutputStream(output_location);
		this.input_location=input_location;
		this.output_location=output_location;
	}
	
	boolean generateStat() throws IOException{
		/*stats: 0:t,1:x,2:y,3:xmin,4:xmax,5:ymin,6:ymax,
		 * 7:speed-x-min,8:speed-x-max,9:speed-y-min,10:speed-y-max,11:speed-min,12:speed-max*/
		double[] data=new double[13];
		boolean initialed=false;
		BufferedReader in=new BufferedReader(new InputStreamReader(raw));
		BufferedWriter out=new BufferedWriter(new OutputStreamWriter(stats));
		String line;
		int count=0;
		while((line=in.readLine())!=null){
			count++;
//			line=raw.nextLine();
			line=line.trim();
//			System.out.println(line);
			String[] lines = line.split(" ");
			int l=lines.length-1;
//			System.out.println("Length: "+lines.length);
			double[] tmp=new double[3];
			int i=2;
			while(l>=0){
				if(!lines[l].isEmpty()){
					tmp[i]=Double.parseDouble(lines[l]);
					i--;
				}
				l--;
			}
			double t = tmp[0];
			double x = tmp[1];
			double y =tmp[2];
//			System.out.println(t+" : "+x+" : "+y);
			if(initialed==false){
				/*initial values*/
				data[0]=t;
				data[1]=x;
				data[2]=y;
				
				data[3]=x;
				data[4]=x;
				data[5]=y;
				data[6]=y;
				data[7]=Math.abs(x);
				data[9]=Math.abs(y);
				data[11]=distance(1,x,y);
				initialed=true;
			}else{
				data[3]=Math.min(data[3], x);
				data[4]=Math.max(data[4], x);
				data[5]=Math.min(data[5], y);
				data[6]=Math.max(data[6], y);
				data[7]=Math.min(data[7], Math.abs(x-data[1]));
				data[8]=Math.max(data[8], Math.abs(x-data[1]));
				data[9]=Math.min(data[9], Math.abs(y-data[2]));
				data[10]=Math.max(data[10], Math.abs(y-data[2]));
				data[11]=Math.min(data[11],distance(t-data[0],x-data[1],y-data[2]));
				data[12]=Math.max(data[11],distance(t-data[0],x-data[1],y-data[2]));
				
				data[0]=t;
				data[1]=x;
				data[2]=y;
			}

		}
		System.out.println(count+" entries read from "+input_location+" ,result recorded in "+output_location);
		for(int i=3;i<data.length;i++)
			out.write(data[i]+",");
		out.newLine();
		in.close();
		out.close();
		
		return true;
	}
	
	double distance(double time, double x, double y){
		return Math.sqrt(Math.pow(x,2)+Math.pow(y,2))/time;
	}
	
}
