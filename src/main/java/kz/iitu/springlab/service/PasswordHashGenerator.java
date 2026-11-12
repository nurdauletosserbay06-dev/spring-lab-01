
package kz.iitu.springlab.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "User123!";

        String hash1 = encoder.encode(password);
        String hash2 = encoder.encode(password);

        System.out.println("Hash 1: " + hash1);
        System.out.println("Hash 2: " + hash2);

        System.out.println("Hashes are different: "
                + !hash1.equals(hash2));

        System.out.println("Hash 1 matches: "
                + encoder.matches(password, hash1));

        System.out.println("Hash 2 matches: "
                + encoder.matches(password, hash2));
    }
}
