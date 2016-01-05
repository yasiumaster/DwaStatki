package model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Rock {

	Image image;
	private int x, y;
	
	public Rock() throws SlickException {
		image = new Image("res/bullet.png");
		x = 50;
		y = 50;
	}
	
	public Image getRock() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
}
