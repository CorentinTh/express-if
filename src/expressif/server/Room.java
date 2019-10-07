package expressif.server;

import expressif.common.Message;
import expressif.common.Payload;
import expressif.common.RoomInfo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private List<Client> clients = new ArrayList<>();
    private History history;
    private InetAddress groupAdress;
    private int groupPort;
    private MulticastSocket multiCastSocket;

    public Room(String name) {
        this.name = name;
        this.history = new LocalStorageHistory(name);
    }
    
    public Room(String name, String inetAddr, int groupPort) throws IOException{
        this.name = name;
        this.history = new LocalStorageHistory(name);
        this.groupAdress = InetAddress.getByName(inetAddr);
        this.groupPort = groupPort;
		this.multiCastSocket =  new MulticastSocket(groupPort);
    }

    public String getName() {
        return name;
    }

    public void joinRoom(Client client) {
    	try {
    		multiCastSocket.joinGroup(this.groupAdress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        clients.add(client);
    }

    public void leaveRoom(Client client) {
    	try {
    		multiCastSocket.leaveGroup(groupAdress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        clients.remove(client);
    }

    public void sendMessage(Message message){
        sendData(new Payload(Payload.Topic.NEW_MESSAGE, message));
        history.addMessage(message);
    }

    public void sendData(Payload payload) {
//        for (Client client : clients) {
//            client.sendData(payload);
//        }
    	 String toSend = payload.toString();
    	 DatagramPacket dataMessage = new DatagramPacket(toSend.getBytes(), toSend.length(), this.groupAdress, this.groupPort);
         try {
         	multiCastSocket.send(dataMessage);
 		} catch (IOException e) {
 			e.printStackTrace();
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
