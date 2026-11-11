package kz.iitu.springlab.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String password = "User123!";

        String hash = encoder.encode(password);

        System.out.println("New hash: " + hash);
        System.out.println("Password matches: "
                + encoder.matches(password, hash));
    }
}