package lt.vcs.vcs_project.backend;

import lt.vcs.vcs_project.utils.IOObjectStreamUtils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import static lt.vcs.vcs_project.backend.Role.ADMIN;

public class AccountServices {
    final private String fileName = "AccountList.txt";
    final private Account defaultAdminAccount = new Account("admin", "", "", "admin", ADMIN);

    private Hashtable<String,Account> accountCollection = new Hashtable<>();

    AccountServices() {
        readFromFile();
        this.addAdminIfMissing();

    }


    public long getCount(){
        return accountCollection.size();
    }

    public void addAccount(Account account){
        //todo: jeigu studentas arba destytojas, reikia pataisyti ir ju kolekcijas
        if (!accountCollection.containsKey(account.getLoginId())) {
            accountCollection.put(account.getLoginId(), account);
            writeToFile();
        } else {
            //trow exception - duplicate
            System.out.printf("Account addition failure: User %s already exists\n", account.getLoginId());
        }
    }

    public void addAccount(String csv){
        //todo: jeigu studentas arba destytojas, reikia pataisyti ir ju kolekcijas
        Account accountCSV = new Account(csv);
        if (!accountCollection.containsKey(accountCSV.getLoginId())) {
            accountCollection.put(accountCSV.getLoginId(), accountCSV);
            writeToFile();
        } else {
            //trow exception - duplicate
            System.out.printf("Account addition failure: User %s already exists\n", accountCSV.getLoginId());
        }
    }


    public boolean authenticate(String loginId, String password){

        if(accountCollection.containsKey(loginId)) {
            return accountCollection.get(loginId).authenticate(password);
        } else {
            System.out.printf("loginId or password do not match\n");
        }
        return false;
    }

    public void updateAccount(Account account){
        //todo: jeigu studentas arba destytojas, reikia pataisyti ir ju kolekcijas
        if (accountCollection.containsKey(account.getLoginId())) {
            accountCollection.put(account.getLoginId(), account);
            writeToFile();
        } else {
            System.out.printf("Failure: User %s does not exist\n", account.getLoginId());
        }
    }

    public void updateAccount(String csv){
        //todo: jeigu studentas arba destytojas, reikia pataisyti ir ju kolekcijas ???
        Account accountCSV = new Account(csv);
        //todo: reikalingas account update -> kad nebutu galima pakeisti account'o ir roles
        if (accountCollection.containsKey(accountCSV.getLoginId())) {
            accountCollection.put(accountCSV.getLoginId(), accountCSV);
            writeToFile();
        } else {
            System.out.printf("Failure: User %s does not exist\n", accountCSV.getLoginId());
        }
    }

    public void removeAccount(String loginId){
        //todo: jeigu studentas arba destytojas, reikia pataisyti ir ju kolekcijas
        accountCollection.remove(loginId);
        writeToFile();
     }

    public Account getAccount(String accountId){
        if (accountCollection.containsKey(accountId))  return accountCollection.get(accountId);
        System.out.printf("Failure: User %s does not exist\n", accountId);
        return null;
    }

    public boolean containsKey(String accountId){
        return accountCollection.containsKey(accountId);
    }

    Integer adminCount(){
        Integer adminCount = 0;
        Set<String> keys = accountCollection.keySet();
        for (String key:keys) {
            if (accountCollection.get(key).getRole() == ADMIN) adminCount++;
        }
        return adminCount;
    }

    void addAdminIfMissing(){
        if (this.adminCount()<1) {
            this.addAccount(defaultAdminAccount);
        }
    }


    private void readFromFile(){
        try {
        accountCollection = (Hashtable<String, Account>) IOObjectStreamUtils.readFirstObjectFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("%s not found in Account Collection \n", fileName);
        }

    }


    private void writeToFile(){
        IOObjectStreamUtils.writeObjectToFile(fileName, accountCollection);

    }


    public String listAccounts() {
        StringBuilder returnString = new StringBuilder("AccountId\tFirst name\tLast Name\tRole\tstudent\\lecturerId\n=====================================================\n");
        Set<String> keys = accountCollection.keySet();
        for (String key:keys) {
            Account listingAccount = accountCollection.get(key);
            returnString.append(key +"\t" + listingAccount.getFirstName() +
                    "\t" + listingAccount.getSecondName() +
                    "\t" + listingAccount.getRole().toString() +
                    "\t" + listingAccount.getPersonalId() +"\n");
        }
        return returnString.toString();
    }

    public String toStringCSV(String loginId){
        return accountCollection.get(loginId).toStringCSV();
    }

    public String getStudentId(String loginId){
        if (accountCollection.get(loginId).getRole().equals(Role.STUDENT)) {
            return accountCollection.get(loginId).getPersonalId();
        } else {
            return null;
        }
    }

    public String getLecturerId(String loginId){
        if (accountCollection.get(loginId).getRole().equals(Role.LECTURER)) {
            return accountCollection.get(loginId).getPersonalId();
        } else {
            return null;
        }
    }

}
