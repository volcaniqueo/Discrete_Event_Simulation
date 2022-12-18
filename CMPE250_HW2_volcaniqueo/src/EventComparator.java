

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
	
	
	/*
	 * For the event prioritization;
	 * First checks the time, if equal;
	 * Lower the id is prioritized.
	 */
	public int compare(Event first, Event second) {
		if (Math.abs(first.time - second.time) < 0.0000000001) { // Equality accepted when their difference's absolute value is smaller than 0.0000000001
			if (first.associatedPlayer.getId() < second.associatedPlayer.getId())
				return -1;
			else if (first.associatedPlayer.getId() > second.associatedPlayer.getId())
				return 1;
			else
				return -1;
		}
		else if (first.time < second.time)
			return -1;
		else if (first.time > second.time)
			return 1;
		else
			return -1;
	}

}
