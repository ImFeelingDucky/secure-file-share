# secure-file-share
The bulk of this repository is the work of one night of coding, with a party in between, in a collaboration between [ImFeelingDucky](https://github.com/ImFeelingDucky) and [14cobra](https://github.com/14cobra) for a university assignment. It does not exemplify coding best-practices and has been made public for public entertainment rather than usage. However, the ideas behind secure-file-share indeed held merit, and further work in underway on a spiritual successor: https://github.com/ImFeelingDucky/quickshare.

More formally: This repository is deprecated and further work on secure filesharing happens at https://github.com/ImFeelingDucky/quickshare.

Previous:
This application allows easy and secure password-based sharing of files through a centralised server model.

Files are encrypted locally, transferred to a server through a TCP socket connection, and stored in their encrypted form on the server. Then, when downloaded, files are transferred to the client, still encrypted, until they arrive on the client computer when they are decrypted locally.

Ensuring encryption and decryption only happens locally means passwords are never sent over the network, and so files' integrity remains guaranteed, as long as the underlying cryptography is trustworthy.

## Usage
For production usage, we will build a `.jar` file. However, until then, you may compile and run the files yourself with `javac`.

For simplicity, you can compile all `.java` files directly in the `src/` directory.
```shell script
javac *.java
``` 

Then, to use the app, you'll need first ensure a server is running that clients can connect to.

To start a server on port 9999, inside `src/`, run
```shell script
java SocketServer 9999
```

To connect a client and start sharing files, issue a command to run one of the following actions: list files, download a file, or upload a file.

Upload a file:
```shell script
java CliClient upload localhost 9999 william test-file.txt
```

List files uploaded for a user:
```shell script
java CliClient list localhost 9999 william
```

Download a file that is currently on the server:
```shell script
java CliClient download localhost 9999 william test-file.txt
```

When uploading a file, you'll be prompted to enter a password. This will encrypt the file, and ensure that even though others may download your file, it's privacy remains.
When downloading a file, you'll need to enter the same password again for it to be decrypted and accessible. 
