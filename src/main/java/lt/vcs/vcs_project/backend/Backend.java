package lt.vcs.vcs_project.backend;

import lt.vcs.vcs_project.utils.ScannerUtils;

public class Backend {
    public static AccountServices accounts = new AccountServices();
    public void initialize(){
       // accounts = new AccountServices();
        new StudentServices();
    }

    public static String login() {
        System.out.printf("\n\nWelcome! Please login.\n");
        while (true) {
            System.out.print("Username: ");
            String username = ScannerUtils.scanString();
            System.out.print("Password: ");
            String password = ScannerUtils.scanString();

            if (accounts.authenticate(username,password) ) {
                return username;
            }
            System.out.println("Login failed, please try again");
        }
    }

    public static String loginDefault() {
        System.out.printf("\n\nWelcome! Please login.\n");
       /* while (true) {
            System.out.print("Username: ");
            String username = ScannerUtils.scanString();
            System.out.print("Password: ");
            String password = ScannerUtils.scanString();

            if (accounts.authenticate(username,password) ) {
                return username;
            }
            System.out.println("Login failed, please try again");
        }*/
        return "admin";
    }
    public static Role getRole(String loginId) {
        return accounts.getAccount(loginId).getRole();
    }

    public static String getFirstName(String loginId) {
        return accounts.getAccount(loginId).getFirstName();
    }

    public static String getSecondName(String loginId) {
        return accounts.getAccount(loginId).getSecondName();
    }
}
