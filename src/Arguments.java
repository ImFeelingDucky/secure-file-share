/**
 * A class for easily containing the arguments of the command line
 *
 * @author Sean Bowman
 **/

import java.util.*;
import java.io.*;

@SuppressWarnings("serial")
public class Arguments implements Serializable {
    // Data fields
    private Action request;
    private String directory;
    private String filename;
    private String hostname;
    private int port;
    private boolean valid;
    private String[] allArgs;

    /**
     * Constructor
     **/
    public Arguments(String[] args) {
        allArgs = args;
        request = Action.valueOf(args[0].toUpperCase());

        // Check args validity
        if (request.equals("download") || request.equals("list") || request.equals("upload")) {
            valid = true;
        } else {
            valid = false;
        }

        directory = args[1];

        if (request.equals("list")) {
            hostname = args[2];
            port = Integer.parseInt(args[3]);
        } else {
            filename = args[2];
            hostname = args[3];
            port = Integer.parseInt(args[4]);
        }
    }

    /**
     * Returns a boolean based on if the set of args is valid or not
     * @return true for valid and false for invalid
     **/
    public boolean isValid() {
        return valid;
    }

    // Getter methods to return the data fields to caller
    public Action getRequest() {
        return request;
    }

    public String getDirectory() {
        return directory;
    }

    public String getFilename() {
        return filename;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public String toString() {
        String string = "";
        for (int i = 0; i < allArgs.length; i++) {
            string += allArgs[i];
        }
        return string;
    }
}
