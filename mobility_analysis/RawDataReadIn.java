package mobility_analysis;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStreamReader;
import java.util.HashMap;

public class RawDataReadIn {

	/*read in the coordination of all the files
	 * update the infomatrix
	 * hashmap<nodename, nodeInfo>*/
	File FileRawDataDic;
	File ResultRecord;
	int lines=0; //define which line to read
	
	double d2dRange=50;
	double capacityMax=5;
	boolean getStat=false;
	
	RawDataReadIn(String RawDataPath, int lines){
		this.FileRawDataDic=new File(RawDataPath);
		this.lines=lines;
	}

	RawDataReadIn(String RawDataPath){
		this.FileRawDataDic=new File(RawDataPath);
	}

	RawDataReadIn(String RawDataPath, double capacityMax, double d2dRange){
		this.FileRawDataDic=new File(RawDataPath);
		this.capacityMax=capacityMax;
		this.d2dRange=d2dRange;
	}	
	
	RawDataReadIn(String RawDataPath, String ResultRecordFile, int lines){
		this.FileRawDataDic=new File(RawDataPath);
		this.ResultRecord=new File(ResultRecordFile);
		this.lines=lines;
	}
	
	
	boolean linesUpdate(int lines){
		this.lines=lines;
		return true;
	}
	
	HashMap<String,nodeInfo> infoUpdate(HashMap<String,nodeInfo> matrix) throws IOException{
		/*update the information matrix by current coordinations*/
		BufferedReader in;
		for(File f: FileRawDataDic.listFiles()){
			String nodename= f.getName();
			in=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String line=null;
			int count=0;
			while((line=in.readLine())!=null){
				count++;
				if(count==lines) break;
			}
			if(count<lines){
				matrix.remove(nodename); //this node has left the cellular network
			}else{
				/*obtain the coordinates*/
				line=line.trim();
				String[] coordinates = line.split(" ");
				int l=coordinates.length-1;
				double[] tmp=new double[3];
				int i=2;
				while(l>=0){
					if(!coordinates[l].isEmpty()){
						tmp[i]=Double.parseDouble(coordinates[l]);
						i--;
					}
					l--;
				}
				double t = tmp[0];
				double x = tmp[1];
				double y =tmp[2];
				/*update the info matrix*/
				if(matrix.containsKey(nodename)){
					//already exists, update
					nodeInfo ni=matrix.get(nodename);
					ni.update(x, y);
					matrix.put(nodename, ni);
//					if(getStat==false){
//						capacityMax=ni.getMaxCapacity();
//						d2dRange=ni.getd2dRange();
//						getStat=true;
//					}
				}else{
					//not exists, create
					nodeInfo ni=new nodeInfo(x,y,capacityMax,d2dRange);
					matrix.put(nodename, ni);					
				}
			}
			in.close();			
		}

		return matrix;
	}
	
	int getMaxCapacity(){
		return (int) capacityMax;
	}
	
	int getd2dRange(){
		return (int) d2dRange;
	}
	
}



