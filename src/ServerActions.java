import java.io.File;

public class ServerActions {
    public static String[] list(String directory) {
        return new File(directory).list();
    }
}
