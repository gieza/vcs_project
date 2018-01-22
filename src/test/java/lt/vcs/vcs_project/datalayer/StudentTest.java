package lt.vcs.vcs_project.datalayer;

import org.junit.Test;


import static java.time.LocalDate.now;
import static org.junit.Assert.*;

public class StudentTest {

    @Test
    public void givenSampleStudent_whenAddCourse_thenCourseIsInTheStudentObject() {
        //given
        Student studentSample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2", "l0001", "");
        //when
        String coursesBefore = studentSample.getEnrolledCouses().toString();
        studentSample.addCourse(exampleCourse.getCourseCode());
        String coursesAfter = studentSample.getEnrolledCouses().toString();
        //then
        assertEquals(true, coursesBefore.equals("[]") && coursesAfter.equals("[c0001]"));
    }

    @Test
    public void givenSampleStudentWithACourse_whenAddAnotherCourse_thenTheStudentObjectHasBothCourses() {
        //given
        Student studentSample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2", "l0001", "");
        Course anotherCourse = new Course("c0002", "Teorine Grioviakasyba", now().plusDays(10), "1.5", "l0001", "");
        studentSample.addCourse(exampleCourse.getCourseCode());
        //when
        String coursesBefore = studentSample.getEnrolledCouses().toString();
        studentSample.addCourse(anotherCourse.getCourseCode());
        String coursesAfter = studentSample.getEnrolledCouses().toString();
        //then
        assertEquals(true, coursesBefore.equals("[c0001]") && coursesAfter.equals("[c0002, c0001]"));
    }

    @Test
    public void givenSampleStudentWithACourse_whenTheSameCourseAdded_thenTheStudentObjectHasSingleCourseCopy() {
        //given
        Student studentSample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2", "l0001", "");
        studentSample.addCourse(exampleCourse.getCourseCode());
        //when
        String coursesBefore = studentSample.getEnrolledCouses().toString();
        studentSample.addCourse(exampleCourse.getCourseCode());
        String coursesAfter = studentSample.getEnrolledCouses().toString();
        //then
        assertEquals(coursesBefore, coursesAfter);
    }

    @Test
    public void givenSampleStudentWithoutACourse_whenACourseIsRemoved_thenSameStudentObjectIsReturned() {
        //given
        Student studentSample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2", "l0001", "");
        //when
        String coursesBefore = studentSample.getEnrolledCouses().toString();
        studentSample.removeCourse(exampleCourse.getCourseCode());
        String coursesAfter = studentSample.getEnrolledCouses().toString();
        //then
        assertEquals(coursesBefore, coursesAfter);
    }

    @Test
    public void givenSampleStudentWithACourse_whenTheCourseIsRemoved_thenStudentObjectHasNoCourses() {
        //given
        Student studentSample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2", "l0001", "");
        studentSample.addCourse(exampleCourse.getCourseCode());
        //when
        String coursesBefore = studentSample.getEnrolledCouses().toString();
        studentSample.removeCourse(exampleCourse.getCourseCode());
        String coursesAfter = studentSample.getEnrolledCouses().toString();
        //then
        assertEquals(true, coursesAfter.equals("[]") && coursesBefore.equals("[c0001]"));
    }
}