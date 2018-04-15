/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 *
 * @author Donat Avdijaj
 */
public class NorthPanel extends JPanel{
    private String username;
    public NorthPanel(String username){
        super();
        this.username = username;
        
        setLayout(new GridLayout(1,3));
        add(createSearchPanel());
        add(createTitlePanel());
        add(createUserPanel());
    }
    
    public void setImageToLabel(JLabel label, String PicURL){
        ImageIcon imgThisImg;
        imgThisImg = new ImageIcon(PicURL);
        label.setIcon(imgThisImg);
    }
    
    public JPanel createSearchPanel(){
        JLabel searchLabel = new JLabel("Search: ");
        setImageToLabel(searchLabel, "src/media/search.png");
        JTextField searchField = new JTextField(15);
        
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        
        searchPanel.setBackground(Color.LIGHT_GRAY);
        
        return searchPanel;
    }
    
    public JPanel createTitlePanel(){
        JLabel titleLabel = new JLabel("PS3 Preservation Project");
        
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);
        
        titlePanel.setBackground(Color.LIGHT_GRAY);
        
        return titlePanel;
    }
    
    public JPanel createUserPanel(){
        JLabel userLabel = new JLabel(username);
        setImageToLabel(userLabel, "src/media/user.png");
        
        JPanel userPanel = new JPanel();
        userPanel.add(userLabel);
        
        userPanel.setBackground(Color.LIGHT_GRAY);
        
        return userPanel;
    }
    
}
