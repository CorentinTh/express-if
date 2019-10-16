package expressif.client;

import expressif.common.Message;
import expressif.common.Payload;
import expressif.common.RoomInfo;
import expressif.common.RoomList;

import java.io.ObjectInputStream;

public class ReceptionThreadClient extends Thread {
    private Client client;

    public ReceptionThreadClient(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(client.getSocket().getInputStream());

            while (true) {
                Payload payload = (Payload) inputStream.readObject();

                System.out.println("[new payload] " + payload.getTopic().toString() + " " + payload.getContent());

                switch (payload.getTopic()) {
                    case LIST_ROOM:
                        synchronized (this) {
                            RoomList roomList = (RoomList) payload.getContent();
                            client.getGuiActions().setRoomList(roomList);
                        }
                        break;
                    case ROOM_INFO:
                        synchronized (this) {
                            RoomInfo roomInfo = (RoomInfo) payload.getContent();
                            System.out.println(roomInfo);
                            client.setupMCConnection(roomInfo.getAddress(), roomInfo.getPort());
                            client.getGuiActions().setRoomInfo(roomInfo);
                            client.emitToMultiCast(new Payload(Payload.Topic.JOIN_ROOM, client.getPseudo()));
                        }
                        break;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
