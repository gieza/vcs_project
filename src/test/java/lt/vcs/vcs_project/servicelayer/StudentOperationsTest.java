package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Course;
import lt.vcs.vcs_project.datalayer.Student;
import org.junit.Test;

import static java.time.LocalDate.now;
import static lt.vcs.vcs_project.datalayer.DataLayer.*;
import static lt.vcs.vcs_project.servicelayer.StudentOperations.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class StudentOperationsTest {
    private final Student studentExample = new Student("juonis", "juonis", "Jonas", "Petraitis",
            "s0001", "3450101000", "19450101",
            "juons@petraitis.lt", "863303003", "M", "Jurgio g.1-13, Juonava");

    @Test
    public void givenValidStudentCSV_whenMakeStudentFromCSV_thenReferenceStudentIsReturned() {
        //given
        final String studentCSV_correct = "juonis,juonis,Jonas,Petraitis,s0001,3450101000,19450101," +
                "juons@petraitis.lt,863303003,M,Jurgio g.1-13, Juonava";
        Student referenceStudent = studentExample;
        //when
        Student actualStudent = makeStudentFromCSV(studentCSV_correct);
        // +then
        assertThat(actualStudent).isEqualToComparingFieldByFieldRecursively(referenceStudent);
    }

    @Test
    public void GivenStudentCSVwithMandatoryFields_whenMakeStudentFromCSV_thenReferenceStudentIsReturned() {
        //given
        final String studentCSV_mandatory = "juonis,juonis,Jonas,Petraitis,s0001";
        final Student referenceStudentMandatory = new Student("juonis", "juonis", "Jonas", "Petraitis",
                "s0001", "", "",
                "", "", "", "");
        //when
        Student actualStudent = makeStudentFromCSV(studentCSV_mandatory);
        //then
        assertThat(actualStudent).isEqualToComparingFieldByFieldRecursively(referenceStudentMandatory);
    }

    @Test
    public void GivenStudentCSVwithTooFewFields_whenMakeStudentFromCSV_thenNullIsReturned() {
        //given
        final String student_CSV_tooShort = "student";
        //when
        Student actualStudent = makeStudentFromCSV(student_CSV_tooShort);
        //then
        assertEquals(null, actualStudent);
    }


    @Test
    public void GivenSampleStudentAndValidUpdateCSV_whenUpdateStudentFromCSV_thenUpdatedStudentIsReturned() {
        //given
        final String validCSVforStudentUpadate = "Aloyzas,Kazlauskas,999999,20011111,alius@kapenai.com," +
                "112,F,Pievu g.6, Kapenai, Akmenes r.";
        final Student referenceUpdatedStudent = new Student("juonis", "juonis", "Aloyzas",
                "Kazlauskas", "s0001", "999999", "20011111",
                "alius@kapenai.com", "112", "F", "Pievu g.6, Kapenai, Akmenes r.");
        Student student_for_update = studentExample;
        //when
        Student actualStudent = updateStudentFromCSV(student_for_update, validCSVforStudentUpadate);
        //then
        assertThat(actualStudent).isEqualToComparingFieldByFieldRecursively(referenceUpdatedStudent);
    }

    @Test
    public void GivenSampleStudentAndTooShortUpdateCSV_whenUpdateStudentFromCSV_thenReturnedStudentIsOriginal() {
        //given
        final String validCSVforStudentUpadate = "Aloyzas,Kazlauskas,999999,20011111,alius@kapenai.com," +
                "112,";

        Student student_for_update = studentExample;
        //when
        Student actualStudent = updateStudentFromCSV(student_for_update, validCSVforStudentUpadate);
        //then
        assertThat(actualStudent).isEqualToComparingFieldByFieldRecursively(studentExample);

    }


    @Test
    public void GivenStudentInCollectionAndOutOfDateCourse_whenAssigningAvailableCourse_thenCourseIsNotAssigned() {
        //given
        Student sampleStudent = StudentOperations.makeStudentFromCSV("JB,youwon'tguess,James,BOND,s007,007,19450101," +
                "james.bond@mi5.gov.uk,undisclosed,M,somewhere on the globe");
        Course exampleCourseOutOfDate = new Course("c0001", "Praktine Astrologija", now().minusDays(10), "2",
                "l0001", "");
        if (students.studentExists(sampleStudent.getStudentId()))
            students.removeStudent(sampleStudent.getStudentId());
        students.addStudent(sampleStudent);
        if (courses.courseExists(exampleCourseOutOfDate.getCourseCode()))
            courses.removeCourse(exampleCourseOutOfDate.getCourseCode());
        courses.addCourse(exampleCourseOutOfDate);

        assignAvailableCourse2Student(exampleCourseOutOfDate.getCourseCode(), sampleStudent.getStudentId());
        boolean courseHasStudentEnrolled = courses.getCourse(exampleCourseOutOfDate.getCourseCode())
                .isEnrolled(sampleStudent.getStudentId());
        boolean studentHasEnrolledInCourse = students.getStudent(sampleStudent.getStudentId())
                .hasCourse(sampleStudent.getStudentId());
        assertThat(!studentHasEnrolledInCourse && !courseHasStudentEnrolled);
    }

    @Test
    public void GivenStudentInCollectionAndAvailableCourse_whenAssigningAvailableCourse_thenCourseIsAddedToLists() {
        //given
        Student sampleStudent = StudentOperations.makeStudentFromCSV("JB,youwon'tguess,James,BOND,s007,007,19450101," +
                "james.bond@mi5.gov.uk,undisclosed,M,somewhere on the globe");
        Course exampleCourseAvailable = new Course("c0001", "Praktine Astrologija", now().plusDays(10), "2",
                "l0001", "");
        if (students.studentExists(sampleStudent.getStudentId()))
            students.removeStudent(sampleStudent.getStudentId());
        students.addStudent(sampleStudent);
        if (courses.courseExists(exampleCourseAvailable.getCourseCode()))
            courses.removeCourse(exampleCourseAvailable.getCourseCode());
        courses.addCourse(exampleCourseAvailable);
        //when
        assignAvailableCourse2Student(exampleCourseAvailable.getCourseCode(), sampleStudent.getStudentId());
        //then
        boolean courseHasStudentEnrolled = courses.getCourse(exampleCourseAvailable.getCourseCode())
                .isEnrolled(sampleStudent.getStudentId());
        boolean studentHasEnrolledInCourse = students.getStudent(sampleStudent.getStudentId())
                .hasCourse(sampleStudent.getStudentId());
        assertThat(studentHasEnrolledInCourse && courseHasStudentEnrolled);
    }

}