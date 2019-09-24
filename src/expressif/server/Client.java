package expressif.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private String pseudo;
    private BufferedReader socIn;
    private PrintStream socOut;
    private ClientListener listener;

    public Client(Socket socket, ClientListener listener) throws IOException {
        this.socket = socket;
        this.listener = listener;
        this.socIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socOut = new PrintStream(socket.getOutputStream());


        while (true) {
            String line = socIn.readLine();
            System.out.println("[Client] " + line);
        }
    }

    public void sendData(String topic, String payload) {
        socOut.println(topic + " - " + payload);
    }

    public interface ClientListener {
        void onJoinRoom(String room);

        void onLeaveRoom();

        void onNewMessage();
    }
}
