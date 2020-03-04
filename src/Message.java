import java.io.Serializable;

/**
 * Packages a request or response sent between client and server.
 */
public class Message implements Serializable {
    public Head head;

    /**
     * The body of a Message is an array of bytes, allowing easy serialisation
     * and simple transfer of files or data without having to worry about file-type or encoding.
     */

    public byte[] body;

    public Message(Head h, byte[] b) {
        head = h;
        body = b;
    }

    public Head getHead() {
        return head;
    }

    public byte[] getBody() {
        return body;
    }
}