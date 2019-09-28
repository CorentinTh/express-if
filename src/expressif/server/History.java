package expressif.server;

import java.util.List;

import expressif.common.Message;

public interface History {

    public List<Message> getHistory();

    public void addMessage(Message message);

}