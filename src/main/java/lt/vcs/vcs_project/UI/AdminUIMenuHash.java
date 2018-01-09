package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.utils.BiHashMap;

import java.util.Hashtable;

public class AdminUIMenuHash {
    public static final Hashtable<String,String> menuOptions = new Hashtable<>() {{
        put("TOP", "\t1 Account Menu\n\t2 Student Menu\n\t3 Lecturer menu\n\t4 Course menu\n\t9 Logout\nPossible options[1-4,9]:");
        put("ACCOUNT", "\t0 Top menu\n\t1 List Accounts\n\t2 Print Account\n\t3 Add Account\n\t4 Update Account\n\t5 Remove account\nPossible options[0-5]:");
        put("STUDENT", "\t0 Top menu\n\t1 List Students\n\t2 Print Student\n\t3 Add Student\n\t5 Update Student\n\t6 Remove Student\n\t7 Assign course\nPossible options[0-7]:");
        put("LECTURER", "\t0 Top menu\n\t1 List Lecturers\n\t2 Print Lecturer\n3 Add Lecturer\n\t5 Update Lecturer\n\t6 Remove Lecturer\n\t7 Assign Lecturer\nPossible options[0-7]:");
        put("COURSE", "\t0 Top menu\n\t1 List Courses\n\t2 Print Course\n\t3 Add Course\n\t4 Update Course\n\t5 Remove Course\n\t6 Assign Course\nPossible options[0-6]:");
    }};

    public static final BiHashMap<String,String,String> menuNavigation = new BiHashMap<String,String,String>(){{
        put("TOP", "1","ACCOUNT");
        put("TOP", "2","STUDENT");
        put("TOP", "3","LECTURER");
        put("TOP", "4","COURSE");
        put("ACCOUNT", "0","TOP");
        put("ACCOUNT", "1","LIST_ACCOUNTS");
        put("ACCOUNT", "2","PRINT_ACCOUNT");
        put("ACCOUNT", "3","ADD_ACCOUNT");
        put("ACCOUNT", "4","UPDATE_ACCOUNT");
        put("ACCOUNT", "5","REMOVE_ACCOUNT");
        put("STUDENT", "0","TOP");
        put("LECTURER", "0","TOP");
        put("COURSE", "0","TOP");
    }};
}
