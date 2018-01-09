package lt.vcs.vcs_project.backend;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class AccountServicesTest {
    private AccountServices accountServices = new AccountServices();
    private Account defaultAdminAccount1 = new Account("admin1", "admin", "", "admin", Role.ADMIN);
    private Account defaultAdminAccount2 = new Account("admin2", "admin2", "Adminas", "Admintauskas", Role.ADMIN);

    @Test
    public void givenEmptyAccountList_for_then_AccountListSize_is_1() {
        //ismesti account faila
        File accountCollectionFile = new File("AccountList.txt");
        accountCollectionFile.delete();
        assertEquals("newly created Account list has 1 members",1, accountServices.getCount() );
    }

    @Test
    public void givenEmptyAccountList_for_then_has_admin() {
        System.out.println(accountServices.listAccounts());
        assertEquals("newly created Account list has Admin member",true, accountServices.containsKey("admin") );
    }

   @Test
    public void givenEmptyAccountList_with_2_accounts_when_account_is_removed_then_AccountListSize_is_1() {
      // accountServices.addAccount(defaultAdminAccount);
       accountServices.addAccount(defaultAdminAccount2);
        assertEquals("After adding 2 accounts newly created Account list has 2 member", 2, accountServices.getCount());
       System.out.println(accountServices.listAccounts());
    }
/*
            accountCollection.removeAccount("admin2");
        assertEquals("After 1 account removal, list has 2 member",1, accountCollection.getsize());
        System.out.println("2:" + accountCollection.toString());
        accountCollection.removeAccount("admin2");
        System.out.println("3:" + accountCollection.toString());
        accountCollection.removeAccount("admin");
        System.out.println("4:" + accountCollection.toString());
    }
    */

  /*   @Test
    public void givenEmptyAccountHandler_for_then_1_Admin_Account_Returned() {
        assertEquals("For newly created empty Account list, there is always 1 Admin account",
                1, (long) accountHandler.adminCount());
    }

    @Test
    public void givenEmptyAccountHandler_when_new_account_is_added_then_Listsize_is_2(){
        accountHandler.addAccount(defaultAdminAccount2);
        assertEquals("Expected Account count is 2",
                2, (long) accountHandler.getsize());
    }

    /*@Test
    public void givenAccountHandler_when_new_account_is_added_then_Listsize_is_2(){
        accountHandler.addAccount(defaultAdminAccount2);
        assertEquals("Expected Account count is 2",
                2, (long) accountHandler.getsize());*/
/*
    @Test
    public void givenAccountHandler_with_Admin_account_then_AdminCount_returns_1(){
        accountHandler.addAccount(defaultAdminAccount);
        assertEquals("Expected Account count is 1",
                1, (long) accountHandler.adminCount());
    }

    @Test
    public void givenEmptyAccountList_with_2_accounts_when_account_is_removed_then_AccountListSize_is_1() {
        accountHandler.addAccount(defaultAdminAccount);
        accountHandler.addAccount(defaultAdminAccount2);
        System.out.println(accountHandler.toString());
        accountHandler.removeAccount("admin2");
        System.out.println(accountHandler.toString());
        accountHandler.removeAccount("admin");
        System.out.println(accountHandler.toString());

    }*/
}

