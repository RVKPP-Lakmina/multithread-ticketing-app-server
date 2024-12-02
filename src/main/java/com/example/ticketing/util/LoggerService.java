package com.example.ticketing.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.*;

import com.example.ticketing.model.User;
import com.example.ticketing.util.interfaces.IEventLogger;

import java.awt.*;

/**
 * LoggerService is a utility class that provides a simple logging mechanism
 * with a graphical user interface (GUI) to display log messages in real-time.
 * It uses a separate thread to process log messages and a Swing window to
 * display them.
 */
public class LoggerService implements IEventLogger {
    // A thread-safe queue to store log messages.
    private final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    // A flag to control the running state of the logger thread.
    private volatile boolean running = true;

    /**
     * Constructs a LoggerService instance, initializes the logger window,
     * and starts the logger thread.
     */
    public LoggerService() {
        // Create the logger window on the Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(this::createLoggerWindow);
        // Start the logger thread to process log messages.
        Thread loggerThread = new Thread(this::processLogs);
        loggerThread.setDaemon(true);
        loggerThread.start();
    }

    // The text area to display log messages.
    private JTextArea logArea;
    private JFrame frame;

    /**
     * Creates and displays the logger window with a text area to show log messages.
     */
    private void createLoggerWindow() {
        this.frame = new JFrame("Logger");
        logArea = new JTextArea(20, 50);
        logArea.setEditable(false);
        frame.add(new JScrollPane(logArea), BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Processes log messages from the queue and appends them to the log area.
     * This method runs in a separate thread.
     */
    private void processLogs() {
        while (running || !logQueue.isEmpty()) {
            try {
                // Take a log message from the queue.
                String logMessage = logQueue.take();
                // Append the log message to the log area on the EDT.
                SwingUtilities.invokeLater(() -> logArea.append(logMessage + "\n"));
            } catch (InterruptedException e) {
                // Restore the interrupted status.
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Adds a log message to the queue to be processed and displayed.
     *
     * @param message the log message to be added
     */
    public void log(String message) {
        logQueue.offer(message);
    }

    public void log(String message, User user) {
    }

    public void error(String message) {
        logQueue.offer(message);
    }

    /**
     * Stops the logger thread gracefully.
     */
    public void stop() {
        this.frame.dispose();
        running = false;
    }
}