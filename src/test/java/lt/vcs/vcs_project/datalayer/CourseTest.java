package lt.vcs.vcs_project.datalayer;

import org.junit.Test;

import java.util.HashSet;

import static java.time.LocalDate.now;
import static org.junit.Assert.*;

public class CourseTest {

    @Test
    public void givenACourseandSampleStudent_whenEnrollingTheStudentToCourse_thenStudentIsFoundOnEnrolledList() {
        //given
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "l0001", "");
        final Student studentExample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        //when
        boolean studentNotEnrolledOnCourseBefore = !exampleCourse.isEnrolled(studentExample.getStudentId());
        exampleCourse.enrollStudent(studentExample.getStudentId());
        boolean studentEnrolledOnCourseAfter = exampleCourse.isEnrolled(studentExample.getStudentId());
        //then
        assertEquals(true, studentEnrolledOnCourseAfter && studentNotEnrolledOnCourseBefore);
    }

    @Test
    public void givenCourseWithEnrolledStudent_whenEnrollingTheStudentfromCourse_thenEnrolledListDoesNotChange() {
        //given
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2", "l0001", "");
        final Student studentExample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        exampleCourse.enrollStudent(studentExample.getStudentId());
        //when
        HashSet<String> enrolledStudentsBefore = exampleCourse.getEnrolledStudents();
        exampleCourse.deEnrollStudent(studentExample.getStudentId());
        HashSet<String> enrolledStudentsAfter = exampleCourse.getEnrolledStudents();
        //then
        assertEquals(enrolledStudentsBefore.toString(), enrolledStudentsAfter.toString());
    }

    @Test
    public void givenCourseWithEnrolledStudent_whenDeEnrollingTheStudentfromCourse_thenStudentIsNotOnEnrolledList() {
        //given
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "l0001", "");
        final Student studentExample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        exampleCourse.enrollStudent(studentExample.getStudentId());
        //when
        boolean studentEnrolledOnCourseBefore = exampleCourse.isEnrolled(studentExample.getStudentId());
        exampleCourse.deEnrollStudent(studentExample.getStudentId());
        boolean studentNotEnrolledOnCourseAfter = !exampleCourse.isEnrolled(studentExample.getStudentId());
        //then
        assertEquals(true, studentEnrolledOnCourseBefore && studentNotEnrolledOnCourseAfter);
    }

    @Test
    public void givenCourseWithEnrolledStudent_whenDeEnrollingOtherStudent_thenEnrolledListDoesNotChange() {
        //given
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2", "l0001", "");
        final Student studentExample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        final Student studentOther = new Student("student", "student", "Student2", "Student2", "s0002", "", "", "", "", "", "");
        exampleCourse.enrollStudent(studentExample.getStudentId());
        //when
        HashSet<String> enrolledStudentsBefore = exampleCourse.getEnrolledStudents();
        exampleCourse.deEnrollStudent(studentOther.getStudentId());
        HashSet<String> enrolledStudentsAfter = exampleCourse.getEnrolledStudents();
        //then
        assertEquals(enrolledStudentsBefore.toString(), enrolledStudentsAfter.toString());
    }

    @Test
    public void givenCourseStartingAfterTodayWith0Students_whenCheckedForAvailabilty_thenTrueIsReturned() {
        //given
        Course courseNotYetStarted = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "l0001", "");
        //when
        boolean isAvailable = courseNotYetStarted.available() && courseNotYetStarted.getEnrolledStudentCount() == 0;
        //then
        assertEquals(true, isAvailable);
    }

    @Test
    public void givenCourseAlreadyStartedWith0Student_whenCheckedForAvailability_thenFalseIsReturned() {
        //given
        Course courseNotYetStarted = new Course("c0001", "Praktine Astrologija", now().minusDays(10), "2",
                "l0001", "");
        //when
        boolean isAvailable = courseNotYetStarted.available() && courseNotYetStarted.getEnrolledStudentCount() == 0;
        //then
        assertEquals(false, isAvailable);
    }

    @Test
    public void givenCourseStartingAfterTodayAndWith11Student_whenCheckedForAvailability_thenTrueIsReturned() {
        //given
        Course courseNotYetStarted = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "l0001", "");
        //enroll 11 students
        for (int i = 1; i <= 11; i++) {
            courseNotYetStarted.enrollStudent("S000" + Integer.toString(i));
        }

        //when
        boolean isAvailable = courseNotYetStarted.available() && courseNotYetStarted.getEnrolledStudentCount() == 11;
        //then
        assertEquals(true, isAvailable);
    }

    @Test
    public void givenCourseStartingAfterTodayAndWith12Student_whenCheckedForAvailability_thenFalseIsReturned() {
        //given
        Course courseNotYetStarted = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "l0001", "");
        //enroll 12 students
        for (int i = 1; i <= 12; i++) {
            courseNotYetStarted.enrollStudent("S000" + Integer.toString(i));
        }

        //when
        boolean isAvailable = !courseNotYetStarted.available() && courseNotYetStarted.getEnrolledStudentCount() == 12;
        //then
        assertEquals(true, isAvailable);
    }

    @Test
    public void givenNewCourseStartingAfterToday_whenAddingSameStudent12Times_thenCourseIsStillAvailable() {
        //given
        Course courseNotYetStarted = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "l0001", "");
        final Student studentExample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        //when
        for (int i = 1; i <= 12; i++) {
            courseNotYetStarted.enrollStudent(studentExample.getStudentId());
        }
        //then
        assertEquals(true, courseNotYetStarted.available());
    }

}