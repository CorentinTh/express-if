/*
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
*/

import java.io.*;
import java.net.*;

public class EchoServer {

    /**
     * receives a request from client then sends an echo to the client
     *
     * @param clientSocket the client socket
     **/
    private static void doService(Socket clientSocket) {
        try {
            BufferedReader socIn;
            socIn = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                String line = socIn.readLine();
                socOut.println(line);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

    public static void main(String[] args) {
        ServerSocket listenSocket;
        int port = 4242;

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        System.out.println("Started server on port: " + port);

        try {
            listenSocket = new ServerSocket(port); //port
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("connexion from:" + clientSocket.getInetAddress());
                doService(clientSocket);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}

  
