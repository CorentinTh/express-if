package expressif.common;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

    String user;
    String content;
    LocalDateTime date;

    public Message(String user, String content, LocalDateTime date) {
        this.user = user;
        this.content = content;
        this.date = date;
    }


    @Override
    public String toString() {
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

//    public static void main(String[] args) {
//        System.out.println(new Message("bob", "coucou", LocalDateTime.now()));
//    }
}