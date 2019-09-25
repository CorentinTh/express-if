package expressif.common;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class RoomList {
    private List<Pair<String, Integer>> roomList = new ArrayList<Pair<String, Integer>>();

    public RoomList() {
    }

    public void addRoom(String name, Integer userCount){
        roomList.add(new Pair<>(name, userCount));
    }

    public List<Pair<String, Integer>> getRoomList() {
        return roomList;
    }
}