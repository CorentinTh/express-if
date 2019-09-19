/*
 * EchoClient
 * Example of a TCP client
 * Date: 10/01/04
 * Authors:
*/

import java.io.*;
import java.net.*;


public class EchoClient {


    /**
     * main method
     * accepts a connection, receives a message from client then sends an echo to the client
     **/
    public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        BufferedReader socIn = null;

        int port = 4242;
        String host = "127.0.0.1";

        if (args.length == 2) {
            port = Integer.parseInt(args[1]);
            host = args[0];
        }

        System.out.println("Started client on: " + host + ":" + port);

        try {
            // creation socket ==> connexion
            echoSocket = new Socket(host, port);
            socIn = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
            socOut = new PrintStream(echoSocket.getOutputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to:" + host);
            System.exit(1);
        }

        String line;
        while (true) {
            line = stdIn.readLine();
            if (line.equals(".")) break;
            socOut.println(line);
            System.out.println("echo: " + socIn.readLine());
        }
        socOut.close();
        socIn.close();
        stdIn.close();
        echoSocket.close();
    }
}


