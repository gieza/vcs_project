package lt.vcs.vcs_project.backend;

import java.io.Serializable;
import java.util.Arrays;


public /*abstract*/ class Account  implements Serializable {
   // private static final Integer MAXPASSWORDRETRIES = 5;


    private String loginId;
    private String firstName;
    private String secondName;
    private String password;
    private Role role;
    private String personalId; // only to be used by Student and Lecturer accounts

    public Account(String loginId, String password, String firstName, String secondName,  Role role) {
       if (role.equals(Role.ADMIN)) {
           this.loginId = loginId;
           this.firstName = firstName;
           this.secondName = secondName;
           this.password = password;
           this.role = role;
           this.personalId="";
       } else {
           System.out.printf("Failure: cannot create %s type of account with ID\n", role.toString());
       }

    }

    public Account(String loginId, String password, String firstName, String secondName,  Role role,String personalId) {
        if (!role.equals(Role.ADMIN)) {
            this.loginId = loginId;
            this.firstName = firstName;
            this.secondName = secondName;
            this.password = password;
            this.role = role;
            this.personalId = personalId;
        } else  {
            System.out.printf("Failure: Admin role should not have ID\n");
        }

    }

    public Account(String csv) {
        String[] inputArray = csv.split(",");
        if (inputArray.length == 5 &&
                Role.valueOf(inputArray[4].toUpperCase()) == Role.ADMIN) {
            this.loginId = inputArray[0];
            this.password = inputArray[1];
            this.firstName = inputArray[2];
            this.secondName = inputArray[3];
            this.role = Role.ADMIN;
            this.personalId="";
        } else if (inputArray.length == 6 && (
                Role.valueOf(inputArray[4].toUpperCase()) == Role.STUDENT ||
                        Role.valueOf(inputArray[4].toUpperCase()) == Role.LECTURER)) {
            this.loginId = inputArray[0];
            this.password = inputArray[1];
            this.firstName = inputArray[2];
            this.secondName = inputArray[3];
            this.role = Role.valueOf(inputArray[4].toUpperCase());
            this.personalId = inputArray[5];
        } else {
            System.out.printf("Account creation Failure: input data has incorrect number of fields\n");
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


    public String toString(){
        return "U:" + this.loginId + " N:" + this.firstName + " " + this.secondName + " R:" + this.role.toString();
    }

    public String toStringCSV(){

        return  String.join(",", Arrays.asList(this.loginId,this.password,this.firstName,this.secondName,this.role.toString(),this.personalId));

    }

    static public String printHeaderCSV(){
        return  "Login,Password,First name,Second name";
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
}
