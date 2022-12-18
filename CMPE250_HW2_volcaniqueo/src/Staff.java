

public class Staff implements Comparable<Staff>{
	
	private int id;
	
	public Staff(int id) {
		this.id = id;
	}
	
	public int compareTo(Staff other) {
		if (this.id < other.id)
			return -1;
		if (this.id > other.id)
			return 1;
		else
			return -1;
	}
	
	
	
	

}
