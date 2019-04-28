package citySaver.tiles;

import java.awt.image.BufferedImage;

import citySaver.gfx.Assets;

public class SteelWallTile extends Tile {

	public SteelWallTile( int id) {
		super(Assets.steelWall, id);
		
	}
	
	@Override
	public boolean isSolid() {
		return true ;
	}

}
