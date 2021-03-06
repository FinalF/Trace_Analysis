package mobility_analysis;

public class nodeInfo {

	/*Yufeng Fall2014*/
	/*define the necessary info needs for each node
	 * 0. coordination
	 * 1. signal strength
	 * 2. supported devices capacity in theory 
	 * 3. actual capacity
	 * 4. common interests shared with the GO
	 * 5. D2D communication distance*/
	double t=0; //time stamp
	double x=0;
	double y=0;
	double ss=0;
	int capacity=0;
	double capacityMax=8;
	int capacityActual=0;
	double commonI=0;
	double ssPara=0;
	double distanceSqua=0;
	double d2dRange=50;
	
	nodeInfo(){
		
	}
	nodeInfo(double x, double y){
		this.x=x;
		this.y=y;
		this.distanceSqua=Math.pow(x,2)+Math.pow(y, 2);
		this.ssPara=Math.random()*distanceSqua+1; //random in [1,d^2]
		this.ss=ssPara/distanceSqua;
		this.capacity= (int) (Math.random()*capacityMax+1);
		this.capacityActual=(int) (Math.random()*this.capacity);
//		this.capacityActual=capacity;
		this.commonI=Math.random();
	}
	
	nodeInfo(double t,double x, double y, double capacityMax, double d2dRange){
		this.t=t;
		this.x=x;
		this.y=y;
		this.distanceSqua=Math.pow(x,2)+Math.pow(y, 2);
		this.ssPara=Math.random()*distanceSqua+1; //random in [1,d^2]
		this.ss=ssPara/distanceSqua;
		this.capacityMax=capacityMax;
		this.d2dRange=d2dRange;
		this.capacity= (int) (Math.random()*capacityMax+1);
		this.capacityActual=(int) (Math.random()*this.capacity);
//		this.capacityActual=capacity;
		this.commonI=Math.random();
	}
	
	
	
	void updateXY(double t, double x, double y){
		//only update the coordinates, so that the GOs won't change
		this.t=t;
		this.x=x;
		this.y=y;
		this.distanceSqua=Math.pow(x,2)+Math.pow(y, 2);
//		this.capacity= (int) Math.random()*6;
		this.capacityActual=(int) Math.ceil((Math.random()*this.capacity));
//		this.capacityActual=capacity;
		this.commonI=Math.random();		
	}
	
	
	void update(double t, double x, double y){
		this.t=t;
		this.x=x;
		this.y=y;
		this.distanceSqua=Math.pow(x,2)+Math.pow(y, 2);
		this.ssPara=Math.random()*distanceSqua+1; //random in [1,d^2]
		this.ss=ssPara/distanceSqua;
//		this.capacity= (int) Math.random()*6;
		this.capacityActual=(int) Math.ceil((Math.random()*this.capacity));
//		this.capacityActual=capacity;
		this.commonI=Math.random();
	}
	
	void capacityReduce(){
		this.capacityActual--;
	}
	
	int getMaxCapacity(){
		return (int) capacityMax;
	}
	
	int getd2dRange(){
		return (int) d2dRange;
	}
	
	 void print(){
		 System.out.println("x " +x+" y: "+y);
		 System.out.println("signal strength: "+ss+" capacity: "+capacity+" actual capacity: "+capacityActual);
	 }
	
}
