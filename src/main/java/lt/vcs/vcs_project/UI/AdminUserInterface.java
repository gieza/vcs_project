package lt.vcs.vcs_project.UI;
//todo: add extra menu item to load demo data

import lt.vcs.vcs_project.backend.*;
import lt.vcs.vcs_project.utils.ScannerUtils;

import static lt.vcs.vcs_project.UI.UI_common.*;

public class AdminUserInterface implements UserInterface {
    static String menuPosition = "TOP";
    static String menuChoice = "";


    private String currentAccount;

    @Override
    public void open(String accountId) {
        currentAccount = accountId;
        menuChoice = "";
        menuPosition = "TOP";
        //this.adminUser = (Admin) user;
        //System.out.println("Administration menuPosition");
        while (true) {
            //todo refactor to be only in backend login
            clearScreen();
            System.out.printf("\nHello %s %s,\n", DataOperations.getFirstName(accountId), DataOperations.getSecondName(accountId));
            printMenuOptions();
            menuChoice = ScannerUtils.scanString();
            if (menuChoice.equals("9") && menuPosition.equals("TOP")) {
                break;
            }
            ;
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
                DataOperations.listAccounts();
                ;
                menuPosition = "ACCOUNT";
                break;
            case "PRINT_ACCOUNT":
                printAccount();
                menuPosition = "ACCOUNT";
                break;
            case "ADD_ACCOUNT":
                addAccount();
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
                menuPosition = "ACCOUNT";
                break;
            case "LIST_STUDENTS":
                listStudent();
                menuPosition = "STUDENT";
                break;
            case "PRINT_STUDENT":
                printStudent();
                menuPosition = "STUDENT";
                break;
            case "ADD_STUDENT":
                addStudent();
                menuPosition = "STUDENT";
                break;
            case "UPDATE_STUDENT":
                updateStudent();
                menuPosition = "STUDENT";
                break;
            case "CHANGE_STUDENT_PASSWORD":
                changeStudentPassword();
                menuPosition = "STUDENT";
                break;
            case "REMOVE_STUDENT":
                removeStudent();
                menuPosition = "STUDENT";
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
                DataOperations.listCourses();
                menuPosition = "COURSE";
                break;
            case "PRINT_COURSE":
                printCourse();
                menuPosition = "COURSE";
                break;
            case "ADD_COURSE":
                addCourse();
                menuPosition = "COURSE";
                break;
            case "UPDATE_COURSE":
                updateCourse();
                menuPosition = "COURSE";
                break;
            case "REMOVE_COURSE":
                removeCourse();
                menuPosition = "COURSE";
                break;
            case "ASSIGN_COURSE_STUDENT":
                assignCourse2Student();
                menuPosition = "COURSE";
                break;
            case "ASSIGN_COURSE_LECTURER":
                assignCourse2Lecturer();
                menuPosition = "COURSE";
                break;

            case "loadsomedata":
                DataOperations.addSomeData();
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
        //System.out.printf(menuOptions.get(menuPosition));
    }


    private void printAccount() { //todo:not implemented
        String selectedAccount = selectAccount(currentAccount);
        if (selectedAccount != null) {
            DataOperations.printAccount(selectedAccount);
        }
    }

    private void addAccount() {
        System.out.printf("\nEnter new Admin Account data in CommaSeparatedValue format\nfollowing template: %s\n:",
                AccountOperations.getNewAccountDataInputTemplate());
        String userInput = ScannerUtils.scanString() + ",ADMIN";
        System.out.printf("Entered values %s\n", userInput);
        DataOperations.addAccount(userInput);
    }

    private void updateAccount() {
        String selectedAccount = selectAccount(currentAccount);
        if (selectedAccount != null) {
            String headline = AccountOperations.getUpdateHeader();
            System.out.printf("\nCurrent Admin Account values are:\n%s\n", headline);
            printUnderLineForString(headline);
            System.out.println(DataOperations.getCurrentDataforUpdate(selectedAccount));
            System.out.printf("\n\nEnter new Admin Account data in CommaSeparatedValue format" +
                            "\nfollowing template:\n %s\n",
                    AccountOperations.getUpdateAccountDataInputTemplate());
            String userInput = ScannerUtils.scanString();
            DataOperations.updateAccount(selectedAccount, userInput);
            //print updated values
            DataOperations.printAccount(selectedAccount);
            waitForEnter();
        }
    }

    private void changeAccountPassword() {
        String selectedAccount = selectAccount(currentAccount);
        String newPassword = askForNewPassword();
        if (newPassword.length() > 0) {
            DataOperations.changeAccountPassword(selectedAccount, newPassword);
        } else {
            System.out.println("User password cannot be empty");
        }
        waitForEnter();
    }

    private void removeAccount() { //todo:not implemented
        String selectedAccount = selectAccount(currentAccount);
        if (selectedAccount != null) {
            System.out.printf("\nSorry, account update functionally not implemented.\n\treturning to menu\n\n");
        }
    }

    private String selectAccount(String currentAccount) {
        System.out.printf("\n\nCurrent Account %s.\n\tpress Enter to select current account, otherwise enter new account:", currentAccount);
        String userInput = ScannerUtils.scanString(); //todo pakeisti scannerutils, kad tiktu ir tuscia eilute
        if (userInput.equals("")) {
            System.out.printf("Selected account: %s\n", currentAccount);
            return currentAccount;
        } else if (DataOperations.accountAdminExists(userInput)) {
            System.out.printf("Selected account: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, Account %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }

    private void listStudent() {
        DataOperations.listStudents(); //todo:not implemented
    }

    private void printStudent() { //todo:not implemented
        String selectedStudent = selectStudent();
        if (selectedStudent != null) {
            DataOperations.printAccount(selectedStudent);
        }
    }

    private void addStudent() {
        System.out.printf("\nEnter new Student data in CommaSeparatedValue format\nfollowing template: %s\n:",
                Student.printHeaderCSV());
        String userInput = ScannerUtils.scanString();
        System.out.printf("Entered values %s\n", userInput);
        DataOperations.addStudent(userInput);
    }

    private void updateStudent() {//todo:not implemented
        String selectedStudent = selectStudent();
        if (selectedStudent != null) {
            System.out.printf("\nSorry, Student update functionally not implemented.\n\treturning to menu\n\n");
        }
    }

    private void changeStudentPassword() {
        String selectedStudent = selectStudent();
        String newPassword = askForNewPassword();
        if (newPassword.length() > 0) {
            DataOperations.changeStudentPassword(selectedStudent, newPassword);
        } else {
            System.out.println("User password cannot be empty");
        }
        waitForEnter();
    }

    private void removeStudent() {//todo:not implemented - gal atskirai prideti password??
        String selectedStudent = selectStudent();
        if (selectedStudent != null) {
            System.out.printf("\nSorry, Student update functionally not implemented.\n\treturning to menu\n\n");
        }
    }

    private String selectStudent() {
        System.out.printf("\n\nEnter to Student Id to select student:");
        String userInput = ScannerUtils.scanString();
        if (DataOperations.studentExists(userInput)) {
            System.out.printf("Selected student: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, StudentId %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }


    private void listLecturer() {
        DataOperations.listLecturers(); //todo:not implemented
    }

    private void printLecturer() { //todo:not implemented
        String selectedLecturer = selectLecturer();
        if (selectedLecturer != null) {
            DataOperations.printAccount(selectedLecturer);
        }
    }

    private void addLecturer() {
        System.out.printf("\nEnter new Lecturer data in CommaSeparatedValue format\nfollowing template: %s\n:",
                Lecturer.printHeaderCSV());
        String userInput = ScannerUtils.scanString();
        System.out.printf("Entered values %s\n", userInput);
        DataOperations.addLecturer(userInput);
    }

    private void updateLecturer() {//todo:not implemented
        String selectedLecturer = selectLecturer();
        if (selectedLecturer != null) {
            System.out.printf("\nSorry, Lecturer update functionally not implemented.\n\treturning to menu\n\n");
        }
    }

    private void changeLecturerPassword() {
        String selectedLecturer = selectLecturer();
        String newPassword = askForNewPassword();
        if (newPassword.length() > 0) {
            DataOperations.changeLecturerPassword(selectedLecturer, newPassword);
        } else {
            System.out.println("User password cannot be empty");
        }
        waitForEnter();
    }

    private void removeLecturer() {//todo:not implemented - gal atskirai prideti password??
        String selectedLecturer = selectLecturer();
        if (selectedLecturer != null) {
            System.out.printf("\nSorry, Lecturer update functionally not implemented.\n\treturning to menu\n\n");
        }
    }

    private String selectLecturer() {
        System.out.printf("\n\nEnter to Lecturer Id to select Lecturer:");
        String userInput = ScannerUtils.scanString();
        if (DataOperations.lecturerExists(userInput)) {
            System.out.printf("Selected Lecturer: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, Lecturer Id %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }

    private void printCourse() { //todo:not implemented
        String selectedCourse = selectCourse();
        if (selectedCourse != null) {
            DataOperations.printCourse(selectedCourse);
        }
    }


    private void addCourse() {
        System.out.printf("\nEnter new Lecturer data in CommaSeparatedValue format\nfollowing template: %s\n:",
                Lecturer.printHeaderCSV());
        String userInput = ScannerUtils.scanString();
        System.out.printf("Entered values %s\n", userInput);
        DataOperations.addLecturer(userInput);
    }

    private void updateCourse() {//todo:not implemented
        String selectedCourse = selectCourse();
        if (selectedCourse != null) {
            System.out.printf("\nSorry, course update functionally not implemented.\n\treturning to menu\n\n");
        }
    }

    private void removeCourse() {//todo:not implemented - gal atskirai prideti password??
        String selectedCourse = selectCourse();
        if (selectedCourse != null) {
            System.out.printf("\nSorry, course remove functionally not implemented.\n\treturning to menu\n\n");
        }
    }

    private void assignCourse2Student() {
        String selectedCourse = selectCourse();
        String selectedStudent = selectStudent();
        if (selectedCourse != null && selectedStudent != null) {
            DataOperations.assignAnyCourse2Student(selectedCourse, selectedStudent);
        }
    }

    private void assignCourse2Lecturer() {
        String selectedCourse = selectCourse();
        String selectedLecturer = selectLecturer();
        if (selectedCourse != null && selectedLecturer != null) {
            DataOperations.assignCourse2Lecturer(selectedCourse, selectedLecturer);
        }
    }

    private String selectCourse() {
        System.out.printf("\n\nEnter to Course Code to select Course:");
        String userInput = ScannerUtils.scanString();
        if (DataOperations.courseExists(userInput)) {
            System.out.printf("Selected Course: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, Course %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }
}