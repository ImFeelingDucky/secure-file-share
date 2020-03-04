import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class TestEncryption {
    public static void main(String[] args) {
        try {
            EncryptionStrategy encryption = new AesEncryptionStrategy();

            byte[] plainText = "Hello mom".getBytes(StandardCharsets.UTF_8);
            System.out.println("plainText: " + new String(plainText, StandardCharsets.UTF_8));

            byte[] cipherText = encryption.encrypt(plainText, "bigEpic");
            System.out.println("cipherText: " + new String(cipherText));

            byte[] decryptedCipherText = encryption.decrypt(cipherText, "bigEpic");
            System.out.println("decryptedCipherText: " + new String(decryptedCipherText, StandardCharsets.UTF_8));

            System.exit(0);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

}
