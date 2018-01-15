package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.DataLayer;
import lt.vcs.vcs_project.utils.ScannerUtils;

public class OperationsCourse {


    public static String selectCourse() {
        System.out.printf("\n\nEnter to Course Code to select Course:");
        String userInput = ScannerUtils.scanString();
        if (DataLayer.courseExists(userInput)) {
            System.out.printf("Selected Course: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, Course %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }
}
