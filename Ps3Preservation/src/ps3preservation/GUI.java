/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import ps3preservation.data.Users;

/**
 *
 * @author Donat Avdijaj
 */
public class GUI extends JFrame {
    private Users user;
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
        contentPanel.add(new CenterPanel("GAME_NAME", "placeholder.jpg"));
        contentPanel.add(new CenterPanel("GAME_NAME2", "placeholder.jpg"));
        contentPanel.add(new CenterPanel("GAME_NAME3", "placeholder.jpg"));
        contentPanel.add(new CenterPanel("GAME_NAME4", "placeholder.jpg"));
        contentPanel.add(new CenterPanel("GAME_NAME5", "placeholder.jpg"));
        contentPanel.add(new CenterPanel("GAME_NAME6", "placeholder.jpg"));
        
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
    
//    public static void main(String[] args) {
//        new GUI("PS3 Preservation Project");
//    }
    
}
