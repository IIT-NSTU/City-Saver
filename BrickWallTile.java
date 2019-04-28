package citySaver.tiles;

import java.awt.image.BufferedImage;

import citySaver.gfx.Assets;

public class BrickWallTile extends Tile {

	public BrickWallTile( int id) {
		super(Assets.brickWall, id);
		
	}
	
	@Override
	public boolean isSolid() {
		return true ;
	}

}
