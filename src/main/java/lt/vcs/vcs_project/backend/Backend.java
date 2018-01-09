package lt.vcs.vcs_project.backend;

import lt.vcs.vcs_project.utils.ScannerUtils;

public class Backend {
    public static AccountServices accounts = new AccountServices();
    public static StudentServices students = new StudentServices();


    public static String login() {
        System.out.printf("\n\nWelcome! Please login.\n");
        while (true) {
            System.out.print("Username: ");
            String username = ScannerUtils.scanString();
            System.out.print("Password: ");
            String password = ScannerUtils.scanString();

            if (accounts.authenticate(username, password)) {
                return username;
            }
            System.out.println("Login failed, please try again");
        }
    }

    public static String loginDefault() {
        System.out.printf("\n\nWelcome! Admin.\n");
        return "admin";
    }

    public static Role getRole(String loginId) {
        return accounts.getAccount(loginId).getRole();
    }

    public static String getFirstName(String loginId) {
        return accounts.getAccount(loginId).getFirstName();
    }

    public static String getSecondName(String loginId) {
        return accounts.getAccount(loginId).getSecondName();
    }

    public static void listAccounts() {
        System.out.println(accounts.listAccounts());
    }

    public static void printAccount(String loginId) {
        System.out.println(accounts.getAccount(loginId).toString());
    }

    public static boolean accountExists(String loginId) {
        return accounts.containsKey(loginId);
    }

    public static boolean accountAdminExists(String loginId) {
        return (accounts.containsKey(loginId) && accounts.getAccount(loginId).getRole() == Role.ADMIN);
    }

    public static void addAccount(String csv) {
        accounts.addAccount(csv);
    }


    public static void listStudents() {
        System.out.println(students.listStudents());
    }

    public static void printStudent(String studentId) {
        System.out.println(students.getStudent(studentId).toString());
    }


    public static boolean studentExists(String loginId) {
        return students.containsKey(loginId);
    }

    public static void addStudent(String csv) {
        Student newStudent = new Student(csv);

        if (!(accounts.containsKey(newStudent.getLoginId()) &&
                students.containsKey(newStudent.getStudentId()))) {
            accounts.addAccount(newStudent.getAccount());
            students.addStudent(newStudent);
        } else {
            if (accounts.containsKey(newStudent.getLoginId())) {
                System.out.printf("Sorry, account %s already exists", newStudent.getLoginId());
            } else {
                System.out.printf("Sorry, StudentId %s already exists", newStudent.getStudentId());
            }
        }
    }

}
