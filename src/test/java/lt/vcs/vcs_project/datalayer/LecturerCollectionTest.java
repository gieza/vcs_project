package lt.vcs.vcs_project.datalayer;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class LecturerCollectionTest {
    private LecturerCollection lecturerCollection;

    @Before
    public void InitializeCourseCollection() {
        //course file is removed
        File lecturerCollectionFile = new File("LecturerList.txt");
        lecturerCollectionFile.delete();
        lecturerCollection = new LecturerCollection();
    }

    @Test
    public void givenNewLecturerList_whenGetCount_thenListSizeIs0() {
        assertEquals(0, lecturerCollection.getCount());
    }


    @Test
    public void givenNewLecturerList_WhenLecturerIsAdded_thenLecturerIsInTheCollection() {
        //given
        Lecturer lecturerSample = new Lecturer("albert", "emc2", "Marie", "Curie", "L0001",
                "84_88", "1867-11-07", "Marie.Curie@sorbonne-universite.fr", "+33 01 44 27 27 68", "F",
                "Sorbonne Université - 21, rue de l'École-de-médecine 75006 Paris");
        //when
        lecturerCollection.addLecturer(lecturerSample);
        //then
        assertEquals(true, lecturerCollection.lecturerExists(lecturerSample.getLecturerId()));
    }

    @Test
    public void givenLecturerListWithALecturer_WhenSameLecturerIsAdded_thenLecturerListHasSingleLecturer() {
        //given
        Lecturer lecturerSample = new Lecturer("albert", "emc2", "Marie", "Curie", "L0001",
                "84_88", "1867-11-07", "Marie.Curie@sorbonne-universite.fr", "+33 01 44 27 27 68", "F",
                "Sorbonne Université - 21, rue de l'École-de-médecine 75006 Paris");
        lecturerCollection.addLecturer(lecturerSample);
        //when
        lecturerCollection.addLecturer(lecturerSample);
        //then
        assertEquals(1, lecturerCollection.getCount());
    }


    @Test
    public void givenLecturerListWithALecturer_whenTheLecturer_IsUpdated_thenCollectionHasUpdatedLecturer_() {
        //given
        Lecturer lecturerSample = new Lecturer("albert", "emc2", "Marie", "Curie", "L0001",
                "84_88", "1867-11-07", "Marie.Curie@sorbonne-universite.fr", "+33 01 44 27 27 68", "F",
                "Sorbonne Université - 21, rue de l'École-de-médecine 75006 Paris");
        Lecturer updatedLecturer = new Lecturer("albert", "emc2", "Albert", "Einstein",
                "L0001", "299792458", "1879-03-14",
                "albert@emcLab.cern", "+41 22 76 784 84", "M", "CERN, CH-1211 Geneva 23, Switzerland");
        lecturerCollection.addLecturer(lecturerSample);

        // when
        lecturerCollection.updateLecturer(updatedLecturer);
        //then
        assertThat(lecturerCollection.getLecturer(lecturerSample.getLecturerId()))
                .isEqualToComparingFieldByFieldRecursively(updatedLecturer);
    }

    @Test
    public void givenLecturerListWithALecturer_whenTheLecturerIsRemoved_thenTheLecturerIsNotInLecturerList() {
        //given
        Lecturer lecturerSample = new Lecturer("albert", "emc2", "Marie", "Curie", "L0001",
                "84_88", "1867-11-07", "Marie.Curie@sorbonne-universite.fr", "+33 01 44 27 27 68", "F",
                "Sorbonne Université - 21, rue de l'École-de-médecine 75006 Paris");

        lecturerCollection.addLecturer(lecturerSample);
        //when
        boolean studentInCollectionBefore = lecturerCollection.lecturerExists(lecturerSample.getLecturerId());
        lecturerCollection.removeLecturer(lecturerSample.getLecturerId());
        boolean studentInCollectionAfter = lecturerCollection.lecturerExists(lecturerSample.getLecturerId());
        //then
        assertNotEquals(studentInCollectionBefore, studentInCollectionAfter);
    }

    @Test
    public void givenLecturerListWithALecturer_whenWrongLecturerIsRemoved_thenLecturerListStillHoldsOriginalLecturer() {
        //given
        Lecturer lecturerSample = new Lecturer("albert", "emc2", "Marie", "Curie", "L0001",
                "84_88", "1867-11-07", "Marie.Curie@sorbonne-universite.fr", "+33 01 44 27 27 68", "F",
                "Sorbonne Université - 21, rue de l'École-de-médecine 75006 Paris");
        Lecturer anotherLecturer = new Lecturer("albert", "emc2", "Albert", "Einstein",
                "L0002", "299792458", "1879-03-14",
                "albert@emcLab.cern", "+41 22 76 784 84", "M", "CERN, CH-1211 Geneva 23, Switzerland");
        lecturerCollection.addLecturer(lecturerSample);
        //when
        boolean studentInCollectionBefore = lecturerCollection.lecturerExists(lecturerSample.getLecturerId());
        lecturerCollection.removeLecturer(anotherLecturer.getLecturerId());
        boolean studentInCollectionAfter = lecturerCollection.lecturerExists(lecturerSample.getLecturerId());
        //then
        assertEquals(studentInCollectionBefore, studentInCollectionAfter);
    }
}