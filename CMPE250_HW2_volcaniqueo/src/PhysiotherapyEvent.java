

import java.util.PriorityQueue;

public class PhysiotherapyEvent extends Event implements Comparable<PhysiotherapyEvent> {
	
	public static double totalWaitingTime = 0.00;
	public static int totalPhysiotherapyEvents = 0;
	public static double totalPhysiotherapyTime = 0.00;
	public static int maxLengthQueue = 0;
	public static PriorityQueue<PhysiotherapyEvent> pEventsQueue = new PriorityQueue<PhysiotherapyEvent>();

	public PhysiotherapyEvent(Player associatedPlayer, double time, double duration, String type, Physiotherapist associatedStaff) {
		super(associatedPlayer, time, duration, type, associatedStaff);
	}
	
	public int compareTo(PhysiotherapyEvent other) { 
		if (Math.abs(this.associatedPlayer.getCurrentTrainingTime() - other.associatedPlayer.getCurrentTrainingTime()) < 0.0000000001) { // // Equality accepted when their difference's absolute value is smaller than 0.0000000001
			if (Math.abs(this.time - other.time) < 0.0000000001) { // // Equality accepted when their difference's absolute value is smaller than 0.0000000001
				if (this.associatedPlayer.getId() < other.associatedPlayer.getId())
					return -1;
				else if (this.associatedPlayer.getId() > other.associatedPlayer.getId())
					return 1;
				else
					return -1;
			}
			else if (this.time < other.time)
				return -1;
			else if (this.time > other.time)
				return 1;
			else
				return -1;
		}
		else if (this.associatedPlayer.getCurrentTrainingTime() > other.associatedPlayer.getCurrentTrainingTime())
			return -1;
		else if (this.associatedPlayer.getCurrentTrainingTime() < other.associatedPlayer.getCurrentTrainingTime())
			return 1;
		else
			return -1;
	}
}

