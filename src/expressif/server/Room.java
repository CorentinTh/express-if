package expressif.server;

import expressif.common.Message;
import expressif.common.Payload;
import expressif.common.RoomInfo;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private List<Client> clients = new ArrayList<>();
    private History history;

    public Room(String name) {
        this.name = name;
        this.history = new LocalStorageHistory(name);
    }

    public String getName() {
        return name;
    }

    public void joinRoom(Client client) {
        clients.add(client);
    }

    public void leaveRoom(Client client) {
        clients.remove(client);
    }

    public void sendMessage(Message message){
        sendData(new Payload(Payload.Topic.NEW_MESSAGE, message));
        history.addMessage(message);
    }

    public void sendData(Payload payload) {
        for (Client client : clients) {
            client.sendData(payload);
        }
    }

    public int getUserCount(){
        return clients.size();
    }

    public RoomInfo getRoomInfo(){
        RoomInfo ri = new RoomInfo(name);

        for (Client client : clients) {
            ri.addUserName(client.getPseudo());
        }

        System.out.println(history.getHistory());
        ri.addHistory(history.getHistory());

        return  ri;
    }

}
