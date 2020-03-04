/*=============================================
 * Project: City Saver
 * Author: MAHFUJUR RAHMAN
 * Date: 3/5/19
 
 =============================================*/
package CitySaver;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class About extends JLayeredPane {

	//DeveloperInfo di ;
	public About() {
		//di = new DeveloperInfo();
	}
	
	public void paint(Graphics g ) {
		//super.paint(g) ; 
		BufferedImage screen = null;
		try {
			screen = ImageIO.read(new File("World/picture.jpg"));		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		g.drawImage(screen, 0, 0,CitySaver.getGameWidth()+250,CitySaver.getGameHeight(), null);				
		
		 g.setColor(Color.lightGray);
	     g.setFont(new Font("BLACK", Font.BOLD, 50));
	     g.drawString("City Saver", 404, 84);	
	     g.setColor(Color.BLACK);	
	     g.drawString("City Saver", 400, 80);

	     g.setColor(Color.GRAY);
	     g.setFont(new Font("BLACK", Font.PLAIN, 32));
	     g.drawString("Developer's Information : " , 104 , 204);
	     g.drawString(" * Khair Ahammed", 124, 254);
	     g.drawString("IIT(NSTU)",184, 304 );
	     g.drawString(" * Mahfujur Rahman", 124, 354);
	     g.drawString("IIT(NSTU)",184, 404 ); 
	     g.drawString(" * Tahrim Kabir", 124, 454);
	     g.drawString("IIT(NSTU)",184, 504 );
	     
	}
}

class DeveloperInfo extends JLabel{
	public void paint(Graphics g) {
		//super.paint(g);
    	
	}
}
