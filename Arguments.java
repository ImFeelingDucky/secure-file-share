/**
 * A class for easily containing the arguments of the command line
 * @author Sean Bowman
 **/

import java.util.*;
import java.io.*;

public class Arguments {
  // Data fields  
  private String request;
  private String directory;
  private String filename;
  private String hostname;
  private int port;
  private boolean valid;

  /** 
   * Constructor 
   **/
  public Arguments(String[] args) {
    request = args[0];
    directory = args[1];
    filename = args[2];
    hostname = args[3];
    port = Integer.parseInt(args[4]);

    // Check args validity
    if (request.equals("download") || request.equals("list") || request.equals("upload")) {
      valid = true;
    }
    else {valid = false;}

    if (request.equals("list")){
      hostname = args[2];  
      port = Integer.parseInt(args[4]);
    }
  }

  /**
   * Returns a boolean based on if the set of args is valid or not
   * @return true for valid and false for invalid
   **/
  public boolean isValid () {return valid;}

  
  // Getter methods to return the data fields to caller
  public String getRequest() {return request;}
  public String getDirectory() {return directory;}
  public String getFilename() {return filename;}
  public String getHostname() {return hostname;}
  public int getPort() {return port;}
}
