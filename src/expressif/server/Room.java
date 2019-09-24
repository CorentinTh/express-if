package expressif.server;

import expressif.common.Message;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private List<Client> clients = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void sendMessage(Message message) {
        // TODO
        sendData("new-message", "test");
    }

    public void joinRoom(Client client) {
        clients.add(client);

        //tODO
        sendData("new-expressif.client", "tes2");
    }

    public void leaveRoom(Client client) {
        clients.remove(client);

        //TODO
        sendData("remove-expressif.client", "tes3");
    }

    private void sendData(String topic, String payload) {
        for (Client client : clients) {
            client.sendData(topic, payload);
        }
    }
}
