import java.io.Serializable;
import java.util.Map;

/**
 * Packages the metadata for a Message request or response between client and server.
 */
public class Head implements Serializable {
    private String hostname;
    private int port;
    private Action action;

    /**
     * Stores the action-specific metadata needed by the server or client to interpret the message
     *
     * For example, "Content-Type" records whether the associated body is a file, intended to be written to
     * the filesystem, or is text, intended to be displayed to a person.
     */
    private Map<String, String> arguments;
    private boolean successful;

    /**
     * Records whether this request or response was successful or failed.
     * @param successful whether this request or response was successful or failed
     */
    public Head(boolean successful) {
        this.successful = successful;
    }

    public Head(String hostname, int port, Action action, Map<String, String> arguments) {
        this.hostname = hostname;
        this.port = port;
        this.action = action;
        this.arguments = arguments;
        this.successful = true;
    }

    public String get(String key) {
        return arguments.get(key);
    }

    public Action getAction() {
        return action;
    }
    public int getPort() { return port; }
    public String getHostname() { return hostname; }
    public boolean isSuccessful() { return successful; }
}
