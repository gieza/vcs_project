package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Course;
import lt.vcs.vcs_project.utils.ScannerUtils;
import lt.vcs.vcs_project.utils.StringDateConversion;

import static lt.vcs.vcs_project.datalayer.DataLayer.courses;

public class OperationsCourse {

    public static Course makeCourseFromCSV(String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length < 4 || inputArray.length > 6) {
            System.out.printf("Course creation failure: input data has too few input fields\n");
            return null;
        } else {
            return new Course(inputArray[0], inputArray[1],
                    StringDateConversion.String2Date(inputArray[3]), inputArray[2],
                    inputArray.length > 4 ? inputArray[4] : "",
                    inputArray.length > 5 ? inputArray[5] : "");
        }
    }


    public static void addCourse(String csv) {
        Course newCourse = makeCourseFromCSV(csv);

        if (courses.containsKey(newCourse.getCourseCode())) {
            System.out.printf("Sorry, cannot add Course, Course Code %s already exists",
                    newCourse.getCourseCode());
        } else {
            courses.addCourse(newCourse);
        }
    }

    public static void addCourse() {
        System.out.printf("\nEnter new Course data in CommaSeparatedValue format\nfollowing template: %s\n",
                PrintingCourse.COURSE_HEADER_CSV);
        String userInput = ScannerUtils.scanString();
        System.out.printf("Entered values %s\n", userInput);
        addCourse(userInput);
    }


    public static void updateCourse(String courseCode) {
        System.out.printf("\nCurrent Student %s values are:\n", courseCode);
        PrintingCourse.printCourseForUpdate(courses.getCourse(courseCode));

        System.out.println("\n\nEnter new Course data in CommaSeparatedValue format" +
                "\nfollowing template:");
        System.out.println(PrintingCourse.UPDATE_COURSE_DATA_INPUT_TEMPLATE);
        String userInput = ScannerUtils.scanString();
        Course updatedCourse = updateCourseFromCSV(courses.getCourse(courseCode), userInput);
        courses.updateCourse(updatedCourse);

        //print updated values
        System.out.println("\nNew Values Are:");
        PrintingCourse.printCourseForUpdate(courses.getCourse(courseCode));
    }

    public static void updateCourse() {
        String selectedCourse = OperationsCourse.selectCourse();
        if (selectedCourse != null) {
            updateCourse(selectedCourse);
        }
    }

    public static Course updateCourseFromCSV(Course course, String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length != 4) {
            System.out.printf("Course update Failure: input data has incorrect number of fields\n");
            return course; //no updates are applied --> input course is returned
        } else {
            course.setTitle(inputArray[0]);
            course.setStartDate(inputArray[1]);
            course.setCredit(inputArray[2]);
            course.setDescription(inputArray[3]);
            return course;
        }
    }

    public static void removeCourse() {
        String selectedCourse = selectCourse();
        courses.removeCourse(selectedCourse);
        //OperationsStudent.removeCourse(selectedCourse);
        //OperationsLecturer.removeCourse(selectedCourse);
        //todo:remove course from students and lecturer collections
    }


    public static String selectCourse() {
        System.out.printf("\n\nEnter to Course Code to select Course:");
        String userInput = ScannerUtils.scanString();
        if (courses.containsKey(userInput)) { //todo:courseExists
            System.out.printf("Selected Course: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, Course %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }
}
