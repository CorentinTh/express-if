package expressif.client;

import expressif.common.Message;
import expressif.common.Payload;
import expressif.common.RoomList;
import javafx.application.Platform;

import java.io.*;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements GUI.Listener {
    private GUI.Actions guiActions;
    private Socket papSocket;
    private MulticastSocket multicastSocket;
    ObjectOutputStream outputStream;

    public Socket getSocket() {
        return papSocket;
    }

    public Client() {
    }

    private void setupConnection(String host, int port) {
        try {
        	papSocket = new Socket(host, port);
            outputStream = new ObjectOutputStream(papSocket.getOutputStream());
            new ReceptionThreadClient(this).start();
            //TODO pas sûre de comment l'initialiser
            multicastSocket = new MulticastSocket();
            new MulticastReceptionThreadClient(this).start();
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
