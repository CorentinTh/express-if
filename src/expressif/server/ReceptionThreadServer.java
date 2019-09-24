package expressif.server;

import expressif.common.Message;
import expressif.common.Payload;

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
    	Room currentRoom = null;
    	try {
	        while (true){
	        	Payload payload = (Payload) client.getSocIn().readObject();
	        	switch (payload.getTopic()) {
	        	case LOGIN:
	        		client.setPseudo((String) payload.getContent());
	        		break;
	        	case JOIN_ROOM:
	        		currentRoom = rooms.getRoom((String) payload.getContent());
	        		currentRoom.joinRoom(client);
	        		break;
	        	case LEAVE_ROOM:
	        		//if(currentRoom =! null){
	        			currentRoom.leaveRoom(client);
	        		//}
	        		break;
	        	}
	            // TODO
	            // if (reception "join room A") Room room = rooms.get('A'); room.join(client);
	        }
    	} catch(Exception e) {
    		System.out.println(e);
    	}
    }


}
