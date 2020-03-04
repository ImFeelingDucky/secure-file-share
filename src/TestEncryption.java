import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class TestEncryption {
    public static void main(String[] args) {
        try {
            byte[] plainText = "Hello mom".getBytes(StandardCharsets.UTF_8);
            System.out.println("plainText: " + new String(plainText, StandardCharsets.UTF_8));

            byte[] cipherText = AesEncryptionStrategy.encrypt(plainText, "bigEpic");
            System.out.println("cipherText: " + new String(cipherText));

            byte[] decryptedCipherText = AesEncryptionStrategy.decrypt(cipherText, "bigEpic");
            System.out.println("decryptedCipherText: " + new String(decryptedCipherText, StandardCharsets.UTF_8));

            System.exit(0);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

}
