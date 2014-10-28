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
			//put the infonode into the arraylist 
			candidates.add(matrix.get(key));
		}
		/*sort it based on the signal strength*/
		candidates=sort(candidates, 0,candidates.size()-1);
		return true;
	}
	
	
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
//	boolean insert(nodeInfo ni,int head,int tail){
//		//put the entry into the arraylist in order
//		if(head==tail){
//			if(ni.ss>=candidates.get(head).ss) candidates.add(head,ni);
//			if(ni.ss<candidates.get(head).ss) candidates.add(head+1,ni);
//		}else{
//			int midIndex=(head+tail)/2;
//			nodeInfo mid=candidates.get(midIndex); //get the mid point
//			if(mid.ss==ni.ss) candidates.add(midIndex, ni);
//			if(ni.ss>mid.ss) return insert(ni,head,midIndex-1);
//			if(ni.ss<mid.ss) return insert(ni,midIndex+1,tail);
//		}
//		
//		return true;
//	}
	
	void candidatesPrint(){
		for(int i=0;i<candidates.size(); i++){
			nodeInfo ni=candidates.get(i);
			ni.print();
		}
	}
}
