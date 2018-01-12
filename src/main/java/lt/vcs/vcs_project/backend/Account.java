package lt.vcs.vcs_project.backend;

import java.io.Serializable;
import java.util.Arrays;

import static lt.vcs.vcs_project.backend.Role.*;


public /*abstract*/ class Account implements Serializable {

    private String loginId;
    private String firstName;
    private String secondName;
    private String password;
    private Role role;
    private String personalId; // only to be used by Student and Lecturer login accounts

    public Account(String loginId, String password, String firstName, String secondName, Role role) {
        if (role.equals(ADMIN)) {
            this.loginId = loginId;
            this.firstName = firstName;
            this.secondName = secondName;
            this.password = password;
            this.role = role;
            this.personalId = "1";
        } else {
            System.out.printf("Failure: cannot create %s type of account with ID\n", role.toString());
        }

    }

    public Account(String loginId, String password, String firstName, String secondName, Role role, String personalId) {
        if (!role.equals(ADMIN)) {
            this.loginId = loginId;
            this.firstName = firstName;
            this.secondName = secondName;
            this.password = password;
            this.role = role;
            this.personalId = personalId;
        } else {
            System.out.printf("Failure: Admin role should not have ID\n");
        }
    }



    public Account() {
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public Role getRole() {
        return this.role;
    }


    public String toString() {
        return "Account Data:\n=============\nLogin Name:"
                + this.loginId + "\nName:" + this.firstName + " " + this.secondName
                + "\n Password:" + this.password
                + "\n Role:" + this.role.toString() + "\n"
                + "\n PersonalId:" + this.personalId
                ;
    }

    public String toStringCSV() {

        return String.join(",", Arrays.asList(this.loginId, this.firstName, this.secondName,
                this.role.toString(), this.personalId));

    }

    public String toStringForListing() {
        return String.format("%-15s %20s %-25s %-8s %-8s", this.loginId, this.firstName, this.secondName,
                this.role.toString(), this.personalId);
    }


    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    @Override
    public boolean equals(Object o) {
        return true;
    }
}
