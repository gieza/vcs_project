package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.backend.Backend;
import lt.vcs.vcs_project.utils.BiHashMap;
import lt.vcs.vcs_project.utils.ScannerUtils;

import java.util.Hashtable;

public class AdminUserInterface implements UserInterface {
    static String menuPosition = "TOP";
    static String menuChoice = "";

   /* static final Hashtable<String,String> menuHeader = new Hashtable<>() {{
        put("TOP", "Select One of the following\n");
        put("2", "2"); }};*/
    static final Hashtable<String,String> menuOptions = new Hashtable<>() {{
        put("TOP", "Enter number to select one of the following:\n1 Account Menu\n2 Student Menu\n3 Lecturer menu\n4 Course menu\n9 Logout\nPossible options[1-4,9]:");
        put("ACCOUNT", "Enter number to select one of the following:\n0 Top menu\n1 List Accounts\n2 Add Account\n3 Update Account\n4 Remove account\nPossible options[0-5]:");
        put("STUDENT", "Enter number to select one of the following:\n0 Top menu\n1 List Students\n2 Print Student\n3 Add Student\n5 Update Student\n6 Remove Student\n7 Assign course\nPossible options[0-7]:");
        put("LECTURER", "Enter number to select one of the following:\n0 Top menu\n1 List Lecturers\n2 Print Lecturer\n3 Add Lecturer\\n5 Update Lecturer\\n6 Remove Lecturer\\n7 Assign Lecturer\\nPossible options[0-7]:");
        put("COURSE", "Enter number to select one of the following:\n0 Top menu\n1 List Courses\n2 Print Course\n3 Add Course\\n5 Update Course\\n6 Remove Course\\n7 Assign Course\\nPossible options[0-7]:");
        }};

    static final BiHashMap<String,String,String> menuNavigation = new BiHashMap<String,String,String>(){{
        put("TOP", "1","ACCOUNT");
        put("TOP", "2","STUDENT");
        put("TOP", "3","LECTURER");
        put("TOP", "4","COURSE");
        put("ACCOUNT", "0","TOP");
        put("STUDENT", "0","TOP");
        put("LECTURER", "0","TOP");
        put("COURSE", "0","TOP");
    }};

    //private Admin adminUser;

    @Override
    public void open(String accountId) {
        menuChoice="";
        menuPosition = "TOP";
        //this.adminUser = (Admin) user;
        System.out.println("Administration menuPosition");
        while (true) {
            System.out.printf("\nHello %s %s,\n", Backend.getFirstName(accountId), Backend.getSecondName(accountId));
            printMenuOptions();
            menuChoice = ScannerUtils.scanString();
            if (menuChoice.equals("9") && menuPosition.equals("TOP")) {
                break;
            } else {
                runDecision(menuPosition,menuChoice);
            }
        }
    }

    private void runDecision(String menu,String subMenu){
        String decision = menuNavigation.get(menu,subMenu);
        System.out.printf("Next action: %s\n\n",decision);
        if (decision == null) {
            menuPosition = "TOP";
            return;
        }
        switch (decision) {
            case "ACCOUNT": case "STUDENT": case "LECTURER":  case "COURSE": case "TOP":
                menuPosition=decision;
                break;
            default:
                menuPosition = "TOP";
        }
    }

    private void printMenuOptions() {
        //System.out.printf(menuOptions.get(menuHeader));
        System.out.printf(menuOptions.get(menuPosition));
        //System.out.printf(menuOptions.get(menuPosition));
    };

}