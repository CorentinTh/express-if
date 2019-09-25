package expressif.client;

import expressif.common.Payload;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ReceptionThreadClient extends Thread {
    private Client client;

    public ReceptionThreadClient(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(client.getSocket().getInputStream());

            while (true) {
                Payload payload = (Payload) inputStream.readObject();

                switch (payload.getTopic()){
                    case LIST_ROOM:
                        //payload.getContent();
                        break;
                    default:
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
