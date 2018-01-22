package lt.vcs.vcs_project.datalayer;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class AccountCollectionTest {
    private AccountCollection accountCollection;
    private Account adminAccount1 = new Account("admin1", "admin", "", "admin", Role.ADMIN);
    private Account adminAccount2 = new Account("admin2", "admin2", "Adminas", "Admintauskas", Role.ADMIN);
    private Account adminAccount2_updated = new Account("admin2", "admin2", "Jonas", "Jonaitis", Role.ADMIN);

    @Before
    public void InitializeAccountCollection() {
        //account file is removed
        File accountCollectionFile = new File("AccountList.txt");
        accountCollectionFile.delete();
        accountCollection = new AccountCollection();
    }

    @Test
    public void givenNewAccountList_whenGetCount_thenListSizeIs1() {
        assertEquals(1, accountCollection.getCount());
    }

    @Test
    public void givenNewAccountList_whenCheckingForAdminExistence_thenTrueIsReturned() {
        assertEquals(true, accountCollection.accountExists("admin"));
    }

    @Test
    public void givenNewAccountList_whenCheckingForAdmin2Exitence_thenFalseIsReturned() {
        assertEquals(false, accountCollection.accountExists("admin2"));
    }

    @Test
    public void givenNewAccountList_When2ndAccountIsAdded_thenAccountListSizeIs2() {
        //when
        accountCollection.addAccount(adminAccount2);
        //then
        assertEquals(true, accountCollection.accountExists(adminAccount2.getLoginId()));
    }

    @Test
    public void givenAccountListWith2Accounts_WhenSame2ndAccountIsAdded_thenAccountListSizeIs2() {
        //given
        accountCollection.addAccount(adminAccount2);
        //when
        accountCollection.addAccount(adminAccount2);
        //then
        assertEquals(2, accountCollection.getCount());
    }

    @Test
    public void givenAccountListWith2Accounts_when2ndAccountIsRemoved_thenAccountListSizeIs1() {
        //given
        accountCollection.addAccount(adminAccount2);
        //when
        accountCollection.removeAccount(adminAccount2.getLoginId());
        //then
        assertEquals(1, accountCollection.getCount());
    }

    @Test
    public void givenAccountListWith2Accounts_whenWrongAccountIsRemoved_thenAccountListSizeIs2() {
        //given
        accountCollection.addAccount(adminAccount2);
        //when
        accountCollection.removeAccount(adminAccount1.getLoginId());
        //then
        assertEquals(2, accountCollection.getCount());
    }

    @Test
    public void givenAccountListWith2Accounts_whenBothAccountsAreRemoved_thenAdminAccountIsPresent() {
        //given
        accountCollection.addAccount(adminAccount2);
        //when
        accountCollection.removeAccount("admin");
        accountCollection.removeAccount(adminAccount2.getLoginId());
        //then
        assertEquals(true, accountCollection.accountExists("admin"));
    }

    @Test
    public void givenAccountListWith2Accounts_when2ndAccountIsUpdated_thenItIsEqualToUpdatedAccount() {
        //given
        accountCollection.addAccount(adminAccount2);
        // when
        accountCollection.updateAccount(adminAccount2_updated);
        //then
        assertThat(accountCollection.getAccount(adminAccount2.getLoginId()))
                .isEqualToComparingFieldByFieldRecursively(adminAccount2_updated);
    }


    @Test
    public void givenAccountListWith2Accounts_whenNonExistingAccountIsUpdated_thenItIsNotAddedOrUpdated() {
        //given
        accountCollection.addAccount(adminAccount2);
        //when
        accountCollection.updateAccount(adminAccount1);
        //then
        assertEquals(true, accountCollection.accountExists(adminAccount1.getLoginId()));
    }
}

