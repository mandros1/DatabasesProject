package ps3presentation.business;

import java.util.ArrayList;
import ps3preservation.data.Ps3SQLDatabase;

/**
 * Users class that stores all the variables associated with the user table in
 * the database. It check availability of a username and creates a new user in
 * the table if required
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

    /**
     * Getter method that returns the first name of the user
     *
     * @return The user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter method that returns the last name of the user
     *
     * @return The user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter method that returns the email of the user
     *
     * @return The user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * The constructor that accepts and sets the database connection object
     * which is used to comunicate with the database, username and password of
     * the user created
     *
     * @param db The database connection object
     * @param username The username value
     * @param password The password value
     */
    public Users(Ps3SQLDatabase db, String username, String password) {
        this.username = username;
        this.password = password;
        this.db = db;
    }

    /**
     * The setter method for the user's first name
     *
     * @param firstName The first name of the user that needs to be changed
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * The setter method for the user's last name
     *
     * @param lastName The last name of the user that needs to be changed
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * The setter method for the user's email
     *
     * @param email The email of the user that needs to be changed
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * The constructor for the user that accepts the database connection object,
     * the first name, last name, email, username and password of the user that
     * is being created
     *
     * @param db The database connection object
     * @param username The username of the user
     * @param password The password of the user
     * @param firstName The first name of the user
     * @param lastname The last name of the user
     * @param email The email of the user
     */
    public Users(Ps3SQLDatabase db, String username, String password, String firstName,
            String lastname, String email) {
        this.db = db;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastname;
        this.email = email;
    }

    /**
     * Method that generates the user values using the username and password of
     * the user
     */
    public void generateUser() {
        ArrayList<ArrayList<String>> result = db.getData("select * from users where username = '" + username + "' and "
                + "password = PASSWORD('" + password + "')");
        firstName = result.get(0).get(3);
        lastName = result.get(0).get(4);
        email = result.get(0).get(5);
    }

    /**
     * Method that create user with the username and password available.
     *
     * @return
     */
    public boolean createUser() {
        return db.setData("insert into users(username,password) values('" + username + "',PASSWORD('" + password + "'))");
    }

    /**
     * Method that check the availability of the username
     *
     * @return
     */
    public boolean checkAvailability() {
        ArrayList<ArrayList<String>> result = db.getData("select id from users where username = '" + username + "'");
        if (result.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that authenticates if the username and password are present in the
     * database for login
     *
     * @return
     */
    public boolean authenticate() {
        ArrayList<ArrayList<String>> result = db.getData("select id from users where username = '" + username + "' and password = PASSWORD('" + password + "')");
        if (result.size() == 1) {
            this.id = Integer.parseInt(result.get(0).get(0));
            return true;
        } else {
            return false;
        }
    }

     /**
     * Getter method that returns the id of the user
     *
     * @return The user's id
     */
    public int getId() {
        return id;
    }
     /**
     * Getter method that returns the username of the user
     *
     * @return The username value
     */
    public String getUsername() {
        return username;
    }

     /**
     * Getter method that returns the password of the user
     *
     * @return The user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * The setter method for the user's id
     *
     * @param id The id of the user that needs to be changed
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The setter method for the username value
     *
     * @param username The username of the user that needs to be changed
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * The setter method for the user's password
     *
     * @param password The password of the user that needs to be changed
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
