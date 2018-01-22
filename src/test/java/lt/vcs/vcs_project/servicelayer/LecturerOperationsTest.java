package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Course;
import lt.vcs.vcs_project.datalayer.Lecturer;
import org.junit.Test;

import static java.time.LocalDate.now;
import static lt.vcs.vcs_project.datalayer.DataLayer.courses;
import static lt.vcs.vcs_project.datalayer.DataLayer.lecturers;
import static lt.vcs.vcs_project.servicelayer.LecturerOperations.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class LecturerOperationsTest {
    private final Lecturer lecturerExample = new Lecturer("albert", "emc2", "Albert", "Einstein",
            "L0001", "299792458", "1879-03-14",
            "albert@emcLab.cern", "+41 22 76 784 84", "M", "CERN, CH-1211 Geneva 23, Switzerland");

    @Test
    public void GivenValidLecturerCSV_whenMakeLecturerFromCSV_thenReferenceLecturerIsReturned() {
        //given
        final String lecturerCSV_correct = "albert,emc2,Albert,Einstein,L0001,299792458,1879-03-14," +
                "albert@emcLab.cern,+41 22 76 784 84,M,CERN, CH-1211 Geneva 23, Switzerland";
        Lecturer referenceLecturer = lecturerExample;
        //when
        Lecturer actualLecturer = makeLecturerFromCSV(lecturerCSV_correct);
        //then
        assertThat(actualLecturer).isEqualToComparingFieldByFieldRecursively(referenceLecturer);
    }

    @Test
    public void GivenLecturerCSVwithMandatoryFields_whenMakeLecturerFromCSV_thenReferenceLecturerIsReturned() {
        //given
        final String lecturerCSV_mandatory = "albert,emc2,Albert,Einstein,L0001";
        Lecturer referenceLecturer = new Lecturer("albert", "emc2", "Albert", "Einstein",
                "L0001", "", "", "", "", "", "");
        //when
        Lecturer actualLecturer = makeLecturerFromCSV(lecturerCSV_mandatory);
        // +then
        assertThat(actualLecturer).isEqualToComparingFieldByFieldRecursively(referenceLecturer);
    }

    @Test
    public void GivenLecturerCSVwithTooFewFields_whenMakeLecturerFromCSV_thenNullIsReturned() {
        //given
        final String lecturerCSV_mandatory = "albert,emc2,Albert,Einstein";
        //when
        Lecturer actualLecturer = makeLecturerFromCSV(lecturerCSV_mandatory);
        //then
        assertEquals(null, actualLecturer);
    }

    @Test
    public void GivenSampleLecturerAndValidUpdateCSV_whenUpdateLecturerFromCSV_thenUpdatedLecturerIsReturned() {
        //given
        final String validCSVforLecturerUpadate = "Marie,Curie,84_88,1867-11-07,Marie.Curie@sorbonne-universite.fr," +
                "+33 01 44 27 27 68,F,Sorbonne Université - 21, rue de l'École-de-médecine 75006 Paris";
        final Lecturer referenceUpdatedLecturer = new Lecturer("albert", "emc2", "Marie", "Curie", "L0001",
                "84_88", "1867-11-07", "Marie.Curie@sorbonne-universite.fr", "+33 01 44 27 27 68", "F",
                "Sorbonne Université - 21, rue de l'École-de-médecine 75006 Paris");
        Lecturer lecturer_for_update = lecturerExample;
        //when
        Lecturer actualLecturer = updateLecturerFromCSV(lecturer_for_update, validCSVforLecturerUpadate);
        //then
        assertThat(actualLecturer).isEqualToComparingFieldByFieldRecursively(referenceUpdatedLecturer);
    }

    @Test
    public void GivenSampleLecturerAndTooShortUpdateCSV_whenUpdateLecturerFromCSV_thenReturnedLecturerIsOriginal() {
        //given
        final String tooShortCSVforLecturerUpadate = "Marie,Curie,84_88,1867-11-07,Marie.Curie@sorbonne-universite.fr," +
                "+33 01 44 27 27 68,F,";
        Lecturer lecturer_for_update = lecturerExample;
        //when
        Lecturer actualLecturer = updateLecturerFromCSV(lecturer_for_update, tooShortCSVforLecturerUpadate);
        //then
        assertThat(actualLecturer).isEqualToComparingFieldByFieldRecursively(lecturerExample);
    }


    @Test
    public void GivenLecturerInCollectionAndACourse_whenAssigningCourse_thenCourseIsAddedToList() {
        //given
        Lecturer sampleLecturer = LecturerOperations.makeLecturerFromCSV("albert,emc2,Albert,Einstein,L0001," +
                "299792458,1879-03-14,albert@emcLab.cern,+41 22 76 784 84,M,CERN, CH-1211 Geneva 23, Switzerland");
        Course exampleCourse = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "L9999", "");
        if (lecturers.lecturerExists(sampleLecturer.getLecturerId()))
            lecturers.removeLecturer(sampleLecturer.getLecturerId());
        lecturers.addLecturer(sampleLecturer);
        if (courses.courseExists(exampleCourse.getCourseCode()))
            courses.removeCourse(exampleCourse.getCourseCode());
        courses.addCourse(exampleCourse);
        //when
        assignCourse2Lecturer(exampleCourse.getCourseCode(), sampleLecturer.getLecturerId());
        //then
        boolean courseHasCorrectLecturerAssigned =
                courses.getCourse(exampleCourse.getCourseCode()).getLecturerId()
                        .equals(sampleLecturer.getLecturerId());
        boolean lecturerIsReadingCourse = lecturers.getLecturer(sampleLecturer.getLecturerId())
                .isReadingCourse(exampleCourse.getCourseCode());
        assertThat(lecturerIsReadingCourse && courseHasCorrectLecturerAssigned);
    }

}