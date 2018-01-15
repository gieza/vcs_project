package lt.vcs.vcs_project.datalayer;

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

    public Student(String loginId, String firstName, String secondName, String password,
                   String studentId, String personalNumber, String dateOfBirth, String email,
                   String mobileNumber, String gender, String address) {
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

    public Account makeAccount() {
        return new Account(this.getLoginId(), this.getPassword(), this.getFirstName(), this.getSecondName(), Role.STUDENT, this.studentId);
    }

    public void addCourse(String courseId) {
        if (!this.enrolledCourses.contains(courseId)) {
            this.enrolledCourses.add(courseId);
        } else {
            System.out.printf("Student %s is already enrolled into course %s", this.studentId, courseId);
        }

    }

    public HashSet<String> getEnrolledCouses() {
        return this.enrolledCourses;
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
