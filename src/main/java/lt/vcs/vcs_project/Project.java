package lt.vcs.vcs_project;

import lt.vcs.vcs_project.UI.CommandLineUI;
import lt.vcs.vcs_project.servicelayer.AccountRepository;

public class Project {

    public static void main(String[] args){
        AccountRepository accountRepository = new AccountRepository();
        CommandLineUI.run();

    }

}
