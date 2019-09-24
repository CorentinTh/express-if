package expressif.server;

import expressif.common.Message;

import java.io.ObjectInputStream;

public class ReceptionThreadServer extends Thread {
    private Client client;
    private RoomCollection rooms;

    public ReceptionThreadServer(Client client, RoomCollection rooms) {
        this.client = client;
        this.rooms = rooms;
    }

    @Override
    public void run() {

        while (true){
            // TODO
            // if (reception "join room A") Room room = rooms.get('A'); room.join(client);
        }
    }


}
