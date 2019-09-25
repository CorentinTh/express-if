package expressif.server;

import java.util.List;

import expressif.common.Message;

public interface History {
	
	public List<Message> getHistoryOfRoom(String nomRoom);
	
	public void addRoom(String nomRoom);
	
	public void addMessage(String nomRoom, Message message);

}
