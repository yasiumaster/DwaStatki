package model;

public class RockData {

	private float x;
	private float y;
	private int rotation;
	private int id;
	
	public RockData() {
		// TODO Auto-generated constructor stub
	}
	
	public RockData(float x, float y, int rotation, int id) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public int getRotation() {
		return rotation;
	}
	
	
}
