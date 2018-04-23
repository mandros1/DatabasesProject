package ps3presentation.starter;

import java.util.ArrayList;
import ps3preservation.data.Ps3SQLDatabase;
import ps3preservation.presentation.LoginFrame;

/**
 * Starter class to test the Ps3SQLDatabase class. In the final version of the
 * project this class will no directly interact with the Ps3SQLDatabase class,
 * it will business layer between them
 *
 * @author ZG3 team
 */
public class Ps3PreservationStarter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //The hardcoded parameters will not be displayed in the final version, the user will enter his or her own
        Ps3SQLDatabase db = new Ps3SQLDatabase("jdbc", "mysql", "hypercubed.co", "3306", "ps3_preservation?useSSL=false", "ps3_preservation", "M2ZUdOq765uSHhbr");
//       db.connect();
//        ArrayList<ArrayList<String>> users = db.getData("select * from users");
//        for(ArrayList l: users){
//            System.out.println(l.size());
//          
//                System.out.println(l.get(0) +""+ l.get(1));
//            
//        }
        if (db.connect()) {
            System.out.println("Connected succefully");
        } else {
            System.err.println("Connection not succefull");
        }
//        ArrayList<ArrayList<String>> users = db.getData("select * from users");
//        System.out.println(db.getTableInformation("users"));
//        db.setData("delete from users where username = 'a'");
//        ArrayList<ArrayList<String>> users = db.getData("select * from users where username = ");
//        System.out.println(db.getTableInformation("users"));
        new LoginFrame(db);
    }
}
