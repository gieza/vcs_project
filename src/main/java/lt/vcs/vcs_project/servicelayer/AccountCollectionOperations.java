package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.UI.UI_common;
import lt.vcs.vcs_project.datalayer.Account;
import lt.vcs.vcs_project.utils.ScannerUtils;

import java.util.Set;

import static lt.vcs.vcs_project.datalayer.DataLayer.accounts;
import static lt.vcs.vcs_project.datalayer.Role.ADMIN;


public class AccountCollectionOperations {

    public static void printOutAccount(String accountId) {
        AccountOperations.printOutAccount(accounts.getAccount(accountId));
    }

    public static void listAccounts() {
        System.out.println(AccountOperations.ACCOUNT_LISTING_HEADER);
        UI_common.printUnderLineForString(AccountOperations.ACCOUNT_LISTING_HEADER);
        Set<String> keys = accounts.getKeyset();
        for (String key : keys) {
            if (accounts.getAccount(key).getRole() == ADMIN) {
                AccountOperations.listOutAccount(accounts.getAccount(key));
            }
        }
    /*    return returnString.toString();

        //print out header
        //for each account list accounts
        AccountOperations.listOutAccount(DataLayer.accounts.getAccount(accountId));*/
    }

    static public void addAccount(String csv) {
        Account accountCSV = AccountOperations.makeAccountFromCSV(csv);
        if (accountCSV == null)
            return;
        accounts.addAccount(accountCSV);
    }

    static public void addAccount() {
        System.out.printf("\nEnter new Admin Account data in CommaSeparatedValue format" +
                "\nfollowing template: %s\n:", AccountOperations.NEW_ACCOUNT_DATA_INPUT_TEMPLATE);
        String userInput = ScannerUtils.scanString() + ",ADMIN";
        //System.out.printf("Entered values %s\n", userInput);

        addAccount(userInput);
    }

    static public void updateAccount(String accountId) {
        System.out.printf("\nCurrent Account %s values are:\n", accountId);
        Account accountForUpdate = accounts.getAccount(accountId);
        AccountOperations.printOutAccountForUpdate(accountForUpdate);

        System.out.println("\n\nEnter new Admin Account data in CommaSeparatedValue format" +
                "\nfollowing template:");
        System.out.println(AccountOperations.UPDATE_ACCOUNT_DATA_INPUT_TEMPLATE);
        String userInput = ScannerUtils.scanString();
        accounts.setAccount(accountId,
                AccountOperations.updateAccountFromCSV(accountForUpdate, userInput));
        //print updated values
        System.out.println("\nNew Values Are:");
        AccountOperations.printOutAccountForUpdate(accounts.getAccount(accountId));
    }


}
