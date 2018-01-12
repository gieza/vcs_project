package lt.vcs.vcs_project.UI;

import static lt.vcs.vcs_project.utils.ScannerUtils.scanString;

public class UI_common {
    public static void waitForEnter() {
        System.out.println("\npress Enter to continue...");
        scanString();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printUnderLineForString(String forUnderlining) {
        int underlineLength = forUnderlining.length() + 1;
        for (int i = 0; i < underlineLength; i++)
            System.out.print("=");
        System.out.println();

    }
}
