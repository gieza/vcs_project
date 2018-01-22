package lt.vcs.vcs_project.datalayer;

import org.junit.Test;

import static java.time.LocalDate.now;
import static org.junit.Assert.*;

public class LecturerTest {

    @Test
    public void givenSampleLecturer_whenAddCourse_thenCourseIsInTheLecturerObject() {
        //given
        Lecturer LecturerSample = new Lecturer("albert", "emc2", "Marie", "Curie", "L0001",
                "84_88", "1867-11-07", "Marie.Curie@sorbonne-universite.fr", "+33 01 44 27 27 68", "F",
                "Sorbonne Université - 21, rue de l'École-de-médecine 75006 Paris");
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2", "l0001", "");
        //when
        String coursesBefore = LecturerSample.getReadCourses().toString();
        LecturerSample.addCourse(exampleCourse.getCourseCode());
        String coursesAfter = LecturerSample.getReadCourses().toString();
        //then
        assertEquals(true, coursesBefore.equals("[]") && coursesAfter.equals("[c0001]"));
    }

    @Test
    public void givenSampleLecturerWithACourse_whenAddAnotherCourse_thenLecturerObjectHasBothCourses() {
        //given
        Lecturer LecturerSample = new Lecturer("albert", "emc2", "Marie", "Curie", "L0001",
                "84_88", "1867-11-07", "Marie.Curie@sorbonne-universite.fr", "+33 01 44 27 27 68", "F",
                "Sorbonne Université - 21, rue de l'École-de-médecine 75006 Paris");
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2", "l0001", "");
        Course anotherCourse = new Course("c0002", "Teorine Grioviakasyba", now().plusDays(10), "1.5", "l0001", "");
        LecturerSample.addCourse(exampleCourse.getCourseCode());
        //when
        String coursesBefore = LecturerSample.getReadCourses().toString();
        LecturerSample.addCourse(anotherCourse.getCourseCode());
        String coursesAfter = LecturerSample.getReadCourses().toString();
        //then
        assertEquals(true, coursesBefore.equals("[c0001]") && coursesAfter.equals("[c0002, c0001]"));
    }

    @Test
    public void givenSampleLecturerWithACourse_whenTheSameCourseAdded_thenLecturerObjectHasSingleCourseCopy() {
        //given
        Lecturer LecturerSample = new Lecturer("albert", "emc2", "Marie", "Curie", "L0001",
                "84_88", "1867-11-07", "Marie.Curie@sorbonne-universite.fr", "+33 01 44 27 27 68", "F",
                "Sorbonne Université - 21, rue de l'École-de-médecine 75006 Paris");
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2", "l0001", "");
        LecturerSample.addCourse(exampleCourse.getCourseCode());
        //when
        String coursesBefore = LecturerSample.getReadCourses().toString();
        LecturerSample.addCourse(exampleCourse.getCourseCode());
        String coursesAfter = LecturerSample.getReadCourses().toString();
        //then
        assertEquals(coursesBefore, coursesAfter);
    }

    @Test
    public void givenSampleLecturerWithoutACourse_whenACourseIsRemoved_thenSameLecturerObjectIsReturned() {
        //given
        Lecturer LecturerSample = new Lecturer("albert", "emc2", "Marie", "Curie", "L0001",
                "84_88", "1867-11-07", "Marie.Curie@sorbonne-universite.fr", "+33 01 44 27 27 68", "F",
                "Sorbonne Université - 21, rue de l'École-de-médecine 75006 Paris");
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2", "l0001", "");
        //when
        String coursesBefore = LecturerSample.getReadCourses().toString();
        LecturerSample.removeCourse(exampleCourse.getCourseCode());
        String coursesAfter = LecturerSample.getReadCourses().toString();
        //then
        assertEquals(coursesBefore, coursesAfter);
    }

    @Test
    public void givenSampleLecturerWithACourse_whenTheCourseIsRemoved_thenLecturerObjectHasNoCourses() {
        //given
        Lecturer LecturerSample = new Lecturer("albert", "emc2", "Marie", "Curie", "L0001",
                "84_88", "1867-11-07", "Marie.Curie@sorbonne-universite.fr", "+33 01 44 27 27 68", "F",
                "Sorbonne Université - 21, rue de l'École-de-médecine 75006 Paris");
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2", "l0001", "");
        LecturerSample.addCourse(exampleCourse.getCourseCode());
        //when
        String coursesBefore = LecturerSample.getReadCourses().toString();
        LecturerSample.removeCourse(exampleCourse.getCourseCode());
        String coursesAfter = LecturerSample.getReadCourses().toString();
        //then
        assertEquals(true, coursesAfter.equals("[]") && coursesBefore.equals("[c0001]"));
    }
}