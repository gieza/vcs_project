package lt.vcs.vcs_project;

import lt.vcs.vcs_project.UI.CommandLineUI;
import lt.vcs.vcs_project.db.DbAccess;

public class Project {

    public static void main(String[] args){
        DbAccess dbAccess = new DbAccess();
        CommandLineUI.run();

    }

}
