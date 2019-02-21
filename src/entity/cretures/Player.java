package entity.cretures;

import java.awt.Graphics;

import citySaver.Assets;
import citySaver.Game;



public class Player extends Creatures {
	private Game game ;
	
	public Player(Game game ,float x, float y) {
		super(x, y,Creatures.DEFAULT_CREATURE_WIDTH,Creatures.DEFAULT_CREATURE_HEIGHT);
		this .game = game ;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		getInput();
		move();
	}
	public void getInput() {
		xMove = 0 ; 
		yMove = 0 ;
		if(game.getKeymanager().UP)
			yMove = -speed;
		if(game.getKeymanager().DOWN)
			yMove = speed;
		if(game.getKeymanager().LEFT)
			xMove = -speed;	
		if(game.getKeymanager().RIGHT)
			xMove = speed;	
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
		if(game.getKeymanager().UP)
			g.drawImage(Assets.playerU, (int) x ,(int) y,width , height, null);
		if(game.getKeymanager().DOWN)
			g.drawImage(Assets.playerD, (int) x ,(int) y,width , height, null);
		if(game.getKeymanager().LEFT)
			g.drawImage(Assets.playerL, (int) x ,(int) y,width , height, null);
		if(game.getKeymanager().RIGHT)
			g.drawImage(Assets.playerR, (int) x ,(int) y,width , height, null);
		
	}

}
