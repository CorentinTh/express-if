package expressif.client;

import expressif.common.Message;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.concurrent.Worker.State;

import netscape.javascript.JSObject;


public class GUI extends Application {
    private JSObject window;
    private Listener listener;
    WebEngine webEngine;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Express'IF");
        this.listener = new Client();

        final WebView webView = new WebView();
        final Console console = new Console();
        webEngine = webView.getEngine();

        webEngine.setJavaScriptEnabled(true);
        webEngine.load(getClass().getResource("web/index.html").toExternalForm());
        webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == State.SUCCEEDED) {
                JSObject windowDom = (JSObject) webEngine.executeScript("window");
                windowDom.setMember("javaConnector", listener);
                windowDom.setMember("console", console);
            }
        });

        window = (JSObject) webEngine.executeScript("window");
        window.setMember("javaConnector", listener);
        window.setMember("console", console);

        Actions actions = new Actions() {
            public void addMessage(Message message) {
                webEngine.executeScript("addMessage(" + message +")");
            }

            public void removeUser(String pseudo) {
                webEngine.executeScript("removeUser(" + pseudo +")");
            }

            public void addUser(String pseudo) {
                webEngine.executeScript("addUser(" + pseudo +")");
            }
        };

        listener.setActions(actions);

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
        String onJoinRoom(String roomName);
        void setActions(Actions actions);
    }

    public interface Actions {
        void addMessage(Message message);
        void removeUser(String pseudo);
        void addUser(String pseudo);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

