/*=============================================
 * Project: City-Saver
 * Author: Khair Ahammed
 * Date: 28/3/19
===============================================*/

package CitySaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class CitySaver extends JFrame implements KeyListener{

    private GamePanel mp = null;
    private About ab ;
    private static int gameWidth = 850;
    private static int gameHeight = 800;
    private static boolean isInGame = false;
    private static String currentState = "WelcomeScreen" ;		
    private Thread mt = null;		
    private WelcomeScreen ws = null;	
    private Audio audio = null;	
    private GameRecorder gr ;

    public CitySaver() {

        // Welcome scene
        GameRecorder.loadRecord();						
        ws = new WelcomeScreen();					
        this.add(ws);

        audio = new Audio("Sound/welcome.wav");
        audio.play();

        // Menu options
        JMenuBar jmb = new JMenuBar();
        JMenu jmG = new JMenu("Game (G)");
        
       
       // JMenuItem jmiReturn = new JMenuItem("Return (R)");
        JMenuItem about = new JMenuItem("About (A)");	
        JMenuItem jmiExit = new JMenuItem("Exit (E)");	
        


        // Hot keys

        jmiExit.setMnemonic('E');
        jmG.setMnemonic('G');
        //jmiReturn.setMnemonic('R');
        about.setMnemonic('A');
        jmb.add(jmG);
        jmG.add(jmiExit);
       // jmG.add(jmiReturn);
        jmG.add(about);


        this.addKeyListener(this);

//        jmiReturn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                gr = new GameRecorder();
//                setIsInGame(true);
//                gr.reset() ;	
//                mp = new GamePanel();
//                mt = new Thread(mp);
//                mt.start();
//                add(mp);
//                repaint();
//                revalidate();
//                addKeyListener(mp);
//            }
//        });

        // Add actions
        // Exit
        jmiExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameRecorder.saveRecord();		
                System.exit(0);			
            }
        });
        
        //About
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	about();
            }
        });






        this.setTitle("City Saver");				
        this.setResizable(false);				
        this.setJMenuBar(jmb);
        this.setVisible(true);
        this.setBounds(100, 100, gameWidth + 250, gameHeight);	 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
    }
    
    // About
    private void about() {
    	if(getCurrentState() == "GamePanel") { // from Gamepanel 
	    	setCurrentState("About");
	    	setIsInGame(false);								
	    	ab = new About();								
	        this.remove(mp);								
	        mp = null;											
	        this.addKeyListener(this);						
	        this.add(ab);									
	        this.setVisible(true);							
	        this.repaint();									
	        this.revalidate();}								
    	else if(getCurrentState() == "About"){  //from About 
//    		setCurrentState("About");
//	    	setIsInGame(false);	
//	    	ab = new About();
//	        this.remove(this);
//	        //mp = null;
//	        this.addKeyListener(this);
//	        this.add(ab);
//	        this.setVisible(true);
//	        this.repaint();
//	        this.revalidate();
    		
    	}else {
    		setCurrentState("About");    // from Welcome Screen 
	    	setIsInGame(false);		
	    	ab = new About();	
	        this.remove(ws);	
	        ws = null;		
	        this.addKeyListener(this);	
	        this.add(ab);	
	        this.setVisible(true);
	        this.repaint();
	        this.revalidate();				
    		
    	}
    }

      // Back to welcome screen			
//    private void back() {
//    	if(getCurrentState() == "GamePanel") {  // from gamepanel 
//    		setCurrentState("WelcomeScreen");
//	        setIsInGame(false);		
//	        audio = new Audio("Sound/welcome.wav");
//	        audio.play();
//	        ws = new WelcomeScreen();
//	        this.remove(mp);
//	        mp = null;
//	        this.addKeyListener(this);
//	        this.add(ws);
//	        this.setVisible(true);
//	        this.repaint();
//	        this.revalidate();
//    	}else if(getCurrentState()== "About") {		 //from About 	
//
//    		setCurrentState("WelcomeScreen");
//    		setIsInGame(false);		
//	        audio = new Audio("Sound/welcome.wav");
//	        audio.play();
//	        ws = new WelcomeScreen();
//	        this.remove(ab);
//	        ab = null;
//	        this.addKeyListener(this);
//	        this.add(ws);
//	        this.setVisible(true);
//	        this.repaint();
//	        this.revalidate();
//    		
//    	}else {									//from WelcomeScreen 
////    		setIsInGame(false);		
////    		setCurrentState("WelcomeScreen");
////	        audio = new Audio("Sound/welcome.wav");
////	        audio.play();
////	        ws = new WelcomeScreen();
////	        this.remove(this);
////	        ws = null;
////	        this.addKeyListener(this);
////	        this.add(ws);
////	        this.setVisible(true);
////	        this.repaint();
////	        this.revalidate();
//    	}
//    	
//    }

    // Start the game
    public void gameStart() {
        setIsInGame(true);	
        setCurrentState("GamePanel");	
        WelcomeScreen.run = false; 	

        this.removeKeyListener(this);
        
        // Stage scene
        ws.stage() ;
        this.revalidate();
        this.repaint();	
        
        // Game start
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() { 
                remove(ws);
                ws = null;
            	//mp.setGameOver(false);
                mp = new GamePanel();
                mt = new Thread(mp);
                mt.start();
                add(mp);
                repaint();
                revalidate();
                addKeyListener(mp);
            }
        }, 3000);
    }

    public static int getGameWidth() {
        return gameWidth;
    }

    public static int getGameHeight() {
        return gameHeight;
    }

    public static boolean isIsInGame() {
        return isInGame;
    }

    public static void setIsInGame(boolean isInGame) {
        isInGame = isInGame;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (ws != null) {
            if (e != null) {
                gameStart();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	
    }

    public static void main(String[] args) {
        CitySaver cs = new CitySaver();
    }

	public static String getCurrentState() {
		return currentState;
	}

	public static void setCurrentState(String currentState) {
		CitySaver.currentState = currentState;
	}
}


