import java.net.*;
import java.io.*;
 
/**
 * This is the chat client program.
 * Type 'bye' to terminte the program.
 *
 * @author www.codejava.net
 */
public class SocketClient {
    private Arguments arguments;
 
    public SocketClient(Arguments arguments) {
        this.arguments = arguments;
    }
 
    public void execute() {
      try {
        if (!arguments.isValid()) {return;}

        ArgsSocket socket = new ArgsSocket(arguments.getHostname(), arguments.getPort(), arguments);

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
