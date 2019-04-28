package citySaver.entities.creatures;

import java.awt.Graphics;

import citySaver.Game;
import citySaver.Handler;
import citySaver.gfx.Assets;

public class Player extends  Creature{
	//private Game game;

	public Player(Handler handler,float x, float y) {
		super(handler,x, y,Creature.DEFAULT_CREATURE_HEIGHT,Creature.DEFAULT_CREATURE_WIDTH);
		//this.game = game;
	}

	@Override
	public void tick() {
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
	}
	
	public void getInput() {
		xMove = 0;
		yMove = 0;
		
		if(handler.getKeyManager().up)
			yMove = -speed;
		if(handler.getKeyManager().down)
			yMove = speed;
		if(handler.getKeyManager().left)
			xMove = -speed;
		if(handler.getKeyManager().right)
			xMove = speed;
	}

	@Override
	public void render(Graphics g) {
		if(handler.getKeyManager().up) 
			g.drawImage(Assets.playerU,(int) x,(int) y,width,height, null);
			
		
		else if(handler.getKeyManager().down) 
		g.drawImage(Assets.playerD,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()),width,height, null);
		//g.drawImage(Assets.playerL,(int) (x - game.getGameCamera().getxOffset()),(int) (y - game.getGameCamera().getyOffset()),width,height, null);
		//g.drawImage(Assets.playerR,(int)(x - game.getGameCamera().getxOffset()),(int) (y - game.getGameCamera().getyOffset()),width,height, null);
		else if(handler.getKeyManager().left)
		g.drawImage(Assets.playerL,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()),width,height, null);
		else
		g.drawImage(Assets.playerR,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()),width,height, null);
	}

}
