/*=============================================
 * Project: City Saver
 * Author: Tahrim Kabir
 * Date: 22/4/19
 =============================================*/

package CitySaver;

import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeScreen extends JLayeredPane {

    static boolean run = true;
    JLabel jl = new JLabel();
    JLabel jl2 = new JLabel();
    JLabel jl1 = new JLabel();
    DrawWelcome dw = new DrawWelcome();

    public WelcomeScreen() {

        this.add(dw, 10);
        dw.setBounds(0, 0, CitySaver.getGameWidth() + 250, CitySaver.getGameHeight());
        dw.setVisible(true);
        dw.setOpaque(true);
        
        jl.setBounds(320, 310, 500, 100);
        jl.setForeground(Color.red);
        jl.setFont(new Font("BLACK", Font.ITALIC, 40));					
        jl.setVisible(true);				
        jl.setOpaque(false);					
        jl.setText("Enter any key to start");				
        jl.setBackground(Color.white);
        this.setVisible(true);
        this.setOpaque(true);
        this.add(jl, 0);
        

        java.util.Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                int timer = 0;
                while (run) {
                    if (timer % 3 != 0) {
                        jl.setText("Enter any key to start");		
                        
                    } else {
                        jl.setText("");
                    }
                    repaint();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    timer++;
                }
            }
        }, 1000);
    }

    public void stage() {
        jl1.setFont(new Font("BLACK", Font.BOLD, 90) );
        jl1.setOpaque(true);
        jl1.setVisible(true);
        jl1.setHorizontalAlignment(SwingConstants.CENTER);
        jl1.setBackground(Color.BLACK);
        jl1.setForeground(Color.yellow);
        jl1.setBounds(0, 0, CitySaver.getGameWidth() + 250, CitySaver.getGameHeight() - 100);
        jl1.setText("Stage 1");

        this.setBackground(Color.BLACK);
        this.remove(dw);
        this.remove(jl);
        this.add(jl1, 20);
        this.repaint();			
        this.revalidate();

        java.util.Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                int counter = 0;

                while (true) {
                    if (counter % 2 == 0) {
                        jl1.setText("");
                        counter++;
                    } else {
                        jl1.setHorizontalTextPosition(JLabel.CENTER);
                        jl1.setText("Stage 1");
                        counter++;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000);
    }


}

class DrawWelcome extends JLabel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, CitySaver.getGameWidth() + 250, CitySaver.getGameHeight());
        
    	BufferedImage screen = null;
		try {
			screen = ImageIO.read(new File("World/picture.jpg"));		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		g.drawImage(screen, 0, 0,CitySaver.getGameWidth()+250,CitySaver.getGameHeight(), null);				
		
         //"City Saver" and shadow
        g.setColor(Color.lightGray);
        g.setFont(new Font("BLACK", Font.BOLD, 90));
        g.drawString("City Saver", 304, 284);

        g.setColor(Color.BLACK);
        g.drawString("City Saver", 300 , 280);
        
        
    }

       
    
}
