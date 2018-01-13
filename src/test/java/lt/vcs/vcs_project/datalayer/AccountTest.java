package lt.vcs.vcs_project.datalayer;

import lt.vcs.vcs_project.servicelayer.AccountOperations;
import org.junit.Test;

import static lt.vcs.vcs_project.datalayer.Role.ADMIN;
import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

//org.assertj
public class AccountTest {
    //private Account account = new Account();
    public Account AdminAccount1 = new Account("admin1", "admin", "Adminu", "Adminas", ADMIN);
    private Account AdminAccount1_onlyMandatory = new Account("admin1", "admin", "", "", ADMIN);
    private Account AdminAccount1_CSV = AccountOperations.makeAccountFromCSV("admin1,admin,Adminu,Adminas,ADMIN");
    private Account AdminAccount1_CSV_onlyMandatory = AccountOperations.makeAccountFromCSV("admin1,admin");
    private Account AdminAccount1_CSV_tooShort = AccountOperations.makeAccountFromCSV("");
    private String AdminAccount1_CSV_asStudent = "admin1,admin,Adminu,Adminas,ADMIN,123";

    //private Account defaultAdminAccount2 = new Account("admin2", "admin2", "Adminas", "Admintauskas", Role.ADMIN);

    @Test
    public void givenAccount1CSV_when_constructed_then_Account1_is_returned() {
        assertEquals("Account created from CSV match to the account created by field", AdminAccount1, AdminAccount1_CSV);
    }

    @Test
    public void givenAdminAccount1_CSV_onlyMandatory_when_constructed_then_AdminAccount1_onlyMandatory_is_returned() {
        assertThat(AdminAccount1_onlyMandatory).isEqualToComparingFieldByFieldRecursively(AdminAccount1_CSV_onlyMandatory);
    }

    @Test
    public void StupidgivenAdminAccount1_CSV_onlyMandatory_when_constructed_then_AdminAccount1_onlyMandatory_is_returned() {
        assertThat(AdminAccount1_onlyMandatory).isEqualToComparingFieldByFieldRecursively(AdminAccount1_CSV);
    }

    @Test
    public void givenAdminAccount1_CSV_too_short_when_constructed_then_null_is_returned() {
        assertEquals("Account created from CSV match to the account created by field", null, AdminAccount1_CSV_tooShort);
    }
}
