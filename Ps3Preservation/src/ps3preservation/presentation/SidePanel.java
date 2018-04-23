/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps3preservation.presentation;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Donat Avdijaj, Marin Andros
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
        
        JButton jb = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/media/download.png"));
            jb.setIcon(new ImageIcon(img));
            jb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URL("https://mega.nz/#!olFkHYTZ!yIvOJv_LOHD3T3C8VtR94TWtvLUQqiu2Y35XOzsBouI").toURI());
                    } catch (Exception ex) {
                        Logger.getLogger(SidePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
          } catch (Exception ex) {
            System.out.println(ex);
          }
        
        add(menuLabel);
        add(addLabel);
        add(jb);
    }
    
    public void setImageToLabel(JLabel label, String PicURL){
        ImageIcon imgThisImg;
        imgThisImg = new ImageIcon(PicURL);
        label.setIcon(imgThisImg);
    }
    
}
