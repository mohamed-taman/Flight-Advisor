package org.siriusxi.htec.fa.infra.security.password;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * A random password generator.
 *
 * @author Mohamed Taman
 */
@Log4j2
@Component
public final class PasswordGenerator {
    
    
    /**
     * The size of generated passwords
     */
    private static final int SIZE = 8;
    /**
     * The characters composing generated passwords.
     * The generators randomly picks from these characters.
     */
    private static final char[] CHARACTERS =
        ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_$%?/+=.<>#*")
            .toCharArray();
    private static SecureRandom random;
    
    static {
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            log.error("", e);
        }
    }
    
    private PasswordGenerator() {
    }
    
    public static String sha1Random() {
        var builder = new StringBuilder(SIZE);
        for (int i = 0; i < SIZE; i++)
            builder.append(CHARACTERS[random.nextInt(CHARACTERS.length)]);
        
        return builder.toString();
    }
    
    public static String bcrypt(String password) {
        return new BCryptPasswordEncoder()
                   .encode(password);
    }
}
