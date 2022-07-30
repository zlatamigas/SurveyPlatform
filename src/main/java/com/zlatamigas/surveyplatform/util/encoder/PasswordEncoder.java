package com.zlatamigas.surveyplatform.util.encoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Custom password encoder.
 */
public final class PasswordEncoder {

    private static final String ENCRYPTION_METHOD = "SHA-1";
    private static final int BASE = 16;
    private static final int BIT_32 = 32;
    private static final char ADDITIONAL_SYMBOL = '0';

    /**
     * Encode password.
     *
     * @param password Password to encrypt.
     * @return Encrypted password.
     * @throws NoSuchAlgorithmException This exception is thrown when a particular cryptographic algorithm is requested but is not available in the environment.
     */
    public String encode(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance(ENCRYPTION_METHOD);
        byte[] messageDigest = md.digest(password.getBytes());

        StringBuilder encryptedPassword = new StringBuilder((new BigInteger(1, messageDigest)).toString(BASE));
        while (encryptedPassword.length() < BIT_32) {
            encryptedPassword.insert(0, ADDITIONAL_SYMBOL);
        }

        return encryptedPassword.toString();
    }
}
