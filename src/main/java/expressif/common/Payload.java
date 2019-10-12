package expressif.common;

import java.io.*;
import java.util.Base64;

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

	public static Payload fromString(String s) {
		try {
			byte[] data = Base64.getDecoder().decode(s);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Payload payload = (Payload) ois.readObject();
			ois.close();
			return payload;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}

	public String toString() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.close();
			return Base64.getEncoder().encodeToString(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}
}
