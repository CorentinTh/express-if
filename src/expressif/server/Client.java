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

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.socIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socOut = new PrintStream(socket.getOutputStream());
    }

    public void sendData(String topic, String payload) {
        socOut.println(topic + " - " + payload);
    }

}
