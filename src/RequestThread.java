import java.io.*;
import java.net.Socket;
import java.util.List;

public class RequestThread extends Thread {
    private Socket socket;
    private SocketServer server;
    private PrintWriter writer;
    private Message<Object> request;

    public RequestThread(Socket socket, SocketServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
//
            request = (Message<Object>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Oops :( " + e.getMessage());
        }

        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

//            PrintStream output = new PrintStream(socket.getOutputStream());
//            writer = new PrintWriter(output, true);
//            Action action = server.getRequest(socket);
//            Action action = message.getAction();

            // Option 1
            Action action = request.getAction();

            String directory = request.getFromHead("directory");

            switch (action) {
                case LIST:
                    ServerActions.list(directory);
                    break;
                case UPLOAD:
                    InputStream fileToUpload = (InputStream) request.getBody();
//                    ServerActions.upload();
            }

            // Change this so that it gets the argument for the command

            server.closeRequest(this);

            socket.close();

        } catch (IOException ex) {
            System.out.println("Error in RequestThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
} 
