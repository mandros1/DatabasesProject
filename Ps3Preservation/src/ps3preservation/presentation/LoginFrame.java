package ps3preservation.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import ps3preservation.data.Ps3SQLDatabase;
import ps3presentation.business.Users;

/**
 * The login class that takes care of user;s need to login before using the
 * application in its entirety. It takes the username and password and check the
 * database to see if the user exists. It validates user input and notifies the
 * user if login was successful
 *
 * @author paolo
 */
public class LoginFrame extends JFrame {

    private JLabel feedbackLabel;
    private Ps3SQLDatabase db;
    private Users user;

    /**
     * Constructor that accepts the database object that will be passed on to
     * the business layer as a parameter
     *
     * @param db The database connection object
     */
    public LoginFrame(Ps3SQLDatabase db) {
        this.db = db;
        initializeFrame();
    }

    /**
     * The method that adds all the components to the login frame and places
     * them properly
     */
    public void addComponents() {
        JLabel titleLabel = new JLabel("Pres3rve");
        JLabel subtitleLabel = new JLabel("Please login or create an account");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font font = new Font("Courier", Font.BOLD, 20);
        titleLabel.setFont(font);
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2));
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        JPanel submitPanel = new JPanel(new BorderLayout());
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");
        JButton registerButton = new JButton("Register");

        SubmitListener listener = new SubmitListener(usernameField, passwordField);
        loginButton.addActionListener(listener);
        cancelButton.addActionListener(listener);
        registerButton.addActionListener(listener);
        submitPanel.add(loginButton, BorderLayout.CENTER);
        submitPanel.add(cancelButton, BorderLayout.WEST);
        submitPanel.add(registerButton, BorderLayout.EAST);

        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setSize(new Dimension(100, 100));
        feedbackLabel = new JLabel(" ");
        feedbackPanel.add(feedbackLabel);
        feedbackLabel.setHorizontalAlignment(JLabel.CENTER);
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(subtitleLabel, BorderLayout.CENTER);
        northPanel.add(inputPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);
        add(submitPanel, BorderLayout.CENTER);
        add(feedbackPanel, BorderLayout.SOUTH);
    }

    /**
     * Method that initializes the frame and sets it size
     */
    public void initializeFrame() {
        setTitle("Login");
        addComponents();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(200, 120));
        pack();
    }

    /**
     * Method that authenticates the username and password against the business
     * layer object
     *
     * @param username The username that needs to be checked
     * @param password The password that needs to be checked
     * @return boolean value that represents if the user is authenticated
     */
    public boolean authenticate(String username, String password) {
        user = new Users(db, username, password);
        return user.authenticate();

    }

    /**
     * Method that hides the window when it is not being used
     */
    public void disposeWindow() {
        this.setVisible(false);
    }

    /**
     * Method that creates new window for user registration and hides the login
     * one
     */
    public void registerUser() {
        disposeWindow();
        new RegisterFrame(this, db);

    }

    /**
     * Inner class that is responsible of listening and reacting to when the
     * user decides to login
     */
    class SubmitListener implements ActionListener {

        JTextField usernameField;
        JPasswordField passwordField;

        public SubmitListener(JTextField usernameField, JPasswordField passwordField) {
            this.usernameField = usernameField;
            this.passwordField = passwordField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Login")) {
                feedbackLabel.setForeground(Color.red);
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.length() < 1 || password.length() < 1) {

                    feedbackLabel.setText("Username and password fields required");
                } else {

                    if (authenticate(username, password)) {
                        feedbackLabel.setForeground(Color.GREEN);
                        feedbackLabel.setText("Login successful");
                        new java.util.Timer().schedule(new java.util.TimerTask() {
                            @Override
                            public void run() {
                                disposeWindow();
                                Users fullUser = new Users(db, username, password);
                                fullUser.generateUser();
                                new GUI("PS3 Preservation Project User: " + username, fullUser, db);
                            }

                        }, 1000);

                    } else {
                        feedbackLabel.setText("Login unsuccessful");
                    }
                }

            } else if (e.getActionCommand().equals("Cancel")) {
                feedbackLabel.setForeground(Color.black);
                feedbackLabel.setText("Sorry to see you go. Goodbye!");
                new java.util.Timer().schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        System.exit(0);
                    }

                }, 1000);
            } else if (e.getActionCommand().equals("Register")) {
                registerUser();
            }
        }

    }

}
