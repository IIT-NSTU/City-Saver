/*=============================================
 * Project: tank
 * Author: Khair Ahammed
 * Date: 3/5/19
 ==============================================*/

package CitySaver ;

import java.util.Vector;

public class Tank {
    private int x = 0;
    private int y = 0;
    private int direction = 0;
    private int speed = 10;
    private int type = 0;
    private Bullet bullet = null;
    private Vector<Bullet> bv = new Vector<>();
    private boolean isAlive = true;

    public Tank (int x, int y, int type, int speed) {
        setX(x);
        setY(y);
        setType(type);
        setSpeed(speed);
    }

    public boolean touchMaterial() {
        Material m = null;

        switch (getDirection()) {
            case 0:
                for (int i = 0; i < GamePanel.getMat().size(); i++) {
                    m = GamePanel.getMat().get(i);
                    if (getX() - 25 < m.getX() + m.getWidth() && getX() + 25 > m.getX()
                            && getY() - 25 - getSpeed() < m.getY() + m.getHeight()
                            && getY() + 25 > m.getY()) return true;
                }
                break;
            case 1:
                for (int i = 0; i < GamePanel.getMat().size(); i++) {
                    m = GamePanel.getMat().get(i);
                    if (getX() - 25  < m.getX() + m.getWidth() && getX() + 25 > m.getX()
                            && getY() + 25 + getSpeed() > m.getY()
                            && getY() - 25 < m.getY() + m.getHeight()
                            ) return true;
                }
                break;
            case 2:
                for (int i = 0; i < GamePanel.getMat().size(); i++) {
                    m = GamePanel.getMat().get(i);
                    if (getY() - 25 < m.getY() + m.getHeight() && getY() + 25 > m.getY()
                            && getX() - 25 - getSpeed() < m.getX() + m.getWidth()
                            && getX() + 25 > m.getX()) return true;
                }
                break;
            case 3:
                for (int i = 0; i < GamePanel.getMat().size(); i++) {
                    m = GamePanel.getMat().get(i);
                    if (getY() - 25 < m.getY() + m.getHeight() && getY() + 25 > m.getY()
                            && getX() + 25 + getSpeed() > m.getX()
                            && getX() - 25 < m.getX() + m.getWidth()
                            ) return true;
                }
                break;
        }

        return false;
    }

    public void fire() {
        Thread t = null;

        for (int i = bv.size() - 1; i >= 0; i--) {
            if (!bv.get(i).isAlive()) bv.remove(i);
        }

        if (bv.size() < 5) {
            // determine the direction
            switch (getDirection()) {
                case 0:
                    bullet = new Bullet(getX(), getY() - 26, 0, 13);
                    break;
                case 1:
                    bullet = new Bullet(getX(), getY() + 26, 1, 13);
                    break;
                case 2:
                    bullet = new Bullet(getX() - 26, getY(), 2, 13);
                    break;
                case 3:
                    bullet = new Bullet(getX() + 26, getY(), 3, 13);
                    break;
            }

            bv.add(bullet);
        }
        // no more than 5 bullets each time

        t = new Thread(bullet);
        t.start();
    }

    public void moveUp() {
        // Limit the tanks inside the game screen
        if (getY() - 25 - getSpeed() >= 0 && !touchMaterial()) setY(getY() - getSpeed());
    }

    public void moveDown() {
        if (getY() + 75 + getSpeed() <= CitySaver.getGameHeight() && !touchMaterial()) setY(getY() + getSpeed());
    }

    public void moveLeft() {
        if (getX() - 23 - getSpeed() >= 0 && !touchMaterial()) setX(getX() - getSpeed());
    }

    public void moveRight() {
        if (getX() + 23 + getSpeed() <= CitySaver.getGameWidth() && !touchMaterial()) setX(getX() + getSpeed());
    }

    public void moveAhead() {
        switch(getDirection()) {
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveRight();
                break;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Vector<Bullet> getBv() {
        return bv;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public void removeBullet(int n) {
        bv.remove(n);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
