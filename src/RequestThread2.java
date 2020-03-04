import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RequestThread2 extends Thread {
    public static final String FILES_DIRECTORY = Paths.get(System.getProperty("user.dir"), "files").toString();
    private Socket socket;

    public RequestThread2(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        // Receive request from client
        Message request = readRequest();



        // Build response Message object
        byte[] body = new byte[0];
        try {
            body = Files.readAllBytes(Paths.get(RequestThread.FILES_DIRECTORY, "max", "a.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> arguments = new HashMap<>();
        arguments.put("Content-Type", "file");
        Message response = new Message(new Head(request.getHead().getHostname(), request.getHead().getPort(), Action.LIST, arguments), body);



        // Send completed response Message object to client
        sendResponse(response);
    }

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
            System.out.println("OOps baby baby... I can't read a Message request from the clieeeeeeeent :'(");
            System.exit(1);
        }

        return request;
    }

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
            System.out.println("OOOOOps no no thats a no no cant write response");
            e.printStackTrace();
        }
    }
}