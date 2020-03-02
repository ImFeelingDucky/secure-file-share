import java.io.File;

public interface EncryptionStrategy {
    File encrypt(String password, File inputFile, File outputFile);
    File decrypt(String password, File inputFile, File outputFile);
}
