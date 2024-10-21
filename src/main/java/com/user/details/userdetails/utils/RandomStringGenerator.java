package com.user.details.userdetails.utils;

import java.util.Random;

public class RandomStringGenerator {

    private RandomStringGenerator() {

    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();
    private static final int length = 125;

    /**
     * This method is Used to generate Random String of particular length
     * which will be used as a Token
     *
     * @return token
     */
    public static String generateRandomString() {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            builder.append(CHARACTERS.charAt(index));
        }
        return builder.toString();
    }
}
