

import java.util.PriorityQueue;

public class TrainingEvent extends Event implements Comparable<TrainingEvent> {
	
	public static double totalWaitingTime = 0.00;
	public static int totalTrainingEvents = 0;
	public static double totalTrainingTime = 0.00;
	public static int maxLengthQueue = 0;
	public static PriorityQueue<TrainingEvent> tEventsQueue = new PriorityQueue<TrainingEvent>();

	public TrainingEvent(Player associatedPlayer, double time, double duration, String type, Staff associatedStaff) {
		super(associatedPlayer, time, duration, type, associatedStaff);
	}
	
	public int compareTo(TrainingEvent other) {
		if (Math.abs(this.time - other.time) < 0.0000000001) { // Equality accepted when their difference's absolute value is smaller than 0.0000000001
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
	
	
	

}
