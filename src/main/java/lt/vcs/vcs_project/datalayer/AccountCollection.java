package lt.vcs.vcs_project.datalayer;

import lt.vcs.vcs_project.servicelayer.AccountRepository;
import lt.vcs.vcs_project.utils.IOObjectStreamUtils;

import java.io.FileNotFoundException;
import java.io.InvalidClassException;
import java.util.Hashtable;
import java.util.Set;

import static lt.vcs.vcs_project.datalayer.Role.ADMIN;

public class AccountCollection {
    final private String fileName = "AccountList.txt";
    final private Account defaultAdminAccount = new Account("admin", "admin", "Adminu", "adminas", ADMIN);

    private Hashtable<String, Account> accountCollection = new Hashtable<>();

    public AccountCollection() {
        //readFromFile();
        //this.addAdminIfMissing();


    }

    public long getCount() {
        return accountCollection.size();
    }

    public boolean isAdmin(String loginId) {
        return AccountRepository.sqlIsAdmin(loginId);
    }

    public void updateAccount(Account account) {
        //accountCollection.put(account.getLoginId(), account);
        //writeToFile();
        AccountRepository.sqlUpdateAccount(account);
    }

    public void addAccount(Account account) {
        if (!accountExists(account.getLoginId())) {
            AccountRepository.sqlAddAccount(account);
            //writeToFile();
        } else {
            //trow exception - duplicate
            System.out.printf("Account addition failure: User %s already exists\n", account.getLoginId());
        }
    }

    public void removeAccount(String loginId) {
        AccountRepository.sqlDeleteAccount(loginId);
        addAdminIfMissing();
        //writeToFile();
    }

    public Account getAccount(String accountId) {
        return AccountRepository.getSqlAccount(accountId);
    }

    public boolean accountExists(String accountId) {
        return AccountRepository.sqlAccountExists(accountId);
    }

    private Integer adminCount() {
        return AccountRepository.adminCount();
    }

    void addAdminIfMissing() {
        if (this.adminCount() < 1) {
            this.addAccount(defaultAdminAccount);
        }
    }


    private void readFromFile() {
        try {
            accountCollection = (Hashtable<String, Account>) IOObjectStreamUtils.readFirstObjectFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("File %s not found. Creating new one.\n", fileName);
        } catch (InvalidClassException e) {
            System.out.printf("File %s is corrupt. \n", fileName);
        }
    }

    private void writeToFile() {
        IOObjectStreamUtils.writeObjectToFile(fileName, accountCollection);

    }

    public Set<String> getKeyset() {
        return AccountRepository.getKeyset();
    }

    public String getStudentId(String loginId) {
        if (accountCollection.get(loginId).getRole().equals(Role.STUDENT)) {
            return accountCollection.get(loginId).getPersonalId();
        } else {
            return null;
        }
    }

    public String getLecturerId(String loginId) {
        if (accountCollection.get(loginId).getRole().equals(Role.LECTURER)) {
            return accountCollection.get(loginId).getPersonalId();
        } else {
            return null;
        }
    }

    public Role getRole(String accountId) {
        return AccountRepository.getSqlAccount(accountId).getRole();
    }
}
