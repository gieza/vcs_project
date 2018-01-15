package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.servicelayer.OperationsLecturer;
import lt.vcs.vcs_project.servicelayer.PrintingCourse;
import lt.vcs.vcs_project.servicelayer.PrintingLecturer;
import lt.vcs.vcs_project.utils.ScannerUtils;

import static lt.vcs.vcs_project.UI.LecturerUIMenuDefinition.menuNavigation;
import static lt.vcs.vcs_project.UI.LecturerUIMenuDefinition.menuOptions;
//import static lt.vcs.vcs_project.UI.UI_common.waitForEnter;
import static lt.vcs.vcs_project.datalayer.DataLayer.accounts;
import static lt.vcs.vcs_project.utils.ScannerUtils.waitForEnter;
import static lt.vcs.vcs_project.UI.MenuTitle.*;

public class LecturerUserInterface implements UserInterface {
    static MenuTitle menuPosition = TOP;
    static String menuChoice = "";


    private String currentAccount;
    private String currentLecturerId;

    @Override
    public void navigateMenu(String accountId) {
        currentAccount = accountId;
        currentLecturerId = accounts.getAccount(currentAccount).getPersonalId();
        menuChoice = "";
        menuPosition = TOP;
        while (true) {
            printMenuOptions();
            menuChoice = ScannerUtils.scanString();
            if (menuNavigation.get(menuPosition, menuChoice) != null &&
                    menuNavigation.get(menuPosition, menuChoice) == (LOGOUT)) {
                break;
            } else {
                runDecision(menuPosition, menuChoice);
            }
        }
    }

    private void runDecision(MenuTitle menu, String subMenu) {
        MenuTitle decision = menuNavigation.get(menu, subMenu);
        System.out.printf("Next action: %s\n\n", decision);
        if (decision == null) {
            menuPosition = TOP;
            return;
        }
        switch (decision) {
            case ACCOUNT:
            case STUDENT:
            case LECTURER:
            case COURSE:
            case TOP:
                menuPosition = decision;
                break;
            case PRINT_LECTURER:
                PrintingLecturer.printLecturer(currentLecturerId);
                menuPosition = LECTURER;
                waitForEnter();
                break;
            case UPDATE_LECTURER:
                OperationsLecturer.updateLecturer(currentLecturerId);
                menuPosition = LECTURER;
                waitForEnter();
                break;
            case CHANGE_LECTURER_PASSWORD:
                OperationsLecturer.changeLecturerPassword(currentLecturerId);
                menuPosition = LECTURER;
                waitForEnter();
                break;
            case LIST_ASSIGNED_COURSES:
                PrintingCourse.listAssignedCourses(currentLecturerId);

                waitForEnter();
                break;
            case LIST_COURSES:
                PrintingCourse.listCourse();
                menuPosition = COURSE;
                waitForEnter();
            case PRINT_COURSE:
                PrintingCourse.printCourse();
                menuPosition = COURSE;
                waitForEnter();
            default:
                menuPosition = TOP;
        }
    }

    private void printMenuOptions() {
        System.out.print("Logged-in User: " + currentAccount);
        System.out.print("Lecturer ID: " + currentLecturerId);
        System.out.print(menuPosition.toString() +
                " Menu\n============================================\n" +
                "Enter number to select one of the following:\n");
        System.out.printf(menuOptions.get(menuPosition));
    }

}
