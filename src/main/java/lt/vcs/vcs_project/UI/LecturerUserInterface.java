package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.servicelayer.OperationsLecturer;
import lt.vcs.vcs_project.servicelayer.PrintingLecturer;
import lt.vcs.vcs_project.utils.ScannerUtils;

import static lt.vcs.vcs_project.UI.LecturerUIMenuDefinition.*;
import static lt.vcs.vcs_project.datalayer.DataLayer.accounts;

public class LecturerUserInterface implements UserInterface {
    static String menuPosition = "TOP";
    static String menuChoice = "";


    private String currentAccount;
    private String currentLecturerId;

    @Override
    public void navigateMenu(String accountId) {
        currentAccount = accountId;
        currentLecturerId = accounts.getAccount(currentAccount).getPersonalId();
        menuChoice = "";
        menuPosition = "TOP";
        while (true) {
            printMenuOptions();
            menuChoice = ScannerUtils.scanString();
            if (menuNavigation.get(menuPosition, menuChoice).equals("LOGOUT")) {
                break;
            } else {
                runDecision(menuPosition, menuChoice);
            }
        }
    }

    private void runDecision(String menu, String subMenu) {
        String decision = menuNavigation.get(menu, subMenu);
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
                PrintingLecturer.printLecturer(currentLecturerId);
                menuPosition = "LECTURER";
                break;
            case "UPDATE_LECTURER":
                OperationsLecturer.updateLecturer(currentLecturerId);
                menuPosition = "LECTURER";
                break;
            default:
                menuPosition = "TOP";
        }
    }

    private void printMenuOptions() {
        System.out.print("Logged-in User: " + currentAccount);
        System.out.print("Lecturer ID: " + currentLecturerId);
        System.out.print(menuPosition + " Menu\n============================================\n" +
                "Enter number to select one of the following:\n");
        System.out.printf(menuOptions.get(menuPosition));
        //System.out.printf(menuOptions.get(menuPosition));
    }

}
