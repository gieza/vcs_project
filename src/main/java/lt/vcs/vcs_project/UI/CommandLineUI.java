package lt.vcs.vcs_project.UI;

import lt.vcs.vcs_project.servicelayer.AccountOperations;
import lt.vcs.vcs_project.servicelayer.AccountPrints;
import lt.vcs.vcs_project.utils.ScannerUtils;

import static lt.vcs.vcs_project.datalayer.DataLayer.accounts;

public class CommandLineUI {
    private static String currentUser = "";

    public static void run() {
        UserInterface userInterface = null;
        while (true) {
            currentUser = login();
            switch (accounts.getRole(currentUser)) {
                case ADMIN:
                    userInterface = new AdminUserInterface();
                    break;
                case STUDENT:
                    userInterface = new StudentUserInterface();
                    break;
                case LECTURER:
                    userInterface = new LecturerUserInterface();
                    break;
            }
            userInterface.navigateMenu(currentUser);
        }
    }

    private static String login() {
        System.out.printf("\n\nWelcome! Please login.\n");
        while (true) {
            System.out.print("Username: ");
            String username = ScannerUtils.scanString();
            System.out.print("Password: ");
            String password = ScannerUtils.scanString();

            if (AccountOperations.authenticate(username, password)) {
                AccountPrints.printGreetings(username);
                return username;
            }
            System.out.println("Login failed, please try again");
        }
    }

    private static String loginDefault() {
        System.out.printf("\n\nWelcome! Admin.\n");
        return "admin";
    }


}
