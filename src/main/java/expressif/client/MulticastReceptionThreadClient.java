package expressif.client;

import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

import expressif.common.Message;
import expressif.common.Payload;
import expressif.common.RoomInfo;
import expressif.common.RoomList;

public class MulticastReceptionThreadClient extends Thread {
    private Client client;

    public MulticastReceptionThreadClient(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            MulticastSocket socket = client.getMulticastSocket();

            while (true) {

                byte[] buf = new byte[10240];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                Payload payload = Payload.fromString(received);

                System.out.println("[new payload] " + payload.getTopic().toString());

                switch (payload.getTopic()) {
                    case NEW_MESSAGE:
                        synchronized (this) {
                            Message message = (Message) payload.getContent();
                            System.out.println(message);
                            client.getGuiActions().addMessage(message);
                        }
                        break;
                    case JOIN_ROOM:
                        synchronized (this) {
                            client.getGuiActions().addUser((String) payload.getContent());
                        }
                        break;
                    case LEAVE_ROOM:
                        synchronized (this) {
                            client.getGuiActions().removeUser((String) payload.getContent());
                        }
                        break;
                    default:
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
