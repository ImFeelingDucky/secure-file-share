import java.util.Map;

public class Head {
    private Map<String, String> map;
    private Action action;

    public String get(String key) {
        return map.get(key);
    }

    public Action getAction() {
        return action;
    }
}
