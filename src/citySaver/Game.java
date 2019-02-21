package citySaver;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import state.State;
import input.KeyManager;
import state.GameState;
import state.MenuState;

public class Game implements Runnable{

	private Window display; 
	public int height , width ;
	public String title ;
	private Thread thread ; 
	boolean Running = false ; 
	
	private BufferStrategy bs ;
	private Graphics g ; 
	
	private BufferedImage testimage ; 
	
	
	//States
	private State gameState ; 
	private State menuState ;
	
	//Input
	private KeyManager keymanager;
	
	public Game(String title , int width , int height) {
		this.height = height; 
		this.width = width ; 
		this.title = title ; 
		keymanager  = new KeyManager();
	}
	
	private void init () {
		display = new Window(title,width ,height) ;

		display .getFrame().addKeyListener(keymanager);
		Assets.init();
		gameState = new GameState(this);
		menuState = new MenuState(this);
		State.setState(gameState);
	}
//	int x = 0 ;
	
	private void update() {
//		x +=1 ;
		keymanager.update();
		
		if(State.getState() !=null) {
			State.getState().update();
		}
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return; 
		} 
		g=bs.getDrawGraphics();
		//clear Screen 
		g.clearRect(0, 0, width, height);	
		
		//Draw here 
					//		g.setColor(Color.black);	
			 		//		g.fillRect(0, 0, 50, height);	
		
//		g.drawImage(sheet.crop(0, 0, 32, 32), 20, 20, null);	
//		g.drawImage(Assets.player, x, 0, null);		
		
		if(State.getState() !=null) {
			State.getState().render(g);
		}
		
		//End Drawing 
		bs.show(); 
		g.dispose();
	}
	
	@Override
	public void run() { 
		init();
		
		int fps = 60 ; 
		double timePerTick = 1_000_000_000/fps;
		double delta = 0 ;
		long  now  ;
		long lastTime = System.nanoTime();

		long timer = 0 ;
		int ticks = 0 ;
		
		while(Running) {	
			now  = System.nanoTime();	
			delta +=(now - lastTime) /timePerTick;	
			
			timer += now -lastTime ; 
			lastTime = now ;	
			if(delta >= 1) {
				update();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1_000_000_000) {
				System.out.println("Ticks and Frames :"+ticks);
				ticks = 0;
				timer = 0 ;
			}
			 
		}
		stop(); 
	}
	
	public synchronized void start() {
		if(Running) {
			return ; 
		}
		Running = true ;
		thread = new Thread (this);
		thread.start(); 
	}
	
	public synchronized void stop() {
		if(!Running) {
			return ;
		}
		Running = false ;
		
		try {
			thread.join();
		} catch (InterruptedException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public KeyManager getKeymanager() {
		return keymanager ;
	}
	
}
