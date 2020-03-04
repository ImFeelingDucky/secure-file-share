import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.lang.*;

public class SocketServer {
    private int port;

    public SocketServer(int port) {
        this.port = port;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("File server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                RequestThread2 newRequest = new RequestThread2(socket);
                newRequest.start();
            }

        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: java SocketServer <port-number>");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

        SocketServer server = new SocketServer(port);
        server.execute();
    }
}
