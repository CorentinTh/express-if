package expressif.server;

import expressif.common.Message;

import java.io.ObjectInputStream;

public class ReceptionThread extends Thread {
    private ObjectInputStream inputStream;
    private Listener listener;

    public ReceptionThread(ObjectInputStream inputStream, Listener listener) {
        this.inputStream = inputStream;
        this.listener = listener;
    }

    @Override
    public void run() {

        while (true){
            // TODO
            // if (reception message) listener.onMessage(message)
        }
    }

    public interface Listener{
        void onMessage(Message message);
    }
}
