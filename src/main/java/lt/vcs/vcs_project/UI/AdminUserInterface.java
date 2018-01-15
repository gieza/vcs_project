package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.datalayer.DataLayer;
import lt.vcs.vcs_project.servicelayer.*;
import lt.vcs.vcs_project.utils.ScannerUtils;

import static lt.vcs.vcs_project.UI.UI_common.*;
import static lt.vcs.vcs_project.datalayer.DataLayer.accounts;
import static lt.vcs.vcs_project.servicelayer.OperationsLecturer.*;
import static lt.vcs.vcs_project.servicelayer.PrintingLecturer.*;

public class AdminUserInterface implements UserInterface {
    static String menuPosition = "TOP";
    static String menuChoice = "";


    private String currentAccount;

    @Override
    public void open(String accountId) {
        currentAccount = accountId;
        menuChoice = "";
        menuPosition = "TOP";

        while (true) {
            if (menuPosition.equals("LOGOUT")) {
                break;
            }
            clearScreen();
            System.out.printf("\nHello %s %s,\n", DataLayer.getFirstName(accountId),
                    DataLayer.getSecondName(accountId)); //todo print greeting in login method, here just list current account
            printMenuOptions();
            menuChoice = ScannerUtils.scanString();
            if (menuChoice.equals("9") && menuPosition.equals("TOP")) {
                break;
            }
            runDecision(menuPosition, menuChoice);
        }
    }

