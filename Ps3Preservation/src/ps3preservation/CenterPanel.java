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
import javax.swing.JTable;

/**
 *
 * @author Donat Avdijaj
 */
public class CenterPanel extends JPanel {

    public CenterPanel(String gameName, String img) {
        super();
        setLayout(new GridLayout(1, 2));
        
        JPanel contentPanel = new JPanel();
        JLabel contentLabel = new JLabel(gameName);
        contentPanel.add(contentLabel);
        
        JPanel imgPanel = new JPanel();
        JLabel imgLabel = new JLabel(gameName);
        setImageToLabel(imgLabel, "src/media/" + img);
        imgPanel.add(imgLabel);
        
        add(imgPanel);
        add(contentPanel);
    }
    
    public void setImageToLabel(JLabel label, String PicURL){
        ImageIcon imgThisImg;
        imgThisImg = new ImageIcon(PicURL);
        label.setIcon(imgThisImg);
    }
}
