import javax.crypto.NoSuchPaddingException;
import java.security.GeneralSecurityException;

public interface EncryptionStrategy {
    byte[] encrypt(byte[] plainText, String password) throws GeneralSecurityException;
    byte[] decrypt(byte[] cipherText, String password) throws GeneralSecurityException;
}
