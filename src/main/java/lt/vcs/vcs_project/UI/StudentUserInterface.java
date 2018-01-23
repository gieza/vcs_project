package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.servicelayer.StudentOperations;
import lt.vcs.vcs_project.servicelayer.CoursePrints;
import lt.vcs.vcs_project.servicelayer.StudentPrints;
import lt.vcs.vcs_project.utils.ScannerUtils;

import static lt.vcs.vcs_project.UI.MenuTitle.*;
import static lt.vcs.vcs_project.UI.StudentUIMenuDefinition.*;
import static lt.vcs.vcs_project.datalayer.DataLayer.accounts;
import static lt.vcs.vcs_project.utils.ScannerUtils.waitForEnter;

public class StudentUserInterface implements UserInterface {
    private static MenuTitle menuPosition;
    private static String menuChoice;


    private String currentAccount;
    private String currentStudentId;

    @Override
    public void navigateMenu(String accountId) {
        currentAccount = accountId;
        currentStudentId = accounts.getAccount(currentAccount).getPersonalId();
        menuChoice = "";
        menuPosition = TOP;
        while (true) {
            printMenuOptions();
            menuChoice = ScannerUtils.scanString();
            if (menuNavigation.get(menuPosition, menuChoice) != null &&
                    menuNavigation.get(menuPosition, menuChoice) == LOGOUT) {
                break;      //logout - no need to proceed further
            } else {
                runDecision(menuPosition, menuChoice);
            }
        }
    }

    private void runDecision(MenuTitle menu, String subMenu) {
        MenuTitle decision = menuNavigation.get(menu, subMenu);
        //System.out.printf("Next action: %s\n\n", decision);
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
            case PRINT_STUDENT:
                StudentPrints.printStudent(currentStudentId);
                menuPosition = STUDENT;
                waitForEnter();
                break;
            case UPDATE_STUDENT:
                StudentOperations.updateStudent(currentAccount);
                menuPosition = STUDENT;
                waitForEnter();
                break;
            case CHANGE_STUDENT_PASSWORD:
                StudentOperations.changeStudentPassword(currentAccount);
                menuPosition = STUDENT;
                waitForEnter();
                break;
            case ASSIGN_COURSE_STUDENT:
                StudentOperations.assignAvailableCourse2Student(currentStudentId);
                //menuPosition = "STUDENT";
                waitForEnter();
                break;
            case LIST_COURSES:
                CoursePrints.listCourse();
                menuPosition = COURSE;
                waitForEnter();
                break;
            case LIST_AVAILABLE_COURSES:
                CoursePrints.listAvailableCourses();
                menuPosition = COURSE;
                waitForEnter();
                break;
            case LIST_ENROLLED_COURSES:
                CoursePrints.listEnrolledCourses(currentStudentId);
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
        System.out.print("Logged-in User: " + currentAccount);
        System.out.print("Student ID: " + currentStudentId);
        System.out.print(menuPosition.toString() +
                " Menu\n============================================\n" +
                "Enter number to select one of the following:\n");
        System.out.print(menuOptions.get(menuPosition));
    }

}
