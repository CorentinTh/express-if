/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package examples;

import java.io.*;
import java.net.*;

public class EchoServerMT  {

    public static void main(String args[]){
        ServerSocket listenSocket;
        int port = 4242;

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        try {
            listenSocket = new ServerSocket(port); //port
            System.out.println("Server ready...");
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Connexion from:" + clientSocket.getInetAddress());
                ClientThread ct = new ClientThread(clientSocket);
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}

  