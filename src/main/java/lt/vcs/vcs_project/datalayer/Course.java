package lt.vcs.vcs_project.datalayer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;

import static java.time.LocalDate.now;

public class Course implements Serializable {
    private String courseCode;
    private String title;
    private String description;
    private LocalDate startDate;
    private String credit;
    private String lecturerCode;
    private Integer enrolledStudentCount;
    private HashSet<String> enrolledStudents;

    private final Integer MAX_STUDENTS_IN_COURSE = 12;

    public Course(String courseCode, String title, LocalDate startDate, String credit,
                  String lecturerCode, String description) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.credit = credit;
        this.lecturerCode = lecturerCode;
        this.enrolledStudentCount = 0;
        enrolledStudents = new HashSet<>();
    }

    @Override
    public String toString() {
        String enrolledStudentsList = "";
        for (String student : enrolledStudents) {
            enrolledStudentsList = student + "\n";
        }
        return "Course{" +
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



    public boolean available() {

        return (this.enrolledStudentCount < MAX_STUDENTS_IN_COURSE &&
                (this.startDate.isAfter(now())));
    }

    public void enrollStudent(String studentId) {
        if (!this.enrolledStudents.contains(studentId)) {
            this.enrolledStudents.add(studentId);
            this.enrolledStudentCount++;
        } else {
            System.out.printf("Student %s is already enrolled into course %s", studentId, this.courseCode);
        }

    }

    public void deEnrollStudent(String studentId) {
        if (this.enrolledStudents.contains(studentId)) {
            this.enrolledStudents.remove(studentId);
            this.enrolledStudentCount--;
        } else {
            System.out.printf("Student %s is not enrolled into course %s", studentId, this.courseCode);
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getStartDateAsString() {
        return this.startDate.toString();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate);
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getLecturerId() {
        return lecturerCode;
    }

    public void setLecturerId(String lecturerCode) {
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
