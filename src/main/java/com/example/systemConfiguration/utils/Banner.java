package com.example.systemConfiguration.utils;

public class Banner {
    private static final int WIDTH = 60;
    private static final String YELLOW = "\033[0;33m";
    private static final String RESET = "\033[0m";
    private static final String RED = "\033[0;31m";

    public static void printBanner(String title) {
        String border = "*".repeat(WIDTH);
        String centeredTitle = String.format("* %-58s *", centerText(title, WIDTH - 4));

        System.out.println(YELLOW + border);
        System.out.println(centeredTitle);
        System.out.println(border + RESET);
    }

    public static void errorBanner(String message) {
        System.out.println(RED + message + RESET);
    }

    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }
}
