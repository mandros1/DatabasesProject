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
 *
 * @author paolo
 */
public class LoginFrame extends JFrame {

    private JLabel feedbackLabel;
    private Ps3SQLDatabase db;
    private Users user;

    /**
     *
     * @param db
     */
    public LoginFrame(Ps3SQLDatabase db) {
        this.db = db;
        initializeFrame();
    }

    /**
     *
     */
    public void addComponents() {
        JLabel titleLabel = new JLabel("Pres3rve");
        JLabel subtitleLabel = new JLabel("Please login or create an account");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font font = new Font("Courier", Font.BOLD,20);
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
        northPanel.add(titleLabel,BorderLayout.NORTH);
        northPanel.add(subtitleLabel,BorderLayout.CENTER);
        northPanel.add(inputPanel,BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);
        add(submitPanel, BorderLayout.CENTER);
        add(feedbackPanel, BorderLayout.SOUTH);
    }

    /**
     *
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
     *
     * @param username
     * @param password
     * @return
     */
    public boolean authenticate(String username, String password) {
        user = new Users(db, username, password);
        return user.authenticate();

    }

    /**
     *
     */
    public void disposeWindow() {
        this.setVisible(false);
    }

    /**
     *
     */
    public void registerUser() {
        disposeWindow();
        new RegisterFrame(this, db);

    }

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
                                Users fullUser = new Users(db,username, password);
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
