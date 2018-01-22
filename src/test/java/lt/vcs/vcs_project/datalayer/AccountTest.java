package lt.vcs.vcs_project.datalayer;

import org.junit.Test;


import static lt.vcs.vcs_project.datalayer.Role.ADMIN;
import static org.junit.Assert.assertEquals;

public class AccountTest {
    final private Account defaultAdminAccount = new Account("admin", "admin", "Adminu", "adminas", ADMIN);
    final private String correctPassword = "admin";
    final private String incorrectPassword = "admin2";

    //test authenticate
    @Test
    public void givenDefaultAdminAccount_whenAuthenticatedWithCorrectPsswd_thenAuthenticationIsTrue() {
        assertEquals(true, defaultAdminAccount.authenticate(correctPassword));
    }

    @Test
    public void givenDefaultAdminAccount_whenAuthenticatedWithWrongPsswd_thenAuthenticationFalse() {
        assertEquals(false, defaultAdminAccount.authenticate(incorrectPassword));
    }
}
