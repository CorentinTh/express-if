package expressif.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private String pseudo;
    private ObjectInputStream socIn;
    private PrintStream socOut;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.socIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        this.socOut = new PrintStream(socket.getOutputStream());
    }

    public void sendData(String topic, String payload) {
        socOut.println(topic + " - " + payload);
    }

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public ObjectInputStream getSocIn() {
		return socIn;
	}

	public void setSocIn(ObjectInputStream socIn) {
		this.socIn = socIn;
	}

	public PrintStream getSocOut() {
		return socOut;
	}

	public void setSocOut(PrintStream socOut) {
		this.socOut = socOut;
	}
    
    

}
