package lt.vcs.vcs_project.servicelayer;

import lt.vcs.vcs_project.datalayer.Account;
import org.junit.Test;

import static lt.vcs.vcs_project.servicelayer.AccountOperations.*;
import static org.assertj.core.api.Assertions.assertThat;
import static lt.vcs.vcs_project.datalayer.Role.ADMIN;
import static org.junit.Assert.assertEquals;

public class AccountOperationsTest {

    private final Account adminAccount1 = new Account("admin1", "admin1",
            "Adminu", "Adminas", ADMIN);

    @Test
    public void givenCorrectCSV_whenMakingAccountFromCSV_thenReferenceAccountIsReturned() {
        //given
        final Account referenceAccount = new Account("admin1", "admin1",
                "Adminu", "Adminas", ADMIN);
        final String correctCSVforNewAccount = "admin1,admin1,Adminu,Adminas";
        //when
        Account actualAccount = makeAccountFromCSV(correctCSVforNewAccount);
        //then
        assertThat(actualAccount).isEqualToComparingFieldByFieldRecursively(referenceAccount);
    }


    @Test(expected = AssertionError.class)
    public void givenDifferentCSV_whenMakingAccountFromCSV_thenReturnedAccountDoesNotWithReferenceAccount() {
        //given
        final String different_CSV_new_account = "admi1,admin1,Adminu,Adminas";
        final Account referenceAccount = new Account("admin1", "admin1",
                "Adminu", "Adminas", ADMIN);
        //when
        Account actualAccount = makeAccountFromCSV(different_CSV_new_account);
        //then
        assertThat(actualAccount).isEqualToComparingFieldByFieldRecursively(referenceAccount);
    }


    @Test
    public void givenCSVwithMandatoryFields_whenMakingAccountFromCSV_thenReferenceAccountIsReturned() {
        //given
        final String newAccount_CSV_onlyMandatory = "admin2,admin";
        final Account referenceAccountOnlyMandarotyFields = new Account("admin2", "admin",
                "", "", ADMIN);
        //when
        Account actualAccount = makeAccountFromCSV(newAccount_CSV_onlyMandatory);
        //  then
        assertThat(actualAccount)
                .isEqualToComparingFieldByFieldRecursively(referenceAccountOnlyMandarotyFields);
    }

    @Test
    public void givenCSVhasTooFewFields_whenMakeAccountFromCSV_thenNullIsReturned() {
        //given
        final String CSV_tooShort = "";
        //when+then
        assertEquals(null, makeAccountFromCSV(CSV_tooShort));
    }

    @Test
    public void givenCSVwithTooManyFields_whenMakeAccountFromCSV_thenNullIsReturned() {
        //given
        final String CSV_tooLong = "admi1,admin1,Adminu,Adminas,ADMIN";
        //when+then
        assertEquals(null, makeAccountFromCSV(CSV_tooLong));
    }

    @Test
    public void givenSampleAccountAndValidCSV_whenUpdateAccountFromCSV_thenReferenceAccountIsReturned() {
        //given
        final String valid_CSV_for_update = "Jonas,Jonaitis";
        final Account refenceAccountAfterUpdate = new Account("admin1", "admin1",
                "Jonas", "Jonaitis", ADMIN);
        Account account_for_update = adminAccount1;
        //when
        Account actualAccount = updateAccountFromCSV(account_for_update, valid_CSV_for_update);
        // then
        assertThat(actualAccount).isEqualToComparingFieldByFieldRecursively(refenceAccountAfterUpdate);
    }

    @Test
    public void givenSampleAccountAndTooShortCSV_whenUpdateAccountFromCSV_thenOriginalAccountIsReturned() {
        //given
        final String tooShort_CSV_for_Update = "Jonaitis";
        Account account_for_update = adminAccount1;
        //when
        Account actualAccount = updateAccountFromCSV(account_for_update, tooShort_CSV_for_Update);
        //then
        assertThat(actualAccount).isEqualToComparingFieldByFieldRecursively(account_for_update);
    }

    @Test
    public void givenSampleAccountAndTooSLongCSV_whenUpdateAccountFromCSV_thenOriginalAccountIsReturned() {
        //given
        final String tooLong_CSV_for_Update = "Jonaitis,Jonaitis,Jonaitis";
        Account account_for_update = adminAccount1;
        //when
        Account updateResult = updateAccountFromCSV(account_for_update, tooLong_CSV_for_Update);
        //then
        assertThat(updateResult).isEqualToComparingFieldByFieldRecursively(account_for_update);
    }

    @Test
    public void givenDefaultAdminAccountInCollection_whenAuthenticatedWithCorrectCredentials_thenTrueIsReturned() {
        assertEquals(true, authenticate("admin", "admin"));
    }

    @Test
    public void givenDefaultAdminAccountInCollection_whenAuthenticatedWithWrongPassword_thenFalseIsReturned() {
        assertEquals(false, authenticate("admin", "nimda"));
    }

    @Test
    public void givenDefaultAdminAccountInCollection_whenAuthenticatedWithNonExistingUser_thenFalseIsReturned() {
        assertEquals(false, authenticate("No_Such_user", "nimda"));
    }
}