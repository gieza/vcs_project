package lt.vcs.vcs_project.datalayer;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static java.time.LocalDate.now;
import static lt.vcs.vcs_project.utils.DateConversionUtils.parseLocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class CourseCollectionTest {
    private CourseCollection courseCollection;

    @Before
    public void InitializeCourseCollection() {
        //course file is removed
        File courseCollectionFile = new File("CourseList.txt");
        courseCollectionFile.delete();
        courseCollection = new CourseCollection();
    }

    @Test
    public void givenNewCourseList_whenGetCount_thenListSizeIs0() {
        assertEquals(0, courseCollection.getCount());
    }

    @Test
    public void givenNewCourseList_WhenCourseIsAdded_thenCourseIsInTheCollection() {
        //given
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "l0001", "");
        //when
        courseCollection.addCourse(exampleCourse);
        //then
        assertEquals(true, courseCollection.courseExists(exampleCourse.getCourseCode()));
    }

    @Test
    public void givenCourseListWithACourse_WhenSameCourseIsAdded_thenCourseListHasSingleCourse() {
        //given
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "l0001", "");
        courseCollection.addCourse(exampleCourse);
        //when
        courseCollection.addCourse(exampleCourse);
        //then
        assertEquals(1, courseCollection.getCount());
    }


    @Test
    public void givenCourseListWithACourse_whenTheCourseIsUpdated_thenCollectionHasUpdatedCourse() {
        //given
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "l0001", "");
        Course updatedCourse = new Course("c0001", "Teorine griovakasyba", parseLocalDate("2018-04-02"),
                "1.5", "l0001", "Lorem");
        courseCollection.addCourse(exampleCourse);

        // when
        courseCollection.updateCourse(updatedCourse);
        //then
        assertThat(courseCollection.getCourse(exampleCourse.getCourseCode()))
                .isEqualToComparingFieldByFieldRecursively(updatedCourse);
    }

    @Test
    public void givenCourseListWithACourse_whenTheCourseIsRemoved_thenTheCourseIsNotInCourseList() {
        //given
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "l0001", "");
        courseCollection.addCourse(exampleCourse);
        //when
        boolean courseInCollectionBefore = courseCollection.courseExists(exampleCourse.getCourseCode());
        courseCollection.removeCourse(exampleCourse.getCourseCode());
        boolean courseInCollectionAfter = courseCollection.courseExists(exampleCourse.getCourseCode());
        //then
        assertNotEquals(courseInCollectionBefore, courseInCollectionAfter);
    }

    @Test
    public void givenCourseListWithACourse_whenWrongCourseIsRemoved_thenCourseListStillHoldsOriginalCourse() {
        //given
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "l0001", "");
        Course anotherCourse = new Course("c0002", "Teorine griovakasyba", parseLocalDate("2018-04-02"),
                "1.5", "l0001", "Lorem");
        courseCollection.addCourse(exampleCourse);
        //when
        boolean courseInCollectionBefore = courseCollection.courseExists(exampleCourse.getCourseCode());
        courseCollection.removeCourse(anotherCourse.getCourseCode());
        boolean courseInCollectionAfter = courseCollection.courseExists(exampleCourse.getCourseCode());
        //then
        assertEquals(courseInCollectionBefore, courseInCollectionAfter);
    }


    @Test
    public void enrollStudent() {
    }

    @Test
    public void deEnrollStudent() {
    }
}