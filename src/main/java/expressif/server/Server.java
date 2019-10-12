package expressif.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.MulticastSocket;

public class Server {

    public static void main(String[] args) {
        ServerSocket listenSocket;
        int port = 4242;
        RoomCollection rooms = new RoomCollection();

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }


        try {
            int roomIp = 12;
            listenSocket = new ServerSocket(port);

            rooms.add(new Room("Divers", "239.255.12." + String.valueOf(roomIp++), port));
            rooms.add(new Room("4IFA", "239.255.12." + String.valueOf(roomIp++), port));
            rooms.add(new Room("Rencontre +40ans", "239.255.12." + String.valueOf(roomIp++), port));
            rooms.add(new Room("G@merz 4 life", "239.255.12." + String.valueOf(roomIp), port));

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
