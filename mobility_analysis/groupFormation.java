package mobility_analysis;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class groupFormation {
/*Alogrithm implemented here
 * 1. extract information from the hashmap to a sorted list (based on signal strenth)
 * 2. pick up GOs
 * 3. check possible GMs -- > get the statistisc of dirrect connection*/
	
//	HashMap<String,nodeInfo> matrix;
	ArrayList<nodeInfo> candidates;
	int totalNode=0;
	int requiredNode=0;
	double nrRatio=0.7;
	
	/*statistics */
	int GMs=0;
	int numOfGOs=0;
	
	groupFormation(double nrRatio){
		this.candidates = new ArrayList<nodeInfo>();
	}
	
	
	/*-------PART I: update the geo/signal information, saved for GO & GM sslection--------------------*/
	boolean candidatesUpdate(HashMap<String,nodeInfo> matrix){
		totalNode=0;
		GMs=0;
		candidates = new ArrayList<nodeInfo>();
		for(String key : matrix.keySet()){
			//put the infonode into the arraylist 
			candidates.add(matrix.get(key));
			totalNode++;
		}
		/*sort it based on the signal strength*/
		candidates=sort(candidates, 0,candidates.size()-1);
		return true;
	}
	
	


	/*-------PART I: update the geo/signal information, saved for GO & GM sslection--------------------*/
	

	/*-------PART II: Pick up GOs, GMs--------------------*/
	
	boolean formGroup(){
		this.requiredNode=(int) (totalNode*nrRatio);
		Set<Integer> GOs=new HashSet<Integer>();//record the index of GOs
		int i=0;
		int assumedTotal=totalNode;
		while(i<candidates.size() && assumedTotal>requiredNode){
			if(candidates.get(i).capacityActual>0){//check its utilization
				assumedTotal-=candidates.get(i).capacity;
				GOs.add(i); //record this candidate as the GO
			}
			i++;
		}
		numOfGOs=GOs.size();
		/*check whether non-GOs can join nearby GOs*/
		for(int j=0; j<candidates.size();j++){
			if(!GOs.contains(j)){
				nodeInfo cur=candidates.get(j);
				for(int k: GOs){
					nodeInfo GO=candidates.get(k);
					if(distance(cur,GO)<=GO.d2dRange && GO.capacityActual>0){
						GMs++;
						GO.capacityReduce();
						candidates.add(k,GO); //update the GO
					}
				}
			}
		}
		return true;
	}
	
	double distance(nodeInfo cur, nodeInfo GO){
		return Math.sqrt(Math.pow(cur.x-GO.x,2)+Math.pow(cur.y-GO.y,2));
	}
	
	/*-------PART II: Pick up GOs, GMs--------------------*/
	
	
	
	/*Merge sort*/
	ArrayList<nodeInfo> sort(ArrayList<nodeInfo> candidates, int head, int tail){
		ArrayList<nodeInfo> tmp= new ArrayList<nodeInfo>();
		if(head>tail){
		}else if(head==tail){
			tmp.add(candidates.get(head));
		}else{
			int mid=(head+tail)/2;
			ArrayList<nodeInfo> left=sort(candidates,head,mid);
			ArrayList<nodeInfo> right=sort(candidates,mid+1,tail);
			tmp=merge(left,right);
		}
		return tmp;
	}
	ArrayList<nodeInfo> merge(ArrayList<nodeInfo> left,ArrayList<nodeInfo> right){
		int i=0;
		int j=0;
		int l=left.size()+right.size();
		ArrayList<nodeInfo> tmp=new ArrayList<nodeInfo>(l);
		int k=0;
		while(k<l){
			if(left.size()==0 || i==left.size()){
				tmp.add(right.get(j));
				j++;
			}else if(right.size()==0 || j==right.size()){
				tmp.add(left.get(i));
				i++;
			}else{
				if(left.get(i).ss>=right.get(j).ss){
					tmp.add(left.get(i));
					i++;
				}else{
					tmp.add(right.get(j));
					j++;
				}
			}
			k++;
		}
		return tmp;
	}
	
	
	int numberOfGO(){
		return numOfGOs;
	}

	int numberOfGM(){
		return GMs;
	}
	
	/*print out the information of Go candidates*/
	void candidatesPrint(){
		System.out.println("candidates based on ss");
		for(int i=0;i<candidates.size(); i++){
			nodeInfo ni=candidates.get(i);
			ni.print();
		}
		System.out.println();
	}
	
	int currentConnection(){
		return totalNode-GMs;
	}
	
	int totalConnection(){
		return totalNode;
	}
	
	double actualRatio(){
		return ((double)(totalNode-GMs))/((double)totalNode);
	}
}
