package expressif.server;

import java.util.ArrayList;
import java.util.List;

public class RoomCollection {
    List<Room> rooms = new ArrayList<>();

    public RoomCollection() {
    }

    void add(Room room){
        rooms.add(room);
    }

    Room getRoom(String name){
        return rooms
                .stream()
                .filter(room -> room.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
