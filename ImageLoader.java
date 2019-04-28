package citySaver.gfx;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;



public class ImageLoader {
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
	

}
