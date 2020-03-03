import java.util.Map;

public class Head {
    private String hostname;
    private int port;
    private Action action;
    private Map<String, String> arguments;

    public Head(String hostname, int port, Action action, Map<String, String> arguments) {
        this.hostname = hostname;
        this.port = port;
        this.action = action;
        this.arguments = arguments;
    }

    public String get(String key) {
        return arguments.get(key);
    }

    public Action getAction() {
        return action;
    }
    public int getPort() { return port; }
    public String getHostname() { return hostname; }
}
