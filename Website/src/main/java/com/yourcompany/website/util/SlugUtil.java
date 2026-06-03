package com.yourcompany.website.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class SlugUtil {

    public static String toSlug(String input) {
        if (input == null || input.isEmpty()) return "";

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String noAccent = pattern.matcher(normalized).replaceAll("")
                .replaceAll("đ", "d").replaceAll("Đ", "D");

        return noAccent.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .trim();
    }

    public static String generateUniqueSlug(String name, boolean exists) {
        String slug = toSlug(name);
        if (exists) {
            slug = slug + "-" + System.currentTimeMillis();
        }
        return slug;
    }
}