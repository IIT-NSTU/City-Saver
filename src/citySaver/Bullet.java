/*=============================================
 * Project: City-Saver
 * Author: Khair Ahammed
 * Date: 2/5/19
 
==============================================*/

package CitySaver;

public class Bullet implements Runnable {
    private int x;
    private int y;
    private int direction;
    private int speed = 8;
    private boolean isAlive = false;

    public Bullet (int x, int y, int d, int s) {
        setX(x);
        setY(y);
        setDirection(d);
        setSpeed(s);
        isAlive = true;
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public void run() {
        while (isAlive()) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (getDirection()) {
                case 0:
                    setY(getY() - getSpeed());
                    break;
                case 1:
                    setY(getY() + getSpeed());
                    break;
                case 2:
                    setX(getX() - getSpeed());
                    break;
                case 3:
                    setX(getX() + getSpeed());
                    break;
            }

            if (x < -1 || x > CitySaver.getGameWidth() || y < -1 || y > CitySaver.getGameHeight()) {
                isAlive = false;
                break;
            }
        }
    }
}
