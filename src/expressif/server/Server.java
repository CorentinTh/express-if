package expressif.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        ServerSocket listenSocket;
        int port = 4242;
        RoomCollection rooms = new RoomCollection();

        rooms.add(new Room("test-room-1"));
        rooms.add(new Room("test-room-2"));

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        try {
            listenSocket = new ServerSocket(port);
            System.out.println("Server started on port: " + port);

            while (true) {
                Socket clientSocket = listenSocket.accept();
                Client client = new Client(clientSocket);

                System.out.println("New connexion:" + clientSocket.getInetAddress());

                ReceptionThreadServer ct = new ReceptionThreadServer(client, rooms);
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }

    }
}
