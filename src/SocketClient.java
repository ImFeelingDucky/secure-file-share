import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.rmi.activation.UnknownObjectException;

public class SocketClient {
    public Message makeRequest(Message request) throws IOException {
//        try {
        System.out.println("Connecting socket to host" + request.getHead().getHostname() + " and port: " + request.getHead().getPort());
        Socket socket = new Socket(request.getHead().getHostname(), request.getHead().getPort());
//        } catch (UnknownHostException e) {
//            System.out.println("Could not connect to host.");
//            System.exit(1);
//        }

        System.out.println("Connected to the file server...");

        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject(request);
        System.out.println("Sent request to file server...");

//      Read the response back from the server as a Message

        try {
//            try this baby
//            Attempt 1: get a string across. Successful
//            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
//            String IReadThis = (String) is.readObject();
//            expeting this to be what we write to the socket in SocketServer: request.getHead().getHostname()
//            System.out.println(IReadThis);

//            Attempt 2: Get a Message across
//            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
//            Message bouncedRequest = (Message) is.readObject();
//            System.out.println("We bounced a request to server and got back (its hostname): " + bouncedRequest.getHead().getHostname());

            //            Attempt 3: Get a Message across with a body
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Message response = (Message) is.readObject();
            System.out.println("We got a custom made response from server (with body bytes): " + new String(response.getBody(), StandardCharsets.UTF_8));
            System.out.println("response has Content-Type: " + response.getHead().get("Content-Type"));

        } catch (IOException e) {
            System.out.println("client has proble.mmmmmm cant even getinputstream >:( (IOException)");
            e.printStackTrace();
            System.exit(0);
        } catch ( ClassNotFoundException e) {
            System.out.println("client has class not found exception >:(");
            e.printStackTrace();
            System.exit(0);

        }

        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
//        TODO: Uncomment this next line and remove the following line
//            return (Message) ois.readObject();
            return new Message(null, null);
//        } catch (ClassNotFoundException | IOException e) {
        } catch (IOException e) {
            System.out.println("Sorry dudes :( Problem reading response from server");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
}
