package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.servicelayer.OperationsStudent;
import lt.vcs.vcs_project.servicelayer.PrintingCourse;
import lt.vcs.vcs_project.servicelayer.PrintingStudent;
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
        currentStudentId = accounts.getAccount(currentStudentId).getPersonalId();
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
                PrintingStudent.printStudent(currentStudentId);
                menuPosition = STUDENT;
                waitForEnter();
                break;
            case UPDATE_STUDENT:
                OperationsStudent.updateStudent(currentAccount);
                menuPosition = STUDENT;
                waitForEnter();
                break;
            case CHANGE_STUDENT_PASSWORD:
                OperationsStudent.changeStudentPassword(currentAccount);
                menuPosition = STUDENT;
                waitForEnter();
            case ASSIGN_COURSE_STUDENT:
                OperationsStudent.assignAvailableCourse2Student(currentAccount);
                //menuPosition = "STUDENT";
                waitForEnter();
            case LIST_COURSES:
                PrintingCourse.listCourse();
                menuPosition = COURSE;
                waitForEnter();
            case LIST_AVAILABLE_COURSES:
                PrintingCourse.listAvailableCourses();
                menuPosition = COURSE;
                waitForEnter();
            case LIST_ENROLLED_COURSES:
                PrintingCourse.listEnrolledCourses(currentStudentId);
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
        System.out.print("Student ID: " + currentStudentId);
        System.out.print(menuPosition.toString() +
                " Menu\n============================================\n" +
                "Enter number to select one of the following:\n");
        System.out.print(menuOptions.get(menuPosition));
    }

}
