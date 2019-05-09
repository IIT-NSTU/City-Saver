/*=============================================
 * Project: Khair Ahammed
 * Author: Khair Ahammed
 * Date: 3/5/19
===============================================*/

package CitySaver;

import java.util.Random; 
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    private static Vector<EnemyTank> ets = new Vector<>();

    public EnemyTank(int x, int y, int c, int s) {
        super(x, y, c, s);
    }

    public static Vector<EnemyTank> getEts() {
        return ets;
    }

    public void setEts(Vector<EnemyTank> ets) {
        this.ets = ets;
    }

    // determine if the enemy tank is touching other enemy tanks
    // setting all the numbers to 25 to prevent sudden turn error
    public boolean isTouched() {

        switch (getDirection()) {
            case 0:
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et = ets.get(i);
                    if (et!= this) {
                        if (getX() + 25 > et.getX() - 25 && getX() - 25 < et.getX() + 25
                                && getY() - 25 - getSpeed() < et.getY() + 25
                                && getY() + 25 > et.getY() - 25) return true;
                    }
                }
                break;
            case 1:
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et = ets.get(i);
                    if (et!= this) {
                        if (getX() + 25 > et.getX() - 25 && getX() - 25 < et.getX() + 25
                                && getY() + 25 + getSpeed() > et.getY() - 25
                                && getY() - 25 < et.getY() + 25) return true;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et = ets.get(i);
                    if (et!= this) {
                        if (getX() - 25 - getSpeed() < et.getX() + 25
                                && getX() + 25 > et.getX() - 25
                                && getY() - 25 < et.getY() + 25
                                && getY() + 25 > et.getY() - 25) return true;
                    }
                }
                break;
            case 3:
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et = ets.get(i);
                    if (et!= this) {
                        if (getX() - 25 < et.getX() + 25
                                && getX() + 25 + getSpeed() > et.getX() - 25
                                && getY() - 25 < et.getY() + 25
                                && getY() + 25 > et.getY() - 25) return true;
                    }
                }
                break;
        }

        return false;
    }


    @Override
    public void run() {

        while (isAlive()) {
            Random rand = new Random();

            setDirection(rand.nextInt(4));

            for (int i = 0; i < rand.nextInt(1500) + 10; i++) {

                switch (getDirection()) {
                    case 0:
                        if (!isTouched()) {
                            if (getY() - getSpeed() - 25 <= 0) {
                                setDirection(1);
                                moveAhead();
                            }
                            moveAhead();
                        }
                        break;
                    case 1:
                        if (!isTouched()) {
                            if (getY() + getSpeed() + 25 > CitySaver.getGameHeight()) {
                                setDirection(0);
                                moveAhead();
                            }
                            moveAhead();

                        }
                        break;
                    case 2:
                        if (!isTouched()) {
                            if (getX() - getSpeed() - 23 < 0) {
                                setDirection(3);
                                moveAhead();
                            }
                            moveAhead();

                        }
                        break;
                    case 3:
                        if (!isTouched()) {
                            if (getX() - getSpeed() + 23 > CitySaver.getGameWidth()) {
                                setDirection(2);
                                moveAhead();
                            }
                            moveAhead();
                        }
                        break;
                }
                try {
                    Thread.sleep(rand.nextInt(50));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!isAlive()) break;
        }
    }
}
