package expressif.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import static javafx.application.Application.launch;

public class Client implements GUI.Listener {
    ReceptionThreadClient socketThread;
    GUI.Actions guiActions;
    Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public Client() {
    }

    @Override
    public String onInit(String firstname, String lastname, String host, int port) {

        try {
            socket = new Socket(host, port);

            socketThread = new ReceptionThreadClient(this);
            socketThread.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//
//        System.out.println(firstname);
//
//        try {
//            Socket echoSocket = null;
//            PrintStream socOut = null;
//            BufferedReader stdIn = null;
//            BufferedReader socIn = null;
//
//            System.out.println("Started expressif.client on: " + host + ":" + port);
//
//            try {
//                // creation socket ==> connexion
//                echoSocket = new Socket(host, port);
//                socIn = new BufferedReader(
//                        new InputStreamReader(echoSocket.getInputStream()));
//                socOut = new PrintStream(echoSocket.getOutputStream());
//                stdIn = new BufferedReader(new InputStreamReader(System.in));
//            } catch (UnknownHostException e) {
//                System.err.println("Don't know about host:" + host);
//                System.exit(1);
//            } catch (IOException e) {
//                System.err.println("Couldn't get I/O for " + "the connection to:" + host);
//                System.exit(1);
//            }
//
//            String line;
//            while (true) {
//                line = stdIn.readLine();
//                if (line.equals(".")) break;
//                socOut.println(line);
//                System.out.println("echo: " + socIn.readLine());
//            }
//            socOut.close();
//            socIn.close();
//            stdIn.close();
//            echoSocket.close();
//
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//

        return "ok";
    }

    @Override
    public void setActions(GUI.Actions actions) {
        this.guiActions = actions;
    }
}
