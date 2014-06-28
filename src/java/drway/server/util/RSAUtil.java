/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.util;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

/**
 *
 * @author MIRO
 */
public class RSAUtil {

    private static final String XFORM
            = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";

    public static byte[] encrypt(byte[] plaintext, PublicKey pub)
            throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(XFORM);
        cipher.init(Cipher.ENCRYPT_MODE, pub);
        return cipher.doFinal(plaintext);
    }

    public static byte[] decrypt(byte[] ciphertext, PrivateKey pvt)
            throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(XFORM);
        cipher.init(Cipher.DECRYPT_MODE, pvt);
        return cipher.doFinal(ciphertext);
    }

}
