package entity;

import java.awt.Graphics;

public abstract class Entity {
	protected float x , y ; 
	public int width , height ;   //for sizing 
	public Entity (float x , float y, int height , int width  ) {
		this.x = x ; 
		this.y = y ;
		this.height = height ; 
		this.width = width ; 
	
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x; 
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public abstract void update();
	public abstract void render(Graphics g);
}
