package expressif.client;

import expressif.common.RoomList;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements GUI.Listener {
    private GUI.Actions guiActions;
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public Client() {
    }

    private void setupConnection(String host, int port) {
        try {
            socket = new Socket(host, port);

            new ReceptionThreadClient(this).start();
        } catch (UnknownHostException e) {
            System.out.println("Unknown host");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String onInit(String firstname, String lastname, String host, int port) {
        setupConnection(host, port);

        // TODO: emit
        this.guiActions.displayView(2);

        return "ok";
    }

    @Override
    public String onJoinRoom(String roomName) {
        System.out.println("HEREE" + roomName);

        // TODO: emit
        return "ok";
    }

    @Override
    public void setActions(GUI.Actions actions) {
        this.guiActions = actions;
    }

    void emit() {

    }

    public void setRooms(RoomList roomList) {
        this.guiActions.addRoomList(roomList);
    }
}
