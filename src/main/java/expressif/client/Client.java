package expressif.client;

import expressif.common.Message;
import expressif.common.Payload;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

public class Client implements GUI.Listener {
    private GUI.Actions guiActions;

    public MulticastSocket getMulticastSocket() {
        return multicastSocket;
    }

    private Socket papSocket;
    private MulticastSocket multicastSocket;
    ObjectOutputStream outputStream;
    InetAddress multicastAddress;
    int multicastPort;
    String pseudo;

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
            multicastAddress = address;
            multicastPort = port;
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
        pseudo = firstname + " " + lastname;
        emitToPaP(new Payload(Payload.Topic.LOGIN, pseudo));
        this.guiActions.displayView(2);

        return "ok";
    }

    public GUI.Actions getGuiActions() {
        return guiActions;
    }

    @Override
    public String onJoinRoom(String roomName) {

        emitToPaP(new Payload(Payload.Topic.JOIN_ROOM, roomName));
//        emitToMultiCast(new Payload(Payload.Topic.JOIN_ROOM, roomName));
        this.guiActions.displayView(3);

        return "ok";
    }

    @Override
    public String onNewMessage(String content) {
//        emitToPaP(new Payload(Payload.Topic.NEW_MESSAGE, content));
        emitToMultiCast(new Payload(Payload.Topic.NEW_MESSAGE, new Message(pseudo, content, LocalDateTime.now())));

        return "ok";
    }

    @Override
    public String onLeaveRoom() {
//        emitToPaP(new Payload(Payload.Topic.LEAVE_ROOM));
        emitToMultiCast(new Payload(Payload.Topic.LEAVE_ROOM));
        this.guiActions.displayView(2);

        return "ok";
    }

    @Override
    public void setActions(GUI.Actions actions) {
        this.guiActions = actions;
    }

    void emitToMultiCast(Payload payload){
        try {
            byte[] data = payload.toString().getBytes();
            multicastSocket.send(new DatagramPacket(data, data.length, multicastAddress, multicastPort));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void emitToPaP(Payload payload) {
        try {
            outputStream.writeObject(payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getPseudo() {
        return pseudo;
    }
}
