package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Account;
import lt.vcs.vcs_project.utils.ScannerUtils;

import static lt.vcs.vcs_project.datalayer.DataLayer.accounts;
import static lt.vcs.vcs_project.datalayer.Role.ADMIN;
import static lt.vcs.vcs_project.servicelayer.PrintService.askForNewPassword;


public class AccountOperations {


    public static Account makeAccountFromCSV(String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length < 2 || inputArray.length > 4) {
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

    public static boolean authenticate(String accountId, String password) {
        if (!accounts.accountExists(accountId)) return false;
        return accounts.getAccount(accountId).authenticate(password);
    }

    static public void addAccount(String csv) {
        Account accountCSV = makeAccountFromCSV(csv);
        if (accountCSV == null)
            return;
        accounts.addAccount(accountCSV);
    }

    static public void addAccount() {
        System.out.printf("\nEnter new Admin Account data in CommaSeparatedValue format" +
                "\nfollowing template: %s\n:", AccountPrints.NEW_ACCOUNT_DATA_INPUT_TEMPLATE);
        String userInput = ScannerUtils.scanString() + ",ADMIN";

        addAccount(userInput);
    }

    static public void updateAccount(String accountId) {
        String accountIdForUpdate = selectAccount(accountId);

        System.out.printf("\nCurrent Account %s values are:\n", accountIdForUpdate);
        Account accountForUpdate = accounts.getAccount(accountIdForUpdate);
        AccountPrints.printAccountForUpdate(accountForUpdate);

        System.out.println("\n\nEnter new Admin Account data in CommaSeparatedValue format" +
                "\nfollowing template:");
        System.out.println(AccountPrints.UPDATE_ACCOUNT_DATA_INPUT_TEMPLATE);
        String userInput = ScannerUtils.scanString();
        accounts.updateAccount(AccountOperations.updateAccountFromCSV(accountForUpdate, userInput));
        //print updated values
        System.out.println("\nNew Values Are:");
        AccountPrints.printAccountForUpdate(accounts.getAccount(accountIdForUpdate));
    }


    static public void changeAccountPassword(String currentAccount) {
        String selectedAccount = selectAccount(currentAccount);
        String newPassword = askForNewPassword();
        if (newPassword.length() > 0) {
            accounts.getAccount(selectedAccount).setPassword(newPassword);
        } else {
            System.out.println("User password cannot be empty");
        }
    }

    static public void removeAccount(String currentAccount) {
        String selectedAccount = selectAccount(currentAccount);
        if (selectedAccount != null) {
            accounts.removeAccount(selectedAccount);
        }
    }

    public static String selectAccount(String currentAccount) {
        System.out.printf("\n\nCurrent Account %s.\n" +
                "\tpress Enter to select current account, otherwise enter new account:\n", currentAccount);
        String userInput = ScannerUtils.scanString();
        if (userInput.equals("")) {
            System.out.printf("Selected account is: %s\n", currentAccount);
            return currentAccount;
        } else if (accounts.AdminExists(userInput)) {
            System.out.printf("Selected account is: %s\n", userInput);
            return userInput;
        } else {
            System.out.printf("\nSorry, Account %s does not exists.\n\treturning to menu\n\n", userInput);
            return null;
        }
    }
}

