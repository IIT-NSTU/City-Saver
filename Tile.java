package citySaver.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	
	//static stuff  here
	public static  Tile[] tiles = new Tile[256];
	public static  Tile grassTile  =  new GrassTile(0);
	public static  Tile waterTile  =  new WaterTile(4);
	public static  Tile brickWallTile  =  new BrickWallTile(2);
	public static  Tile steelWallTile  =  new SteelWallTile(3);
	public static  Tile blankTile  =  new BlankTile(1);
	public static  Tile baseTile  =  new BaseTile(5);
	
	//class
	public static final int TILEWIDTH = 9, TILEHEIGHT = 16;
	protected BufferedImage texture;
	protected final int id;
	public Tile(BufferedImage texture,int id) {
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g,int x, int y) {
		g.drawImage(texture,x,y,TILEWIDTH,TILEHEIGHT,null);
	}
	
	public boolean isSolid() {
		return false;
	}
	public int getId() {
		return id;
	}

}
