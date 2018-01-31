package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Course;

import java.util.Set;

import static lt.vcs.vcs_project.datalayer.DataLayer.*;

public class CoursePrints extends PrintService {
    public static final String COURSE_HEADER_CSV = "courseCode,Title,startDate[yyyy-MM-dd]," +
            "Credit,lecturerCode,Description\n";


    private static final String COURSE_LISTING_FORMATING = "%-8s %-30s %-12s %-8s %-12s %-10s\n";

    private static final String COURSE_LISTING_HEADER = String.format(COURSE_LISTING_FORMATING,
            "Code", "title", "start Date", "credit", "lecturer", "# of Students");

    private static void listCourse(Course course) {
        System.out.printf(COURSE_LISTING_FORMATING, course.getCourseCode(), course.getTitle(),
                course.getStartDate(), course.getCredit(), course.getLecturerId(), course.getEnrolledStudentCount());
    }

    public static void listCourse(Set<String> CourseList) {
        if (CourseList == null) return;

        System.out.print(COURSE_LISTING_HEADER);
        for (String key : CourseList) {
            listCourse(courses.getCourse(key));
        }
    }

    public static void listCourse() {
        Set<String> keys = courses.getKeyset();
        listCourse(keys);
    }

    public static void listAvailableCourses() {
        Set<String> keys = courses.getAvailableCourseKeyset();
        listCourse(keys);

    }

    public static void listEnrolledCourses(String studentId) {
        Set<String> keys = students.getStudent(studentId).getEnrolledCouses();
        listCourse(keys);
    }

    public static void listAssignedCourses(String lecturerId) {
        Set<String> keys = lecturers.getLecturer(lecturerId).getReadCourses();
        listCourse(keys);
    }

    private static void printCourse(Course course) {
        String lecturerName = "";
        if (lecturers.lecturerExists(course.getLecturerId())) {
            lecturerName = lecturers.getLecturer(course.getLecturerId()).getFirstName() +
                    " " + lecturers.getLecturer(course.getLecturerId()).getSecondName();
        }
        System.out.println("\nCourse Details:\n================" +
                "\nCourse Code: " + course.getCourseCode() +
                "\nTitle: " + course.getTitle() + "\nDescription: " + course.getDescription() +
                "\nStartDate: " + course.getStartDate() +
                "\nCredit: " + course.getCredit() +
                "\nLecturer Id: " + course.getLecturerId() +
                "\nLecturer Name: " + lecturerName +
                "\n# of Enrolled Students: " + course.getEnrolledStudentCount());
        StudentPrints.listStudent(course.getEnrolledStudents());
    }

    private static void printCourse(String CourseId) {
        printCourse(courses.getCourse(CourseId));
    }

    public static void printCourse() {
        String selectedCourse = CourseOperations.selectCourse();
        if (selectedCourse != null) {
            printCourse(selectedCourse);
        }
    }

    public static final String UPDATE_COURSE_DATA_INPUT_TEMPLATE = "Title,startDate[yyyy-MM-dd]," +
            "Credit,Description\n";
    private static final String COURSE_UPDATE_FORMATER = "%-25s %-12s %-8s %-40s\n";
    private static final String COURSE_UPDATE_HEADER = String.format(COURSE_UPDATE_FORMATER,
            "Title", "startDate", "Credit", "Description");

    public static void printCourseForUpdate(Course course) {
        System.out.print(COURSE_UPDATE_HEADER);
        printUnderLineForString(COURSE_UPDATE_HEADER);
        System.out.printf(COURSE_UPDATE_FORMATER,
                course.getTitle(), course.getStartDate(), course.getCredit(), course.getDescription());
    }
}


