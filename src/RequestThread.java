import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RequestThread extends Thread {
    public static final String FILES_DIRECTORY = Paths.get(System.getProperty("user.dir"), "server-files").toString();
    private Socket socket;
//    private SocketServer server;

//    public RequestThread(Socket socket, SocketServer server) {
//        this.socket = socket;
//        this.server = server;
//    }

    public RequestThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        Message request = null;
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            request = (Message) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Server: Oops :( could not read Request Message" + e.getMessage());
            System.exit(1);
        }

        Action action = request.getAction();
        String directory = request.getFromHead("directory");

        Map<String, String> arguments = new HashMap<>();

//        TODO: Set this inside the switch (action)
        arguments.put("Content-Type", "text");
        arguments.put("directory", directory);

//    undelete this
//        byte[] body = null;

//        Delet this -_-
        byte[] body = new byte[0];
        try {
            body = Files.readAllBytes(Paths.get(FILES_DIRECTORY, "max", "a.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean debugging = true;
//        Delette above this -_-


        if (!debugging) {
            switch (action) {
                case LIST:
                    String files = String.join("\n", new File(FILES_DIRECTORY).list());
                    body = files.getBytes(StandardCharsets.UTF_8);
                    System.out.println("Got list query. Responded with: \n" + files);
                    break;
                case UPLOAD:
                    byte[] fileToUpload = request.getBody();
                case DOWNLOAD:
                    try {
                        String filename = request.getHead().get("filename");

                        Path path = Paths.get(FILES_DIRECTORY, directory, filename);

                        System.out.println("Sending file at path: " + path.toString() + " to client.");

                        body = Files.readAllBytes(new File(request.getHead().get("filename")).toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + action);
            }
        }

        Message response = new Message(new Head(request.getHead().getHostname(), request.getHead().getPort(), action, arguments), body);

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(response);
        } catch (IOException e) {
            System.out.println("Couldn't return response to client, because IOException :( ");
            e.printStackTrace();
        }

        // Change this so that it gets the argument for the command

//        server.closeRequest(this);

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
