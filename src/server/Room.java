package server;

import core.Message;

import java.net.Socket;
import java.util.List;

public class Room {
    private String name;
    private List<Client> clients;

    public void sendMessage(Message message) {
        sendData("new-message", "test");
    }

    public void joinRoom(Client client) {
        clients.add(client);
        sendData("new-client", "tes2");
    }

    public void leaveRoom(Client client) {
        clients.remove(client);
        sendData("remove-client", "tes3");
    }

    private void sendData(String topic, String payload) {
        for (Client client : clients) {
            client.sendData(topic, payload);
        }
    }
}
