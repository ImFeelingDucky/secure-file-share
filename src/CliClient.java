import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CliClient {
    private SocketClient socketClient = new SocketClient();
    private static String usage = "Usage: java CliClient <action> <hostname> <port> <commandArguments>" +
            "\nwhere action is one of: upload, download, list.";

    public static void main(String[] args) {
        CliClient cliClient = new CliClient();
        cliClient.runCommand(args);
    }

    public void runCommand(String[] args) {
        Action action = null;

        try {
            action = Action.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(usage);
//            System.out.println("Usage: RTFM");
            System.exit(1);
        }

//        Generically:
//        java CliClient <action> <hostname> <port> <commandArguments>

//        With our current set of three actions:
//        java CliClient <action> <hostname> <port> <directory> [filename]


        String hostname = args[1];
        int port = Integer.parseInt(args[2]);

        String directory = args[3];

        Map<String, String> arguments = new HashMap<>();
        arguments.put("directory", directory);

        byte[] body = null;

        // Check args validity
        switch (action) {
            case LIST:
                break;
            case UPLOAD:
                String uploadFilename = args[4];
                Path path = Paths.get(uploadFilename);

                arguments.put("filename", path.getFileName().toString());

                try {
                    System.out.println("PATH READING FROM: " + path.toString());
                    body = Files.readAllBytes(path);
                } catch (IOException e) {
                    System.out.println("Could not read file: " + uploadFilename);
                    System.exit(1);
                }

                break;
            case DOWNLOAD:
                String downloadFilename = args[4];
                arguments.put("filename", downloadFilename);

                break;
            default:
                System.out.println(usage);
        }

        Message request = new Message(new Head(hostname, port, action, arguments), body);

        // Then we send the request and wait, then the server sends its response back to us
        try {
            Message response = socketClient.makeRequest(request);
            handleResponse(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleResponse(Message request, Message response) throws IOException {
        if (!response.getHead().isSuccessful()) {
            System.out.println("The server encountered an error processing your request.");
            System.exit(1);
        }

        switch (response.getHead().get("Content-Type")) {
            case "text":
                System.out.println("Got text from server:");
                System.out.println(new String(response.getBody(), StandardCharsets.UTF_8));
                break;
            case "file":
                System.out.println("Got file from server:");
                System.out.println(new String(response.getBody(), StandardCharsets.UTF_8));

                Path path = Paths.get(System.getProperty("user.dir"), "client-files", request.getHead().get("directory"), request.getHead().get("filename"));
                System.out.println("Writing to file: " + path.toString());

                Files.createDirectories(path.getParent());
                byte[] encryptedBytes = response.getBody();

                System.out.println("Please enter your file's password:");
                Scanner scanner = new Scanner(System.in);
                String password = scanner.nextLine();

//                TODO: Apply password and decrypt downloaded file
//                byte[] decryptedBytes = AesEncryptionStrategy.decrypt(encryptedBytes, password);

//                Files.write(path, decryptedBytes);
                Files.write(path, encryptedBytes);
                break;
            case "none":
                break;
        }
    }
}
