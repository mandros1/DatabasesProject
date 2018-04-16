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
//        ArrayList<ArrayList<String>> results = db.getData("select * from users");
//        for (ArrayList r : results) {
//
//            System.out.println(r.get(0) + " " + r.get(1) + " " + r.get(2));
//        }
//        if(db.setData("insert into users values("+100+",'Jure','Fis')")){
//            System.out.println("yup");
//        }else{
//            System.out.println("nope");
//        }
            
//        Software s = new Software(1, "GameName");
//        Releases release = new Releases("1", 0, "");
//        ReleasePackageXref releasePackage = new ReleasePackageXref(0, 0, 0);
//        Packages packageSoftware = new Packages(0, name, filename, source_url, package_type, type, package_channel, 0, 0, 0, hash, 0, 0);
//        PackageFileXref packageFile = new PackageFileXref(0, 0, 0);
//        Licenses licence = new Licenses(0, name, data, 0);
//        Files file = new Files(0, file_id, hash, 0);
    }
}
