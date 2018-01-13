package lt.vcs.vcs_project.datalayer;

import java.util.Arrays;
import java.util.HashSet;

public class Student extends Account {

    private String studentId;
    private String personalNumber;
    private String dateOfBirth;
    private String email;
    private String mobileNumber;
    private String gender;
    private String address;
    private HashSet<String> enrolledCourses;

    public Student(String loginId, String firstName, String secondName, String password, String studentId, String personalNumber, String dateOfBirth, String email, String mobileNumber, String gender, String address) {
        super(loginId, firstName, secondName, password, Role.STUDENT);
        this.studentId = studentId;
        this.personalNumber = personalNumber;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.address = address;
        enrolledCourses = new HashSet<>();
    }


    public Student(String csv) {
        super();
        String[] inputArray = csv.split(",");
        if (inputArray.length >= 11) {
            this.setLoginId(inputArray[0]);
            this.setPassword(inputArray[1]);
            this.setFirstName(inputArray[2]);
            this.setSecondName(inputArray[3]);
            this.setRole(Role.STUDENT);
            this.setPersonalId(inputArray[4]);
            this.studentId = inputArray[4];
            this.personalNumber = inputArray[5];
            this.dateOfBirth = inputArray[6];
            this.email = inputArray[7];
            this.mobileNumber = inputArray[8];
            this.gender = inputArray[9];
            this.address = inputArray[10];
            for (int i = 11; i < inputArray.length; i++) {
                this.address = this.address + "," + inputArray[11];
            }
            enrolledCourses = new HashSet<>();
        } else {
            System.out.printf("Failure: cannot create Student -- number of fields do not match required number\n");
        }
    }

    public Account getAccount() {
        return new Account(this.getLoginId(), this.getPassword(), this.getFirstName(), this.getSecondName(), Role.STUDENT, this.studentId);
    }

    static public String printHeaderCSV() {
        return "Login,Password,First name,Second name,StudentId,personalNumber,dateOfBirth,email,mobileNumber,gender,address";
    }

    public String toStringCSV() {
        return String.join(",", Arrays.asList(this.getLoginId(), this.getPassword(), this.getFirstName(), this.getSecondName(),
                this.getRole().toString(), this.studentId, this.personalNumber, this.dateOfBirth, this.email, this.mobileNumber, this.gender, this.address));

    }

    public void addCourse(String courseId) {
        if (!this.enrolledCourses.contains(courseId)) {
            this.enrolledCourses.add(courseId);
        } else {
            System.out.printf("Student %s is already enrolled into course %s", this.studentId, courseId);
        }

    }

    public String getStudentId() {
        return studentId;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
