import java.io.File;
import java.util.Arrays;

public class ServerActions {
    public static String list(String directory) {
        return  String.join("\n", new File(directory).list());
    }
}
