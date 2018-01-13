package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.UI.UI_common;
import lt.vcs.vcs_project.datalayer.Account;
import java.util.Arrays;
import static lt.vcs.vcs_project.datalayer.Role.*;


public class AccountOperations {

    public static final String NEW_ACCOUNT_DATA_INPUT_TEMPLATE = "Login,Password,First name,Second name";
    public static final String UPDATE_ACCOUNT_DATA_INPUT_TEMPLATE = "First name,Second name";
    public static final String ACCOUNT_LISTING_HEADER = String.format("%-15s %20s %-25s %-8s %-8s", "Login", "First name", "Second name", "Role", "ID");
    public static final String ACCOUNT_UPDATE_HEADER = String.format("%-20s %-25s\n", "First name", "Second name");

    public static Account makeAccountFromCSV(String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length < 2 || inputArray.length > 5) {
            System.out.printf("Account creation Failure: input data has incorrect number of fields\n");
            return null;
        } else {
            return new Account(inputArray[0], inputArray[1],
                    inputArray.length > 2 ? inputArray[2] : "",
                    inputArray.length > 3 ? inputArray[3] : "",
                    ADMIN);
        }
    }

    public static Account updateAccountFromCSV(Account account, String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length != 2) {
            System.out.printf("Account update Failure: input data has incorrect number of fields\n");
            return account;
        } else {
            account.setFirstName(inputArray[0]);
            account.setSecondName(inputArray[1]);
            return account;
        }
    }

    public String printAccountInCSV(Account account) {

        return String.join(",", Arrays.asList(account.getLoginId(), account.getFirstName(),
                account.getSecondName(), account.getRoleAsString(), account.getPersonalId()));

    }

    public static void listOutAccount(Account account) {
        System.out.printf("%-15s %20s %-25s %-8s %-8s\n", account.getLoginId(), account.getFirstName(),
                account.getSecondName(), account.getRoleAsString(), account.getPersonalId());
    }

    public String getCurrentValuesForUpdate(Account account) {
        return String.format("%-20s %-25s", account.getFirstName(), account.getSecondName());
    }

    public static void printOutAccount(Account account) {
        System.out.println("Account Data:\n=============\nLogin Name:"
                + account.getLoginId() + "\nPassword:" + account.getPassword()
                + "\nName:" + account.getFirstName() + " " + account.getSecondName()
                + "\nRole:" + account.getRoleAsString()
                + "\nPersonalId=" + account.getPersonalId());
    }


    static public void printOutAccountForUpdate(Account account) {
        System.out.print(ACCOUNT_UPDATE_HEADER);
        UI_common.printUnderLineForString(ACCOUNT_UPDATE_HEADER);
        System.out.printf("%-20s %-25s\n", account.getFirstName(), account.getSecondName());
    }

    static public String getListingHeader() {
        return ACCOUNT_LISTING_HEADER;
    }

    static public String getUpdateHeader() {
        return ACCOUNT_UPDATE_HEADER;
    }


}

