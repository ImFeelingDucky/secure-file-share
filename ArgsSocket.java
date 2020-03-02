/**
 * A class that extends the Socket class to allow a socket object to carry other
 * arguments
 * @author Sean Bowman
 **/

import java.net.*;
import java.io.*;
import java.util.*;

public class ArgsSocket extends Socket {
  // Data fields
  private Arguments arguments;
  
  public ArgsSocket(String hostname, int port, Arguments arguments) throws IOException {
    super(hostname, port);
    this.arguments = arguments;
  }

  /**
   * Returns the arguments passed with the socket
   * @return return the arguments
   **/
  public Arguments getArguments() {return arguments;}
}
