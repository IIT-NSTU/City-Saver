/**********************************************
 * Project: City-Saver
 * Author: Mahfujur Rahman
 * Date: 21/3/19
 ***********************************************/

package CitySaver;

import java.awt.*;

public class Brick extends Material {

    public Brick(int x, int y) {
        super(x, y);
        setColor(new Color(128, 85, 0));	
        setBulletThrough(false);	
        setHeight(30);
        setWidth(30);
    }
}
