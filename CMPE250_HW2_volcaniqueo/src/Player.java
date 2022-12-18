

import java.util.HashMap;

public class Player {
	
	protected static HashMap<Integer, Player> playersNotAvailable = new HashMap<Integer, Player>();
	protected static int nofCancelled = 0;
	protected static double totalTurnaroundTime = 0.00;
	private int id;
	private int sLevel;
	protected int nofMassagesTaken;
	private double totalTrainingTime;
	private double currentTrainingTime;
	protected double timeSpentTurnaround;
	protected double timeSpentPQueue;
	protected double timeSpentMQueue;
	protected double trainingInTime;
	
	
	public Player(int id, int sLevel) {
		
		this.id = id;
		this.sLevel = sLevel;
		this.nofMassagesTaken = 0;
		this.totalTrainingTime = 0.00;
		this.currentTrainingTime = 0.00;
		timeSpentTurnaround = 0.00;
		timeSpentPQueue = 0.00;
		timeSpentMQueue = 0.00;
		trainingInTime = 0.00;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getsLevel() {
		return sLevel;
	}


	public void setsLevel(int sLevel) {
		this.sLevel = sLevel;
	}


	public int getNofMassagesTaken() {
		return nofMassagesTaken;
	}


	public void setNofMassagesTaken(int nofMassagesTaken) {
		this.nofMassagesTaken = nofMassagesTaken;
	}


	public double getTotalTrainingTime() {
		return totalTrainingTime;
	}


	public void setTotalTrainingTime(double totalTrainingTime) {
		this.totalTrainingTime = totalTrainingTime;
	}


	public double getCurrentTrainingTime() {
		return currentTrainingTime;
	}


	public void setCurrentTrainingTime(double currentTrainingTime) {
		this.currentTrainingTime = currentTrainingTime;
	}


	
	
	
	
	

}
