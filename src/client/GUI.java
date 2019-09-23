package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.concurrent.Worker.State;

import netscape.javascript.JSObject;


public class GUI extends Application {
    private JSObject javascriptConnector;
    private Listener listener;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Web View");
        this.listener = new Client();

        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        final Console console = new Console();

        webEngine.setJavaScriptEnabled(true);
        webEngine.load(getClass().getResource("web/index.html").toExternalForm());
        webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == State.SUCCEEDED) {
                JSObject windowDom = (JSObject) webEngine.executeScript("window");
                windowDom.setMember("javaConnector", listener);
                windowDom.setMember("console", console);
            }
        });

        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember("javaConnector", listener);
        window.setMember("console", console);

        Scene scene = new Scene(webView);
        stage.setScene(scene);
        stage.show();
    }

    public static class Console {
        public void log(String text) {
            System.out.println("[WebView console] " + text);
        }
    }

    public interface Listener {
        String onInit(String firstname, String lastname, String host, int port);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

