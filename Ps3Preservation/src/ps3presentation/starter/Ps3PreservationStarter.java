package ps3presentation.starter;

import ps3preservation.presentation.*;
import ps3preservation.data.Ps3SQLDatabase;

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

        if (db.connect()) {
            System.out.println("Connected succefully");
        } else {
            System.err.println("Connection not succefull");
        }
        new LoginFrame(db);
    }
}
