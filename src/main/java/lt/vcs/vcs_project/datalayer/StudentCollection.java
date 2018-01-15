package lt.vcs.vcs_project.datalayer;

import lt.vcs.vcs_project.utils.IOObjectStreamUtils;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Set;

public class StudentCollection {
    final private String fileName = "StudentList.txt";

    private Hashtable<String, Student> studentCollection = new Hashtable<>();

    StudentCollection() {
        readFromFile();
    }

    public void addStudent(Student student) {
        if (!studentCollection.containsKey(student.getStudentId())) {
            studentCollection.put(student.getStudentId(), student);
            writeToFile();
        } else {
            //trow exception - duplicate
            System.out.printf("Student addition failure: Student %s already exists\n", student.getStudentId());
        }
    }


    public void updateStudent(Student student) {
        studentCollection.put(student.getStudentId(), student);
    }

    public void removeStudent(String studentId) {
        studentCollection.remove(studentId);
        writeToFile();
    }

    public boolean containsKey(String accountId) {
        return studentCollection.containsKey(accountId);
    }

    private void readFromFile() {
        try {
            studentCollection = (Hashtable<String, Student>) IOObjectStreamUtils.readFirstObjectFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("%s not found in Account Collection \n", fileName);
        }

    }


    private void writeToFile() {
        IOObjectStreamUtils.writeObjectToFile(fileName, studentCollection);

    }

    public String getLoginId(String studentId) {
        if (studentCollection.containsKey(studentId)) {
            return studentCollection.get(studentId).getLoginId();
        } else {
            return null;
        }
    }

    public Student getStudent(String studentId) {
        return studentCollection.get(studentId);
    }

    public Set<String> getKeyset() {
        return studentCollection.keySet();
    }

    public void addCourse(String studentId, String courseCode) {
        studentCollection.get(studentId).addCourse(courseCode);
    }

    public void removeCourse(String studentId, String courseCode) {
        studentCollection.get(studentId).removeCourse(courseCode);
    }
}
