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
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import ps3preservation.data.Users;

/**
 *
 * @author Donat Avdijaj
 */
public class GUI extends JFrame {

    private Users user;
    private JTextField searchField;
    private ArrayList<CenterPanel> contentArray;

    public GUI(String title, Users user) {
        super(title);
        this.user = user;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(new SidePanel(), BorderLayout.WEST);

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();

        contentPanel.setLayout(new GridLayout(0, 2));

        contentArray = new ArrayList<CenterPanel>();

        for (int i = 0; i < 6; i++) {
            CenterPanel panel = new CenterPanel("GAME_NAME" + (i + 1), "placeholder.jpg");
            contentArray.add(panel);
            contentPanel.add(panel);
        }

        centerPanel.add(new NorthPanel(user), BorderLayout.NORTH);
        centerPanel.add(contentPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
        initializeMainFrame();
    }

    public void setProperFrameSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int height = screenSize.height * 2 / 3;
        int width = screenSize.width * 2 / 3;

        setPreferredSize(new Dimension(width, height));
    }

    public void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mnFile.add(mntmExit);
        JMenuItem mntmAbout = new JMenuItem("About");
        JMenu mnAbout = new JMenu("About");
        mnAbout.add(mntmAbout);
        mntmAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        menuBar.add(mnAbout);
    }

    public void initializeMainFrame() {
        setProperFrameSize();
        initializeMenuBar();
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new GUI("PS3 Preservation Project", new Users(1, "test1", "test2"));
    }

    class NorthPanel extends JPanel {

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
            searchLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    for (CenterPanel item : contentArray) {
                        if (searchField.getText().equals(item.getContentLabel().getText())) {
//                            JOptionPane.showMessageDialog(null,item,"Search result",JOptionPane.PLAIN_MESSAGE);
                            JFrame frame = new JFrame();
                            frame.add(item);
                            frame.pack();
                            frame.setSize(450, 300);
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);
                        }
                    }
                }
            });

            setImageToLabel(searchLabel, "src/media/search.png");
            searchField = new JTextField(15);

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
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

    class CenterPanel extends JPanel {

        JLabel contentLabel;

        public CenterPanel(String gameName, String img) {
            super();
            setLayout(new GridLayout(1, 2));

            JPanel contentPanel = new JPanel();
            contentLabel = new JLabel(gameName);

            contentPanel.setLayout(new GridLayout(2, 1));

            JPanel areaPanel = new JPanel();
            JTextArea contentArea = new JTextArea(20, 20);
            contentArea.setLineWrap(true);
            contentArea.setWrapStyleWord(true);
            contentArea.setEditable(false);
            contentArea.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad llamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

            contentPanel.add(contentLabel);
            areaPanel.add(contentArea);
            contentPanel.add(areaPanel);

            JPanel imgPanel = new JPanel();
            JLabel imgLabel = new JLabel();

            ImageIcon icon = new ImageIcon("src/media/" + img);
            Image scaleImage = icon.getImage().getScaledInstance(158, 188, Image.SCALE_DEFAULT);

            setImageToLabel(imgLabel, scaleImage);
            imgPanel.add(imgLabel);

            add(imgPanel);
            add(contentPanel);
        }

        public JLabel getContentLabel() {
            return contentLabel;
        }

        public void setContentLabel(JLabel contentLabel) {
            this.contentLabel = contentLabel;
        }

        public void setImageToLabel(JLabel label, Image PicURL) {
            ImageIcon imgThisImg;
            imgThisImg = new ImageIcon(PicURL);
            label.setIcon(imgThisImg);
        }
    }

}
