package lt.vcs.vcs_project.backend;

import static lt.vcs.vcs_project.backend.Role.*;
//import static lt.vcs.vcs_project.backend.Role.valueOf;

public class AccountOperations {
    public static Account accountFromCSV(String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length < 2 || inputArray.length > 6) {
            System.out.printf("Account creation Failure: input data has incorrect number of fields\n");
            return null;
        } else if (inputArray.length <= 5) {
            return new Account(inputArray[0], inputArray[1],
                    inputArray.length < 3 ? "" : inputArray[2],
                    inputArray.length < 4 ? "" : inputArray[3],
                    ADMIN);
        } else if (inputArray.length == 6 && (
                valueOf(inputArray[4].toUpperCase()) == STUDENT ||
                        valueOf(inputArray[4].toUpperCase()) == LECTURER)) {
            return new Account(inputArray[0], inputArray[1], inputArray[2], inputArray[3],
                    valueOf(inputArray[4].toUpperCase()), inputArray[5]);
        }
    }

    public static Account updateFromCSV(Account account, String csv) {
        //todo - lauku kiekis yra kitoks, todel kitokia konvertacija reiklainga
        Account accountCSV = accountFromCSV(csv);
        if (accountCSV == null) {
            System.out.printf("Account update Failure: input data has incorrect number of fields\n");
            return account;
        } else {
            account.setFirstName(accountCSV.getFirstName());
            account.setSecondName(accountCSV.getSecondName());
            return account;
        }
    }

    static public String getNewAccountDataInputTemplate() {
        return "Login,Password,First name,Second name";
    }

    static public String getListingHeader() {
        return String.format("%-15s %20s %-25s %-8s %-8s", "Login", "First name", "Second name", "Role", "ID");
    }
}
