package lt.vcs.vcs_project.datalayer;

import lt.vcs.vcs_project.utils.IOObjectStreamUtils;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Set;

public class CourseCollection {

    final private String fileName = "CourseList.txt";

    private Hashtable<String, Course> courseCollection = new Hashtable<>();

    public CourseCollection() {
        readFromFile();
    }

    public long getCount() {
        return courseCollection.size();
    }

    public void addCourse(Course course) {
        if (!courseCollection.containsKey(course.getCourseCode())) {
            courseCollection.put(course.getCourseCode(), course);
            writeToFile();
        } else {
            //todo: trow exception - duplicate
            System.out.printf("Student addition failure: Lecturer %s already exists\n", course.getCourseCode());
        }
    }

    public void addCourse(String csv) {
        Course courseCSV = new Course(csv);
        if (!courseCollection.containsKey(courseCSV.getCourseCode())) {
            courseCollection.put(courseCSV.getCourseCode(), courseCSV);
            writeToFile();
        } else {
            //trow exception - duplicate
            System.out.printf("Student addition failure: lecturer %s already exists\n", courseCSV.getCourseCode());
        }
    }

    public void updateCourse(Course course) {

        if (courseCollection.containsKey(course.getCourseCode())) {
            courseCollection.put(course.getCourseCode(), course);
            writeToFile();
        } else {
            System.out.printf("Update failure: lecturer %s does not exist\n", course.getCourseCode());
        }
    }

    public void updateCourse(String csv) {
        Course courseCSV = new Course(csv);
        if (courseCollection.containsKey(courseCSV.getCourseCode())) {
            courseCollection.put(courseCSV.getCourseCode(), courseCSV);
            writeToFile();
        } else {
            System.out.printf("Student update failure: lecturer %s does not exist\n", courseCSV.getCourseCode());
        }
    }


    public void removeCourse(String courseCode) {
        //todo: jeigu studentas arba destytojas, reikia pataisyti ir ju kolekcijas
        courseCollection.remove(courseCode);
        writeToFile();
    }

    public boolean containsKey(String courseCode) {
        return courseCollection.containsKey(courseCode);
    }

    private void readFromFile() {
        try {
            courseCollection = (Hashtable<String, Course>) IOObjectStreamUtils.readFirstObjectFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("%s not found in Account Collection \n", fileName);
        }

    }


    private void writeToFile() {
        IOObjectStreamUtils.writeObjectToFile(fileName, courseCollection);

    }

    //  code
//  tittle
//  desciption
//  startDate
//  credit
//  lecturerCode
    public String listCourses() {
        //todo:update listing
        StringBuilder returnString = new StringBuilder("Course Code\tTitle\tStart Date\tCredit\tlecturer\t# of Students\n" +
                "\n=====================================================\n");
        Set<String> keys = courseCollection.keySet();
        for (String key : keys) {
            Course listingCourse = courseCollection.get(key);
            returnString.append(key + "\t" + listingCourse.getCourseCode() +
                    "\t" + listingCourse.getTitle() + "\t" + listingCourse.getTitle() +
                    "\t" + listingCourse.getStartDate().toString() + "\t" + listingCourse.getCredit() +
                    "\t" + DataLayer.lecturers.getLecturerName(listingCourse.getLecturerCode()) + "\t" +
                    "\t" + listingCourse.getEnrolledStudentCount() + "\n");
        }
        return returnString.toString();
    }

    public String listAvailableCourses() {
        //todo:update listing


        return "";
    }

    public Course getCourse(String CourseCode) {
        return courseCollection.get(CourseCode);
    }

    public void enrollStudent(String courseCode, String studentId) {
        courseCollection.get(courseCode).enrollStudent(studentId);


    }

    public void derollStudent() {

    }
}
