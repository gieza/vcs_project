package lt.vcs.vcs_project.frontend;

import lt.vcs.vcs_project.UI.AdminUserInterface;
import lt.vcs.vcs_project.UI.LecturerUserInterface;
import lt.vcs.vcs_project.UI.StudentUserInterface;
import lt.vcs.vcs_project.UI.UserInterface;
import lt.vcs.vcs_project.backend.Backend;

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
                        break;
                    case STUDENT:
                        userInterface = new StudentUserInterface();
                        break;
                    case LECTURER:
                        userInterface = new LecturerUserInterface();
                        break;
                }
                userInterface.open(currentUser);
            }
    }

}
