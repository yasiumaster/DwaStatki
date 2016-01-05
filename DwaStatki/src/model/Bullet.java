package model;

import org.newdawn.slick.Image;

public class Bullet {
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;
	private String author = "NONE";
	
	Image image;

	public Bullet(Image image, int x, int y, String author) {
		this.x = x;
		this.y = y;
		this.image = image;
		this.height = image.getHeight();
		this.width = image.getWidth();
		this.author = author;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void incrementY(int i) {
		y-=i;
	}
	
	public Image getBullet() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getAuthor() {
		return author;
	}
}