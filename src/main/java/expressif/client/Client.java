package expressif.client;

import expressif.common.Payload;

import java.io.*;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements GUI.Listener {
    private GUI.Actions guiActions;

    public MulticastSocket getMulticastSocket() {
        return multicastSocket;
    }

    private Socket papSocket;
    private MulticastSocket multicastSocket;
    ObjectOutputStream outputStream;

    public Socket getSocket() {
        return papSocket;
    }

    public Client() {
    }

    private void setupPAPConnection(String host, int port) {
        try {
            papSocket = new Socket(host, port);
            outputStream = new ObjectOutputStream(papSocket.getOutputStream());
            new ReceptionThreadClient(this).start();

        } catch (Exception e) {
            System.out.println("Unknown host");
            e.printStackTrace();
        }
    }

    public void setupMCConnection(InetAddress address, int port) {
        try {
            multicastSocket = new MulticastSocket(port);
            multicastSocket.joinGroup(address);
            new MulticastReceptionThreadClient(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String onInit(String firstname, String lastname, String host, int port) {
        setupPAPConnection(host, port);
        emit(new Payload(Payload.Topic.LOGIN, firstname + " " + lastname));
        this.guiActions.displayView(2);

        return "ok";
    }

    public GUI.Actions getGuiActions() {
        return guiActions;
    }

    @Override
    public String onJoinRoom(String roomName) {


        emit(new Payload(Payload.Topic.JOIN_ROOM, roomName));
        this.guiActions.displayView(3);

        return "ok";
    }

    @Override
    public String onNewMessage(String content) {
        emit(new Payload(Payload.Topic.NEW_MESSAGE, content));

        return "ok";
    }

    @Override
    public String onLeaveRoom() {
        emit(new Payload(Payload.Topic.LEAVE_ROOM));
        this.guiActions.displayView(2);

        return "ok";
    }

    @Override
    public void setActions(GUI.Actions actions) {
        this.guiActions = actions;
    }

    void emit(Payload payload) {
        try {
            outputStream.writeObject(payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
