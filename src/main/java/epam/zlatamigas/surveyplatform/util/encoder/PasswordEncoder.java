package epam.zlatamigas.surveyplatform.util.encoder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Custom password encoder.
 */
public final class PasswordEncoder {

    private static final String ENCRYPTION_METHOD = "SHA-1";

    private static final byte[] SEED = new byte[]{1, 43, 32, 2, 2, 1, 43, 32, 2, 79, 1, 43, 32, 2, 2, 29};

    private static final int BASE = 16;
    private static final int PASSWORD_LENGTH = 20;
    private static final BigInteger MOD_BASE = new BigInteger("100000000000000000000");
    private static final char ADDITIONAL_SYMBOL = '0';

    /**
     * Encode password.
     *
     * @param password Password to encrypt.
     * @return Encrypted password.
     * @throws NoSuchAlgorithmException This exception is thrown when a particular cryptographic algorithm is requested but is not available in the environment.
     */
    public String encode(String password) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION_METHOD);
        messageDigest.update(SEED);
        messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] encodedBytes = messageDigest.digest();

        BigInteger bigInteger = new BigInteger(1, encodedBytes);
        bigInteger = bigInteger.mod(MOD_BASE);

        StringBuilder encodedPassword = new StringBuilder(bigInteger.toString(BASE));

        while (encodedPassword.length() < PASSWORD_LENGTH) {
            encodedPassword.append(ADDITIONAL_SYMBOL);
        }

        return encodedPassword.toString();
    }
}
