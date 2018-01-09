package lt.vcs.vcs_project.backend;

import lt.vcs.vcs_project.utils.IOObjectStreamUtils;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Set;

public class LecturerServices {

    final private String fileName = "LecturerList.txt";

    private Hashtable<String, Lecturer> lecturerCollection = new Hashtable<>();

    public long getCount() {
        return lecturerCollection.size();
    }

    public void addLecturer(Lecturer lecturer) {
        if (!lecturerCollection.containsKey(lecturer.getLecturerId())) {
            lecturerCollection.put(lecturer.getLecturerId(), lecturer);
            writeToFile();
            //if (accounts.containsKey(student.getLoginId()));
            //todo: if (AccountServices.containsKey(student.getLoginId())) {

            // };
        } else {
            //trow exception - duplicate
            System.out.printf("Student addition failure: Lecturer %s already exists\n", lecturer.getLecturerId());
        }
    }

    public void addLecturer(String csv) {
        Lecturer lecturerCSV = new Lecturer(csv);
        if (!lecturerCollection.containsKey(lecturerCSV.getLecturerId())) {
            lecturerCollection.put(lecturerCSV.getLecturerId(), lecturerCSV);
            writeToFile();
        } else {
            //trow exception - duplicate
            System.out.printf("Student addition failure: lecturer %s already exists\n", lecturerCSV.getLecturerId());
        }
    }

    public void updateLecturer(Lecturer lecturer) {
        //todo: jeigu studentas arba destytojas, reikia pataisyti ir ju kolekcijas
        if (lecturerCollection.containsKey(lecturer.getLecturerId())) {
            lecturerCollection.put(lecturer.getLecturerId(), lecturer);
            writeToFile();
        } else {
            System.out.printf("Update failure: lecturer %s does not exist\n", lecturer.getLecturerId());
        }
    }

    public void updateLecturer(String csv) {
        //todo: jeigu studentas arba destytojas, reikia pataisyti ir ju kolekcijas ???
        Lecturer lecturerCSV = new Lecturer(csv);
        //todo: reikalingas account update -> kad nebutu galima pakeisti account'o ir roles
        if (lecturerCollection.containsKey(lecturerCSV.getLecturerId())) {
            lecturerCollection.put(lecturerCSV.getLoginId(), lecturerCSV);
            writeToFile();
        } else {
            System.out.printf("Student update failure: lecturer %s does not exist\n", lecturerCSV.getLecturerId());
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


    public String listLecturers() {
        //todo:update listing
        StringBuilder returnString = new StringBuilder("AccountId\tFirst name\tLast Name\tRole\tlecturerId\tPersonalNumber\tDoB\tEmail\tMobile#\tF/M\tAddress" +
                "\n=====================================================\n");
        Set<String> keys = lecturerCollection.keySet();
        for (String key : keys) {
            Lecturer listingLecturer = lecturerCollection.get(key);
            returnString.append(key + "\t" + listingLecturer.getFirstName() +
                    "\t" + listingLecturer.getSecondName() + "\t" + listingLecturer.getRole().toString() +
                    "\t" + listingLecturer.getLecturerId() + "\t" + listingLecturer.getPersonalNumber() +
                    "\t" + listingLecturer.getDateOfBirth() + "\t" + listingLecturer.getEmail() +
                    "\t" + listingLecturer.getMobileNumber() + "\t" + listingLecturer.getGender() +
                    "\t" + listingLecturer.getAddress() + "\n");
        }
        return returnString.toString();
    }


    public String toStringCSV(String lecturerId) {
        return lecturerCollection.get(lecturerId).toStringCSV();
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

}
