import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RequestThread2 extends Thread {
    public static final String FILES_DIRECTORY = Paths.get(System.getProperty("user.dir"), "server-files").toString();
    private Socket socket;

    public RequestThread2(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        // Receive request from client
        Message request = readRequest();


        // Build response Message object
        Message response = buildResponse(request);


        // Send completed response Message object to client
        sendResponse(response);
    }

    public Message buildResponse(Message request) {
        byte[] body = new byte[0];
        Map<String, String> arguments = new HashMap<>();

        switch (request.getHead().getAction()) {
            case LIST:
                String files = String.join("\n", Paths.get(FILES_DIRECTORY, request.getHead().get("directory")).toFile().list());
                body = files.getBytes(StandardCharsets.UTF_8);
                System.out.println("Got list query. Responding with: \n" + files);

                arguments.put("Content-Type", "text");
                break;
            case DOWNLOAD:
                System.out.println("TRYING TO SERVE DOWNLOAD REQUES.");
                System.out.println("directory: " + request.getHead().get("directory") + "\nfilename: " + request.getHead().get("filename"));

                try {
                    body = Files.readAllBytes(Paths.get(RequestThread2.FILES_DIRECTORY, request.getHead().get("directory"), request.getHead().get("filename")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                arguments.put("Content-Type", "file");
                break;
        }

        return new Message(new Head(request.getHead().getHostname(), request.getHead().getPort(), request.getHead().getAction(), arguments), body);
    }

    /**
     * Reads a request Message object from the ObjectInputStream associated with this RequestThread's socket.
     *
     * @return the request
     */
    public Message readRequest() {
        Message request = null;
        ObjectInputStream inputStream = null;

        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("nonononononono can't get inputstream :'(");
            e.printStackTrace();
        }

        try {
            request = (Message) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Server: Can't read request Message object from client.");
            System.exit(1);
        }

        return request;
    }

    /**
     * Sends a response Message object to the client through the ObjectOutputStream associated with
     * this RequestThread's socket.
     *
     * @param response
     */
    public void sendResponse(Message response) {
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.writeObject(response);
        } catch (IOException e) {
            System.out.println("Server: Can't write response Message object to client socket.");
            e.printStackTrace();
        }
    }
}