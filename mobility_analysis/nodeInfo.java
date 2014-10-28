package mobility_analysis;

public class nodeInfo {

	/*Yufeng Fall2014*/
	/*define the necessary info needs for each node
	 * 1. signal strength
	 * 2. supported devices capacity in theory 
	 * 3. actual capacity
	 * 4. common interests shared with the GO*/
	double ss=0;
	int capacity=0;
	int capacityActual=0;
	double commonI=0;
	double ssPara=0;
	double distanceSqua=0;
	nodeInfo(){
		
	}
	nodeInfo(double x, double y){
		this.distanceSqua=Math.pow(x,2)+Math.pow(y, 2);
		this.ssPara=Math.random()*distanceSqua+1; //random in [1,d^2]
		this.ss=ssPara/distanceSqua;
		this.capacity= (int) Math.random()*6;
		this.capacityActual=(int) Math.random()*this.capacity;
		this.commonI=Math.random();
	}
	
	void update(double x, double y){
		this.distanceSqua=Math.pow(x,2)+Math.pow(y, 2);
		this.ssPara=Math.random()*distanceSqua+1; //random in [1,d^2]
		this.ss=ssPara/distanceSqua;
//		this.capacity= (int) Math.random()*6;
		this.capacityActual=(int) Math.random()*this.capacity;
		this.commonI=Math.random();
	}
	
}
