package client;

import javafx.application.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import static javafx.application.Application.launch;

public class Client implements GUI.Listener {
    public Client() {

    }

    @Override
    public String onInit(String firstname, String lastname, String host, int port) {

        System.out.println(firstname);

        try {
            Socket echoSocket = null;
            PrintStream socOut = null;
            BufferedReader stdIn = null;
            BufferedReader socIn = null;

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


        } catch (Exception e) {
            System.out.println(e);
        }


        return null;
    }
}
