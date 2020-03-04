import java.net.*;
import java.io.*;

/**
 * Manages packing and sending requests to a server and unpacking the server's response.
 */
public class SocketClient {
    public Message makeRequest(Message request) throws IOException {
        System.out.println("Connecting socket to host '" + request.getHead().getHostname() + "' at port '" + request.getHead().getPort() + "'");
        Socket socket = new Socket(request.getHead().getHostname(), request.getHead().getPort());

        System.out.println("Connected to the file server...");

        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject(request);
        System.out.println("Sent request to file server...");

//      Read the response back from the server as a Message

        try {
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            return (Message) is.readObject();
        } catch (IOException e) {
            System.out.println("Could not read response from server.");
            e.printStackTrace();
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find the Message class.");
            e.printStackTrace();
            System.exit(0);
        }

        return null;
    }
}
