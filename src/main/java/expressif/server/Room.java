package expressif.server;

import expressif.common.Message;
import expressif.common.Payload;
import expressif.common.RoomInfo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private List<Client> clients = new ArrayList<>();
    private History history;
    private InetAddress groupAddress;
    private int groupPort;
    private MulticastSocket multiCastSocket;

    public Room(String name) {
        this.name = name;
        this.history = new LocalStorageHistory(name);
    }

    public InetAddress getGroupAddress() {
        return groupAddress;
    }

    public Room(String name, String inetAddr, int groupPort) throws IOException {
        this.name = name;
        this.history = new LocalStorageHistory(name);
        this.groupAddress = InetAddress.getByName(inetAddr);
        this.groupPort = groupPort;

        multiCastSocket = new MulticastSocket(groupPort);
        multiCastSocket.joinGroup(groupAddress);
        Room room = this;

        new Thread(() -> {
            try {


                while (true){
                    byte[] buf = new byte[10240];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    multiCastSocket.receive(packet);
                    String received = new String(packet.getData(), 0, packet.getLength());
                    Payload payload = Payload.fromString(received);

                    if(payload.getTopic() == Payload.Topic.NEW_MESSAGE){
                        System.out.println("New Message");
                        room.addMessage((Message) payload.getContent());
                    }
                }


            } catch (IOException e) {

            }
        }).start();


    }

    public String getName() {
        return name;
    }

    public void joinRoom(Client client) {
        clients.add(client);
    }

    public void leaveRoom(Client client) {
        System.out.println("LEAVE ROOM ---------2");
        clients.remove(client);
    }

    public void sendMessage(Message message) {
        sendData(new Payload(Payload.Topic.NEW_MESSAGE, message));
        history.addMessage(message);
    }

    public void addMessage(Message message){
        history.addMessage(message);
    }

    public void sendData(Payload payload) {
//        for (Client client : clients) {
//            client.sendData(payload);
//        }
        String toSend = payload.toString();
        DatagramPacket dataMessage = new DatagramPacket(toSend.getBytes(), toSend.length(), this.groupAddress, this.groupPort);
        try {
            multiCastSocket.send(dataMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getUserCount() {
        return clients.size();
    }

    public RoomInfo getRoomInfo() {
        RoomInfo ri = new RoomInfo(name);

        ri.setAddress(groupAddress);
        ri.setPort(groupPort);

        for (Client client : clients) {
            ri.addUserName(client.getPseudo());
        }

        System.out.println(history.getHistory());
        ri.addHistory(history.getHistory());

        return ri;
    }

}
