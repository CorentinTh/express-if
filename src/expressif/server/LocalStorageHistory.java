package expressif.server;

import expressif.common.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LocalStorageHistory implements History {
    Path filePath;

    public LocalStorageHistory(String roomName) {
        filePath = Paths.get(System.getProperty("user.dir"), "history", roomName + ".hist");
    }

    @Override
    public List<Message> getHistory() {
        List<Message> messages = new ArrayList<>();

        try {
            Files.lines(filePath).forEach(s -> {
                if(!s.equals("")) {
                    messages.add(Message.fromString(s));
                }
            });

            return messages;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void addMessage(Message message) {
        try {
            Files.write(filePath, (message.toString() + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        LocalStorageHistory h = new LocalStorageHistory("e");
        h.addMessage(new Message("e", "ert", LocalDateTime.now()));
        h.addMessage(new Message("er", "er", LocalDateTime.now()));


        for (Message message : h.getHistory()) {
            System.out.println(message.toJson());
        }
        System.out.println();
    }

}