    private void runDecision(String menu, String subMenu) {
        String decision = AdminUIMenuDefinition.menuNavigation.get(menu, subMenu);
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
            case "LIST_ACCOUNTS":
                AccountCollectionOperations.listAccounts();
                waitForEnter();
                menuPosition = "ACCOUNT";
                break;
            case "PRINT_ACCOUNT":
                printAccount();
                menuPosition = "ACCOUNT";
                break;
            case "ADD_ACCOUNT":
                AccountCollectionOperations.addAccount();
                waitForEnter();
                menuPosition = "ACCOUNT";
                break;
            case "UPDATE_ACCOUNT":
                updateAccount();
                menuPosition = "ACCOUNT";
                break;
            case "CHANGE_ACCOUNT_PASSWORD":
                changeAccountPassword();
                menuPosition = "ACCOUNT";
                break;
            case "REMOVE_ACCOUNT":
                removeAccount();
                //menuPosition is being set in removeAccount method
                break;
            case "LIST_STUDENTS":
                PrintingStudent.listStudent();
                waitForEnter();
                menuPosition = "STUDENT";
                break;
            case "PRINT_STUDENT":
                PrintingStudent.printStudent();
                waitForEnter();
                menuPosition = "STUDENT";
                break;
            case "ADD_STUDENT":
                OperationsStudent.addStudent();
                waitForEnter();
                menuPosition = "STUDENT";
                break;
            case "UPDATE_STUDENT":
                OperationsStudent.updateStudent();
                waitForEnter();
                menuPosition = "STUDENT";
                break;
            case "CHANGE_STUDENT_PASSWORD":
                OperationsStudent.changeStudentPassword();
                waitForEnter();
                menuPosition = "STUDENT";
                break;
            case "REMOVE_STUDENT":
                OperationsStudent.removeStudent();
                menuPosition = "STUDENT";
                waitForEnter();
                break;
            case "LIST_LECTURERS":
                listLecturer();
                menuPosition = "LECTURER";
                break;
            case "PRINT_LECTURER":
                printLecturer();
                menuPosition = "LECTURER";
                break;
            case "ADD_LECTURER":
                addLecturer();
                menuPosition = "LECTURER";
                break;
            case "CHANGE_LECTURER_PASSWORD":
                changeLecturerPassword();
                menuPosition = "LECTURER";
                break;
            case "UPDATE_LECTURER":
                updateLecturer();
                menuPosition = "LECTURER";
                break;
            case "REMOVE_LECTURERS":
                removeLecturer();
                menuPosition = "LECTURER";
                break;
            case "LIST_COURSES":
                PrintingCourse.listCourse();
                waitForEnter();
                menuPosition = "COURSE";
                break;
            case "PRINT_COURSE":
                PrintingCourse.printCourse();
                waitForEnter();
                menuPosition = "COURSE";
                break;
            case "ADD_COURSE":
                OperationsCourse.addCourse();
                waitForEnter();
                menuPosition = "COURSE";
                break;
            case "UPDATE_COURSE":
                OperationsCourse.updateCourse();
                waitForEnter();
                menuPosition = "COURSE";
                break;
            case "REMOVE_COURSE":
                OperationsCourse.removeCourse();
                waitForEnter();
                menuPosition = "COURSE";
                break;
            case "ASSIGN_COURSE_STUDENT":
                OperationsStudent.assignAnyCourse2Student();
                waitForEnter();
                menuPosition = "COURSE";
                break;
            case "ASSIGN_COURSE_LECTURER":
                OperationsCourse.assignCourse2Lecturer();
                waitForEnter();
                menuPosition = "COURSE";
                break;

            case "loadsomedata":
                addSomeData();
                menuPosition = "COURSE";
                break;
            default:
                menuPosition = "TOP";
        }
    }

    private void printMenuOptions() {
        System.out.printf("%s Menu\n============================================\n" +
                "Enter number to select one of the following:\n", menuPosition);
        System.out.printf(AdminUIMenuDefinition.menuOptions.get(menuPosition));
    }


    private void printAccount() {
        String selectedAccount = selectAccount(currentAccount);
        if (selectedAccount != null) {
            AccountCollectionOperations.printOutAccount(selectedAccount);
        }
        waitForEnter();
    }


    private void updateAccount() {
        String selectedAccount = selectAccount(currentAccount);
        if (selectedAccount != null) {
            AccountCollectionOperations.updateAccount(selectedAccount);
            waitForEnter();
        }
    }

    private void changeAccountPassword() {
        String selectedAccount = selectAccount(currentAccount);
        String newPassword = askForNewPassword();
        if (newPassword.length() > 0) {
            accounts.getAccount(selectedAccount).setPassword(newPassword);
        } else {
            System.out.println("User password cannot be empty");
        }
        waitForEnter();
    }

    private void removeAccount() { //todo:not implemented
        String selectedAccount = selectAccount(currentAccount);
        if (selectedAccount != null) {
            accounts.removeAccount(selectedAccount);
            if (selectedAccount.equals(currentAccount)) {
                menuPosition = "LOGOUT";
                //current account does not exist anymore -> it has to leave immediately
            } else {
                menuPosition = "ACCOUNT";
            }
        }
        waitForEnter();
    }

    private String selectAccount(String currentAccount) {
        System.out.printf("\n\nCurrent Account %s.\n" +
                "\tpress Enter to select current account, otherwise enter new account:\n", currentAccount);
        String userInput = ScannerUtils.scanString();
        if (userInput.equals("")) {
            System.out.printf("Selected account is: %s\n", currentAccount);
            return currentAccount;
        } else if (DataLayer.accounts.AdminExists(userInput)) {
            System.out.printf("Selected account is: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, Account %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }


    public static void addSomeData() {
        AccountCollectionOperations.addAccount("Mikka,jumalauta1,Mikka,Saariniemi");
        AccountCollectionOperations.addAccount("admin3,admin,Pekka,Peltonen");
        OperationsStudent.addStudent("juonis,juonis,Jonas,Petraitis,s0001,3450101000,19450101,juons@petraitis.lt,863303003,M,Jurgio g.1-13, Juonava");
        OperationsStudent.addStudent("petras,kurmelis2,Petras,Jonaitis,s0222,3450101002,19450101,petras@gmail.com,863303003,M,Vytauto g.3, Kaukoliku km., Mazeikiu raj.");
        OperationsStudent.addStudent("JB,youwon'tguess,James,BOND,s007,007,19450101,james.bond@mi5.gov.uk,undisclosed,M,somewhere on the globe");
        OperationsStudent.addStudent("JB,youwon'tguess,James,BOND,s007,007,19450101,james.bond@mi5.gov.uk,undisclosed,M,somewhere on the globe");

    }
}