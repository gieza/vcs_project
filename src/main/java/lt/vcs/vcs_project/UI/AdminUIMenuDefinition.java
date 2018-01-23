package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.utils.BiHashMap;

import java.util.Hashtable;

import static lt.vcs.vcs_project.UI.MenuTitle.*;

class AdminUIMenuDefinition {
    static final Hashtable<MenuTitle, String> menuOptions = new Hashtable<>() {{
        put(TOP, "\t1 Account Menu\n\t2 Student Menu\n\t3 Lecturer Menu\n\t4 Course Menu\n" +
                "\t9 Logout\nPossible options[1-4,9]:");
        put(ACCOUNT, "\t0 Top menu\n\t1 List Accounts\n\t2 Print Account\n\t3 Add ADMIN Account\n" +
                "\t4 Update ADMIN Account\n\t5 Change ADMIN Password \n\t6 Remove ADMIN Account\nPossible options[0-6]:");
        put(STUDENT, "\t0 Top menu\n\t1 List Students\n\t2 Print Student\n\t3 Add Student\n" +
                "\t4 Update Student\n\t5 Change password\n\t6 Remove Student\n" +
                "\t7 Assign Student to Course\nPossible options[0-7]:");
        put(LECTURER, "\t0 Top menu\n\t1 List Lecturers\n\t2 Print Lecturer\n\t3 Add Lecturer\n" +
                "\t4 Update Lecturer\n\t5 Change Password\n\t6 Remove Lecturer\n\t" +
                "7 Assign Lecturer to course\nPossible options[0-7]:");
        put(COURSE, "\t0 Top menu\n\t1 List Courses\n\t2 Print Course\n\t3 Add Course\n" +
                "\t4 Update Course\n\t5 Remove Course\n\t6 Assign Course to Student\n" +
                "\t7 Assign Course to Lecturer\nPossible options[0-7]:");
    }};

    static final BiHashMap<MenuTitle, String, MenuTitle> menuNavigation =
            new BiHashMap<>() {{
                put(TOP, "1", ACCOUNT);
                put(TOP, "2", STUDENT);
                put(TOP, "3", LECTURER);
                put(TOP, "4", COURSE);
                put(TOP, "loadsomedata", loadsomedata);
                put(TOP, "9", LOGOUT);
                put(ACCOUNT, "0", TOP);
                put(ACCOUNT, "1", LIST_ACCOUNTS);
                put(ACCOUNT, "2", PRINT_ACCOUNT);
                put(ACCOUNT, "3", ADD_ACCOUNT);
                put(ACCOUNT, "4", UPDATE_ACCOUNT);
                put(ACCOUNT, "5", CHANGE_ACCOUNT_PASSWORD);
                put(ACCOUNT, "6", REMOVE_ACCOUNT);
                put(STUDENT, "0", TOP);
                put(STUDENT, "1", LIST_STUDENTS);
                put(STUDENT, "2", PRINT_STUDENT);
                put(STUDENT, "3", ADD_STUDENT);
                put(STUDENT, "4", UPDATE_STUDENT);
                put(STUDENT, "5", CHANGE_STUDENT_PASSWORD);
                put(STUDENT, "6", REMOVE_STUDENT);
                put(STUDENT, "7", ASSIGN_COURSE_STUDENT);
                put(LECTURER, "0", TOP);
                put(LECTURER, "1", LIST_LECTURERS);
                put(LECTURER, "2", PRINT_LECTURER);
                put(LECTURER, "3", ADD_LECTURER);
                put(LECTURER, "4", UPDATE_LECTURER);
                put(LECTURER, "5", CHANGE_LECTURER_PASSWORD);
                put(LECTURER, "6", REMOVE_LECTURER);
                put(LECTURER, "7", ASSIGN_COURSE_LECTURER);
                put(COURSE, "0", TOP);
                put(COURSE, "1", LIST_COURSES);
                put(COURSE, "2", PRINT_COURSE);
                put(COURSE, "3", ADD_COURSE);
                put(COURSE, "4", UPDATE_COURSE);
                put(COURSE, "5", REMOVE_COURSE);
                put(COURSE, "6", ASSIGN_COURSE_STUDENT);
                put(COURSE, "7", ASSIGN_COURSE_LECTURER);
            }};
}
