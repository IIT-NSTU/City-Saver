package state;

import java.awt.Graphics;

import citySaver.Game;



public abstract class State {
	private static State currentState = null ; 

	protected Game game ;
	public State(Game game) {
		this.game = game ; 
	}
	
	public static void setState(State state) {
		currentState = state ;
	}
	
	public static State getState() {
		return currentState ; 
	}
	
	//CLASS
	public abstract void update();
	public abstract void render(Graphics g);
}
