package com.biestro.chioscoSmoothie;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "smoothie_errors.log";

    public static void logError(String error) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            writer.write(timestamp + " - " + error + "\n");
        } catch (IOException e) {
            System.err.println("Errore durante il logging: " + e.getMessage());
        }
    }
}