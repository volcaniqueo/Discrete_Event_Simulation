

import java.util.PriorityQueue;

public class MassageEvent extends Event implements Comparable<MassageEvent> {
	
	public static PriorityQueue<MassageEvent> mEventsQueue = new PriorityQueue<MassageEvent>();
	public static int nofInvalids = 0;
	public static double totalWaitingTime = 0.00;
	public static int totalMassageEvents = 0;
	public static double totalMassageTime = 0.00;
	public static int maxLengthQueue = 0;

	public MassageEvent(Player associatedPlayer, double time, double duration, String type, Masseur associatedStaff) {
		super(associatedPlayer, time, duration, type, associatedStaff);
	}
	
	public int compareTo(MassageEvent other) {
		if (this.associatedPlayer.getsLevel() > other.associatedPlayer.getsLevel())
			return -1;
		else if (this.associatedPlayer.getsLevel() < other.associatedPlayer.getsLevel())
			return 1;
		else {
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
}
