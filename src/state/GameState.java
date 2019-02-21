package state;

import java.awt.Graphics;

import citySaver.Game;

import entity.cretures.Player;
import tiles.Tile;


public class GameState extends State{

	Player player ;
	public GameState(Game game) {
		super(game);
		player = new Player(game ,100,100);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		player.update();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		player.render(g);
		Tile.tiles[0].render(g, 0, 0);
		 
	}

}
