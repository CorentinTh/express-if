package expressif.server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import expressif.common.Message;

public class VolatileHistory implements History{
    private List<Message> messageHistory;

    public VolatileHistory() {
        messageHistory = new ArrayList<>();
    }

    @Override
    public List<Message> getHistory() {
        return messageHistory;
    }

    @Override
    public void addMessage(Message message) {
        messageHistory.add(message);
    }

	public static void main(String[] args) {
		VolatileHistory test = new VolatileHistory();

		test.addMessage(new Message("bob", "coucou c'est bob", LocalDateTime.now()));
		test.addMessage(new Message("qsd", "coucodsqu c'est bob", LocalDateTime.now()));
		test.addMessage(new Message("bobq", "qsd c'est bob", LocalDateTime.now()));

		for (Message message : test.getHistory()) {
			System.out.println(message);
		}
		System.out.println();
	}

}