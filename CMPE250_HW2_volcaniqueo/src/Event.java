
public class Event {
	
	protected double time;
	protected double duration;
	protected Player associatedPlayer;
	protected String type; // The type is 'in' our 'out'.
	protected Staff associatedStaff;
	
	public Event(Player associatedPlayer, double time, double duration, String type, Staff associatedStaff) {
		this.associatedPlayer = associatedPlayer;
		this.time = time;
		this.duration = duration;
		this.type = type;
		this.associatedStaff = associatedStaff;
	}
	
	
	
}
