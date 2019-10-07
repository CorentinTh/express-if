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
            	//TODO je sais pas comment faire, désolée...
               // Payload payload = (Payload) fromString(inputStream.readObject());
                Payload payload = (Payload) inputStream.readObject();

                System.out.println("[new payload] " + payload.getTopic().toString() + " " + payload.getContent());

                switch (payload.getTopic()) {
                    case LIST_ROOM:
                        synchronized (this){
                            RoomList roomList = (RoomList) payload.getContent();
                            client.getGuiActions().setRoomList(roomList);
                        }
                        break;
                    case ROOM_INFO:
                        synchronized (this){
                            RoomInfo roomInfo = (RoomInfo) payload.getContent();
                            client.getGuiActions().setRoomInfo(roomInfo);
                        }
                        break;

//                    case NEW_MESSAGE:
//                        synchronized (this) {
//                            Message message = (Message) payload.getContent();
//                            System.out.println(message);
//                            client.getGuiActions().addMessage(message);
//                        }
//                        break;
//                    case JOIN_ROOM:
//                        client.getGuiActions().addUser((String) payload.getContent());
//                        break;
//                    case LEAVE_ROOM:
//                        client.getGuiActions().removeUser((String) payload.getContent());
//                        break;
//                    default:
//                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
