package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Lecturer;

import java.util.Set;

import static lt.vcs.vcs_project.datalayer.DataLayer.lecturers;

public class LecturerPrints extends PrintService {
    public static final String LECTURER_HEADER_CSV = "Login,Password,First name,Second name,StudentId," +
            "personalNumber,dateOfBirth,email,mobile,gender,address\n";

    public static final String LECTURER_LISTING_FORMATING = "%-12s %15s %-20s %-12s %-10s %-12s %-25s" +
            "%-12s %-5s %-40s\n";
    public static final String LECTURER_LISTING_HEADER = String.format(LECTURER_LISTING_FORMATING,
            "Lecturer ID", "First name", "Second name", "Login", "Personal#", "DoB", "email",
            "Mobile", "M/F", "Address");

    public static void listLecturer(Lecturer lecturer) {
        System.out.printf(LECTURER_LISTING_FORMATING, lecturer.getLecturerId(), lecturer.getFirstName(),
                lecturer.getSecondName(), lecturer.getLoginId(), lecturer.getPersonalNumber(), lecturer.getDateOfBirth(),
                lecturer.getEmail(), lecturer.getMobileNumber(), lecturer.getGender(), lecturer.getAddress());
    }

    public static void listLecturer(Set<String> lecturerList) {
        if (lecturerList == null) return;

        System.out.print(LECTURER_LISTING_HEADER);
        for (String key : lecturerList) {
            listLecturer(lecturers.getLecturer(key));
        }
    }

    public static void listLecturer() {
        Set<String> keys = lecturers.getKeyset();
        listLecturer(keys);
    }

    public static void printLecturer(Lecturer lecturer) {
        System.out.println("\nLecturer Details:\n================" +
                "\nLecturer ID: " + lecturer.getLecturerId() +
                "\nName: " + lecturer.getFirstName() + " " + lecturer.getSecondName() +
                "\nLogin Name: " + lecturer.getLoginId() + "\nPersonal number: " + lecturer.getPersonalNumber() +
                "\nDate Of Birth: " + lecturer.getDateOfBirth() + "\nEmail: " + lecturer.getEmail() +
                "\nMobile: " + lecturer.getMobileNumber() + "\nGender: " + lecturer.getGender() +
                "\nAddress: " + lecturer.getAddress());
        CoursePrints.listCourse(lecturer.getReadCourses());
    }

    public static void printLecturer(String lecturerId) {
        printLecturer(lecturers.getLecturer(lecturerId));
    }

    public static void printLecturer() {
        String selectedLecturer = LecturerOperations.selectLecturer();
        if (selectedLecturer != null) {
            printLecturer(selectedLecturer);
        }
    }

    public static final String UPDATE_LECTURER_DATA_INPUT_TEMPLATE = "First name,Second name,Personal#,DoB,Email," +
            "Mobile#,Gender,address";
    private static final String LECTURER_UPDATE_FORMATER = "%-20s %-25s %-12s %-12s %-25s %-12s %-5s %-40s\n";

    private static final String LECTURER_UPDATE_HEADER = String.format(LECTURER_UPDATE_FORMATER,
            "First name", "Second name", "Personal#", "DoB", "Email", "Mobile#", "Gender", "address");

    public static void printLecturerForUpdate(Lecturer lecturer) {
        System.out.print(LECTURER_UPDATE_HEADER);
        printUnderLineForString(LECTURER_UPDATE_HEADER);
        System.out.printf(LECTURER_UPDATE_FORMATER,
                lecturer.getFirstName(), lecturer.getSecondName(), lecturer.getPersonalNumber(),
                lecturer.getDateOfBirth(), lecturer.getEmail(), lecturer.getMobileNumber(),
                lecturer.getGender(), lecturer.getAddress());
    }
}
