/*==============================================
 * Project: City-Saver
 * Author: Khair Ahammed
 * Date: 23/4/19
 ==============================================*/

package CitySaver;

import javax.swing.*;
import java.awt.*;

public class Stage extends JPanel implements Runnable{

    int timer = 0;

    public void paint (Graphics g) {
        super.paint(g);


        g.setColor(Color.darkGray);
        g.fillRect(0, 0, CitySaver.getGameWidth() + 250, CitySaver.getGameHeight());



        // flash the string every other second
        if (timer % 3 != 0) {
            g.setColor(Color.yellow);
            g.setFont(new Font("BLACK", Font.BOLD, 70));	
            g.drawString("Stage: 1", 350, 310);	
        }
    }

    @Override
    public void run() {
        while (true) {

            // a timer to make the string flash
            try {
                Thread.sleep(500);
                // prevent overflow of the timer
                if (timer == 10000) timer = 0;
                timer++;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.repaint();
        }
    }
}
