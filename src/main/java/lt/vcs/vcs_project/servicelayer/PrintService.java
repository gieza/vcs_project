package lt.vcs.vcs_project.servicelayer;

public class PrintService {
    public static void printUnderLineForString(String forUnderlining) {
        int underlineLength = forUnderlining.length() + 1;
        for (int i = 0; i < underlineLength; i++)
            System.out.print("=");
        System.out.println();

    }
}
