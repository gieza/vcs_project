package lt.vcs.vcs_project.backend;

import lt.vcs.vcs_project.utils.IOObjectStreamUtils;
//import lt.vcs.vcs_project.backend;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Set;

public class StudentServices {
    final private String fileName = "StudentList.txt";

    private Hashtable<String,Student> studentCollection = new Hashtable<>();

    StudentServices() {
        readFromFile();
    }

    public long getCount(){
        return studentCollection.size();
    }

    public void addStudent(Student student){
        //todo: reikia prideti account; jeigu jo nera
        if (!studentCollection.containsKey(student.getStudentId())) {
            studentCollection.put(student.getStudentId(), student);
            writeToFile();
            //if (accounts.containsKey(student.getLoginId()));
            //todo: if (AccountServices.containsKey(student.getLoginId())) {

           // };
        } else {
            //trow exception - duplicate
            System.out.printf("Student addition failure: Student %s already exists\n", student.getStudentId());
        }
    }

    public void addStudent(String csv){
        //todo: reikia prideti account; jeigu jo nera
        Student studentCSV = new Student(csv);
        if (!studentCollection.containsKey(studentCSV.getStudentId())) {
            studentCollection.put(studentCSV.getStudentId(), studentCSV);
            writeToFile();
        } else {
            //trow exception - duplicate
            System.out.printf("Student addition failure: Student %s already exists\n", studentCSV.getStudentId());
        }
    }

    public void updateStudent(Student student){
        //todo: jeigu studentas arba destytojas, reikia pataisyti ir ju kolekcijas
        if (studentCollection.containsKey(student.getStudentId())) {
            studentCollection.put(student.getStudentId(), student);
            writeToFile();
        } else {
            System.out.printf("Update failure: Student %s does not exist\n", student.getStudentId());
        }
    }

    public void updateStudent(String csv){
        //todo: jeigu studentas arba destytojas, reikia pataisyti ir ju kolekcijas ???
        Student studentCSV = new Student(csv);
        //todo: reikalingas account update -> kad nebutu galima pakeisti account'o ir roles
        if (studentCollection.containsKey(studentCSV.getStudentId())) {
            studentCollection.put(studentCSV.getLoginId(), studentCSV);
            writeToFile();
        } else {
            System.out.printf("Student update failure: student %s does not exist\n", studentCSV.getStudentId());
        }
    }

    public void removeStudent(String studentId){
        //todo: jeigu studentas arba destytojas, reikia pataisyti ir ju kolekcijas
        studentCollection.remove(studentId);
        writeToFile();
    }

    public Account getAccount(String studentId){
        if (studentCollection.containsKey(studentId))  return studentCollection.get(studentId);
        System.out.printf("Get student failure: student %s does not exist\n", studentId);
        return null;
    }

    public boolean containsKey(String accountId){
        return studentCollection.containsKey(accountId);
    }

    private void readFromFile(){
        try {
            studentCollection = (Hashtable<String, Student>) IOObjectStreamUtils.readFirstObjectFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("%s not found in Account Collection \n", fileName);
        }

    }


    private void writeToFile(){
        IOObjectStreamUtils.writeObjectToFile(fileName, studentCollection);

    }


    public String listAccounts() {
        //todo:update listing
        StringBuilder returnString = new StringBuilder("AccountId\tFirst name\tLast Name\tRole\tstudentId\n=====================================================\n");
        Set<String> keys = studentCollection.keySet();
        for (String key:keys) {
            Account listingAccount = studentCollection.get(key);
            returnString.append(key +"\t" + listingAccount.getFirstName() +
                    "\t" + listingAccount.getSecondName() +
                    "\t" + listingAccount.getRole().toString() +
                    "\t" + listingAccount.getPersonalId() +"\n");
        }
        return returnString.toString();
    }

    public String toStringCSV(String studentId){
        return studentCollection.get(studentId).toStringCSV();
    }

    public String getLoginId(String studentId){
        if (studentCollection.containsKey(studentId)) {
            return studentCollection.get(studentId).getLoginId();
        } else {
            return null;
        }
    }



}
