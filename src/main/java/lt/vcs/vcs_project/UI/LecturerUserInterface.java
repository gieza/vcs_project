package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.servicelayer.LecturerOperations;
import lt.vcs.vcs_project.servicelayer.CoursePrints;
import lt.vcs.vcs_project.servicelayer.LecturerPrints;
import lt.vcs.vcs_project.utils.ScannerUtils;

import static lt.vcs.vcs_project.UI.LecturerUIMenuDefinition.menuNavigation;
import static lt.vcs.vcs_project.UI.LecturerUIMenuDefinition.menuOptions;
//import static lt.vcs.vcs_project.UI.UI_common.waitForEnter;
import static lt.vcs.vcs_project.datalayer.DataLayer.accounts;
import static lt.vcs.vcs_project.utils.ScannerUtils.waitForEnter;
import static lt.vcs.vcs_project.UI.MenuTitle.*;

public class LecturerUserInterface implements UserInterface {
    private static MenuTitle menuPosition = TOP;
    private String currentAccount;
    private String currentLecturerId;

    @Override
    public void navigateMenu(String accountId) {
        currentAccount = accountId;
        currentLecturerId = accounts.getAccount(currentAccount).getPersonalId();
        String menuChoice;
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
                LecturerPrints.printLecturer(currentLecturerId);
                menuPosition = LECTURER;
                waitForEnter();
                break;
            case UPDATE_LECTURER:
                LecturerOperations.updateLecturer(currentLecturerId);
                menuPosition = LECTURER;
                waitForEnter();
                break;
            case CHANGE_LECTURER_PASSWORD:
                LecturerOperations.changeLecturerPassword(currentLecturerId);
                menuPosition = LECTURER;
                waitForEnter();
                break;
            case LIST_ASSIGNED_COURSES:
                CoursePrints.listAssignedCourses(currentLecturerId);

                waitForEnter();
                break;
            case LIST_COURSES:
                CoursePrints.listCourse();
                menuPosition = COURSE;
                waitForEnter();
                break;
            case PRINT_COURSE:
                CoursePrints.printCourse();
                menuPosition = COURSE;
                waitForEnter();
                break;
            default:
                menuPosition = TOP;
        }
    }

    private void printMenuOptions() {
        System.out.println("Logged-in User: " + currentAccount);
        System.out.println("Lecturer ID: " + currentLecturerId);
        System.out.println(menuPosition.toString() +
                " Menu\n============================================\n" +
                "Enter number to select one of the following:");
        System.out.println(menuOptions.get(menuPosition));
    }

}
