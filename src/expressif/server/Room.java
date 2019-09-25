package expressif.server;

import expressif.common.Payload;

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

    public void joinRoom(Client client) {
        clients.add(client);
    }

    public void leaveRoom(Client client) {
        clients.remove(client);
    }

    public void sendData(Payload payload) {
        for (Client client : clients) {
            client.sendData(payload);
        }
    }
}
