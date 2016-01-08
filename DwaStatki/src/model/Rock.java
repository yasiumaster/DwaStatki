package model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Rock {

	Image image;
	private float x, y;
	private int width = 0;
	private int height = 0;
	private int id;
	
	public Rock(Image image, float x, float y, int id) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.height = image.getHeight();
		this.width = image.getWidth();
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public Image getRock() {
		return image;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void incrementY(float i) {
		y+=i;
	}
	
	
}
