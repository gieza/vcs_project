package lt.vcs.vcs_project.datalayer;

import lt.vcs.vcs_project.utils.IOObjectStreamUtils;

import java.io.FileNotFoundException;
import java.io.InvalidClassException;
import java.util.Hashtable;
import java.util.Set;

public class CourseCollection {

    final private String fileName = "CourseList.txt";

    private Hashtable<String, Course> courseCollection = new Hashtable<>();

    CourseCollection() {
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
            System.out.printf("Course addition failure: Course %s already exists\n", course.getCourseCode());
        }
    }


    public void updateCourse(Course course) {
        if (courseCollection.containsKey(course.getCourseCode())) {
            courseCollection.put(course.getCourseCode(), course);
            writeToFile();
        } else {
            System.out.printf("Update failure: course %s does not exist\n", course.getCourseCode());
        }
    }

    public void removeCourse(String courseCode) {
        courseCollection.remove(courseCode);
        writeToFile();
    }

    public boolean courseExists(String courseCode) {
        return courseCollection.containsKey(courseCode);
    }

    public Set<String> getKeyset() {
        return courseCollection.keySet();
    }

    public Set<String> getAvailableCourseKeyset() {
        Set<String> availableCourses = courseCollection.keySet();
        for (String key : availableCourses) {
            if (!courseCollection.get(key).available())
                availableCourses.remove(key);
        }
        return availableCourses;
    }

    private void readFromFile() {
        try {
            courseCollection = (Hashtable<String, Course>) IOObjectStreamUtils.readFirstObjectFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("File %s not found. Creating new one.\n", fileName);
        } catch (InvalidClassException e) {
            System.out.printf("File %s is corrupt. \n", fileName);
        }

    }


    private void writeToFile() {
        IOObjectStreamUtils.writeObjectToFile(fileName, courseCollection);

    }


    public Course getCourse(String CourseCode) {
        return courseCollection.get(CourseCode);
    }

    public void enrollStudent(String courseCode, String studentId) {
        courseCollection.get(courseCode).enrollStudent(studentId);
        writeToFile();
    }

    public void deEnrollStudent(String courseCode, String studentId) {
        courseCollection.get(courseCode).deEnrollStudent(studentId);
        writeToFile();
    }
}
