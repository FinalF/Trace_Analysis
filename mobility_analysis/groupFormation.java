package mobility_analysis;


import java.util.ArrayList;
import java.util.HashMap;


public class groupFormation {
/*Alogrithm implemented here
 * 1. extract information from the hashmap to a sorted list (based on signal strenth)
 * 2. pick up GOs
 * 3. check possible GMs -- > get the statistisc of dirrect connection*/
	
//	HashMap<String,nodeInfo> matrix;
	ArrayList<nodeInfo> candidates;
	
	groupFormation(){
		this.candidates = new ArrayList<nodeInfo>();
	}
	
	boolean candidatesUpdate(HashMap<String,nodeInfo> matrix){
		candidates = new ArrayList<nodeInfo>();
		for(String key : matrix.keySet()){
			//put the infonode into the arraylist in order (ascending)
			if(candidates.size()==0) candidates.add(matrix.get(key));
			else insert(matrix.get(key),0,candidates.size()-1);
		}
		return true;
	}
	
	boolean insert(nodeInfo ni,int head,int tail){
		//put the entry into the arraylist in order
		if(head==tail){
			if(ni.ss>=candidates.get(head).ss) candidates.add(head,ni);
			if(ni.ss<candidates.get(head).ss) candidates.add(head+1,ni);
		}else{
			int midIndex=(head+tail)/2;
			nodeInfo mid=candidates.get(midIndex); //get the mid point
			if(mid.ss==ni.ss) candidates.add(midIndex, ni);
			if(ni.ss>mid.ss) insert(ni,head,midIndex-1);
			if(ni.ss<mid.ss) insert(ni,midIndex+1,tail);
		}
		
		return true;
	}
	
	void candidatesPrint(){
		for(int i=0;i<candidates.size(); i++){
			nodeInfo ni=candidates.get(i);
			ni.print();
		}
	}
}
