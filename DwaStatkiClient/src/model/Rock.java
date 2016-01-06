package model;

import java.nio.ByteBuffer;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Serialization;

public class Rock {

	Image image;
	private float x, y;
	private int width = 0;
	private int height = 0;
	
	public Rock(Image image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.height = image.getHeight();
		this.width = image.getWidth();
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
