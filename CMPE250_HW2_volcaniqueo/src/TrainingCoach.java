

import java.util.PriorityQueue;

public class TrainingCoach extends Staff {
	
	public static PriorityQueue<TrainingCoach> trainerQueue = new PriorityQueue<TrainingCoach>();
	public static int nofTrainingCoaches;

	public TrainingCoach(int id) {
		super(id);
		nofTrainingCoaches++;
	}
	
	
	

}
