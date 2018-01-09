package lt.vcs.vcs_project.frontend;

import java.util.Scanner;

public class FrontEnd {
    static String state = "LOGIN";
    static String substate = "NAME";
     void initialize(){

    }

    void run(){
         while (true) {
             printMessage(state,substate);
   //          String inputString = ScannerUtils.scanString();
    //         updateState(state,substate,inputString);
         }
    }



    public void printMessage(String state, String substate) {
         String Detailed_state = state+substate;
         switch (Detailed_state) {
             case "LOGINNONE":
             //    runLogin();


            }
        }


    };



