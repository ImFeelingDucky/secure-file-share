import java.io.Serializable;

public class Message implements Serializable
{
    public Head head;
//    TODO: Document this
    public byte[] body;

    public Message(Head h, byte[] b){
        head = h;
        body = b;
    }

    public String getFromHead(String key) {
        return head.get(key);
    }

    public Head getHead() {
        return head;
    }

    public Action getAction() {
        return head.getAction();
    }

    public byte[] getBody() {
        return body;
    }
}