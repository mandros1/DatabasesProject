/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import ps3preservation.data.Users;

/**
 *
 * @author Donat Avdijaj
 */
public class NorthPanel extends JPanel {

    private Users user;

    public NorthPanel(Users user) {
        super();
        this.user = user;

        setLayout(new GridLayout(1, 3));
        add(createSearchPanel());
        add(createTitlePanel());
        add(createUserPanel());
    }

    public void setImageToLabel(JLabel label, String PicURL) {
        ImageIcon imgThisImg;
        imgThisImg = new ImageIcon(PicURL);
        label.setIcon(imgThisImg);
    }

    public JPanel createSearchPanel() {
        JLabel searchLabel = new JLabel("Search: ");
        setImageToLabel(searchLabel, "src/media/search.png");
        JTextField searchField = new JTextField(15);

        JPanel searchPanel = new JPanel();
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        searchPanel.setBackground(Color.LIGHT_GRAY);

        return searchPanel;
    }

    public JPanel createTitlePanel() {
        JLabel titleLabel = new JLabel("PS3 Preservation Project");

        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);

        titlePanel.setBackground(Color.LIGHT_GRAY);

        return titlePanel;
    }

    public JPanel createUserPanel() {
        JLabel userLabel = new JLabel(user.getUsername());
        userLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                 new UserFrame();
                
            }
        });
        setImageToLabel(userLabel, "src/media/user.png");

        JPanel userPanel = new JPanel();
        userPanel.add(userLabel);

        userPanel.setBackground(Color.LIGHT_GRAY);

        return userPanel;
    }

    class UserFrame extends JFrame {

        public UserFrame() {
            initializeFrame();
        }

        public void initializeFrame() {
            setTitle(user.getUsername());
            addComponents();
            setVisible(true);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setMinimumSize(new Dimension(200, 250));
            pack();
        }

        public void addComponents() {
            JPanel inputPanel = new JPanel(new GridLayout(0, 2));
            inputPanel.add(new JLabel("First name"));
            inputPanel.add(new JLabel(user.getFirstName()));
            inputPanel.add(new JLabel("Last name"));
            inputPanel.add(new JLabel(user.getLastName()));
            inputPanel.add(new JLabel("Email"));
            inputPanel.add(new JLabel(user.getEmail()));
            inputPanel.add(new JLabel("Username"));
            inputPanel.add(new JLabel(user.getUsername()));
            add(inputPanel, BorderLayout.CENTER);
        }
    }
}
