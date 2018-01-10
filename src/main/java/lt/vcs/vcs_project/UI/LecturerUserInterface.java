package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.backend.Account;
import lt.vcs.vcs_project.backend.Backend;
import lt.vcs.vcs_project.backend.Lecturer;
import lt.vcs.vcs_project.backend.Student;
import lt.vcs.vcs_project.utils.ScannerUtils;

public class LecturerUserInterface implements UserInterface {
    static String menuPosition = "TOP";
    static String menuChoice = "";


    private String currentAccount;

    @Override
    public void open(String accountId) {
        currentAccount = accountId;
        menuChoice = "";
        menuPosition = "TOP";
        while (true) {
            //todo refactor to be only in backend login
            System.out.printf("\nHello %s %s,\n", Backend.getFirstName(accountId), Backend.getSecondName(accountId));
            printMenuOptions();
            menuChoice = ScannerUtils.scanString();
            if (menuChoice.equals("9") && menuPosition.equals("TOP")) {
                break;
            } else {
                runDecision(menuPosition, menuChoice);
            }
        }
    }

    private void runDecision(String menu, String subMenu) {
        String decision = LecturerUIMenuHash.menuNavigation.get(menu, subMenu);
        System.out.printf("Next action: %s\n\n", decision);
        if (decision == null) {
            menuPosition = "TOP";
            return;
        }
        switch (decision) {
            case "ACCOUNT":
            case "STUDENT":
            case "LECTURER":
            case "COURSE":
            case "TOP":
                menuPosition = decision;
                break;
            case "PRINT_LECTURER":
                printLecturer();
                menuPosition = "LECTURER";
                break;
            case "UPDATE_LECTURER":
                updateLecturer();
                menuPosition = "LECTURER";
                break;
            default:
                menuPosition = "TOP";
        }
    }

    private void printMenuOptions() {
        System.out.printf("%s Menu\n============================================\n" +
                "Enter number to select one of the following:\n", menuPosition);
        System.out.printf(LecturerUIMenuHash.menuOptions.get(menuPosition));
        //System.out.printf(menuOptions.get(menuPosition));
    }


    private void printLecturer() { //todo:not implemented
        Backend.printAccount(currentAccount);

    }


    private void updateLecturer() {//todo:not implemented

        System.out.printf("\nSorry, Lecturer update functionally not implemented.\n\treturning to menu\n\n");
    }


}