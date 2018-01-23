package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Student;

import java.util.Set;

import static lt.vcs.vcs_project.datalayer.DataLayer.students;

public class StudentPrints extends PrintService {
    public static final String STUDENT_HEADER_CSV = "Login,Password,First name,Second name,StudentId," +
            "personalNumber,dateOfBirth,email,mobile,gender,address\n";


    public static final String STUDENT_LISTING_FORMATING = "%-12s %15s %-20s %-12s %-10s %-12s %-25s " +
            "%-12s %-5s %-40s\n";
    public static final String STUDENT_LISTING_HEADER = String.format(STUDENT_LISTING_FORMATING,
            "Student ID", "First name", "Second name", "Login", "Personal#", "DoB", "email",
            "Mobile", "M/F", "Address");

    public static void listStudent(Student student) {
        System.out.printf(STUDENT_LISTING_FORMATING, student.getStudentId(), student.getFirstName(),
                student.getSecondName(), student.getLoginId(), student.getPersonalNumber(), student.getDateOfBirth(),
                student.getEmail(), student.getMobileNumber(), student.getGender(), student.getAddress());
    }

    public static void listStudent(Set<String> StudentList) {
        if (StudentList == null) return;

        System.out.print(STUDENT_LISTING_HEADER);
        for (String key : StudentList) {
            listStudent(students.getStudent(key));
        }
    }

    public static void listStudent() {
        Set<String> keys = students.getKeyset();
        if (keys != null)
            listStudent(keys);
    }

    public static void printStudent(Student student) {
        System.out.println("\nStudent Details:\n================" +
                "\nStudent ID: " + student.getStudentId() +
                "\nName: " + student.getFirstName() + " " + student.getSecondName() +
                "\nLogin Name: " + student.getLoginId() + "\nPersonal number: " + student.getPersonalNumber() +
                "\nDate Of Birth: " + student.getDateOfBirth() + "\nEmail: " + student.getEmail() +
                "\nMobile: " + student.getMobileNumber() + "\nGender: " + student.getGender() +
                "\nAddress: " + student.getAddress() + "\n");
        CoursePrints.listCourse(student.getEnrolledCouses());
    }

    public static void printStudent(String studentId) {
        printStudent(students.getStudent(studentId));
    }

    public static void printStudent() {
        String selectedStudent = StudentOperations.selectStudent();
        if (selectedStudent != null) {
            printStudent(selectedStudent);
        }
    }
    public static final String UPDATE_STUDENT_DATA_INPUT_TEMPLATE = "First name,Second name,Personal#,DoB,Email," +
            "Mobile#,Gender,address";
    private static final String STUDENT_UPDATE_FORMATER = "%-20s %-25s %-12s %-12s %-25s %-12s %-5s %-40s\n";
    private static final String STUDENT_UPDATE_HEADER = String.format(STUDENT_UPDATE_FORMATER,
            "First name", "Second name", "Personal#", "DoB", "Email", "Mobile#", "Gender", "address");

    public static void printStudentForUpdate(Student student) {
        System.out.print(STUDENT_UPDATE_HEADER);
        printUnderLineForString(STUDENT_UPDATE_HEADER);
        System.out.printf(STUDENT_UPDATE_FORMATER,
                student.getFirstName(), student.getSecondName(), student.getPersonalNumber(),
                student.getDateOfBirth(), student.getEmail(), student.getMobileNumber(),
                student.getGender(), student.getAddress());
    }

}
