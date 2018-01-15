package lt.vcs.vcs_project.datalayer;

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


    public static boolean accountExists(String loginId) {
        return accounts.containsKey(loginId);
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

   /* public static void listCourses() {
        System.out.println(courses.listCourses());
    }

    public static void printCourse(String courseCode) {
        System.out.println(courses.listCourses());
        //todo: not implemented
    }

   public static boolean courseExists(String courseCode) {
        return courses.containsKey(courseCode);
    }*/

   /* public static void assignAnyCourse2Student(String courseCode, String studentId) {
        courses.enrollStudent(courseCode, studentId);
        students.addCourse(studentId, courseCode);
    }

    public static void assignAvailableCourse2Student(String courseCode, String studentId) {
        if (courses.getCourse(courseCode).available()) {
            courses.enrollStudent(courseCode, studentId);
            students.addCourse(studentId, courseCode);
        }
    }*/


    /*public static void assignCourse2Lecturer(String courseCode, String lecturerId) {
        if (courses.containsKey(courseCode) && lecturers.containsKey(lecturerId)) {
            courses.getCourse(courseCode).setLecturerCode(lecturerId);
            lecturers.getLecturer(lecturerId).addCourse(courseCode);
        }
    }*/

}
