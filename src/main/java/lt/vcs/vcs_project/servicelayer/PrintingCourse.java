package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Course;

import java.util.Set;

import static lt.vcs.vcs_project.datalayer.DataLayer.courses;

public class PrintingCourse extends PrintService {

    public static final String COURSE_LISTING_FORMATING = "%-8s %-25s %-10s %-8s %-12s %-25s %-5s " +
            "%-12s %-5s %-40s\n";

    public static final String COURSE_LISTING_HEADER = String.format(COURSE_LISTING_FORMATING,
            "Code", "title", "start Date", "credit", "lecturer", "# of Students");

    public static void listCourse(Course course) {
        //todo: print out Lecturer name instead of code
        System.out.printf(COURSE_LISTING_FORMATING, course.getCourseCode(), course.getTitle(),
                course.getStartDate(), course.getCredit(), course.getLecturerCode(), course.getEnrolledStudentCount());
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
}


