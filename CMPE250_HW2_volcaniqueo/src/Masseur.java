

import java.util.PriorityQueue;

public class Masseur extends Staff {
	
	public static PriorityQueue<Masseur> masseurQueue = new PriorityQueue<Masseur>();
	public static int nofMasseurs;

	public Masseur(int id) {
		super(id);
		nofMasseurs++;
	}

	
}
