package expressif.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import static javafx.application.Application.launch;

public class Client implements GUI.Listener {
    ReceptionThreadClient socketThread;
    GUI.Actions guiActions;
    Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public Client() {
    }

    private void setupConnection(String host, int port){
        try {
            socket = new Socket(host, port);

            socketThread = new ReceptionThreadClient(this);
            socketThread.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String onInit(String firstname, String lastname, String host, int port) {
        setupConnection(host, port);

        // TODO: emit
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

    void emit(){

    }
}
