package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.servicelayer.*;
import lt.vcs.vcs_project.utils.ScannerUtils;

import static lt.vcs.vcs_project.UI.AdminUIMenuDefinition.menuNavigation;
import static lt.vcs.vcs_project.UI.AdminUIMenuDefinition.menuOptions;
import static lt.vcs.vcs_project.UI.MenuTitle.*;
import static lt.vcs.vcs_project.datalayer.DataLayer.accounts;
import static lt.vcs.vcs_project.servicelayer.LecturerOperations.*;
import static lt.vcs.vcs_project.servicelayer.LecturerPrints.listLecturer;
import static lt.vcs.vcs_project.servicelayer.LecturerPrints.printLecturer;
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
            if (!accounts.accountExists(currentAccount)) {
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
                AccountPrints.listAccounts();
                waitForEnter();
                menuPosition = ACCOUNT;
                break;
            case PRINT_ACCOUNT:
                AccountPrints.printAccount(currentAccount);
                waitForEnter();
                menuPosition = ACCOUNT;
                break;
            case ADD_ACCOUNT:
                AccountOperations.addAccount();
                waitForEnter();
                menuPosition = ACCOUNT;
                break;
            case UPDATE_ACCOUNT:
                AccountOperations.updateAccount(currentAccount);
                waitForEnter();
                menuPosition = ACCOUNT;
                break;
            case CHANGE_ACCOUNT_PASSWORD:
                AccountOperations.changeAccountPassword(currentAccount);
                waitForEnter();
                menuPosition = ACCOUNT;
                break;
            case REMOVE_ACCOUNT:
                AccountOperations.removeAccount(currentAccount);
                waitForEnter();
                menuPosition = ACCOUNT;
                break;
            case LIST_STUDENTS:
                StudentPrints.listStudent();
                waitForEnter();
                menuPosition = STUDENT;
                break;
            case PRINT_STUDENT:
                StudentPrints.printStudent();
                waitForEnter();
                menuPosition = STUDENT;
                break;
            case ADD_STUDENT:
                StudentOperations.addStudent();
                waitForEnter();
                menuPosition = STUDENT;
                break;
            case UPDATE_STUDENT:
                StudentOperations.updateStudent();
                waitForEnter();
                menuPosition = STUDENT;
                break;
            case CHANGE_STUDENT_PASSWORD:
                StudentOperations.changeStudentPassword();
                waitForEnter();
                menuPosition = STUDENT;
                break;
            case REMOVE_STUDENT:
                StudentOperations.removeStudent();
                menuPosition = STUDENT;
                waitForEnter();
                break;
            case LIST_LECTURERS:
                listLecturer();
                waitForEnter();
                menuPosition = LECTURER;
                break;
            case PRINT_LECTURER:
                printLecturer();
                waitForEnter();
                menuPosition = LECTURER;
                break;
            case ADD_LECTURER:
                addLecturer();
                waitForEnter();
                menuPosition = LECTURER;
                break;
            case CHANGE_LECTURER_PASSWORD:
                changeLecturerPassword();
                menuPosition = LECTURER;
                waitForEnter();
                break;
            case UPDATE_LECTURER:
                updateLecturer();
                menuPosition = LECTURER;
                waitForEnter();
                break;
            case REMOVE_LECTURER:
                removeLecturer();
                menuPosition = LECTURER;
                waitForEnter();
                break;
            case LIST_COURSES:
                CoursePrints.listCourse();
                waitForEnter();
                menuPosition = COURSE;
                break;
            case PRINT_COURSE:
                CoursePrints.printCourse();
                waitForEnter();
                menuPosition = COURSE;
                break;
            case ADD_COURSE:
                CourseOperations.addCourse();
                waitForEnter();
                menuPosition = COURSE;
                break;
            case UPDATE_COURSE:
                CourseOperations.updateCourse();
                waitForEnter();
                menuPosition = COURSE;
                break;
            case REMOVE_COURSE:
                CourseOperations.removeCourse();
                waitForEnter();
                menuPosition = COURSE;
                break;
            case ASSIGN_COURSE_STUDENT:
                StudentOperations.assignAnyCourse2Student();
                waitForEnter();
                menuPosition = COURSE;
                break;
            case ASSIGN_COURSE_LECTURER:
                LecturerOperations.assignCourse2Lecturer();
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
        System.out.print(menuPosition.toString() +
                " Menu\n============================================\n" +
                "Enter number to select one of the following:\n");
        System.out.printf(menuOptions.get(menuPosition));
    }



    public static void addSomeData() {
        AccountOperations.addAccount("Mikka,jumalauta1,Mikka,Saariniemi");
        AccountOperations.addAccount("admin3,admin,Pekka,Peltonen");
        StudentOperations.addStudent("juonis,juonis,Jonas,Petraitis,s0001,3450101000,19450101,juons@petraitis.lt,863303003,M,Jurgio g.1-13, Juonava");
        StudentOperations.addStudent("petras,kurmelis2,Petras,Jonaitis,s0222,3450101002,19450101,petras@gmail.com,863303003,M,Vytauto g.3, Kaukoliku km., Mazeikiu raj.");
        StudentOperations.addStudent("JB,youwon'tguess,James,BOND,s007,007,19450101,james.bond@mi5.gov.uk,undisclosed,M,somewhere on the globe");
        StudentOperations.addStudent("JB,youwon'tguess,James,BOND,s007,007,19450101,james.bond@mi5.gov.uk,undisclosed,M,somewhere on the globe");

    }
}