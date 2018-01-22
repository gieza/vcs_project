package lt.vcs.vcs_project.datalayer;

import lt.vcs.vcs_project.utils.IOObjectStreamUtils;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Set;

import static lt.vcs.vcs_project.datalayer.Role.ADMIN;

public class AccountCollection {
    final private String fileName = "AccountList.txt";
    final private Account defaultAdminAccount = new Account("admin", "admin", "Adminu", "adminas", ADMIN);

    private Hashtable<String, Account> accountCollection = new Hashtable<>();

    AccountCollection() {
        readFromFile();
        this.addAdminIfMissing();
    }

    public long getCount() {
        return accountCollection.size();
    }

    public boolean AdminExists(String loginId) {
        return (accountCollection.containsKey(loginId)
                && accountCollection.get(loginId).getRole() == ADMIN);
    }

    public void updateAccount(Account account) {
        accountCollection.put(account.getLoginId(), account);
    }

    public void addAccount(Account account) {
        if (!accountCollection.containsKey(account.getLoginId())) {
            accountCollection.put(account.getLoginId(), account);
            writeToFile();
        } else {
            //trow exception - duplicate
            System.out.printf("Account addition failure: User %s already exists\n", account.getLoginId());
        }
    }

    public void removeAccount(String loginId) {
        accountCollection.remove(loginId);
        addAdminIfMissing();
        writeToFile();
    }

    public Account getAccount(String accountId) {
        if (accountCollection.containsKey(accountId)) return accountCollection.get(accountId);
        System.out.printf("Failure: User %s does not exist\n", accountId);
        return null;
    }

    public boolean accountExists(String accountId) {
        return accountCollection.containsKey(accountId);
    }

    private Integer adminCount() {
        Integer adminCount = 0;
        Set<String> keys = accountCollection.keySet();
        for (String key : keys) {
            if (accountCollection.get(key).getRole() == ADMIN) adminCount++;
        }
        return adminCount;
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
        }
    }

    private void writeToFile() {
        IOObjectStreamUtils.writeObjectToFile(fileName, accountCollection);

    }

    public Set<String> getKeyset() {
        return accountCollection.keySet();
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
        return accountCollection.get(accountId).getRole();
    }
}
