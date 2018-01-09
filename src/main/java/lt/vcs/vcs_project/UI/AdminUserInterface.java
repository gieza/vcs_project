package lt.vcs.vcs_project.UI;
//todo: add extra menu item to load demo data
import lt.vcs.vcs_project.backend.Account;
import lt.vcs.vcs_project.backend.Backend;
import lt.vcs.vcs_project.backend.Lecturer;
import lt.vcs.vcs_project.backend.Student;
import lt.vcs.vcs_project.utils.ScannerUtils;

public class AdminUserInterface implements UserInterface {
    static String menuPosition = "TOP";
    static String menuChoice = "";


    private String currentAccount;

    @Override
    public void open(String accountId) {
        currentAccount =accountId;
        menuChoice="";
        menuPosition = "TOP";
        //this.adminUser = (Admin) user;
        //System.out.println("Administration menuPosition");
        while (true) {
            //todo refactor to be only in backend login
            System.out.printf("\nHello %s %s,\n", Backend.getFirstName(accountId), Backend.getSecondName(accountId));
            printMenuOptions();
            menuChoice = ScannerUtils.scanString();
            if (menuChoice.equals("9") && menuPosition.equals("TOP")) {
                break;
            } else {
                runDecision(menuPosition,menuChoice);
            }
        }
    }

    private void runDecision(String menu,String subMenu){
        String decision = AdminUIMenuHash.menuNavigation.get(menu,subMenu);
        System.out.printf("Next action: %s\n\n",decision);
        if (decision == null) {
            menuPosition = "TOP";
            return;
        }
        switch (decision) {
            case "ACCOUNT": case "STUDENT": case "LECTURER":  case "COURSE": case "TOP":
                menuPosition=decision;
                break;
            case "LIST_ACCOUNTS":
                listAccounts();
                menuPosition="ACCOUNT";
                break;
            case "PRINT_ACCOUNT":
                printAccount();
                menuPosition="ACCOUNT";
                break;
            case "ADD_ACCOUNT":
                addAccount();
                menuPosition="ACCOUNT";
                break;
            case "UPDATE_ACCOUNT":
                updateAccount();
                menuPosition="ACCOUNT";
                break;
            case "REMOVE_ACCOUNT":
                removeAccount();
                menuPosition="ACCOUNT";
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
            case "UPDATE_LECTURER":
                updateLecturer();
                menuPosition = "LECTURER";
                break;
            case "REMOVE_LECTURERS":
                removeLecturer();
                menuPosition = "LECTURER";
                break;
            default:
                menuPosition = "TOP";
        }
    }

    private void printMenuOptions() {
        System.out.printf("%s Menu\n============================================\n" +
                "Enter number to select one of the following:\n", menuPosition);
        System.out.printf(AdminUIMenuHash.menuOptions.get(menuPosition));
        //System.out.printf(menuOptions.get(menuPosition));
    }

    private void listAccounts(){
        Backend.listAccounts();
    }

    private void printAccount() { //todo:not implemented
        String selectedAccount = selectAccount(currentAccount);
        if (selectedAccount !=null) {
            Backend.printAccount(selectedAccount);
        }
    }

    private void addAccount(){
        System.out.printf("\nEnter new Admin Account data in CommaSeparatedValue format\nfollowing template: %s\n:",
                Account.printHeaderCSV());
        String userInput = ScannerUtils.scanString()+ ",ADMIN" ;
        System.out.printf("Entered values %s\n",userInput);
        Backend.addAccount(userInput);
    }

    private void updateAccount() { //todo:not implemented
        String selectedAccount = selectAccount(currentAccount);
        if (selectedAccount !=null) {
            System.out.printf("\nSorry, account update functionally not implemented.\n\treturning to menu\n\n");
        }
    }

    private void removeAccount() { //todo:not implemented
        String selectedAccount = selectAccount(currentAccount);
        if (selectedAccount !=null) {
            System.out.printf("\nSorry, account update functionally not implemented.\n\treturning to menu\n\n");
        }
    }

    private String selectAccount(String currentAccount){
        System.out.printf("\n\nCurrent Account %s.\n\tpress Enter to select current account, otherwise enter new account:", currentAccount);
        String userInput = ScannerUtils.scanString(); //todo pakeisti scannerutils, kad tiktu ir tuscia eilute
        if (userInput.equals("")) {
            System.out.printf("Selected account: %s\n",currentAccount);
            return currentAccount;
        } else if (Backend.accountAdminExists(userInput) ) {
                System.out.printf("Selected account: %s\n",userInput);
                return userInput;
        } else {
            System.out.printf("\nSorry, Account %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }

    private void listStudent() {
        Backend.listStudents(); //todo:not implemented
    }

    private void printStudent() { //todo:not implemented
        String selectedStudent = selectStudent();
        if (selectedStudent != null) {
            Backend.printAccount(selectedStudent);
        }
    }

    private void addStudent() {
        System.out.printf("\nEnter new Student data in CommaSeparatedValue format\nfollowing template: %s\n:",
                Student.printHeaderCSV());
        String userInput = ScannerUtils.scanString();
        System.out.printf("Entered values %s\n", userInput);
        Backend.addStudent(userInput);
    }

    private void updateStudent() {//todo:not implemented
        String selectedStudent = selectStudent();
        if (selectedStudent != null) {
            System.out.printf("\nSorry, Student update functionally not implemented.\n\treturning to menu\n\n");
        }
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
        if (Backend.studentExists(userInput)) {
            System.out.printf("Selected student: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, StudentId %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }


    private void listLecturer() {
        Backend.listLecturers(); //todo:not implemented
    }

    private void printLecturer() { //todo:not implemented
        String selectedLecturer = selectLecturer();
        if (selectedLecturer != null) {
            Backend.printAccount(selectedLecturer);
        }
    }

    private void addLecturer() {
        System.out.printf("\nEnter new Lecturer data in CommaSeparatedValue format\nfollowing template: %s\n:",
                Lecturer.printHeaderCSV());
        String userInput = ScannerUtils.scanString();
        System.out.printf("Entered values %s\n", userInput);
        Backend.addLecturer(userInput);
    }

    private void updateLecturer() {//todo:not implemented
        String selectedLecturer = selectLecturer();
        if (selectedLecturer != null) {
            System.out.printf("\nSorry, Lecturer update functionally not implemented.\n\treturning to menu\n\n");
        }
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
        if (Backend.lecturerExists(userInput)) {
            System.out.printf("Selected Lecturer: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, StudentId %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }

}