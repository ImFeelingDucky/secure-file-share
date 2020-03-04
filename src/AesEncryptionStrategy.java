import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class AesEncryptionStrategy implements EncryptionStrategy {
    private static final String CIPHER_TYPE = "AES";
    private static final String CIPHER_SPECIFICATION = "AES/CBC/PKCS5Padding";

    private static byte[] generate16RandomBytes() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);

        return bytes;
    }

    private static Key prepareKey(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 3, 128 * 8);
        return SecretKeyFactory.getInstance("AES").generateSecret(spec);
    }

    public byte[] encrypt(byte[] plainText, String password) throws GeneralSecurityException {
        byte[] iv = generate16RandomBytes();
        byte[] salt = generate16RandomBytes();

        Key key = prepareKey(password, salt);
        Cipher cipher = Cipher.getInstance("AES");

//        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherText = cipher.doFinal(plainText);

        byte[] cipherTextWithIvAndSalt = new byte[cipherText.length + 2 * 16];

        System.arraycopy(iv, 0, cipherTextWithIvAndSalt, 0, 16);
        System.arraycopy(salt, 0, cipherTextWithIvAndSalt, 16, 16);
        System.arraycopy(cipherText, 0, cipherTextWithIvAndSalt, 32, cipherText.length);

        return cipherTextWithIvAndSalt;
    }

    public byte[] decrypt(byte[] cipherTextWithIvAndSalt, String password) throws GeneralSecurityException {
        byte[] iv = Arrays.copyOfRange(cipherTextWithIvAndSalt, 0, 16);
        byte[] salt = Arrays.copyOfRange(cipherTextWithIvAndSalt, 16, 32);
        byte[] cipherText = Arrays.copyOfRange(cipherTextWithIvAndSalt, 32, cipherTextWithIvAndSalt.length);

        Key key = prepareKey(password, salt);
        Cipher cipher = Cipher.getInstance("AES");

//        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] plainText = cipher.doFinal(cipherText);

        return plainText;
    }
}
