package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Course;
import org.junit.Test;

import static lt.vcs.vcs_project.servicelayer.CourseOperations.*;
import static lt.vcs.vcs_project.utils.DateConversionUtils.parseLocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class CourseOperationsTest {

    private final Course courseExample = new Course("c0001", "Praktine Astrologija", parseLocalDate("2018-03-02"), "2",
            "l0001", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vehicula tincidunt magna," +
            " sollicitudin vehicula ex malesuada sed. Mauris congue fringilla mi, vitae iaculis erat.");


    @Test
    public void givenValidCourseCSV_whenMakeCourseFromCSV_thenReferenceCourseIsReturned() {
        //given
        final String validCourseCSV = "c0001,Praktine Astrologija,2018-03-02,2,l0001,Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Sed vehicula tincidunt magna, sollicitudin vehicula ex malesuada " +
                "sed. Mauris congue fringilla mi, vitae iaculis erat.";
        Course referenceCourse = courseExample;
        //when
        Course actualCourse = makeCourseFromCSV(validCourseCSV);
        //then
        assertThat(actualCourse).isEqualToComparingFieldByFieldRecursively(referenceCourse);
    }

    @Test
    public void givenCourseCSVwithMandatory_whenMakeCourseFromCSV_thenReferenceCourseIsReturned() {
        //given
        final String courseCSVwithMandatoryFields = "c0001,Praktine Astrologija,2018-03-02,2";
        Course referenceCourse = new Course("c0001", "Praktine Astrologija", parseLocalDate("2018-03-02"), "2", "", "");
        //when
        Course actualCourse = makeCourseFromCSV(courseCSVwithMandatoryFields);
        //then
        assertThat(actualCourse).isEqualToComparingFieldByFieldRecursively(referenceCourse);
    }

    @Test
    public void givenTooShortCourseCSV_whenMakeCourseFromCSV_thenNullIsReturned() {
        //given
        final String tooShortCourseCSV = "c0001,Praktine Astrologija,2018-03-02";
        //when
        Course actualCourse = makeCourseFromCSV(tooShortCourseCSV);
        //then
        assertEquals(null, actualCourse);
    }

    @Test
    public void givenSampleCourseAndValidUpdateCSV_whenUpdateCourseFromCSV_thenUpdatedCourseIsReturned() {
        //given
        final String validCSVforCourseUpadate = "Teorine griovakasyba,2018-04-02,1.5,Lorem";
        final Course referenceUpdatedCourse = new Course("c0001", "Teorine griovakasyba", parseLocalDate("2018-04-02"),
                "1.5", "l0001", "Lorem");
        Course course_for_update = courseExample;
        //when
        Course actualCourse = updateCourseFromCSV(course_for_update, validCSVforCourseUpadate);
        //then
        assertThat(actualCourse).isEqualToComparingFieldByFieldRecursively(referenceUpdatedCourse);
    }

    @Test
    public void givenSampleCourseAndTooShortUpdateCSV_whenUpdateCourseFromCSV_thenOriginalCourseIsReturned() {
        //given
        final String TooShorCSVforCourseUpadate = "Teorine griovakasyba,2018-04-02,";
        final Course referenceUpdatedCourse = new Course("c0001", "Teorine griovakasyba", parseLocalDate("2018-04-02"),
                "1.5", "l0001", "Lorem");
        Course course_for_update = courseExample;
        //when
        Course actualCourse = updateCourseFromCSV(course_for_update, TooShorCSVforCourseUpadate);
        //then
        assertThat(actualCourse).isEqualToComparingFieldByFieldRecursively(courseExample);
    }

}