import java.io.*;
import java.net.Socket;

public class RequestThread extends Thread {
    private Socket socket;
    private SocketServer server;
    private PrintWriter writer;
 
    public RequestThread(Socket socket, SocketServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            PrintStream output = new PrintStream(socket.getOutputStream());
            writer = new PrintWriter(output, true);
            
            
 
            // Change this so that it gets the argument for the command
            String clientMessage;
            server.printArgs(socket);

            server.removeUser(this);
 
            socket.close();
 
 
        } catch (IOException ex) {
            System.out.println("Error in RequestThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
} 
