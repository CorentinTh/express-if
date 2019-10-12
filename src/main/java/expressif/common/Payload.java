package expressif.common;

import java.io.Serializable;

public class Payload implements Serializable {
	public enum Topic{LOGIN, JOIN_ROOM, LEAVE_ROOM, LIST_ROOM, NEW_MESSAGE, IS_TYPING, ROOM_INFO, LOGOUT};
	private Topic topic;
	private Object content;

    public Payload(Topic topic) {
        this.topic = topic;
    }

    public Payload(Topic topic, Object content) {
        this.topic = topic;
        this.content = content;
    }

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
