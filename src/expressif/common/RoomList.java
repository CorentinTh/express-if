package expressif.common;

import expressif.server.Room;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class RoomList {
    private List<Pair<String, Integer>> roomList = new ArrayList<Pair<String, Integer>>();

    public RoomList() {
    }

    public void addRoom(String name, Integer userCount) {
        roomList.add(new Pair<>(name, userCount));
    }

    public List<Pair<String, Integer>> getRoomList() {
        return roomList;
    }

    @Override
    public String toString() {
        StringBuilder acc = new StringBuilder("[");

        for (Pair<String, Integer> pair : roomList) {
            acc.append("{name: ");
            acc.append(pair.getKey());
            acc.append(", count: ");
            acc.append(pair.getValue());
            acc.append("},");
        }

        acc.setLength(acc.length() - 1);
        acc.append("]");

        return acc.toString();
    }

    public static void main(String[] args) {
        RoomList rl = new RoomList();

        rl.addRoom("test", 5);
        rl.addRoom("tests", 6);

        System.out.println(rl);
    }
}
