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
	int lines=0; //define which line to read
	
	RawDataReadIn(String RawDataPath, int lines){
		this.FileRawDataDic=new File(RawDataPath);
		this.lines=lines;
	}
	
	boolean linesUpdate(int lines){
		this.lines=lines;
		return true;
	}
	
	boolean infoUpdate(HashMap<String,nodeInfo> matrix) throws IOException{
		/*update the information matrix by current coordinations*/
		for(File f: FileRawDataDic.listFiles()){
			String nodename= f.getName();
			BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
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
				}else{
					//not exists, create
					nodeInfo ni=new nodeInfo(x,y);
					matrix.put(nodename, ni);					
				}
			}
			
		}
		return true;
	}
	
}



