import java.io.Serializable;
import java.util.Map;

public class Head implements Serializable {
    private String hostname;
    private int port;
    private Action action;
    private Map<String, String> arguments;
    private boolean successful;

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
