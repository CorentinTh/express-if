package expressif.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import expressif.common.Message;

public class VolatileHistory implements History{
	private HashMap<String, List<Message>> messageHistory;
	
	public VolatileHistory() {
		messageHistory = new HashMap<String, List<Message>>();
	}

	@Override
	public List<Message> getHistoryOfRoom(String nomRoom) {
		return messageHistory.get(nomRoom);
	}

	@Override
	public void addRoom(String nomRoom) {
		messageHistory.put(nomRoom, new ArrayList<Message>());
	}

	@Override
	public void addMessage(String nomRoom, Message message) {
		messageHistory.get(nomRoom).add(message);
	}
	
//	public static void main(String[] args) {
//		VolatileHistory test = new VolatileHistory();
//		Message mes = new Message("bob", "coucou c'est bob", LocalDateTime.now());
//		
//		test.addRoom("TracerForTheWin");
//		test.addMessage("TracerForTheWin", mes);
//		for (Message message : test.getHistoryOfRoom("TracerForTheWin")) {
//			System.out.println(message);
//		}
//		System.out.println();
//	}

}
