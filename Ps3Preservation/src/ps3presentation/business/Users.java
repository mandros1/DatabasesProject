package ps3presentation.business;

import java.util.ArrayList;
import ps3preservation.data.Ps3SQLDatabase;

/**
 *
 * @author Marin Andros, Paolo Stojic, Roberto Anic Banic, Matea
 * Cvijanovic,Donat Avdijaj
 */
public class Users {

    private int id;
    private String username;
    private String password;
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private Ps3SQLDatabase db;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Users(Ps3SQLDatabase db, String username, String password) {
        this.username = username;
        this.password = password;
        this.db = db;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Users(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public boolean createUser() {
        return db.setData("insert into users(username,password) values('" + username + "','" + password + "')");
    }

    public boolean checkAvailability() {
        ArrayList<ArrayList<String>> result = db.getData("select id from users where username = '" + username + "'");
        if (result.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean authenticate() {
        ArrayList<ArrayList<String>> result = db.getData("select id from users where username = '" + username + "' and password = '" + password + "'");
        if (result.size() == 1) {
            this.id = Integer.parseInt(result.get(0).get(0));
            return true;
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
