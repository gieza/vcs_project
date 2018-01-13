package lt.vcs.vcs_project.datalayer;

import static lt.vcs.vcs_project.servicelayer.AccountOperations.*;
import static lt.vcs.vcs_project.datalayer.Role.*;

public class DataLayer {
    public static AccountCollection accounts = new AccountCollection();
    public static StudentCollection students = new StudentCollection();
    public static LecturerCollection lecturers = new LecturerCollection();
    public static CourseCollection courses = new CourseCollection();


    public static Role getRole(String loginId) {
        return accounts.getAccount(loginId).getRole();
    }

    public static String getFirstName(String loginId) {
        return accounts.getAccount(loginId).getFirstName();
    }

    public static String getSecondName(String loginId) {
        return accounts.getAccount(loginId).getSecondName();
    }

    public static boolean authenticate(String accountId, String password) {
        return accounts.getAccount(accountId).authenticate(password);
    }


    public static void printAccount(String loginId) {
        System.out.println(accounts.getAccount(loginId).toString());
    }


    public static boolean accountExists(String loginId) {
        return accounts.containsKey(loginId);
    }


    public static boolean accountAdminExists(String loginId) {
        //todo: reikalingas refaktoringas
        return (accounts.containsKey(loginId) && accounts.getAccount(loginId).getRole() == ADMIN);
    }

    public static void addAccount(String csv) {
        accounts.addAccount(csv);
    }

    public static void updateAccount(String AccountId, String updateCSV) {
        if (accounts.getAccount(AccountId).getRole() == ADMIN) {
            updateFromCSV(accounts.getAccount(AccountId), updateCSV);
        }
    }

    static public String getCurrentDataforUpdate(String accountID) {
        return accounts.getAccount(accountID).getCurrentValuesForUpdate();

    }

    static public void changeAccountPassword(String accountId, String newPassword) {
        if (accounts.containsKey(accountId)) {
            accounts.getAccount(accountId).setPassword(newPassword);
        } else {
            System.out.printf("Account password failed - no such account %s\n", accountId);
        }
    }

    public static void removeAccount(String accountId) {
        if (accounts.containsKey(accountId)) {
            accounts.removeAccount(accountId);
        }
        //UI interface handles case of missing account
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


    static public void changeStudentPassword(String studentId, String newPassword) {
        if (students.containsKey(studentId)) {
            students.getStudent(studentId).setPassword(newPassword);
        } else {
            System.out.printf("Student password failed - no such account %s\n", studentId);
        }
    }

    public static void listLecturers() {
        System.out.println(lecturers.listLecturers());
    }

    public static void printLecturer(String lecturerId) {
        System.out.println(lecturers.getLecturer(lecturerId).toString());
    }


    public static boolean lecturerExists(String loginId) {
        return lecturers.containsKey(loginId);
    }

    public static void addLecturer(String csv) {
        Lecturer newLecturer = new Lecturer(csv);

        if (!(accounts.containsKey(newLecturer.getLoginId()) &&
                students.containsKey(newLecturer.getLecturerId()))) {
            accounts.addAccount(newLecturer.getAccount());
            lecturers.addLecturer(newLecturer);
        } else {
            if (accounts.containsKey(newLecturer.getLoginId())) {
                System.out.printf("Sorry, account %s already exists", newLecturer.getLoginId());
            } else {
                System.out.printf("Sorry, LecturerId %s already exists", newLecturer.getLecturerId());
            }
        }
    }

    static public void changeLecturerPassword(String lecturerId, String newPassword) {
        if (lecturers.containsKey(lecturerId)) {
            lecturers.getLecturer(lecturerId).setPassword(newPassword);
        } else {
            System.out.printf("Lecturer password failed - no such account %s\n", lecturerId);
        }
    }

    public static void listCourses() {
        System.out.println(courses.listCourses());
    }

    public static void printCourse(String courseCode) {
        System.out.println(courses.listCourses());
        //todo: not implemented
    }

    public static boolean courseExists(String courseCode) {
        return courses.containsKey(courseCode);
    }

    public static void assignAnyCourse2Student(String courseCode, String studentId) {
        courses.enrollStudent(courseCode, studentId);
        students.addCourse(studentId, courseCode);
    }

    public static void assignAvailableCourse2Student(String courseCode, String studentId) {
        if (courses.getCourse(courseCode).available()) {
            courses.enrollStudent(courseCode, studentId);
            students.addCourse(studentId, courseCode);
        }
    }


    public static void assignCourse2Lecturer(String courseCode, String lecturerId) {
        if (courses.containsKey(courseCode) && lecturers.containsKey(lecturerId)) {
            courses.getCourse(courseCode).setLecturerCode(lecturerId);
            lecturers.getLecturer(lecturerId).addCourse(courseCode);
        }
    }

    public static void addSomeData() {
        accounts.addAccount("Mikka,jumalauta1,Mikka,Saariniemi");
        accounts.addAccount("admin3,admin,Pekka,Peltonen");
        students.addStudent("juonis,juonis,Jonas,Petraitis,s0001,3450101000,19450101,juons@petraitis.lt,863303003,M,Jurgio g.1-13, Juonava");
        students.addStudent("petras,kurmelis2,Petras,Jonaitis,s0222,3450101002,19450101,petras@gmail.com,863303003,M,Vytauto g.3, Kaukoliku km., Mazeikiu raj.");
        students.addStudent("JB,youwon'tguess,James,BOND,s007,007,19450101,james.bond@mi5.gov.uk,undisclosed,M,somewhere on the globe");
        students.addStudent("JB,youwon'tguess,James,BOND,s007,007,19450101,james.bond@mi5.gov.uk,undisclosed,M,somewhere on the globe");

    }


    public static void listAccounts() {
        System.out.println(accounts.listAccounts());
    }
}