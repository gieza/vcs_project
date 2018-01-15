package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Course;

import java.util.Set;

import static lt.vcs.vcs_project.datalayer.DataLayer.courses;

public class PrintingCourse extends PrintService {
    public static final String COURSE_HEADER_CSV = "courseCode,Title,startDate[yyyy-MM-dd]," +
            "Credit,lecturerCode,Description\n";


    public static final String COURSE_LISTING_FORMATING = "%-8s %-25s %-10s %-8s %-12s %-25s %-5s " +
            "%-12s %-5s %-40s\n";

    public static final String COURSE_LISTING_HEADER = String.format(COURSE_LISTING_FORMATING,
            "Code", "title", "start Date", "credit", "lecturer", "# of Students");

    public static void listCourse(Course course) {
        //todo: print out Lecturer name instead of code
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

    public static void printCourse(Course course) {
        System.out.println("Course Details:\n================" +
                "\nCourse Code" + course.getCourseCode() +
                "\nTitle:" + course.getTitle() + "\n:" + course.getDescription() +
                "\nStartDate:" + course.getStartDate() +
                "\nCredit:" + course.getCredit() +
                "\nEmail:" + course.getLecturerId() +
                "\nLecturer Name:" +//todo: prideti Lecturer name
                "\n# of Enrolled Students:" + course.getEnrolledStudentCount());
        PrintingStudent.listStudent(course.getEnrolledStudents());
    }

    public static void printCourse(String CourseId) {
        printCourse(courses.getCourse(CourseId));
    }

    public static void printCourse() {
        String selectedCourse = OperationsCourse.selectCourse();
        if (selectedCourse != null) {
            printCourse(selectedCourse);
        }
    }

    public static final String UPDATE_COURSE_DATA_INPUT_TEMPLATE = "Title,startDate[yyyy-MM-dd]," +
            "Credit,Description\n";
    public static final String COURSE_UPDATE_HEADER = "Title,startDate[yyyy-MM-dd],Credit,Description\n";

    public static void printCourseForUpdate(Course course) {
        System.out.print(COURSE_UPDATE_HEADER);
        printUnderLineForString(COURSE_UPDATE_HEADER);
        System.out.printf("%-25s %-10s %-8s %-40s\n",
                course.getTitle(), course.getStartDate(), course.getCredit(), course.getDescription());
    }
}


