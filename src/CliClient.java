import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CliClient {
    private SocketClient socket;
    private static String usage = "Usage: java CliClient <action> <hostname> <port> <commandArguments>" +
            "\nwhere action is one of: upload, download, list.";

    public static void main(String[] args) {
        CliClient cliClient = new CliClient();
        cliClient.runCommand(args);
    }

    public void runCommand(String[] args) {
        Action action = null;

        try {
            action = Action.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(usage);
//            System.out.println("Usage: RTFM");
            System.exit(1);
        }

//        Generically:
//        java CliClient <action> <hostname> <port> <commandArguments>

//        With our current set of three actions:
//        java CliClient <action> <hostname> <port> <directory> [filename]


        String hostname = args[1];
        int port = Integer.parseInt(args[2]);

        Map<String, String> arguments = new HashMap<>();

        String directory = args[3];
        arguments.put("directory", directory);

        byte[] body = null;

        // Check args validity
        switch (action) {
            case LIST:
                break;
            case UPLOAD:
            case DOWNLOAD:
                String filename = args[3];
                arguments.put("filename", filename);
                break;
            default:
                System.out.println(usage);
        }

        Message request = new Message(new Head(hostname, port, action, arguments), body);

        try {
            Message response = socket.makeRequest(request);

//          Choose what to do with response based on content type
            switch (response.getHead().get("Content-Type")) {
                case "text":
                    System.out.println(new String(response.getBody(), StandardCharsets.UTF_8));
                    break;
                case "file":
                    File f = new File(response.getHead().get("filename"));
                    Files.write(f.toPath(), response.getBody());
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }
}
