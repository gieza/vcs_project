package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.utils.BiHashMap;

import java.util.Hashtable;

public class LecturerUIMenuHash {
    public static final Hashtable<String, String> menuOptions = new Hashtable<>() {{
        put("TOP", "\t1 Lecturer menu\n\t2 Course menu\n\t9 Logout\nPossible options[1,2,9]:");
        put("LECTURER", "\t0 Top menu\n\t1 Print Lecturer data\n2 Update Lecturer\n\t3 List Assigned Courses\nPossible options[0-3]:");
        put("COURSE", "\t0 Top menu\n\t1 List Courses\n\t2 List Assigned Courses\n\t3 Print Course\n\t\nPossible options[0-3]:");
    }};

    public static final BiHashMap<String, String, String> menuNavigation = new BiHashMap<String, String, String>() {{
        put("TOP", "1", "LECTURER");
        put("TOP", "2", "COURSE");
        put("LECTURER", "0", "TOP");
        put("LECTURER", "1", "PRINT_LECTURER");
        put("LECTURER", "2", "UPDATE_LECTURER");
        put("COURSE", "0", "TOP");
        put("COURSE", "1", "LIST_COURSES");
        put("COURSE", "2", "LIST_ASSIGNED_COURSES");
        put("COURSE", "3", "PRINT_COURSE");

    }};
}
