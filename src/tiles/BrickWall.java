package tiles;

import java.awt.image.BufferedImage;

import citySaver.Assets;



public class BrickWall extends Tile {

	public BrickWall( int id) {
		super(Assets.brickWall, id);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isSolid() {
		return true ;
	}

}
