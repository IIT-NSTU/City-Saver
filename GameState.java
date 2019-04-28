package citySaver.states;

import java.awt.Graphics;

import citySaver.Game;
import citySaver.Handler;
import citySaver.entities.creatures.Player;
import citySaver.gfx.Assets;
import citySaver.worlds.World;

public class GameState extends State {
	private Player player;
	private World world;
	public GameState(Handler handler) {
		super(handler);
		
		world = new World(handler,"res/worlds/world01");
		handler.setWorld(world);
		player = new Player(handler,100,100);
		
		
	
	//game.getGameCamera().move(0,0);
 
	}

	@Override
	public void tick() {
		world.tick();
		player.tick();
		//game.getGameCamera().move(1, 1);
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		player.render(g);
	}

}
