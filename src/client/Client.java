package client;

import javafx.application.Application;

import static javafx.application.Application.launch;

public class Client implements GUI.Listener {
    public Client() {

    }

    @Override
    public String onLogin(String firstname, String lastname) {
        System.out.println("Firstname: " + firstname);
        System.out.println("Lastname: " + lastname);

        return "ok";
    }
}
