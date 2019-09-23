package expressif.server;

import java.net.Socket;

public class SocketThread extends Thread {
    private Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Client.ClientListener listener = new Client.ClientListener() {
                @Override
                public void onJoinRoom(String room) {

                }

                @Override
                public void onLeaveRoom() {

                }

                @Override
                public void onNewMessage() {

                }
            };

            Client client = new Client(socket, listener);

        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}
