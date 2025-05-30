package com.proyecto.sistemas_op_umg2025.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class PasswordEncryptor {
    // Clave secreta de 128 bits (debes guardar esta clave en un lugar seguro)
    private static final String SECRET_KEY = "1234567890123456"; // 16 caracteres = 128 bits

    // Encriptar
    public static String encrypt(String password) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encrypted = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // Desencriptar
    public static String decrypt(String encryptedPassword) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decoded = Base64.getDecoder().decode(encryptedPassword);
        byte[] decrypted = cipher.doFinal(decoded);

        return new String(decrypted);
    }
}
