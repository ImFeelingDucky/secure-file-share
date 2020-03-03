import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestThread extends Thread {
    private Socket socket;
    private SocketServer server;
    private PrintWriter writer;
    private Message request;
    private Message response;

    public RequestThread(Socket socket, SocketServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
//
            request = (Message) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Oops :( " + e.getMessage());
        }


        Action action = request.getAction();
        String directory = request.getFromHead("directory");

        Head requestHead = request.getHead();
        String hostname = requestHead.getHostname();
        int port = requestHead.getPort();
        Map<String, String> arguments = new HashMap<>();

        byte[] body = null;

        switch (action) {
            case LIST:
                String files = ServerActions.list(directory);
                body = files.getBytes(StandardCharsets.UTF_8);

                break;
            case UPLOAD:
                byte[] fileToUpload = request.getBody();
            case DOWNLOAD:
                try {
                    String filename = requestHead.get("filename");

                    Path path = Paths.get(System.getProperty("user.dir"), directory, filename);

                    System.out.println("Sending file at path: " + path.toString() + " to client.");

                    body = Files.readAllBytes(new File(requestHead.get("filename")).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }

        Message request = new Message(new Head(hostname, port, action, arguments), body);

        // Change this so that it gets the argument for the command

        server.closeRequest(this);

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
