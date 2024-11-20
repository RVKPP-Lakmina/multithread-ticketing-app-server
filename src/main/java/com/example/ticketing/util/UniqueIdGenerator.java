package com.example.ticketing.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UniqueIdGenerator {
    private String uniqueId;

    private UniqueIdGenerator() {
        generateUniqueId();
    }

    public static String getUniqueId() {
        UniqueIdGenerator generator = new UniqueIdGenerator();
        return generator.uniqueId;
    }

    private void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    private void generateUniqueId() {
        // Get the current timestamp
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        // Generate a random UUID
        String randomUUID = UUID.randomUUID().toString().replace("-", "");

        // Combine timestamp and UUID
        String uniqueId = timestamp + randomUUID;

        setUniqueId(shuffleString(uniqueId));
    }

    // Utility method to shuffle a string
    private String shuffleString(String input) {
        // Convert the string into a list of characters
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }

        // Shuffle the list
        Collections.shuffle(characters);

        // Convert the shuffled list back into a string
        StringBuilder shuffled = new StringBuilder();
        for (char c : characters) {
            shuffled.append(c);
        }

        return shuffled.toString();
    }
}
