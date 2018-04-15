package ps3preservation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import ps3preservation.data.Users;

/**
 *
 * @author paolo
 */
public class RegisterFrame extends JFrame {

    private Ps3SQLDatabase db;
    private JLabel feedbackLabel;
    JPanel buttonPanel;
    LoginFrame loginFrame;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public RegisterFrame(LoginFrame lf, Ps3SQLDatabase db) {
        this.db = db;
        this.loginFrame = lf;

        this.initializeFrame();

    }

    public void initializeFrame() {
        addComponents();
        setTitle("Register");
        addComponents();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(200, 250));
        pack();
    }

    public void addComponents() {

        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        JTextField lastNameField = new JTextField(25);
        JTextField firstNameField = new JTextField(25);
        JTextField emailField = new JTextField(25);
        inputPanel.add(new JLabel("First name"));
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel("Last name"));
        inputPanel.add(lastNameField);
        inputPanel.add(new JLabel("Email"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Username"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password"));
        inputPanel.add(passwordField);

        buttonPanel = new JPanel();
        JButton createButton = new JButton("Create account");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String firstName = firstNameField.getText();
                String lastname = lastNameField.getText();
                String email = emailField.getText();
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
                if (username.length() < 1 || password.length() < 1 || firstName.length() < 1
                        || lastname.length() < 1 || email.length() < 1) {
                    feedbackLabel.setText("All fields required");
                } else if (matcher.find()) {
                    feedbackLabel.setText("Input email properly");
                } else {
                    Users user = new Users(db, username, password);
                    if (user.checkAvailability()) {
                        if (user.createUser()) {
                            feedbackLabel.setText("Account successfully created");
                            JButton loginButton = new JButton("Login");
                            loginButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    disposeWindow();
                                    loginFrame.setVisible(true);
                                }
                            });
                            buttonPanel.add(new JButton("Login"));
                        } else {
                            feedbackLabel.setText("Account creation unsuccessful");
                        }
                    }
                }
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                feedbackLabel.setText("Sorry to see you go. Goodbye!");
                new java.util.Timer().schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        System.exit(0);
                    }

                }, 1000);
            }
        });
        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);

        JPanel feedbackPanel = new JPanel();
        feedbackLabel = new JLabel("");
        feedbackPanel.add(feedbackLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(feedbackPanel, BorderLayout.SOUTH);
    }
    public void disposeWindow() {
        this.setVisible(false);
    }

}
