package lt.vcs.vcs_project.frontend;

public class FrontEnd {
    static String menu = "";
    static String substate = "";
     void initialize(){

    }

    void run(){
         while (true) {
             printMessage(menu,substate);
   //          String inputString = ScannerUtils.scanString();
    //         updateState(menu,substate,inputString);
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



