package general_stats;
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
	OutputStream stats_PerPerson;
	OutputStream stats_Overall;
	OutputStream stats_InitialLocation;
	String input_location;
	String output_location_perperson;
	String output_location_overall;
	String output_location_initiallocation;
	
	RawData_Process(String input_location, String output_location) throws FileNotFoundException{
		this.raw=new FileInputStream(input_location);
//		this.raw=new Scanner(new File(input_location));
		this.input_location=input_location;
		this.output_location_perperson=output_location+"_PerPerson";
		this.output_location_initiallocation=output_location+"_InitialLocation";
		this.stats_PerPerson=new FileOutputStream(output_location_perperson,true);
		this.stats_InitialLocation=new FileOutputStream(output_location_initiallocation,true);
	}
	
	RawData_Process(String output_location) throws FileNotFoundException{
		this.output_location_perperson=output_location+"_PerPerson";
		this.output_location_overall=output_location+"_Overall";
		this.stats_Overall=new FileOutputStream(output_location_overall,true);
	}
	

	/*calculate max/min stats per person*/

	boolean generateStat() throws IOException{
		/*stats: 0:t,1:x,2:y,3:xmin,4:xmax,5:ymin,6:ymax,
		 * 7:speed-x-min,8:speed-x-max,9:speed-y-min,10:speed-y-max,11:speed-min,12:speed-max*
		 * 13: time-min, 14:time-max*/
		double[] data=new double[15];
		boolean initialed=false;
		BufferedReader in=new BufferedReader(new InputStreamReader(raw));
		BufferedWriter out=new BufferedWriter(new OutputStreamWriter(stats_PerPerson));
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
				/*record the initial value*/
				generateInitial(t,x,y);
				
				
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
				data[14]=Math.max(data[14], t);
				
				data[0]=t;
				data[1]=x;
				data[2]=y;
			}

		}
		System.out.println(count+" entries read from "+input_location+" ,result recorded in "+output_location_perperson);
		for(int i=3;i<data.length;i++)
			out.write(data[i]+",");
		out.newLine();
		in.close();
		out.close();
		
		return true;
	}
	

	/*get the overall stats from above results*/
	boolean generateOveralStat() throws IOException{
		BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(output_location_perperson)));
		BufferedWriter out=new BufferedWriter(new OutputStreamWriter(stats_Overall));
		/*stats: 0:xmin,1:xmax,2:ymin,3:ymax,
		 * 4:speed-x-min,5:speed-x-max,6:speed-y-min,7:speed-y-max,8:speed-min,9:speed-max
		 * 10:time-min, 11:time-max
		 * row 1: average / row  2: max&min*/
		double[][] data=new double[2][12];
		String line;
		int count=0;
		while((line=in.readLine())!=null){
			count++;
			line=line.trim();
//			System.out.println(line);
			String[] lines = line.split(",");
			for(int i=0;i<data[0].length;i++){
				data[0][i]+=Double.parseDouble(lines[i]);
				if(i%2==0) data[1][i]=Math.min(data[1][i], Double.parseDouble(lines[i]));
				if(i%2!=0) data[1][i]=Math.max(data[1][i], Double.parseDouble(lines[i]));
			}
			
		}
		out.write(">>>>>>>>>>>>>>>>>>>>>>>>>\n");
		for(int i=0;i<data[0].length;i++)
			out.write(data[0][i]/count+",");
		out.newLine();
		for(int i=0;i<data[1].length;i++)
			out.write(data[1][i]+",");
		out.write("<<<<<<<<<<<<<<<<<<<<<<<<<\n");
		out.newLine();
		in.close();
		out.close();
		System.out.println(count+" entries of Meta data collected from "+output_location_perperson+" ,result recorded in "+output_location_overall);
		return true;
	}
	
	/*extract initial position*/
	boolean generateInitial(double t,double x, double y) throws IOException{
		BufferedWriter out=new BufferedWriter(new OutputStreamWriter(stats_InitialLocation));
			out.write(t+","+x+","+y);
		out.newLine();
		out.close();
		return true;
	}
	
	
	

	double distance(double time, double x, double y){
		return Math.sqrt(Math.pow(x,2)+Math.pow(y,2))/time;
	}
	
}
