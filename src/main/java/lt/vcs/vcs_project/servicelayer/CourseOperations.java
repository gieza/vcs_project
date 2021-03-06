package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Course;
import lt.vcs.vcs_project.utils.ScannerUtils;

import java.util.Set;

import static lt.vcs.vcs_project.datalayer.DataLayer.courses;
import static lt.vcs.vcs_project.datalayer.DataLayer.lecturers;
import static lt.vcs.vcs_project.utils.DateConversionUtils.parseLocalDate;

public class CourseOperations {

    static Course makeCourseFromCSV(String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length < 4) {
            System.out.printf("Course creation failure: input data has too few input fields\n");
            return null;
        } else {
            if (parseLocalDate(inputArray[2]) == null) {
                System.out.println("Date is provided in wrong format, try \"yyyy-MM-dd\" instead\n");
                return null;
            }
            String description = "";
            if (inputArray.length >= 6) {
                for (int i = 5; i < inputArray.length; i++)
                    description = description + inputArray[i] + ",";
                description = description.substring(0, description.length() - 1);
            }
            return new Course(inputArray[0], inputArray[1],
                    parseLocalDate(inputArray[2]),
                    inputArray[3],
                    inputArray.length > 4 ? inputArray[4] : "",
                    inputArray.length > 5 ? description : "");
        }
    }


    public static void addCourse(String csv) {
        Course newCourse = makeCourseFromCSV(csv);

        if (courses.courseExists(newCourse.getCourseCode())) {
            System.out.printf("Sorry, cannot add Course, Course Code %s already exists\n",
                    newCourse.getCourseCode());
        } else {
            courses.addCourse(newCourse);
        }
    }

    public static void addCourse() {
        CoursePrints.printHeaderForCourseAdd();
        String userInput = ScannerUtils.scanString();
        System.out.printf("Entered values %s\n", userInput);
        addCourse(userInput);
    }


    private static void updateCourse(String courseCode) {
        System.out.printf("\nCurrent Student %s values are:\n", courseCode);
        CoursePrints.printCourseForUpdate(courses.getCourse(courseCode));

        CoursePrints.printHeaderForCourseUpdate();
        String userInput = ScannerUtils.scanString();
        Course updatedCourse = updateCourseFromCSV(courses.getCourse(courseCode), userInput);
        courses.updateCourse(updatedCourse);

        //print updated values
        System.out.println("\nNew Values Are:");
        CoursePrints.printCourseForUpdate(courses.getCourse(courseCode));
    }

    public static void updateCourse() {
        String selectedCourse = CourseOperations.selectCourse();
        if (selectedCourse != null) {
            updateCourse(selectedCourse);
        }
    }

    static Course updateCourseFromCSV(Course course, String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length < 4) {
            System.out.printf("Course update Failure: input data has incorrect number of fields\n");
            return course; //no updates are applied --> input course is returned
        } else {
            String description = "";
            for (int i = 3; i < inputArray.length; i++)
                description = description + inputArray[i] + ",";
            description = description.substring(0, description.length() - 1);
            course.setTitle(inputArray[0]);
            course.setStartDate(inputArray[1]);
            course.setCredit(inputArray[2]);
            course.setDescription(description);
            return course;
        }
    }

    public static void removeCourse() {
        String selectedCourse = selectCourse();
        if (selectedCourse == null)
            return;
        //remove course from Lecturer collection
        String lecturerId = courses.getCourse(selectedCourse).getLecturerId();
        lecturers.removeCourse(lecturerId, selectedCourse);
        //remove course from Student collection
        Set<String> studentList = courses.getCourse(selectedCourse).getEnrolledStudents();
        StudentOperations.removeCourse(studentList, selectedCourse);
        //remove course itself
        courses.removeCourse(selectedCourse);
    }

    public static void deEnrollStudent(Set<String> CourseList, String studentId) {
        if (CourseList == null || studentId == null) return;
        for (String courseCode : CourseList) {
            courses.deEnrollStudent(courseCode, studentId);
        }
    }

    public static String selectCourse() {
        System.out.printf("\n\nEnter to Course Code to select Course:");
        String userInput = ScannerUtils.scanString();
        if (courses.courseExists(userInput)) {
            System.out.printf("Selected Course: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, Course %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }
}
