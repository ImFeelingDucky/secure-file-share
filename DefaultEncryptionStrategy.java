import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
 

public class DefaultEncryptionStrategy {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
 
    public static void encrypt(String password, File inputFile, File outputFile)
            throws EncryptionException {
        doEncryption(Cipher.ENCRYPT_MODE, password, inputFile, outputFile);
    }
 
    public static void decrypt(String password, File inputFile, File outputFile)
            throws EncryptionException {
        doEncryption(Cipher.DECRYPT_MODE, password, inputFile, outputFile);
    }
 
    private static void doEncryption(int cryptionMode, String password, File inputFile,
            File outputFile) throws EncryptionException {
        try {
            Key secretKey = new SecretKeySpec(password.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cryptionMode, secretKey);
             
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
             
            byte[] outputBytes = cipher.doFinal(inputBytes);
             
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
             
            inputStream.close();
            outputStream.close();
             
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new EncryptionException("Error encrypting/decrypting file", ex);
        }
    }
}