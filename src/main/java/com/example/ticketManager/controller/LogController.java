package com.example.ticketManager.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.util.List;
import java.io.IOException;
import java.util.Comparator;

@RestController
public class LogController {

    private final String logDirectory = "logs"; // Path to log directory

    @GetMapping("/api/logger")
    public ResponseEntity<Resource> getLatestLog() throws IOException {
        // Get the latest log file (sorted by file name)
        try (Stream<Path> logFiles = Files.list(Paths.get(logDirectory))) {
            List<Path> sortedLogs = logFiles
                    .filter(Files::isRegularFile)
                    .sorted(Comparator.comparing(Path::getFileName).reversed())
                    .toList();

            if (sortedLogs.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Get the latest log file
            Path latestLogFile = sortedLogs.get(0);
            Resource resource = new UrlResource(latestLogFile.toUri());

            // Return the log file as a downloadable resource
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + latestLogFile.getFileName().toString() + "\"")
                    .body(resource);
        }
    }
}
