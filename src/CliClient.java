public class CliClient {
    SocketClient socket;

    public static void main(String[] args) {
        CliClient cliClient = new CliClient();
        cliClient.runCommand(args);
    }

    public void runCommand(String[] args) {
        Action action = null;
        try {
            action = Action.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Usage: java CliClient <action> ...");
//            System.out.println("Usage: RTFM");
            System.exit(1);
        }

        // Check args validity
        switch (action) {
            case LIST:
                String hostname = args[2];
                int port = Integer.parseInt(args[3]);
                String directory = args[1];

                list(hostname, port, directory);
                break;
            default:

        }
    }

    public String[] list(String hostname, int port, String directory) {
        Message<X> request = new Message(new Head(), null);

//        Response response = socket.makeRequest(request);
        Message<X> response = socket.makeRequest(request);

    }
}
