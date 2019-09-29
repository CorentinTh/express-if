package expressif.common;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Base64;

public class Message implements Serializable {

    String user;
    String content;
    LocalDateTime date;

    public Message(String user, String content, LocalDateTime date) {
        this.user = user;
        this.content = content;
        this.date = date;
    }

    public String toJson() {
        StringBuilder acc = new StringBuilder("{");
        acc.append("user: '");
        acc.append(user);
        acc.append("', content: '");
        acc.append(content);
        acc.append("', date: '");
        acc.append(date);
        acc.append("'}");

        return acc.toString();
    }

    public static void main(String[] args) {
        Message message = new Message("bob", "coucou", LocalDateTime.now());
        String s = message.toString();
        System.out.println(s);
        Message t = Message.fromString(s);
        System.out.println(t.toJson());
    }


    public static Message fromString(String s) {
        try {
            byte[] data = Base64.getDecoder().decode(s);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Message message = (Message) ois.readObject();
            ois.close();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public String toString() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
    
    public String getContent() {
    	return this.content;
    }

//    public static void main(String[] args) {
//        System.out.println(new Message("bob", "coucou", LocalDateTime.now()));
//    }
}