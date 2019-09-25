package expressif.common;

public class Payload {
	public enum Topic{LOGIN, JOIN_ROOM, LEAVE_ROOM, LIST_ROOM, NEW_MESSAGE, IS_TYPING, ROOM_INFO};
	private Topic topic;
	private Object content;
	
	public Topic getTopic() {
		return topic;
	}	
	
	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
}
