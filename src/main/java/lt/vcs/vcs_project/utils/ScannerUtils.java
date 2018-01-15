package lt.vcs.vcs_project.utils;

import java.util.Scanner;

public class ScannerUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String scanString() {
        return scanner.nextLine();
    }

    public static void waitForEnter() {
        System.out.println("\npress Enter to continue...");
        scanString();
    }
}
