/*=============================================
 * Project: City-Saver
 * Author: Khair Ahammed 
 * Date: 05/05/19
=============================================*/

package CitySaver;

import javax.swing.*;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class GamePanel extends JLayeredPane implements KeyListener, Runnable {

    private static Hero hero = null;
    private Vector<EnemyTank> ets = new Vector<>();
    private Vector<Explosion> explosions = new Vector<>();
    private static Vector<Material> mat = new Vector<>();
    private Set<Integer> pressed = new HashSet<>();
    private Vector<Node> nodes = new Vector<>();
    private static boolean gameOver = false;
    private static boolean MissionCompleted = false ;
    private String startSound = "Sound/start.wav";
    private String fireSound = "Sound/fire.wav";
    private String explosionSound = "Sound/Explosion.wav";
    private Audio audio;
    private static boolean loadGame = false;	
    private Material eagle;		

 
    public GamePanel() {

        // Play BGM
        audio = new Audio(startSound);				
        audio.play();
      
            hero = new Hero(425, 550, 0, 5);

            GameRecorder.reset();
             
            setGameOver(false);
            setMissionCompleted(false);
            // Initialize NPC
            initializeNPC();
            
            
            // Draw map
            initializeMap1();
            initializeMap2();


        this.setBounds(0, 0, CitySaver.getGameWidth(), CitySaver.getGameHeight());
    }

    @Override 
    public void paint(Graphics g) {  
        super.paint(g);  

        // game canvas
        g.setColor(Color.BLACK);  
        g.fillRect(0, 0, CitySaver.getGameWidth(), CitySaver.getGameHeight());  
        
        g.setColor(new Color(77, 77, 77));
        g.fillRect(CitySaver.getGameWidth(), 0, CitySaver.getGameWidth()+250, CitySaver.getGameHeight());  
        
        // show game status  
        showInfo(g);  

 //        Draw material(map)
        for (Material m: mat) {
            this.drawMaterial(g, m);
        }
        
       


        // draw hero
        if (hero != null) {
            if (hero.isAlive()) {
            	drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), hero.getType());
           }
            else {
                hero = null;
            }
        } else if (GameRecorder.getMyLife() > 0) {
            hero = new Hero(50, 700, 0, 5)	;
            GameRecorder.setMyLife(GameRecorder.getMyLife() - 1)	;
        } else {
            ets.removeAllElements();
            CitySaver.setIsInGame(false);
            setGameOver(true);			
        }
        
