package lt.vcs.vcs_project.datalayer;

import java.util.HashSet;

public class Lecturer extends Account {

    private String lecturerId;
    private String personalNumber;
    private String dateOfBirth;
    private String email;
    private String mobileNumber;
    private String gender;
    private String address;
    private HashSet<String> readCourses;

    public Lecturer(String loginId, String password, String firstName, String secondName, String lecturerId, String personalNumber, String dateOfBirth, String email, String mobileNumber, String gender, String address) {
        super(loginId, password, firstName, secondName, Role.LECTURER, lecturerId);
        this.lecturerId = lecturerId;
        this.personalNumber = personalNumber;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.address = address;
        this.readCourses = new HashSet<>();
    }

    public Account makeAccount() {
        return new Account(this.getLoginId(), this.getPassword(), this.getFirstName(), this.getSecondName(), Role.STUDENT, this.lecturerId);
    }

    public void addCourse(String courseId) {
        if (!this.readCourses.contains(courseId)) {
            this.readCourses.add(courseId);
        } else {
            System.out.printf("Lecturer %s is already giving into course %s", this.lecturerId, courseId);
        }

    }

    public void removeCourse(String courseId) {
        if (this.readCourses.contains(courseId)) {
            this.readCourses.remove(courseId);
        } else {
            System.out.printf("Lecturer %s is not giving course %s", this.lecturerId, courseId);
        }
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
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

    public HashSet<String> getReadCourses() {
        return this.readCourses;
    }

    public boolean isReadingCourse(String courseCode) {
        return readCourses.contains(courseCode);
    }
}
