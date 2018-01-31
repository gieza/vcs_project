package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Account;

import java.util.Set;

import static lt.vcs.vcs_project.datalayer.DataLayer.accounts;

public class AccountPrints extends PrintService {

    public static final String NEW_ACCOUNT_DATA_INPUT_TEMPLATE = "Login,Password,First name,Second name";
    public static final String UPDATE_ACCOUNT_DATA_INPUT_TEMPLATE = "First name,Second name";
    private static final String ACCOUNT_LISTING_HEADER =
            String.format("%-15s %20s %-25s %-8s %-8s", "Login", "First name", "Second name", "Role", "ID");
    private static final String ACCOUNT_UPDATE_HEADER =
            String.format("%-20s %-25s\n", "First name", "Second name");

    private static void listAccount(Account account) {
        System.out.printf("%-15s %20s %-25s %-8s %-8s\n", account.getLoginId(), account.getFirstName(),
                account.getSecondName(), account.getRoleAsString(), account.getPersonalId());
    }

    public static void listAccounts() {
        System.out.println(ACCOUNT_LISTING_HEADER);
        printUnderLineForString(ACCOUNT_LISTING_HEADER);
        Set<String> keys = accounts.getKeyset();
        for (String key : keys) {
            //if (accounts.getAccount(key).getRole() == ADMIN) {
                listAccount(accounts.getAccount(key));
            //}
        }
    }

    private static void printAccount(Account account) {
        System.out.println("\nAccount Data:\n=============\nLogin Name:"
                + account.getLoginId()
                + "\nName:" + account.getFirstName() + " " + account.getSecondName()
                + "\nRole:" + account.getRoleAsString());
    }

    public static void printAccount(String currentAccountId) {
        String selectedAccount = AccountOperations.selectAccount(currentAccountId);
        if (selectedAccount != null)
            printAccount(accounts.getAccount(selectedAccount));

    }

    static public void printAccountForUpdate(Account account) {
        System.out.print(ACCOUNT_UPDATE_HEADER);
        printUnderLineForString(ACCOUNT_UPDATE_HEADER);
        System.out.printf("%-20s %-25s\n", account.getFirstName(), account.getSecondName());
    }

    static public void printGreetings(String accountId) {
        System.out.printf("\n%s %s, welcome!\n", accounts.getAccount(accountId).getFirstName(),
                accounts.getAccount(accountId).getSecondName());
    }


}
