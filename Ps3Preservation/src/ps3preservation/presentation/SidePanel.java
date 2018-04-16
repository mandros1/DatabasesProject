/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.presentation;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Donat Avdijaj
 */
public class SidePanel extends JPanel{
    
    public SidePanel(){
        super();
        setBackground(Color.GRAY);
        setLayout(new GridLayout(0,1));
        JLabel menuLabel = new JLabel();
        setImageToLabel(menuLabel, "src/media/menu.png");
        
        JLabel addLabel = new JLabel();
        setImageToLabel(addLabel, "src/media/add.png");
        
        JLabel downloadLabel = new JLabel();
        setImageToLabel(downloadLabel, "src/media/download.png");
        
        add(menuLabel);
        add(addLabel);
        add(downloadLabel);
    }
    
    public void setImageToLabel(JLabel label, String PicURL){
        ImageIcon imgThisImg;
        imgThisImg = new ImageIcon(PicURL);
        label.setIcon(imgThisImg);
    }
    
}
