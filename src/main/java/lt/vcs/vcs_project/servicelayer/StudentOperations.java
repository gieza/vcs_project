package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Account;
import lt.vcs.vcs_project.datalayer.Student;
import lt.vcs.vcs_project.utils.ScannerUtils;

import java.util.Set;

import static lt.vcs.vcs_project.datalayer.DataLayer.*;
import static lt.vcs.vcs_project.servicelayer.PrintService.askForNewPassword;

public class StudentOperations {


    public static Student makeStudentFromCSV(String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length < 5) {
            System.out.printf("Student creation failure: input data has too few input fields\n");
            return null;
        } else {
            String address = "";
            if (inputArray.length >= 11) {
                for (int i = 10; i < inputArray.length; i++)
                    address = address + inputArray[i] + ",";
                address = address.substring(0, address.length() - 1);
            }

            return new Student(inputArray[0], inputArray[1], inputArray[2],
                    inputArray[3], inputArray[4],
                    inputArray.length > 5 ? inputArray[5] : "",
                    inputArray.length > 6 ? inputArray[6] : "",
                    inputArray.length > 7 ? inputArray[7] : "",
                    inputArray.length > 8 ? inputArray[8] : "",
                    inputArray.length > 9 ? inputArray[9] : "",
                    inputArray.length > 10 ? address : "");
        }
    }

    public static void addStudent(String csv) {
        Student newStudent = makeStudentFromCSV(csv);

        if (students.studentExists(newStudent.getStudentId())) {
            System.out.printf("Sorry, cannot add Student, StudentId %s already exists\n",
                    newStudent.getStudentId());
        } else if (accounts.accountExists(newStudent.getLoginId())) {
            System.out.printf("Sorry, cannot add Student, login account %s already exists\n",
                    newStudent.getLoginId());
        } else {
            accounts.addAccount(newStudent.makeAccount());
            students.addStudent(newStudent);
        }
    }

    public static void addStudent() {
        System.out.printf("\nEnter new Student data in CommaSeparatedValue format\nfollowing template: %s\n",
                StudentPrints.STUDENT_HEADER_CSV);
        String userInput = ScannerUtils.scanString();
        System.out.printf("Entered values %s\n", userInput);
        addStudent(userInput);
    }

    public static void updateStudent(String studentId) {
        System.out.printf("\nCurrent Student %s values are:\n", studentId);
        StudentPrints.printStudentForUpdate(students.getStudent(studentId));

        System.out.println("\n\nEnter new Student data in CommaSeparatedValue format" +
                "\nfollowing template:");
        System.out.println(StudentPrints.UPDATE_STUDENT_DATA_INPUT_TEMPLATE);
        String userInput = ScannerUtils.scanString();
        Student updatedStudent = updateStudentFromCSV(students.getStudent(studentId), userInput);
        students.updateStudent(updatedStudent);
        //synchronize data in account collection
        accounts.updateAccount(updatedStudent.makeAccount());
        //print updated values
        System.out.println("\nNew Values Are:");
        StudentPrints.printStudentForUpdate(students.getStudent(studentId));

    }

    public static void updateStudent() {
        String selectedStudent = StudentOperations.selectStudent();
        if (selectedStudent != null) {
            updateStudent(selectedStudent);
        }
    }

    public static Student updateStudentFromCSV(Student student, String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length < 8) {
            System.out.printf("Student update Failure: input data has incorrect number of fields\n");
            return student; //no updates are applied --> input student is returned
        } else {
            //address field might contain commas, there anything beyond 8th column should be combined
            String address = "";
            for (int i = 7; i < inputArray.length; i++)
                address = address + inputArray[i] + ",";
            address = address.substring(0, address.length() - 1);
            student.setFirstName(inputArray[0]);
            student.setSecondName(inputArray[1]);
            student.setPersonalNumber(inputArray[2]);
            student.setDateOfBirth(inputArray[3]);
            student.setEmail(inputArray[4]);
            student.setMobileNumber(inputArray[5]);
            student.setGender(inputArray[6]);
            student.setAddress(address);
            return student;
        }
    }

    static public void changeStudentPassword(String studentId) {
        String newPassword = askForNewPassword();
        if (newPassword.length() < 1) {
            System.out.println("User password cannot be empty");
        } else if (students.studentExists(studentId)) {
            Student studentToBeChanged = students.getStudent(studentId);
            studentToBeChanged.setPassword(newPassword);
            students.updateStudent(studentToBeChanged);
            accounts.updateAccount(studentToBeChanged.makeAccount());
        } else {
            System.out.printf("Student password failed - no such account %s\n", studentId);
        }
    }

    static public void changeStudentPassword() {
        String selectedStudent = StudentOperations.selectStudent();
        if (selectedStudent != null)
            changeStudentPassword(selectedStudent);
    }

    static public void removeStudent() {
        String selectedStudent = StudentOperations.selectStudent();
        if (selectedStudent == null)
            return;
        //remove student from courses
        Set<String> courseList = students.getStudent(selectedStudent).getEnrolledCouses();
        CourseOperations.deEnrollStudent(courseList, selectedStudent);
        //remove student itself
        accounts.removeAccount(students.getLoginId(selectedStudent));
        students.removeStudent(selectedStudent);
    }

    public static String selectStudent() {
        System.out.printf("\n\nEnter to Student Id to select student:");
        String userInput = ScannerUtils.scanString();
        if (students.studentExists(userInput)) {
            System.out.printf("Selected student: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, StudentId %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }

    public static void assignAnyCourse2Student() {
        String selectedCourse = CourseOperations.selectCourse();
        String selectedStudent = StudentOperations.selectStudent();
        if (selectedCourse != null && selectedStudent != null) {
            courses.enrollStudent(selectedCourse, selectedStudent);
            students.addCourse(selectedStudent, selectedCourse);
        }
    }

    public static void assignAvailableCourse2Student(String courseCode, String studentId) {
        if (!courses.courseExists(courseCode)) {
            System.out.println("Course or student does not exist");
            return;
        }
        if (courses.getCourse(courseCode).available()) {
            courses.enrollStudent(courseCode, studentId);
            students.addCourse(studentId, courseCode);
        } else {
            System.out.println("Course is not available for enrollment");
        }
    }

    public static void assignAvailableCourse2Student(String studentId) {
        String selectedCourse = CourseOperations.selectCourse();
        if (selectedCourse != null)
            assignAvailableCourse2Student(selectedCourse, studentId);
    }


    public static void removeCourse(Set<String> StudentList, String courseCode) {
        if (StudentList == null || courseCode == null) return;
        for (String studentId : StudentList) {
            students.removeCourse(studentId, courseCode);
        }
    }

}