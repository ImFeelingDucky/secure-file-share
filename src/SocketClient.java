import java.net.*;
import java.io.*;
import java.rmi.activation.UnknownObjectException;

public class SocketClient {
    private Message request;
    private Message response;

    public Message makeRequest(Message request) throws IOException {
//        try {
        Socket socket = new Socket(request.getHead().getHostname(), request.getHead().getPort());
//        } catch (UnknownHostException e) {
//            System.out.println("Could not connect to host.");
//            System.exit(1);
//        }

        System.out.println("Connected to the file server...");

        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject(request);
        System.out.println("Sent request to file server...");

//      Read the response back from the server as a Message<X>

        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            response = (Message) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Sorry dudes :(");
        }

        return response;
    }
}
