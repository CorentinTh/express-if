package expressif.server;

import expressif.common.Message;
import expressif.common.Payload;

import java.time.LocalDateTime;

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
			while (true) {
				Payload payload = (Payload) client.getSocIn().readObject();

				System.out.println("[new payload] " + payload.getTopic().toString() + " " + payload.getContent());

				switch (payload.getTopic()) {
					case LOGIN:
						client.setPseudo((String) payload.getContent());

						client.sendData(new Payload(Payload.Topic.LIST_ROOM, rooms.getRoomList()));
						break;
					case JOIN_ROOM:
						currentRoom = rooms.getRoom((String) payload.getContent());

						if (currentRoom != null) {
							currentRoom.joinRoom(client);
							payload.setContent(client.getPseudo());
							currentRoom.sendData(payload);

							client.sendData(new Payload(Payload.Topic.ROOM_INFO, currentRoom.getRoomInfo()));
						} else {
							System.out.println("On JOIN_ROOM, current room not set! Blame it on the dev!");
						}
						break;
					case LEAVE_ROOM:
						if (currentRoom != null) {
							currentRoom.leaveRoom(client);
							payload.setContent(client.getPseudo());
							currentRoom.sendData(payload);
						} else {
							System.out.println("On leave room, current room not set! Blame it on the dev!");
						}
						break;
					case LIST_ROOM:
						payload.setContent(rooms.getRoomList());
						client.sendData(payload);
						break;
					case IS_TYPING:
						break;
					case NEW_MESSAGE:
						if (currentRoom != null) {
							currentRoom.sendData(new Payload(Payload.Topic.NEW_MESSAGE, new Message(client.getPseudo(),(String) payload.getContent(), LocalDateTime.now())));
						} else {
							System.out.println("On leave room, current room not set! Blame it on the dev!");
						}
						break;
					case ROOM_INFO:
						if (currentRoom != null) {
							payload.setContent(currentRoom.getRoomInfo());
							client.sendData(payload);
						}
						break;
				}
				// TODO
				// if (reception "join room A") Room room = rooms.get('A'); room.join(client);
			}
		} catch (Exception e) {
			if (currentRoom != null) {
				currentRoom.leaveRoom(client);
				currentRoom.sendData(new Payload(Payload.Topic.LEAVE_ROOM, client.getPseudo()));
			}

			System.out.println(e);
			e.printStackTrace();


		}
	}


}
