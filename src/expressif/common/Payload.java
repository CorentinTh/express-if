package expressif.common;

public class Payload {
	public enum Topic{LOGIN, JOIN_ROOM, LEAVE_ROOM};
	private Topic topic;
	private Object content;
	
	public Topic getTopic() {
		return topic;
	}	
	
	public Object getContent() {
		return content;
	}
}
