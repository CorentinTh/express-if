package expressif.common;

import expressif.server.Client;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class RoomInfo implements Serializable {
    private List<Message> history = new ArrayList<>();
    private List<String> users = new ArrayList<String>();
    private String name;
    private InetAddress address;
    private int port;

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public RoomInfo(String name) {
        this.name = name;
    }

    public void addUserName(String name) {
        this.users.add(name);
    }

    public void addHistory(List<Message> history) {
        this.history = history;
    }

    public String toJson() {
        StringBuilder acc = new StringBuilder("{");

        acc.append("name: '");
        acc.append(name);
        acc.append("', history: [");

        if(history !=null){
            for (Message message : history) {
                acc.append(message.toJson());
                acc.append(",");
            }
        }

        acc.append("], users: [");
        for (String user : users) {
            acc.append("'");
            acc.append(user);
            acc.append("',");
        }

        acc.append("]}");

        return acc.toString();
    }
}
