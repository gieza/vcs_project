package lt.vcs.vcs_project.backend;

import lt.vcs.vcs_project.utils.StringDateConversion;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class Course implements Serializable {
    private String courseCode;
    private String title;
    private String description;
    private Date startDate;
    private String credit;
    private String lecturerCode;
    private Integer enrolledStudentCount;
    private HashSet<String> enrolledStudents;

    private final Integer MAX_STUDENTS_IN_COURSE = 12;

    public Course(String courseCode, String title, String description, Date startDate, String credit, String lecturerCode) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.credit = credit;
        this.lecturerCode = lecturerCode;
        this.enrolledStudentCount = 0;
        enrolledStudents = new HashSet<>();
    }

    public Course(String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length == 6) {
            this.courseCode = inputArray[0];
            this.title = inputArray[1];
            this.description = inputArray[2];
            this.startDate = StringDateConversion.String2Date(inputArray[3]);
            this.credit = inputArray[4];
            this.lecturerCode = inputArray[5];
            this.enrolledStudentCount = 0;
            enrolledStudents = new HashSet<>();
        } else {
            System.out.printf("Incorrect number of fields");
        }
    }

    @Override
    public String toString() {
        String enrolledStudentsList = "";
        for (String student : enrolledStudents) {
            enrolledStudentsList = student + "\n";
            //todo paimti print line is student objekto
        }
        return "Course{" + //todo pataisyti formata
                "courseCode='" + courseCode + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", credit='" + credit + '\'' +
                ", lecturerCode='" + lecturerCode + '\'' +
                ", enrolledStudentCount=" + enrolledStudentCount +
                ", enrolledStudents=" + enrolledStudentsList +
                '}';
    }


    public String toStringCSV() {

        return String.join(",", Arrays.asList(this.courseCode, this.title,
                this.description, this.startDate.toString(), this.credit, this.lecturerCode));
    }

    static public String printHeaderCSV() {
        return "courseCode,title,Description,startDate[yyyy-MM-dd],credit,lecturerCode";
    }

    public boolean available() {

        return (this.enrolledStudentCount < MAX_STUDENTS_IN_COURSE && (this.startDate.after(new Date())));
    }

    public void enrollStudent(String studentId) {
        if (!this.enrolledStudents.contains(studentId)) {
            this.enrolledStudents.add(studentId);
            this.enrolledStudentCount++;
        } else {
            System.out.printf("Student %s is already enrolled into course %s", studentId, this.courseCode);
        }

    }


    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getLecturerCode() {
        return lecturerCode;
    }

    public void setLecturerCode(String lecturerCode) {
        this.lecturerCode = lecturerCode;
    }

    public Integer getEnrolledStudentCount() {
        return enrolledStudentCount;
    }

    public void setEnrolledStudentCount(Integer enrolledStudentCount) {
        this.enrolledStudentCount = enrolledStudentCount;
    }

    public HashSet<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(HashSet<String> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}
