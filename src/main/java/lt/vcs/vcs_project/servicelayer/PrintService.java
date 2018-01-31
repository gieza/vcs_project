package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.utils.ScannerUtils;

class PrintService {
    static void printUnderLineForString(String forUnderlining) {
        int underlineLength = forUnderlining.length() + 1;
        for (int i = 0; i < underlineLength; i++)
            System.out.print("=");
        System.out.println();

    }

    static String askForNewPassword() {
        System.out.println("Please, enter new password:");
        return ScannerUtils.scanString();
    }
}
