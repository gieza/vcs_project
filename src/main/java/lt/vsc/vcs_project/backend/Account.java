package lt.vsc.vcs_project.backend;

import java.io.Serializable;

enum roleListEnum{admin, lecturer, student}

public class Account implements Serializable {
    private static final Integer MAXPASSWORDRETRIES = 5;


    private String loginId;
    private String firstName;
    private String secondName;
    private String password;
    private roleListEnum role; //todo: sukonvertuoti i password hash
    private Integer retriesRemaining;
    private boolean accountActive;
    private String personId;



    public Account(String loginId, String firstName, String secondName, String password, roleListEnum role) {
        this.loginId = loginId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.password = password;
        this.role = role;
        this.personId = "";
        this.retriesRemaining = MAXPASSWORDRETRIES;
        this.accountActive = true;

    }

    public boolean authenticate(String password) {
        if (!accountActive) return false;
        boolean authenticationResult = this.password.equals(password);
        if (!authenticationResult && accountActive) {
            if(retriesRemaining >0) {
                this.retriesRemaining--;
            } else {
                this.accountActive = false;
            }
            return false;
        }
        return authenticationResult;
    }

    public boolean isActive(){
        return accountActive;
    }

    public void ResetPassword(String password){
// perrasyti password
        // padaryti active
    }


    public boolean ChangePassword(String oldPassword, String newPassword){
        return false;
    }

    public void makeStudent(String studentId) {
        this.personId=studentId;
        this.role = roleListEnum.student;
    }

    public void makeAdmin() {
        this.personId=this.loginId;
        this.role = roleListEnum.admin;
    }

    public String getloginid(){
        return this.loginId;
    }

    public String toString(){
        return "U:" + this.loginId + " N:" + this.firstName + " " + this.secondName + " R:" + this.role.toString();
    }

    public String toStringCSV(){
        return "";
    }

    static String printHeaderCSV(){
        return "";
    }

}
