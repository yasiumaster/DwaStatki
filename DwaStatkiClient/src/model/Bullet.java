package model;

import org.newdawn.slick.Image;

public class Bullet {
	private int x = 0;
	private int y = 0;
	
	Image image;

	public Bullet(Image image, int x, int y) {
		this.x = x;
		this.y = y;
		this.image = image;
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
}