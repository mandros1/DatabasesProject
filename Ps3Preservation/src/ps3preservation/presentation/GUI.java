/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import static java.util.Objects.hash;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import ps3presentation.business.Files;
import ps3presentation.business.Licenses;
import ps3presentation.business.PackageFileXref;
import ps3presentation.business.Packages;
import ps3presentation.business.ReleasePackageXref;
import ps3presentation.business.Releases;
import ps3presentation.business.Software;
import ps3presentation.business.Users;
import ps3preservation.data.Ps3SQLDatabase;

/**
 *
 * @author Donat Avdijaj
 */
public class GUI extends JFrame {

    private Users user;
    private JTextField searchField;
    private ArrayList<CenterPanel> contentArray;
    private Ps3SQLDatabase database;
    private JPanel contentPanel;
    private JPanel centerPanel;
    private JScrollPane scrollableContent;

    public GUI(String title, Users user, Ps3SQLDatabase database) {
        super(title);
        this.user = user;
        this.database = database;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(new SidePanel(), BorderLayout.WEST);

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(new BorderLayout());

        contentPanel = new JPanel();

        contentPanel.setLayout(new GridLayout(0, 1));

        contentArray = new ArrayList<CenterPanel>();

        NorthPanel northPanel = new NorthPanel(user);

        scrollableContent = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        northPanel.findGames("");

        centerPanel.add(northPanel, BorderLayout.NORTH);
        centerPanel.add(scrollableContent, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        initializeMainFrame();
    }

    public void initializeObjects(String text) {
//        Software s = new Software(1, text);
        Releases release = new Releases("1", 0, "");
        ReleasePackageXref releasePackage = new ReleasePackageXref(0, 0, 0);
//        Packages packageSoftware = new Packages(0, "", "", "", "", "", "", 0.0, 0.0, 0, "".getBytes(), 0, new Byte(""));
        PackageFileXref packageFile = new PackageFileXref(0, 0, 0);
        Licenses licence = new Licenses(0, "", "".getBytes(), 0);
        Files file = new Files(0, "", "".getBytes(), 0);
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        Ps3SQLDatabase db = new Ps3SQLDatabase("jdbc", "mysql", "hypercubed.co", "3306", "ps3_preservation?useSSL=false", "ps3_preservation", "M2ZUdOq765uSHhbr");
        db.connect();
        new GUI("PS3 Preservation Project", new Users(1, "test1", "test2"), db);
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
                    findGames(searchField.getText());
//                    for (CenterPanel item : contentArray) {
//                        if (searchField.getText().equalsIgnoreCase(item.getContentLabel().getText())) {
//                            JFrame frame = new JFrame();
//                            frame.add(item);
//                            frame.pack();
//                            frame.setSize(450, 300);
//                            frame.setLocationRelativeTo(null);
//                            frame.setVisible(true);
//                        }
//                    }
                }
            });

            setImageToLabel(searchLabel, "src/media/search.png");
            searchField = new JTextField(15);

            searchField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        findGames(searchField.getText());
                    }
                }
            }
            );

            JPanel searchPanel = new JPanel();
            searchPanel.add(searchLabel);
            searchPanel.add(searchField);

            searchPanel.setBackground(Color.LIGHT_GRAY);

            return searchPanel;
        }

        public void findGames(String gameName) {
            Software s = new Software(database);
            ArrayList<ArrayList<String>> gamesFound = s.getAllGames(gameName);
            ArrayList<Software> software = new ArrayList<>();
            for (ArrayList list : gamesFound) {
                software.add(new Software(Integer.parseInt("" + list.get(0)), "" + list.get(1)));
            }
            displayGames(software);
        }

        public void displayGames(ArrayList<Software> software) {
//            JOptionPane.showMessageDialog(null, String.format("\nFound %d games", software.size()));
            int tempCount = 0;
            for (Software object : software) {
                System.out.println(object.getName());
                CenterPanel panel = new CenterPanel(object.getName(), String.valueOf(object.getId()) + "TTT" + tempCount, "placeholder.jpg");
                contentArray.add(panel);
                if (++tempCount > 10) {
                    break;
                }
            }
            System.out.println("");

            contentPanel.removeAll();
//            contentPanel.setLayout(new GridLayout(0, 1));

            contentPanel.revalidate();
            contentPanel.repaint();

            for (int i = 0; i < contentArray.size(); i++) {
                contentPanel.add(contentArray.get(i));
                contentArray.get(i).addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        String gameID;
//                        gameID = contentArray.get(i).getGameID();
                    }
                });
            }
            contentPanel.revalidate();
            contentPanel.repaint();
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
        final String gameID;

        public CenterPanel(String gameName, String gameID, String img) {
            super();

            this.gameID = gameID;

            setLayout(new GridLayout(1, 2));

            JPanel contentPanel = new JPanel();
            contentLabel = new JLabel(gameID + ": " + gameName);
            contentPanel.setLayout(new GridLayout(2, 1));
            JPanel areaPanel = new JPanel();
//            JTextArea contentArea = new JTextArea(20, 20);
//            contentArea.setLineWrap(true);
//            contentArea.setWrapStyleWord(true);
//            contentArea.setEditable(false);
//            contentArea.setText("TEST");

            contentPanel.add(contentLabel);
//            areaPanel.add(contentArea);
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

        public String getGameID() {
            return gameID;
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
