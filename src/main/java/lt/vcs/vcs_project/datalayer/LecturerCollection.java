package lt.vcs.vcs_project.datalayer;

import lt.vcs.vcs_project.utils.IOObjectStreamUtils;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Set;

public class LecturerCollection {

    final private String fileName = "LecturerList.txt";

    private Hashtable<String, Lecturer> lecturerCollection = new Hashtable<>();

    public LecturerCollection() {
        readFromFile();
    }

    public long getCount() {
        return lecturerCollection.size();
    }

    public void addLecturer(Lecturer lecturer) {
        if (!lecturerCollection.containsKey(lecturer.getLecturerId())) {
            lecturerCollection.put(lecturer.getLecturerId(), lecturer);
            writeToFile();
        } else {
            //trow exception - duplicate
            System.out.printf("Student addition failure: Lecturer %s already exists\n", lecturer.getLecturerId());
        }
    }

    public void updateLecturer(Lecturer lecturer) {
        if (lecturerCollection.containsKey(lecturer.getLecturerId())) {
            lecturerCollection.put(lecturer.getLecturerId(), lecturer);
            writeToFile();
        } else {
            System.out.printf("Update failure: lecturer %s does not exist\n", lecturer.getLecturerId());
        }
    }

    public void removeLecturer(String lecturerId) {
        //todo: jeigu studentas arba destytojas, reikia pataisyti ir ju kolekcijas
        lecturerCollection.remove(lecturerId);
        writeToFile();
    }

    public Account getAccount(String lecturerId) {
        if (lecturerCollection.containsKey(lecturerId)) return lecturerCollection.get(lecturerId);
        System.out.printf("Get student failure: student %s does not exist\n", lecturerId);
        return null;
    }

    public boolean containsKey(String lecturerId) {
        return lecturerCollection.containsKey(lecturerId);
    }

    public String getLecturerName(String lecturerId) {
        return lecturerCollection.get(lecturerId).getFirstName() + " "
                + lecturerCollection.get(lecturerId).getSecondName();
    }

    private void readFromFile() {
        try {
            lecturerCollection = (Hashtable<String, Lecturer>) IOObjectStreamUtils.readFirstObjectFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("%s not found in Account Collection \n", fileName);
        }

    }


    private void writeToFile() {
        IOObjectStreamUtils.writeObjectToFile(fileName, lecturerCollection);

    }

    public String getLoginId(String lecturerId) {
        if (lecturerCollection.containsKey(lecturerId)) {
            return lecturerCollection.get(lecturerId).getLoginId();
        } else {
            return null;
        }
    }

    public Lecturer getLecturer(String lecturerId) {
        return lecturerCollection.get(lecturerId);
    }

    public Set<String> getKeyset() {
        return lecturerCollection.keySet();
    }

    public void addCourse(String lecturerId, String courseCode) {
        lecturerCollection.get(lecturerId).addCourse(courseCode);
    }

    public void removeCourse(String lecturerId, String courseCode) {
        lecturerCollection.get(lecturerId).removeCourse(courseCode);
    }

}
