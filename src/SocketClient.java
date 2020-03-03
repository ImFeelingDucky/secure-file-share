import java.net.*;
import java.io.*;

public class SocketClient {
    private Message request;
    private Message response;

    public Message makeRequest(Message request) {
        try {
            Socket socket = new Socket(request.getHostname(), request.getPort());

//        PrintStream output = new PrintStream(socket.getOutputStream(), true);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(request);

            System.out.println("Connected to the file server");

//            Read the response back from the server as a Message<X>

            try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                response = (Message) ois.readObject();
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("Sorry dudes :(");
            }

            return response;
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SocketClient client = new SocketClient(new Message(args));
        client.makeRequest();
    }
}
