package ps3preservation;

import java.util.ArrayList;

/**
 * Starter class to test the Ps3SQLDatabase class. In the final version of the
 * project this class will no directly interact with the Ps3SQLDatabase class,
 * it will business layer between them
 *
 * @author ZG3 team
 */
public class Ps3Preservation {

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

//        Prints the database and table information
        System.out.println(db.getDatabaseInformation());
        System.out.println(db.getTableInformation("packages"));

//        Gets the package information
        ArrayList<ArrayList<String>> results = db.getData("select * from packages");
        System.out.println("There are " + results.size() + " packages in the database ");

//        Testing the prepared statements
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("10000");
        ArrayList<ArrayList<String>> results2 = db.getData("select * from packages where id > ?", parameters);
        System.out.println("There are " + results2.size() + " packages in the database with id bigger than 10000");

        ArrayList<ArrayList<String>> users = db.getData("select * from users");
        System.out.println("\nUsers:");
        for (int i = 0; i < users.size(); i++) {
            System.out.println("\nUser#"+(i+1));
            for (int j = 0; j < users.get(i).size(); j++) {
                System.out.print(users.get(i).get(j) + "\t");

            }
        }
        db.setData("insert into users values(3,'mate','password')");
        ArrayList<ArrayList<String>> usersUpdated = db.getData("select * from users");
        System.out.println("\n\nUsers updated:");
        for (int i = 0; i < usersUpdated.size(); i++) {
            System.out.println("\nUser#"+(i+1));
            for (int j = 0; j < usersUpdated.get(i).size(); j++) {
                System.out.print(usersUpdated.get(i).get(j) + "\t");
            }
        }
        db.setData("delete from users where id='3';");
        System.out.println(db.getData("select * from users where id='3';"));
        ArrayList<ArrayList<String>> das = db.getData("select * from users");
        System.out.println("\n\nUsers updated:");
        for (int i = 0; i < das.size(); i++) {
            System.out.println("\nUser#"+(i+1));
            for (int j = 0; j < das.get(i).size(); j++) {
                System.out.print(das.get(i).get(j) + "\t");
            }
        }
    }
}

