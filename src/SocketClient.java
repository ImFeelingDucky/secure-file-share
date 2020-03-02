import java.net.*;
import java.io.*;
 
public class SocketClient {
    private Arguments arguments;
 
    public SocketClient(Arguments arguments) {
        this.arguments = arguments;
    }
 
    public void execute() {
      try {
        if (!arguments.isValid()) {return;}

        Socket socket = new Socket(arguments.getHostname(), arguments.getPort());
        
        PrintStream output = new PrintStream(socket.getOutputStream(), true);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(arguments);

        System.out.println("Connected to the file server");

      } catch (UnknownHostException ex) {
        System.out.println("Server not found: " + ex.getMessage());
      } catch (IOException ex) {
        System.out.println("I/O Error: " + ex.getMessage());
      }
    }

    public static void main(String[] args) {
      SocketClient client = new SocketClient(new Arguments(args));
      client.execute();
    }
}
