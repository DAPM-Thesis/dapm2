package com.dapm2.ingestion.utils;

public class AttributeSanitizer {
    public static String sanitize(String raw) {
        return raw.replaceAll("[:=]", "_")  // Replace JXES-breaking characters
                .replaceAll("\\n", " ")   // Remove newlines
                .replaceAll("[\\r\\t]", " ")
                .trim();
    }
}
