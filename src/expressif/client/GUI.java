package expressif.client;

import expressif.common.Message;
import expressif.common.RoomInfo;
import expressif.common.RoomList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.concurrent.Worker.State;

import javafx.util.Pair;
import netscape.javascript.JSObject;

import java.util.List;


public class GUI extends Application {
    private JSObject window;
    private Listener listener;
    WebEngine webEngine;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Express'IF");
        this.listener = new Client();

        stage.setOnCloseRequest(ev -> {
            listener.onLeaveRoom();
            System.exit(0);
        });

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
                Platform.runLater(() -> {
                    webEngine.executeScript("addMessage(" + message + ")");
                });
            }

            public void removeUser(String pseudo) {
                Platform.runLater(() -> {
                    webEngine.executeScript("removeUser('" + pseudo + "')");
                });
            }

            public void addUser(String pseudo) {
                Platform.runLater(() -> {
                    webEngine.executeScript("addUser('" + pseudo + "')");
                });
            }

            public void setRoomList(RoomList roomList) {
                Platform.runLater(() -> {
                    webEngine.executeScript("addRoomList(" + roomList + ")");
                });
            }

            public void setRoomInfo(RoomInfo roomInfo) {
                Platform.runLater(() -> {
                    webEngine.executeScript("setRoomInfo(" + roomInfo + ")");
                });
            }

            public void displayView(int view) {
                Platform.runLater(() -> {
                    webEngine.executeScript("displayView(" + view + ")");
                });
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

        String onNewMessage(String content);

        String onLeaveRoom();

        void setActions(Actions actions);

    }

    public interface Actions {
        void addMessage(Message message);

        void removeUser(String pseudo);

        void addUser(String pseudo);

        void setRoomList(RoomList roomList);

        void displayView(int view);

        void setRoomInfo(RoomInfo roomInfo);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

