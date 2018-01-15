package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Lecturer;
import lt.vcs.vcs_project.utils.ScannerUtils;

import java.util.Set;


import static lt.vcs.vcs_project.datalayer.DataLayer.*;
import static lt.vcs.vcs_project.servicelayer.PrintService.askForNewPassword;

public class OperationsLecturer {

    public static Lecturer makeLecturerFromCSV(String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length < 5) {
            System.out.printf("Lecturer creation failure: input data has too few input fields\n");
            return null;
        } else {
            String address = "";
            if (inputArray.length >= 11) {
                for (int i = 10; i < inputArray.length; i++)
                    address = address + "," + inputArray[i];
            }
            return new Lecturer(inputArray[0], inputArray[1], inputArray[2],
                    inputArray[3], inputArray[4],
                    inputArray.length > 5 ? inputArray[5] : "",
                    inputArray.length > 6 ? inputArray[6] : "",
                    inputArray.length > 7 ? inputArray[7] : "",
                    inputArray.length > 8 ? inputArray[8] : "",
                    inputArray.length > 9 ? inputArray[9] : "",
                    inputArray.length > 10 ? address : "");
        }
    }

    public static void addLecturer(String csv) {
        Lecturer newLecturer = makeLecturerFromCSV(csv);

        if (lecturers.containsKey(newLecturer.getLecturerId())) {
            System.out.printf("Sorry, cannot add Lecturer, LecturerId %s already exists",
                    newLecturer.getLecturerId());
        } else if (accounts.containsKey(newLecturer.getLoginId())) {
            System.out.printf("Sorry, cannot add Lecturer, login account %s already exists",
                    newLecturer.getLoginId());
        } else {
            accounts.addAccount(newLecturer.makeAccount());
            lecturers.addLecturer(newLecturer);
        }
    }

    public static void addLecturer() {
        System.out.printf("\nEnter new Lecturer data in CommaSeparatedValue format\nfollowing template: %s\n",
                PrintingLecturer.LECTURER_HEADER_CSV);
        String userInput = ScannerUtils.scanString();
        System.out.printf("Entered values %s\n", userInput);
        addLecturer(userInput);
    }

    public static void updateLecturer(String lecturerId) {
        System.out.printf("\nCurrent Lecturer %s values are:\n", lecturerId);
        PrintingLecturer.printLecturerForUpdate(lecturers.getLecturer(lecturerId));

        System.out.println("\n\nEnter new Lecturer data in CommaSeparatedValue format" +
                "\nfollowing template:");
        System.out.println(PrintingLecturer.UPDATE_LECTURER_DATA_INPUT_TEMPLATE);
        String userInput = ScannerUtils.scanString();
        Lecturer updatedLecturer = updateLecturerFromCSV(lecturers.getLecturer(lecturerId), userInput);
        lecturers.updateLecturer(updatedLecturer);
        //synchronize data in account collection
        accounts.updateAccount(updatedLecturer.makeAccount());
        //print updated values
        System.out.println("\nNew Values Are:");
        PrintingLecturer.printLecturerForUpdate(lecturers.getLecturer(lecturerId));

    }

    public static void updateLecturer() {
        String selectedLecturer = selectLecturer();
        if (selectedLecturer != null) {
            updateLecturer(selectedLecturer);
        }
    }

    public static Lecturer updateLecturerFromCSV(Lecturer lecturer, String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length < 8) {
            System.out.printf("Lecturer update Failure: input data has incorrect number of fields\n");
            return lecturer; //no updates are applied --> input Lecturer is returned
        } else {
            //address field might contain commas, there anything beyond 8th column should be combined
            String address = "";
            for (int i = 7; i < inputArray.length; i++)
                address = address + "," + inputArray[i];
            lecturer.setFirstName(inputArray[0]);
            lecturer.setSecondName(inputArray[1]);
            lecturer.setPersonalNumber(inputArray[2]);
            lecturer.setDateOfBirth(inputArray[3]);
            lecturer.setEmail(inputArray[4]);
            lecturer.setMobileNumber(inputArray[5]);
            lecturer.setGender(inputArray[6]);
            lecturer.setAddress(address);
            return lecturer;
        }
    }

    static public void changeLecturerPassword(String lecturerId) {
        String newPassword = askForNewPassword();
        if (newPassword.length() < 1) {
            System.out.println("User password cannot be empty");
        } else if (lecturers.containsKey(lecturerId)) {
            lecturers.getLecturer(lecturerId).setPassword(newPassword);
        } else {
            System.out.printf("Lecturer password failed - no such account %s\n", lecturerId);
        }
    }

    static public void changeLecturerPassword() {
        String selectedLecturer = selectLecturer();
        changeLecturerPassword(selectedLecturer);
    }

    static public void removeLecturer() {
        String selectedLecturer = selectLecturer();
        accounts.removeAccount(lecturers.getLoginId(selectedLecturer));
        Set<String> courseList = lecturers.getLecturer(selectedLecturer).getReadCourses();
        removeCourseFromLecturer(courseList, selectedLecturer);
        lecturers.removeLecturer(selectedLecturer);
    }

    public static void assignCourse2Lecturer() {
        String selectedCourse = OperationsCourse.selectCourse();
        String selectedLecturer = selectLecturer();
        if (selectedCourse != null && selectedLecturer != null) {
            courses.getCourse(selectedCourse).setLecturerId(selectedLecturer);
            lecturers.getLecturer(selectedLecturer).addCourse(selectedCourse);
        }
    }

    static public void removeCourseFromLecturer(Set<String> courseList, String lecturerId) {
        if (courseList == null || lecturerId == null) return;
        for (String courseId : courseList) {
            lecturers.removeCourse(lecturerId, courseId);
        }
    }

    static public void removeCourseFromLecturer(String courseId, String lecturerId) {
        if (courseId == null || lecturerId == null) return;

        lecturers.removeCourse(lecturerId, courseId);
    }

    public static String selectLecturer() {
        System.out.printf("\n\nEnter to Lecturer Id to select Lecturer:");
        String userInput = ScannerUtils.scanString();
        if (lecturers.containsKey(userInput)) {
            System.out.printf("Selected Lecturer: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, LecturerId %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }


}
