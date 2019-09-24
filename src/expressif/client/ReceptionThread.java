package expressif.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ReceptionThread extends Thread{
    private final Client client;

    public ReceptionThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try{
            ObjectInputStream inputStream = new ObjectInputStream(client.getSocket().getInputStream());
            ObjectOutputStream  outputStream = new ObjectOutputStream(client.getSocket().getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
