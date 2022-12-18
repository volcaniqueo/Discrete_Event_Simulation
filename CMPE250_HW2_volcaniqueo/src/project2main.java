

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class project2main {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		HashMap<Integer, Player> playersMap = new HashMap<Integer, Player>();
		PriorityQueue<Event> events = new PriorityQueue<Event>(new EventComparator());
		
		
		
		
		String myLine = in.nextLine();
		String[] myOrder = myLine.split(" ");
		for (int i = 0; i < Integer.parseInt(myOrder[0]); i++) {
			String myInnerLine = in.nextLine();
			String[] myInnerOrder = myInnerLine.split(" ");
			Player player = new Player(Integer.parseInt(myInnerOrder[0]), Integer.parseInt(myInnerOrder[1]));
			playersMap.put(player.getId(), player);
			}
		String myLine1 = in.nextLine();
		String[] myOrder1 = myLine1.split(" ");
		for (int i = 0; i < Integer.parseInt(myOrder1[0]); i++) {
			String myInnerLine = in.nextLine();
			String[] myInnerOrder = myInnerLine.split(" ");
			if (myInnerOrder[0].equals("m")) {
				MassageEvent mEvent = new MassageEvent(playersMap.get(Integer.parseInt(myInnerOrder[1])), Double.parseDouble(myInnerOrder[2]), Double.parseDouble(myInnerOrder[3]), "in", null);
				events.add(mEvent);
			}
			if (myInnerOrder[0].equals("t")) {
				TrainingEvent tEvent = new TrainingEvent(playersMap.get(Integer.parseInt(myInnerOrder[1])), Double.parseDouble(myInnerOrder[2]), Double.parseDouble(myInnerOrder[3]), "in", null);
				events.add(tEvent);
			}
		}
		String myLine2 = in.nextLine();
		String[] myOrder2 = myLine2.split(" ");
		for (int i = 1; i < Integer.parseInt(myOrder2[0]) + 1; i++) {
			Physiotherapist physiotherapist = new Physiotherapist(Physiotherapist.nofPhysiotherapists, Double.parseDouble(myOrder2[i]));
			Physiotherapist.physiotherapistQueue.add(physiotherapist);
		}
		String myLine3 = in.nextLine();
		String[] myOrder3 = myLine3.split(" ");
		for (int i = 0; i < Integer.parseInt(myOrder3[0]); i++) {
			TrainingCoach trainer = new TrainingCoach(TrainingCoach.nofTrainingCoaches);
			TrainingCoach.trainerQueue.add(trainer);
		}
		for (int i = 0; i < Integer.parseInt(myOrder3[1]); i++) {
			Masseur masseur = new Masseur(Masseur.nofMasseurs);
			Masseur.masseurQueue.add(masseur);
		}
		
		double currentTime = 0.00;
		
		while (!(events.isEmpty())) {
			Event myEvent = events.poll();
			currentTime = myEvent.time;
			if (myEvent.type.equals("in")) {
				if (myEvent.associatedPlayer.getNofMassagesTaken() == 3 && myEvent instanceof MassageEvent) {
					MassageEvent.nofInvalids++;
					continue;
				}
				if (Player.playersNotAvailable.containsKey(myEvent.associatedPlayer.getId())) {
					Player.nofCancelled++;
					continue;
				}
			}
			if (myEvent instanceof TrainingEvent) {
				TrainingEvent myTEvent = (TrainingEvent) myEvent;
				if (myTEvent.type.equals("in")) {
					myTEvent.associatedPlayer.trainingInTime = myTEvent.time;
					Player.playersNotAvailable.put(myTEvent.associatedPlayer.getId(), myTEvent.associatedPlayer);
					if (!(TrainingCoach.trainerQueue.isEmpty())) {
						TrainingCoach trainer = TrainingCoach.trainerQueue.poll();
						TrainingEvent.totalTrainingEvents++;
						TrainingEvent.totalTrainingTime += myTEvent.duration;
						myTEvent.associatedPlayer.setCurrentTrainingTime(myTEvent.duration);
						TrainingEvent outTrainingEvent = new TrainingEvent(myTEvent.associatedPlayer, myTEvent.time + myTEvent.duration, 0.00, "out", trainer);
						events.add(outTrainingEvent);
					}
					else {
						TrainingEvent.tEventsQueue.add(myTEvent);
						if (TrainingEvent.tEventsQueue.size() > TrainingEvent.maxLengthQueue)
							TrainingEvent.maxLengthQueue = TrainingEvent.tEventsQueue.size();
					}
					continue;
				}
				if (myTEvent.type.equals("out")) {
					Staff sTrainer = myTEvent.associatedStaff;
					TrainingCoach trainer = (TrainingCoach) sTrainer;
					if (!(Physiotherapist.physiotherapistQueue.isEmpty())) {
						Physiotherapist physiotherapist = Physiotherapist.physiotherapistQueue.poll();
						PhysiotherapyEvent.totalPhysiotherapyEvents++;
						PhysiotherapyEvent.totalPhysiotherapyTime += physiotherapist.getServiceTime();
						PhysiotherapyEvent outPhysiotherapyEvent = new PhysiotherapyEvent(myTEvent.associatedPlayer, myTEvent.time + physiotherapist.getServiceTime(), 0.00, "out", physiotherapist);
						events.add(outPhysiotherapyEvent);
					}
					else {
						PhysiotherapyEvent  inPhysiotherapyEvent = new PhysiotherapyEvent(myTEvent.associatedPlayer, myTEvent.time, 0.00, "in", null);
						PhysiotherapyEvent.pEventsQueue.add(inPhysiotherapyEvent);
						if (PhysiotherapyEvent.pEventsQueue.size() > PhysiotherapyEvent.maxLengthQueue)
							PhysiotherapyEvent.maxLengthQueue = PhysiotherapyEvent.pEventsQueue.size();
					}
					if (!(TrainingEvent.tEventsQueue.isEmpty())) {
						TrainingEvent myQueueTEvent = TrainingEvent.tEventsQueue.poll();
						double waitingTime = myTEvent.time - myQueueTEvent.time;
						TrainingEvent.totalWaitingTime += waitingTime;
						TrainingEvent.totalTrainingEvents++;
						TrainingEvent.totalTrainingTime +=  myQueueTEvent.duration;
						myQueueTEvent.associatedPlayer.setCurrentTrainingTime(myQueueTEvent.duration);
						TrainingEvent outTrainingEvent = new TrainingEvent(myQueueTEvent.associatedPlayer, myTEvent.time + myQueueTEvent.duration, 0.00, "out", trainer);
						events.add(outTrainingEvent);
					}
					else
						TrainingCoach.trainerQueue.add(trainer);
					continue;	
				}
			}
			if (myEvent instanceof PhysiotherapyEvent) {
				PhysiotherapyEvent myPEvent = (PhysiotherapyEvent) myEvent;
				if (myPEvent.type.equals("out")) {
					Staff pTherapist = myPEvent.associatedStaff;
					Physiotherapist physiotherapist = (Physiotherapist) pTherapist;
					Player.playersNotAvailable.remove(myPEvent.associatedPlayer.getId());
					myPEvent.associatedPlayer.setCurrentTrainingTime(0.00);
					double turnaroundTime = myPEvent.time - myPEvent.associatedPlayer.trainingInTime;
					Player.totalTurnaroundTime += turnaroundTime;
					if (!(PhysiotherapyEvent.pEventsQueue.isEmpty())) {
						PhysiotherapyEvent myQueuePEvent = PhysiotherapyEvent.pEventsQueue.poll();
						double waitingTime = myPEvent.time - myQueuePEvent.time;
						myQueuePEvent.associatedPlayer.timeSpentPQueue += waitingTime;
						PhysiotherapyEvent.totalWaitingTime += waitingTime;
						PhysiotherapyEvent.totalPhysiotherapyEvents++;
						PhysiotherapyEvent.totalPhysiotherapyTime += physiotherapist.getServiceTime();
						PhysiotherapyEvent outPhysiotherapyEvent = new PhysiotherapyEvent(myQueuePEvent.associatedPlayer, myPEvent.time + physiotherapist.getServiceTime(), 0.00, "out", physiotherapist);
						events.add(outPhysiotherapyEvent);
					}
					else
						Physiotherapist.physiotherapistQueue.add(physiotherapist);
					continue;
				}
			}
			if (myEvent instanceof MassageEvent) {
				MassageEvent myMEvent = (MassageEvent) myEvent;
				if (myMEvent.type.equals("in")) {
					myMEvent.associatedPlayer.nofMassagesTaken++;
					if (!(Masseur.masseurQueue.isEmpty())) {
						Masseur masseur = Masseur.masseurQueue.poll();
						MassageEvent.totalMassageEvents++;
						MassageEvent.totalMassageTime += myMEvent.duration;
						Player.playersNotAvailable.put(myMEvent.associatedPlayer.getId(), myMEvent.associatedPlayer);
						MassageEvent outMassageEvent = new MassageEvent(myMEvent.associatedPlayer, myMEvent.time + myMEvent.duration, 0.00, "out", masseur);
						events.add(outMassageEvent);
					}
					else {
						Player.playersNotAvailable.put(myMEvent.associatedPlayer.getId(), myMEvent.associatedPlayer);
						MassageEvent.mEventsQueue.add(myMEvent);
						if (MassageEvent.mEventsQueue.size() > MassageEvent.maxLengthQueue)
							MassageEvent.maxLengthQueue = MassageEvent.mEventsQueue.size();
					}
					continue;
				}
				if (myMEvent.type.equals("out")) {
					Staff mMasseur = myMEvent.associatedStaff;
					Masseur masseur = (Masseur) mMasseur;
					Player.playersNotAvailable.remove(myMEvent.associatedPlayer.getId());
					if (!(MassageEvent.mEventsQueue.isEmpty())) {
						MassageEvent myQueueMEvent = MassageEvent.mEventsQueue.poll();
						double waitingTime = myMEvent.time - myQueueMEvent.time;
						myQueueMEvent.associatedPlayer.timeSpentMQueue += waitingTime;
						MassageEvent.totalWaitingTime += waitingTime;
						MassageEvent.totalMassageEvents++;
						MassageEvent.totalMassageTime += myQueueMEvent.duration;
						MassageEvent outMassageEvent = new MassageEvent(myQueueMEvent.associatedPlayer, myMEvent.time + myQueueMEvent.duration, 0.00, "out", masseur);
						events.add(outMassageEvent);
					}
					else
						Masseur.masseurQueue.add(masseur);
					continue;
				}
			}
		}
		
		int maxTQ = TrainingEvent.maxLengthQueue;
		int maxPQ = PhysiotherapyEvent.maxLengthQueue;
		int maxMQ = MassageEvent.maxLengthQueue;
		
		double averageWaitingT = TrainingEvent.totalWaitingTime / TrainingEvent.totalTrainingEvents;
		double averageWaitingP = PhysiotherapyEvent.totalWaitingTime / PhysiotherapyEvent.totalPhysiotherapyEvents;
		double averageWaitingM = MassageEvent.totalWaitingTime / MassageEvent.totalMassageEvents;
		
		double averageT = TrainingEvent.totalTrainingTime / TrainingEvent.totalTrainingEvents;
		double averageP = PhysiotherapyEvent.totalPhysiotherapyTime / PhysiotherapyEvent.totalPhysiotherapyEvents;
		double averageM = MassageEvent.totalMassageTime / MassageEvent.totalMassageEvents;
		
		double averageTurnaround = Player.totalTurnaroundTime / TrainingEvent.totalTrainingEvents;
		
		double maxTimePQ = 0.00;
		int idMaxTimePQ = 0;
		for (Player myPlayer : playersMap.values()) {
			if (Math.abs(myPlayer.timeSpentPQueue - maxTimePQ) < 0.0000000001) {
				if (myPlayer.getId() < idMaxTimePQ)
					idMaxTimePQ = myPlayer.getId();
			}
			if (myPlayer.timeSpentPQueue > maxTimePQ) {
				maxTimePQ = myPlayer.timeSpentPQueue;
				idMaxTimePQ = myPlayer.getId();
			}
		}
		
		double minTimeMQ = Integer.MAX_VALUE;
		int idMinTimeMQ = 0;
		boolean myControl = true;
		for (Player myPlayer : playersMap.values()) {
			if (myPlayer.getNofMassagesTaken() == 3) {
				if (Math.abs(myPlayer.timeSpentMQueue - minTimeMQ) < 0.0000000001) {
					if (myPlayer.getId() < idMinTimeMQ)
						idMinTimeMQ = myPlayer.getId();
				}
				if (myPlayer.timeSpentMQueue < minTimeMQ) {
					minTimeMQ = myPlayer.timeSpentMQueue;
					idMinTimeMQ = myPlayer.getId();
				}
				myControl = false;
			}
		}
		if (myControl) {
			minTimeMQ = -1;
			idMinTimeMQ = -1;
		}
		
		int invalids = MassageEvent.nofInvalids;
		int cancelled = Player.nofCancelled;
		double time = currentTime;
		
		double rAverageWaitingT = Math.round(averageWaitingT * 1000.0) / 1000.0;
		String sAverageWaitingT = String.format("%.3f", rAverageWaitingT);
		double rAverageWaitingP = Math.round(averageWaitingP * 1000.0) / 1000.0;
		String sAverageWaitingP = String.format("%.3f", rAverageWaitingP);
		double rAverageWaitingM = Math.round(averageWaitingM * 1000.0) / 1000.0;
		String sAverageWaitingM = String.format("%.3f", rAverageWaitingM);
		double rAverageT = Math.round(averageT * 1000.0) / 1000.0;
		String sAverageT = String.format("%.3f", rAverageT);
		double rAverageP = Math.round(averageP * 1000.0) / 1000.0;
		String sAverageP = String.format("%.3f", rAverageP);
		double rAverageM = Math.round(averageM * 1000.0) / 1000.0;
		String sAverageM = String.format("%.3f", rAverageM);
		double rAverageTurnaround = Math.round(averageTurnaround * 1000.0) / 1000.0;
		String sAverageTurnaround = String.format("%.3f", rAverageTurnaround);
		double rMaxTimePQ = Math.round(maxTimePQ * 1000.0) / 1000.0;
		String sMaxTimePQ = String.format("%.3f", rMaxTimePQ);
		
		String sMinTimeMQ = null;
		if (!(Math.abs(minTimeMQ + 1) < 0.0000000001)) {
			double rMinTimeMQ = Math.round(minTimeMQ * 1000.0) / 1000.0;
			sMinTimeMQ = String.format("%.3f", rMinTimeMQ);
		}
		
		double rTime = Math.round(time * 1000.0) / 1000.0;
		String sTime = String.format("%.3f", rTime);
		
		out.println(maxTQ);
		out.println(maxPQ);
		out.println(maxMQ);
		out.println(sAverageWaitingT);
		out.println(sAverageWaitingP);
		out.println(sAverageWaitingM);
		out.println(sAverageT);
		out.println(sAverageP);
		out.println(sAverageM);
		out.println(sAverageTurnaround);
		out.print(idMaxTimePQ);
		out.println(" " + sMaxTimePQ);
		
		if (Math.abs(minTimeMQ + 1) < 0.0000000001)
			out.println("-1" + " " + "-1");
		else {
			out.print(idMinTimeMQ);
			out.println(" " + sMinTimeMQ);
		}
		
		out.println(invalids);
		out.println(cancelled);
		out.println(sTime);
		
		
			
	}
		
		

}
