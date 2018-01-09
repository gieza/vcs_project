package lt.vcs.vcs_project.frontend;

import lt.vcs.vcs_project.UI.AdminUserInterface;
import lt.vcs.vcs_project.UI.UserInterface;
import lt.vcs.vcs_project.backend.Backend;

import static lt.vcs.vcs_project.backend.Role.ADMIN;

public class Project {

    public static void main(String[] args){
            //Application.initialize();
           UserInterface userInterface = null;

            String currentUser;
            while(true) {
                //currentUser = Backend.login();
                currentUser = Backend.loginDefault();
                switch (Backend.getRole(currentUser)) {
                    case ADMIN:
                        userInterface = new AdminUserInterface();
                }
                userInterface.open(currentUser);
            }
    }
 }
