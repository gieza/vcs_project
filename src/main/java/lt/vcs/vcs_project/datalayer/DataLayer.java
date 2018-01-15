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


}
