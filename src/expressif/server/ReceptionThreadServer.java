package expressif.server;

import expressif.common.Payload;

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
		        		if (currentRoom != null){
			        		currentRoom = rooms.getRoom((String) payload.getContent());
			        		currentRoom.joinRoom(client);
			        		payload.setContent(client.getPseudo());
			        		currentRoom.sendData(payload);
		        		} else {
		        			System.out.println("On leave room, current room not set! Blame it on the dev!");
		        		}
		        		break;
		        	case LEAVE_ROOM:
		        		if (currentRoom != null){
		        			currentRoom.leaveRoom(client);
		        			payload.setContent(client.getPseudo());
			        		currentRoom.sendData(payload);
		        		} else {
		        			System.out.println("On leave room, current room not set! Blame it on the dev!");
		        		}
		        		break;
		        	case LIST_ROOM:
		        		payload.setContent(rooms.getRooms());
		        		client.sendData(payload);
		        		break;
		        	case IS_TYPING:
		        		break;
		        	case NEW_MESSAGE:
		        		if (currentRoom != null){
		        			currentRoom.sendData(payload);
		        		} else {
		        			System.out.println("On leave room, current room not set! Blame it on the dev!");
		        		}
		        		break;
		        	case ROOM_INFO:
		        		client.sendData(payload);
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
