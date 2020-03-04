import java.net.*;
import java.io.*;

public class SocketClient {
    public Message makeRequest(Message request) throws IOException {
        System.out.println("Connecting socket to host" + request.getHead().getHostname() + " and port: " + request.getHead().getPort());
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
            System.out.println("client has proble.mmmmmm cant even getinputstream >:( (IOException)");
            e.printStackTrace();
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.out.println("client has class not found exception >:(");
            e.printStackTrace();
            System.exit(0);
        }

        return null;
    }
}
