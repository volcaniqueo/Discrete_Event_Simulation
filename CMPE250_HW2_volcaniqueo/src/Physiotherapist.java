

import java.util.PriorityQueue;

public class Physiotherapist extends Staff {
	
	public static PriorityQueue<Physiotherapist> physiotherapistQueue = new PriorityQueue<Physiotherapist>();
	public static int nofPhysiotherapists;
	private double serviceTime;

	public Physiotherapist(int id, double serviceTime) {
		super(id);
		this.serviceTime = serviceTime;
		nofPhysiotherapists++;
	}
	
	
	public double getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(double serviceTime) {
		this.serviceTime = serviceTime;
	}
	
	
	
	
	
	
	

}