//        if(GameRecorder.getMyLife() < 0 || Eagle.isBulletThrough()) {
//        	 ets.removeAllElements();
//             CitySaver.setIsInGame(false);
//             setGameOver(true);
//        }

        
        //mision completed
        if(GameRecorder.getNumDeadNPC() == 6) {	
        	ets.removeAllElements();
        	setMissionCompleted(true);
        	CitySaver.setIsInGame(false);
        }

        // draw enemies
        for (EnemyTank el: ets) {
            this.drawTank(el.getX(), el.getY(), g, el.getDirection(), el.getType());
        }
 
        // explosion
        explode(g);

        // Game Over
        if (isGameOver()) {
            g.setFont(new Font("BLACK", Font.BOLD, 90));
            g.setColor(Color.white);
            g.drawString("Game Over", 183, 303);
            g.setColor(Color.red);
            g.drawString("Game Over", 180, 300);	

            for (EnemyTank el: getEts()) {			
                el = null;			
            }			

            CitySaver.setIsInGame(false);	
        }
        
        if(isMissionCompleted()) {
        	g.setFont(new Font ("BLACK" , Font.BOLD , 90));
        	g.setColor(Color.WHITE);		
        	g.drawString("Mission",253,303);		
        	g.drawString("Completed",163,403);		
        	g.setColor(new Color(170, 0,0));		
        	g.drawString("Mission",250,300);		
        		
        	g.drawString("Completed",160,400);			
        	for(EnemyTank el : getEts()) {   		
        		el = null ;
        	}
        }
    }

    // =========== Draw map for Stage 1 ==============
    private void initializeMap1() {
        Material brick;
       for (int i = 0; i < 6; i++) {

            //vertical brick	
            //S
            brick = new Brick(200, i * 30 + 200);
            mat.add(brick);
            brick = new Brick(350, i * 30 + 410);
            mat.add(brick);
            
            
            //E
            brick = new Brick(450, i * 30 + 200);
            mat.add(brick);
            brick = new Brick(450, i * 30 + 410);
            mat.add(brick);
            

            //horizontal Brick
            //S
            brick = new Brick(i*30+200, 200);	
            mat.add(brick);
            brick = new Brick(i*30+200, 380);	
            mat.add(brick);
            brick = new Brick(i*30+200, 560);	
            mat.add(brick);
            //E
            brick = new Brick(i*30+450, 200);	
            mat.add(brick);
            brick = new Brick(i*30+450, 380);	
            mat.add(brick);
            brick = new Brick(i*30+450, 560);	
            mat.add(brick);
        }
        

        //============ Base Wall ============
        for (int i = 0; i < 4; i++) {
            brick = new Brick(i * 30 +365,  655);
            mat.add(brick);
        }

        for (int i = 0; i < 2; i++) {
            brick = new Brick(365,  685 + i * 30);  
            mat.add(brick);

            brick = new Brick(455,  685 + i * 30);	
            mat.add(brick);
        }
    }

    private void initializeMap2() {


        eagle = new Eagle(402, 750);
        mat.add(eagle);

    }

    public void drawMaterial(Graphics g, Material m) {
        g.setColor(m.getColor());
        String type = m.getClass().getSimpleName();
        switch (type) {
            case "Water" :
                g.fillRect(m.getX(), m.getY(), m.getWidth(), m.getHeight());
                break;
            case "Brick" :
                g.fill3DRect(m.getX(), m.getY(), m.getWidth(), m.getHeight(), true);
                break;
            case "Eagle" :	
                this.drawEagle(CitySaver.getGameWidth() / 2, CitySaver.getGameHeight() - 80, g);		
                break;		
        }
    }	

    public void initializeNPC() {	
        EnemyTank et = null;	
        // =========== new enemies and their threads ============
        for (int i = 0; i < 6; i++) {		
            if (i < 3) {
                et = new EnemyTank((i + 1) *85, 70, 1, 2);		
            } else {
                et = new EnemyTank(400 + i * 85, 70, 1, 2);	    
            }
            ets.add(et);
            et.setEts(ets);
            et.fire();
            Thread thread = new Thread(et); 	
            thread.start();

        }
    }

    private void showInfo(Graphics g) {
        // =========status Bar ==========
        this.drawTank(900, 120, g, 0, 1);
        this.drawTank(900, 190, g, 0, 0);
        this.drawTank(900, 350, g, 0, 1);
        this.drawTank(900, 540, g, 0, 1);
       // this.setBackground(Color.BLACK); 

        g.setColor(Color.BLACK);
        g.setFont(new Font("BLACK", Font.PLAIN, 30));
        g.drawString(" X " + GameRecorder.getTotalNPC() + "", 940, 130);
        g.drawString(" X " + GameRecorder.getMyLife() + "", 940, 200);
        g.drawString(" X " + GameRecorder.getNumDeadNPC() + "", 940, 360);
        g.drawString(" X " + GameRecorder.getRecord() + "", 940, 550);
        

        g.setColor(new Color(170, 0,0));
        g.setFont(new Font("BLACK", Font.BOLD, 40));
        g.drawString("Score:", 860, 290);
        g.drawString("Status:", 860, 60);
        g.drawString("Highest", 860, 450);
        g.drawString("Score:", 860, 490);
    }

    // ============= Determine if the bullet hits a tank ==============
    public void hitTank(Bullet b, Tank t) {
        switch(t.getDirection()) {
            case 0:
            case 1:
                if (b.getX() > t.getX() -23 && b.getX() < t.getX() + 23
                        && b.getY() > t.getY() - 25 && b.getY() < t.getY() + 25) {

                    b.setAlive(false);
                    makeExplosion(t);
                }
                break;
            case 2:
            case 3:
                if (b.getX() > t.getX() - 25 && b.getX() < t.getX() + 25
                        && b.getY() > t.getY() - 23 && b.getY() < t.getY() + 23) {
                    b.setAlive(false);		
                    makeExplosion(t);		
                }
                break;
        }
    }

    // ===========Hit materials ===============
    public void hitMaterial(Bullet b, Material m) {
        if (!m.isBulletThrough() && b.getX() + 1 >= m.getX() && b.getX() - 1 <= m.getX() + m.getWidth()  
                && b.getY() + 1 >= m.getY()   
                && b.getY() - 1 <= m.getY() + m.getHeight()) {  
            b.setAlive(false); 
            m.setAlive(false); 
        }
    }


    // ============Explosion happens after one tank dies =========
    private void makeExplosion(Tank t) {
        t.setAlive(false);

        Explosion exp = new Explosion(t.getX() - 26, t.getY() - 28);
        explosions.add(exp);
    }

    //===========explosion effect ===========
    private void explode(Graphics g) {
        for (int i = explosions.size() - 1; i >= 0; i--) {
            Explosion el = explosions.get(i);
            if (el.getTimer() > 6) {
                g.setColor(new Color(255, 128, 0));
                g.fillOval(el.getX(), el.getY(), 55, 55);
                try {
                    Thread.sleep(90);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (el.getTimer() > 3) {
                g.setColor(new Color(255, 218, 28));
                g.fillOval(el.getX() + 8, el.getY() + 8, 40, 40);
                try {
                    Thread.sleep(90);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                g.setColor(new Color(255, 254, 208));
                g.fillOval(el.getX() + 15, el.getY() + 13, 20, 20);
                try {
                    Thread.sleep(90);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            audio = new Audio(explosionSound);
            audio.play();		
            el.countDown();		
            if (el.getTimer() == 0) {		
                explosions.remove(el);		
            }
        }
    }
    //==========draw Eagle==========
    public void drawEagle(int x, int y, Graphics g) {
    	
    	g.setColor(new Color(204, 51, 0));		
		int arr1[] = {x-10 , x , x+10 , x};
		int arr2[] = {y-15 , y-5 , y-15 , y+7   } ;
		
		g.fillPolygon(arr1,arr2,4);			
		//Right
		g.drawLine(x+10,y-7,x+25,y-20);
		g.drawLine(x+10,y-6,x+25,y-20);

		g.drawLine(x+7,y,x+20,y-10);

		g.drawLine(x+7,y+1,x+20,y-10);
 
		
		//left
		g.drawLine(x-10,y-7,x-23,y-20);
		g.drawLine(x-10,y-6,x-23,y-20);
		g.drawLine(x-7,y,x-20,y-10);
		g.drawLine(x-7,y+1,x-20,y-10);
		
		//lower
		g.drawLine(x,y+10,x-15,y);
		g.drawLine(x,y+11,x-15,y);
		g.drawLine(x,y+12,x-15,y);
		g.drawLine(x,y+13,x-15,y);
		g.drawLine(x,y+14,x-15,y);

		g.drawLine(x,y+10,x+15,y);
		g.drawLine(x,y+11,x+15,y);
		g.drawLine(x,y+12,x+15,y);
		g.drawLine(x,y+13,x+15,y);
		g.drawLine(x,y+14,x+15,y);
		
		

    }

    // ========== generic way to draw tanks ============
    private void drawTank(int x, int y, Graphics g, int direction, int type) {
        // 0 as hero and 1 as enemy
        switch (type) {
            case 0:
                g.setColor(Color.GREEN);
                break;
            case 1:
                g.setColor(new Color(128, 128, 255));
                break;
            case 2:
                g.setColor(Color.lightGray);
                break;
        }

        //================== draw Hero in 4 directions ===============
        if(type==0) {
	        switch (direction) {
	            case 0:
	                g.fill3DRect(x - 23, y - 25, 10, 50, false);
	                g.fill3DRect(x + 14, y - 25, 10, 50,false);
	                g.fill3DRect(x - 13, y - 15, 27, 30, false);
	
	                
	                g.fillRect(x -1, y - 25, 3, 15 );
	                for (int i = 1; i < 5; i ++) g.drawLine(x - 23, y -25 + 10 * i, x - 14, y -25 + 10 * i);
	                for (int i = 1; i < 5; i ++) g.drawLine(x + 14, y -25 + 10 * i, x + 23, y -25 + 10 * i);
	                g.setColor(Color.RED);
	                g.fillOval(x - 9, y - 10, 18, 18);
	                break;
	            case 1:
	                g.fill3DRect(x - 23, y - 25, 10, 50, false);	
	                g.fill3DRect(x + 14, y - 25, 10, 50,false);
	                g.fill3DRect(x - 13, y - 15, 27, 30, false);	
	                
	                g.fillRect(x -1, y + 10, 3, 15 );
	                for (int i = 1; i < 5; i ++) g.drawLine(x - 23, y -25 + 10 * i, x - 14, y -25 + 10 * i);
	                for (int i = 1; i < 5; i ++) g.drawLine(x + 14, y -25 + 10 * i, x + 23, y -25 + 10 * i);
	                g.setColor(Color.RED);
	                g.fillOval(x - 9, y - 9, 18, 18);
	                break;
	            case 2:
	                g.fill3DRect(x - 25, y - 23, 50, 10, false);    
	                g.fill3DRect(x -25 , y + 14, 50, 10,false);
	                g.fill3DRect(x - 15, y - 13, 30, 27, false);   
	              
	                g.fillRect(x - 25, y - 1, 15, 3 );
	                for (int i = 1; i < 5; i ++) g.drawLine(x -25 + 10 * i, y -23, x -25 + 10 * i, y - 14);
	                for (int i = 1; i < 5; i ++) g.drawLine(x -25 + 10 * i, y + 14, x -25 + 10 * i, y + 23);
	                g.setColor(Color.RED);
	                g.fillOval(x - 10, y - 9, 18, 18);
	                break;
	            case 3:
	                g.fill3DRect(x - 25, y - 23, 50, 10, false);
	                g.fill3DRect(x - 25, y + 14, 50, 10,false);
	                g.fill3DRect(x - 15, y - 13, 30, 27, false);
	               
	                g.fillRect(x + 10, y - 1, 15, 3 );
	                for (int i = 1; i < 5; i ++) g.drawLine(x -25 + 10 * i, y -23, x -25 + 10 * i, y - 14);
	                for (int i = 1; i < 5; i ++) g.drawLine(x -25 + 10 * i, y + 14, x -25 + 10 * i, y + 23);
	                g.setColor(Color.RED);
	                g.fillOval(x - 9, y - 9, 18, 18);
	                break;
	        }
        }else { //Draw Enemy Tanks in 4 Directions
        	switch (direction) {
            case 0:
                g.fill3DRect(x - 23, y - 25, 10, 50, false);
                g.fill3DRect(x + 14, y - 25, 10, 50,false);
                g.fill3DRect(x - 13, y - 15, 27, 30, false);

                
                g.fillRect(x -1, y - 25, 3, 15 );
                for (int i = 1; i < 5; i ++) g.drawLine(x - 23, y -25 + 10 * i, x - 14, y -25 + 10 * i);
                for (int i = 1; i < 5; i ++) g.drawLine(x + 14, y -25 + 10 * i, x + 23, y -25 + 10 * i);
               
                g.fillOval(x - 9, y - 10, 18, 18);
                break;
            case 1:
                g.fill3DRect(x - 23, y - 25, 10, 50, false);	
                g.fill3DRect(x + 14, y - 25, 10, 50,false);
                g.fill3DRect(x - 13, y - 15, 27, 30, false);	
                g.fillOval(x - 9, y - 9, 18, 18);
                g.fillRect(x -1, y + 10, 3, 15 );
                for (int i = 1; i < 5; i ++) g.drawLine(x - 23, y -25 + 10 * i, x - 14, y -25 + 10 * i);
                for (int i = 1; i < 5; i ++) g.drawLine(x + 14, y -25 + 10 * i, x + 23, y -25 + 10 * i);

                break;
            case 2:
                g.fill3DRect(x - 25, y - 23, 50, 10, false);    
                g.fill3DRect(x -25 , y + 14, 50, 10,false);
                g.fill3DRect(x - 15, y - 13, 30, 27, false);   
                g.fillOval(x - 10, y - 9, 18, 18);
                g.fillRect(x - 25, y - 1, 15, 3 );
                for (int i = 1; i < 5; i ++) g.drawLine(x -25 + 10 * i, y -23, x -25 + 10 * i, y - 14);
                for (int i = 1; i < 5; i ++) g.drawLine(x -25 + 10 * i, y + 14, x -25 + 10 * i, y + 23);

                break;
            case 3:
                g.fill3DRect(x - 25, y - 23, 50, 10, false);
                g.fill3DRect(x - 25, y + 14, 50, 10,false);
                g.fill3DRect(x - 15, y - 13, 30, 27, false);
                g.fillOval(x - 9, y - 9, 18, 18);
                g.fillRect(x + 10, y - 1, 15, 3 );
                for (int i = 1; i < 5; i ++) g.drawLine(x -25 + 10 * i, y -23, x -25 + 10 * i, y - 14);
                for (int i = 1; i < 5; i ++) g.drawLine(x -25 + 10 * i, y + 14, x -25 + 10 * i, y + 23);

                break;
        }
        
        }
        

        //============== hero fires ==================
        if (hero != null && hero.getBullet() != null && hero.getBullet().isAlive()) {
            g.setColor(Color.WHITE);

            for (Bullet el: hero.getBv()) {
                g.fill3DRect(el.getX() - 1, el.getY() - 1, 3, 3, true);	
            }
        }	
        
        

        //=============== enemy fires =================
        for (EnemyTank el: ets) {
            if (el.getBullet() != null && el.getBullet().isAlive()) {
                g.setColor(Color.WHITE);

                for (EnemyTank el1: ets) {
                    for (Bullet el2: el.getBv()) {
                        g.fill3DRect(el2.getX() - 1, el2.getY() - 1, 3, 3, true);
                    }
                }
            }
        }
    }

  

    public static Hero getHero() {
        return hero;
    }

    public static void setHero(Hero h) {
        hero = h;
    }

    public Vector<EnemyTank> getEts() {
        return ets;
    }

    public void setEts(Vector<EnemyTank> ets) {
        this.ets = ets;
    }

    public Vector<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Vector<Node> nodes) {
        this.nodes = nodes;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean go) {
        gameOver = go;
    }

    public static boolean isLoadGame() {
        return loadGame;
    }

    public static void setLoadGame(boolean loadGame) {
        GamePanel.loadGame = loadGame;
    }

    public static Vector<Material> getMat() {
        return mat;  
    }

    @Override 
    public void keyTyped(KeyEvent e) {

    }

    //================= hero control
    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());

        if (hero != null) {
            //=============== control directions ===============
            if (pressed.contains(KeyEvent.VK_DOWN)) {
                hero.setDirection(1);
                hero.moveDown();
            }
            if (pressed.contains(KeyEvent.VK_LEFT)) {
                    hero.setDirection(2);
                    hero.moveLeft();
            }
            if (pressed.contains(KeyEvent.VK_RIGHT)) {
                    hero.setDirection(3);
                    hero.moveRight();
            }
            if (pressed.contains(KeyEvent.VK_UP)) {
                    hero.setDirection(0);
                    hero.moveUp();
            }

            //============ control shot =========
            if (pressed.contains(KeyEvent.VK_SPACE)) {
                hero.fire();
                audio = new Audio(fireSound);
                audio.play();
            }

        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }

    @Override
    public void run() {

    	setGameOver(false);
    	setMissionCompleted(false);
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //=============== judge if there's any tanks hit ==================
            for (EnemyTank el: ets) {
                if (hero != null) {
                    for (Bullet b : hero.getBv()) hitTank(b, el);
                    for (Bullet el1 : el.getBv()) hitTank(el1, hero);
                }
            }

            //================ judge if there's any materials get hit =================
            for (Material m: mat) { 
                if (hero != null) {
                    for (Bullet b : hero.getBv()) hitMaterial(b, m);	
                    for (EnemyTank e: ets) {
                        for (Bullet b: e.getBv()) hitMaterial(b, m);	
                    }
                }
            }

            // =============remove dead enemy tanks=================
            for (int i = ets.size() - 1; i >= 0; i--) {
                if (!ets.get(i).isAlive()) {
                    ets.remove(i);
                    // \====Total NPC number decreases as one NPC dies====
                    GameRecorder.setTotalNPC(GameRecorder.getTotalNPC() - 1);
                    GameRecorder.setNumDeadNPC(GameRecorder.getNumDeadNPC() + 1);
                }
            }

            //==============remove dead material=================
            for (int i = mat.size() - 1; i >= 0; i--) {
                if (!mat.get(i).isAlive()) {
                    mat.remove(i);
                }
            }

            // ==============remove hero dead bullets==============
            if (hero != null) {
                if (hero.isTouched()) {
                    makeExplosion(hero);
                }
                for (int i = hero.getBv().size() - 1; i >= 0; i--) {
                    if (!hero.getBv().get(i).isAlive()) hero.removeBullet(i);
                }
            }

            //==============remove enemy dead bullets and fire===============
            for (int j = ets.size() - 1; j >= 0; j--) {
                for (int i = ets.get(j).getBv().size() - 1; i >= 0; i--) {
                    if (!ets.get(j).getBv().get(i).isAlive()) {
                        ets.get(j).removeBullet(i);
                        ets.get(j).fire();
                    }
                }
            }

            //==============See if the commander is still alive==============
            if (!eagle.isAlive()) {
                ets.removeAllElements();
                CitySaver.setIsInGame(false);
                setGameOver(true);
            }

            repaint();
        }
    }

	public static boolean isMissionCompleted() {
		return MissionCompleted;
	}

	public static void setMissionCompleted(boolean missionCompleted) {
		MissionCompleted = missionCompleted;
	}
    


}
