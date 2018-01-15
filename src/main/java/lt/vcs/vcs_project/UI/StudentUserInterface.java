package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.servicelayer.OperationsStudent;
import lt.vcs.vcs_project.servicelayer.PrintingStudent;
import lt.vcs.vcs_project.utils.ScannerUtils;

import static lt.vcs.vcs_project.UI.StudentUIMenuDefinition.*;
import static lt.vcs.vcs_project.datalayer.DataLayer.accounts;

public class StudentUserInterface implements UserInterface {
    static String menuPosition = "TOP";
    static String menuChoice = "";


    private String currentAccount;
    private String currentStudentId;

    @Override
    public void navigateMenu(String accountId) {
        currentAccount = accountId;
        currentStudentId = accounts.getAccount(currentStudentId).getPersonalId();
        menuChoice = "";
        menuPosition = "TOP";
        while (true) {
            printMenuOptions();
            menuChoice = ScannerUtils.scanString();
            if (menuNavigation.get(menuPosition, menuChoice).equals("LOGOUT")) {
                break;      //logout - no need to proceed further
            } else {
                runDecision(menuPosition, menuChoice);
            }
        }
    }

    private void runDecision(String menu, String subMenu) {
        String decision = menuNavigation.get(menu, subMenu);
        //System.out.printf("Next action: %s\n\n", decision);
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
            case "PRINT_STUDENT":
                PrintingStudent.printStudent(currentStudentId);
                menuPosition = "STUDENT";
                break;
            case "UPDATE_STUDENT":
                OperationsStudent.updateStudent(currentAccount);
                menuPosition = "STUDENT";
                break;
            default:
                menuPosition = "TOP";
        }
    }

    private void printMenuOptions() {
        System.out.print("Logged-in User: " + currentAccount);
        System.out.print("Student ID: " + currentStudentId);
        System.out.print(menuPosition + " Menu\n============================================\n" +
                "Enter number to select one of the following:\n");
        System.out.printf(menuOptions.get(menuPosition));
        //System.out.printf(menuOptions.get(menuPosition));
    }

}
