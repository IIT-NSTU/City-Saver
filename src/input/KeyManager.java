package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	private boolean[] keys ;
	public boolean UP ,DOWN, RIGHT, LEFT ; 
	
	public KeyManager() {
		keys = new boolean[256];
	}
	
	
	public void update() {
		UP = keys[KeyEvent.VK_W ] || keys[KeyEvent.VK_UP];
		DOWN = keys[KeyEvent.VK_S ] || keys[KeyEvent.VK_DOWN];
		LEFT = keys[KeyEvent.VK_A ] || keys[KeyEvent.VK_LEFT];
		RIGHT = keys[KeyEvent.VK_D ] || keys[KeyEvent.VK_RIGHT];
	}
	 
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = true ; 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = false ; 
	}

}
