package lt.vcs.vcs_project.datalayer;

import java.util.Arrays;
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

    public Lecturer(String loginId, String firstName, String secondName, String password, String lecturerId, String personalNumber, String dateOfBirth, String email, String mobileNumber, String gender, String address) {
        super(loginId, firstName, secondName, password, Role.LECTURER);
        this.lecturerId = lecturerId;
        this.personalNumber = personalNumber;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.address = address;
        this.readCourses = new HashSet<>();
    }

    public Lecturer(String csv) {
        super();
        String[] inputArray = csv.split(",");
        if (inputArray.length >= 11) {
            this.setLoginId(inputArray[0]);
            this.setPassword(inputArray[1]);
            this.setFirstName(inputArray[2]);
            this.setSecondName(inputArray[3]);
            this.setRole(Role.STUDENT);
            this.setPersonalId(inputArray[4]);
            this.lecturerId = inputArray[4];
            this.personalNumber = inputArray[5];
            this.dateOfBirth = inputArray[6];
            this.email = inputArray[7];
            this.mobileNumber = inputArray[8];
            this.gender = inputArray[9];
            this.address = inputArray[10];
            for (int i = 11; i < inputArray.length; i++) {
                this.address = this.address + "," + inputArray[11];
            }
            this.readCourses = new HashSet<>();
        } else {
            System.out.printf("Failure: cannot create Lecturer -- number of fields do not match required number\n");
        }
    }

    public Account makeAccount() {
        return new Account(this.getLoginId(), this.getPassword(), this.getFirstName(), this.getSecondName(), Role.STUDENT, this.lecturerId);
    }

    static public String printHeaderCSV() {
        return "Login,Password,First name,Second name,lecturerId,personalNumber,dateOfBirth,email,mobileNumber,gender,address";
    }

    public String toStringCSV() {
        return String.join(",", Arrays.asList(this.getLoginId(), this.getPassword(), this.getFirstName(), this.getSecondName(),
                this.getRole().toString(), this.lecturerId, this.personalNumber, this.dateOfBirth, this.email, this.mobileNumber, this.gender, this.address));

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
}
