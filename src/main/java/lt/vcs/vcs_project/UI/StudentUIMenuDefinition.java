package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.utils.BiHashMap;

import java.util.Hashtable;

public class StudentUIMenuDefinition {
    public static final Hashtable<String, String> menuOptions = new Hashtable<>() {{
        put("TOP", "\t1 Student Menu\n\t2 Course menu\n\t9 Logout\nPossible options[1-2,9]:");
        put("STUDENT", "\t0 Top menu\n\t1 Print Student data\n\t2 Update Student\n" +
                "\t3 Change password\n\t4 Assign course\nPossible options[0-4]:");
        put("COURSE", "\t0 Top menu\n\t1 List Courses\n\t2 List Available Courses\n\t3 List Enrolled Courses\n\t4 Print Course\n\t5 Enroll to Course\n\tPossible options[0-5]:");
    }};

    public static final BiHashMap<String, String, String> menuNavigation = new BiHashMap<String, String, String>() {{
        put("TOP", "1", "STUDENT");
        put("TOP", "2", "COURSE");
        put("STUDENT", "0", "TOP");
        put("STUDENT", "1", "PRINT_STUDENT");
        put("STUDENT", "2", "UPDATE_STUDENT");
        put("STUDENT", "3", "CHANGE_STUDENT_PASSWORD");
        put("STUDENT", "4", "ASSIGN_COURSE");
        put("COURSE", "0", "TOP");
        put("COURSE", "1", "LIST_COURSES");
        put("COURSE", "2", "LIST_AVAILABLE_COURSES");
        put("COURSE", "3", "LIST_ENROLLED_COURSES");
        put("COURSE", "4", "PRINT_COURSE");
        put("COURSE", "5", "ASSIGN_COURSE");
    }};

}
