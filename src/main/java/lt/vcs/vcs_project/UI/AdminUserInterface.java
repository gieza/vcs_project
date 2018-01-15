package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.servicelayer.*;
import lt.vcs.vcs_project.utils.ScannerUtils;

import static lt.vcs.vcs_project.UI.AdminUIMenuDefinition.menuNavigation;
import static lt.vcs.vcs_project.UI.AdminUIMenuDefinition.menuOptions;
import static lt.vcs.vcs_project.UI.MenuTitle.*;
import static lt.vcs.vcs_project.datalayer.DataLayer.accounts;
import static lt.vcs.vcs_project.servicelayer.OperationsLecturer.*;
import static lt.vcs.vcs_project.servicelayer.PrintingLecturer.listLecturer;
import static lt.vcs.vcs_project.servicelayer.PrintingLecturer.printLecturer;
import static lt.vcs.vcs_project.utils.ScannerUtils.waitForEnter;

public class AdminUserInterface implements UserInterface {
    static MenuTitle menuPosition = TOP;
    static String menuChoice = "";


    private String currentAccount;

    @Override
    public void navigateMenu(String accountId) {
        currentAccount = accountId;
        menuChoice = "";
        menuPosition = TOP;

        while (true) {
            printMenuOptions();
            menuChoice = ScannerUtils.scanString();
            if (menuNavigation.get(menuPosition, menuChoice) != null &&
                    menuNavigation.get(menuPosition, menuChoice) == LOGOUT)
                break;

            runDecision(menuPosition, menuChoice);
            //check if current account still exists, otherwise logout - account might have been removed
            if (!accounts.containsKey(currentAccount)) {
                break;
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
            case LIST_ACCOUNTS:
                PrintingAccount.listAccounts();
                waitForEnter();
                menuPosition = ACCOUNT;
                break;
            case PRINT_ACCOUNT:
                PrintingAccount.printAccount(currentAccount);
                waitForEnter();
                menuPosition = ACCOUNT;
                break;
            case ADD_ACCOUNT:
                OperationsAccount.addAccount();
                waitForEnter();
                menuPosition = ACCOUNT;
                break;
            case UPDATE_ACCOUNT:
                OperationsAccount.updateAccount(currentAccount);
                waitForEnter();
                menuPosition = ACCOUNT;
                break;
            case CHANGE_ACCOUNT_PASSWORD:
                OperationsAccount.changeAccountPassword(currentAccount);
                waitForEnter();
                menuPosition = ACCOUNT;
                break;
            case REMOVE_ACCOUNT:
                OperationsAccount.removeAccount(currentAccount);
                waitForEnter();
                menuPosition = ACCOUNT;
                break;
            case LIST_STUDENTS:
                PrintingStudent.listStudent();
                waitForEnter();
                menuPosition = STUDENT;
                break;
            case PRINT_STUDENT:
                PrintingStudent.printStudent();
                waitForEnter();
                menuPosition = STUDENT;
                break;
            case ADD_STUDENT:
                OperationsStudent.addStudent();
                waitForEnter();
                menuPosition = STUDENT;
                break;
            case UPDATE_STUDENT:
                OperationsStudent.updateStudent();
                waitForEnter();
                menuPosition = STUDENT;
                break;
            case CHANGE_STUDENT_PASSWORD:
                OperationsStudent.changeStudentPassword();
                waitForEnter();
                menuPosition = STUDENT;
                break;
            case REMOVE_STUDENT:
                OperationsStudent.removeStudent();
                menuPosition = STUDENT;
                waitForEnter();
                break;
            case LIST_LECTURERS:
                listLecturer();
                menuPosition = LECTURER;
                break;
            case PRINT_LECTURER:
                printLecturer();
                menuPosition = LECTURER;
                break;
            case ADD_LECTURER:
                addLecturer();
                menuPosition = LECTURER;
                break;
            case CHANGE_LECTURER_PASSWORD:
                changeLecturerPassword();
                menuPosition = LECTURER;
                break;
            case UPDATE_LECTURER:
                updateLecturer();
                menuPosition = LECTURER;
                break;
            case REMOVE_LECTURER:
                removeLecturer();
                menuPosition = LECTURER;
                break;
            case LIST_COURSES:
                PrintingCourse.listCourse();
                waitForEnter();
                menuPosition = COURSE;
                break;
            case PRINT_COURSE:
                PrintingCourse.printCourse();
                waitForEnter();
                menuPosition = COURSE;
                break;
            case ADD_COURSE:
                OperationsCourse.addCourse();
                waitForEnter();
                menuPosition = COURSE;
                break;
            case UPDATE_COURSE:
                OperationsCourse.updateCourse();
                waitForEnter();
                menuPosition = COURSE;
                break;
            case REMOVE_COURSE:
                OperationsCourse.removeCourse();
                waitForEnter();
                menuPosition = COURSE;
                break;
            case ASSIGN_COURSE_STUDENT:
                OperationsStudent.assignAnyCourse2Student();
                waitForEnter();
                menuPosition = COURSE;
                break;
            case ASSIGN_COURSE_LECTURER:
                OperationsLecturer.assignCourse2Lecturer();
                waitForEnter();
                menuPosition = COURSE;
                break;

            case loadsomedata:
                addSomeData();
                menuPosition = TOP;
                break;
            default:
                menuPosition = TOP;
        }
    }

    private void printMenuOptions() {
        System.out.printf("\n\nLogged-in User: %s\n\n", currentAccount);
        System.out.print(menuPosition + " Menu\n============================================\n" +
                "Enter number to select one of the following:\n");
        System.out.printf(menuOptions.get(menuPosition.toString()));
    }



    public static void addSomeData() {
        OperationsAccount.addAccount("Mikka,jumalauta1,Mikka,Saariniemi");
        OperationsAccount.addAccount("admin3,admin,Pekka,Peltonen");
        OperationsStudent.addStudent("juonis,juonis,Jonas,Petraitis,s0001,3450101000,19450101,juons@petraitis.lt,863303003,M,Jurgio g.1-13, Juonava");
        OperationsStudent.addStudent("petras,kurmelis2,Petras,Jonaitis,s0222,3450101002,19450101,petras@gmail.com,863303003,M,Vytauto g.3, Kaukoliku km., Mazeikiu raj.");
        OperationsStudent.addStudent("JB,youwon'tguess,James,BOND,s007,007,19450101,james.bond@mi5.gov.uk,undisclosed,M,somewhere on the globe");
        OperationsStudent.addStudent("JB,youwon'tguess,James,BOND,s007,007,19450101,james.bond@mi5.gov.uk,undisclosed,M,somewhere on the globe");

    }
}