import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.lang.*;

public class SocketServer {
    private int port;
//    private Set<RequestThread> threads = new HashSet<>();

    public SocketServer(int port) {
        this.port = port;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("File server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

//                TODO: Remove this chunk of test code
                RequestThread2 newRequest = new RequestThread2(socket);
////                threads.add(newRequest);
                newRequest.start();








//                TODO: Remove this after debug
//                Ideally we do this in RequestThread

//                Message request = null;
//                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
//                try {
//                    request = (Message) inputStream.readObject();
//                } catch (ClassNotFoundException e) {
//                    System.out.println("OOps baby baby... I can't read a Message request from the clieeeeeeeent :'(");
//                    System.exit(1);
//                }








//                Debugging for a while:
//                Attempt 1: this works
//                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//                outputStream.writeObject(request.getHead().getHostname());

//                  Attempt 2: trying out sendign a MEssage not a String. SUCCESFUL
//                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//                outputStream.writeObject(request);

//                Attempt 3: Send a message across with a hardcoded byte[] body. SUCCESSFUL
//                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//                byte[] bytes = new byte[2];
//                bytes[0] = 72; // H
//                bytes[1] = 105; // i
//
//                Map<String, String> arguments = new HashMap<>();
//                arguments.put("Content-Type", "text");
//                Message response = new Message(new Head(request.getHead().getHostname(), request.getHead().getPort(), Action.LIST, arguments), bytes);
//                outputStream.writeObject(response);
//
//                Attempt 4:
//                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//
//                byte[] body = new byte[0];
//                try {
//                    body = Files.readAllBytes(Paths.get(RequestThread.FILES_DIRECTORY, "max", "a.txt"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Map<String, String> arguments = new HashMap<>();
//                arguments.put("Content-Type", "file");
//                Message response = new Message(new Head(request.getHead().getHostname(), request.getHead().getPort(), Action.LIST, arguments), body);
//                outputStream.writeObject(response);

                // TODO: Deleet e the debugging shit above! >:)
//                RequestThread newRequest = new RequestThread(socket);
////                threads.add(newRequest);
//                newRequest.start();
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

    /**
     * When a client is disconnected, removes the associated thread
     */
//    public void closeRequest(RequestThread request) {
//        threads.remove(request);
//        System.out.println("A request was closed");
//    }
}
