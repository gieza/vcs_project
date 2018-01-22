package lt.vcs.vcs_project.datalayer;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class StudentCollectionTest {

    private StudentCollection studentCollection;

    @Before
    public void InitializeCourseCollection() {
        //student file is removed
        File studentCollectionFile = new File("StudentList.txt");
        studentCollectionFile.delete();
        studentCollection = new StudentCollection();
    }

    @Test
    public void givenNewStudentList_whenGetCount_thenListSizeIs0() {
        assertEquals(0, studentCollection.getCount());
    }

    @Test
    public void givenNewStudentList_WhenStudentIsAdded_thenStudentIsInTheCollection() {
        //given
        Student studentSample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        //when
        studentCollection.addStudent(studentSample);
        //then
        assertEquals(true, studentCollection.studentExists(studentSample.getStudentId()));
    }

    @Test
    public void givenStudentListWithAStudent_WhenSameStudentIsAdded_thenStudentListHasSingleStudent() {
        //given
        Student studentSample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        studentCollection.addStudent(studentSample);
        //when
        studentCollection.addStudent(studentSample);
        //then
        assertEquals(1, studentCollection.getCount());
    }


    @Test
    public void givenStudentListWithAStudent_whenTheStudentIsUpdated_thenCollectionHasUpdatedStudent() {
        //given
        Student studentExample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        final Student updatedStudent = new Student("juonis", "juonis", "Aloyzas",
                "Kazlauskas", "s0001", "999999", "20011111",
                "alius@kapenai.com", "112", "F", "Pievu g.6, Kapenai, Akmenes r.");
        studentCollection.addStudent(studentExample);

        // when
        studentCollection.updateStudent(updatedStudent);
        //then
        assertThat(studentCollection.getStudent(studentExample.getStudentId()))
                .isEqualToComparingFieldByFieldRecursively(updatedStudent);
    }

    @Test
    public void givenStudentListWithAStudent_whenTheStudentIsRemoved_thenTheStudentIsNotInStudentList() {
        //given
        Student studentExample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        studentCollection.addStudent(studentExample);
        //when
        boolean studentInCollectionBefore = studentCollection.studentExists(studentExample.getStudentId());
        studentCollection.removeStudent(studentExample.getStudentId());
        boolean studentInCollectionAfter = studentCollection.studentExists(studentExample.getStudentId());
        //then
        assertNotEquals(studentInCollectionBefore, studentInCollectionAfter);
    }

    @Test
    public void givenStudentListWithAStudent_whenWrongStudentIsRemoved_thenStudentListStillHoldsOriginalStudent() {
        //given
        Student studentExample = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "3450101000", "19450101",
                "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");
        final Student anotherStudent = new Student("juonis", "juonis", "Aloyzas",
                "Kazlauskas", "s0002", "999999", "20011111",
                "alius@kapenai.com", "112", "F", "Pievu g.6, Kapenai, Akmenes r.");
        studentCollection.addStudent(studentExample);
        //when
        boolean studentInCollectionBefore = studentCollection.studentExists(studentExample.getStudentId());
        studentCollection.removeStudent(anotherStudent.getStudentId());
        boolean studentInCollectionAfter = studentCollection.studentExists(studentExample.getStudentId());
        //then
        assertEquals(studentInCollectionBefore, studentInCollectionAfter);
    }

}