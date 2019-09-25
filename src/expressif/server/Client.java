package expressif.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import expressif.common.Payload;

public class Client {
    private Socket socket;
    private String pseudo;
    private ObjectInputStream socIn;
    private ObjectOutputStream socOut;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.socOut = new ObjectOutputStream(socket.getOutputStream());
    }

    public void sendData(Payload payload) {
        try {
			socOut.writeObject(payload);
		} catch (IOException e) {
			System.out.println(e);
		}
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

	public ObjectOutputStream getSocOut() {
		return socOut;
	}

	public void ObjectOutputStream(ObjectOutputStream socOut) {
		this.socOut = socOut;
	}
    
    

}
