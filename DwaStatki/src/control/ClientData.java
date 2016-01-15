package control;

public class ClientData implements ShipAction{

	private int shipX;
	private int shipY;
	private int hp = 100;
	private int points = 0;
	private boolean newShoot = false;
	private boolean isGamePaused = false;
	private static ClientData clientData = null;
	
	private ClientData() {
	}
	
	public static ClientData createClientData(int x, int y) {
		if(clientData == null) {
			clientData = new ClientData();
			clientData.shipX = x;
			clientData.shipY = y;
		}
		
		return clientData;
	}
	
	public boolean getIfNewShootAndReset() {
		if(newShoot) {
			System.out.println("New shoot from client!");
			newShoot = false;
			return true;
		}
		return false;
	}
	
	@Override
	public void shoot() {
		newShoot = true;
		
	}

	@Override
	public void reset() {
		hp = 100;
		points = 0;
	}

	@Override
	public void addPoints() {
		points++;
	}

	@Override
	public void hurt() {
		if(hp>0)
			hp-=10;
	}
	
	public void incrementShipY(int i) {
		shipY +=i;
	}
	
	public void incrementShipX(int i) {
		shipX +=i;
	}

	public int getShipX() {
		return shipX;
	}

	public int getShipY() {
		return shipY;
	}

	public void setShipX(int shipX) {
		this.shipX = shipX;
	}

	public void setShipY(int shipY) {
		this.shipY = shipY;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getHP() {
		return hp;
	}

	public void setGamePaused(boolean gamePaused) {
		isGamePaused = gamePaused;
	}

	public boolean isGamePaused() {
		return isGamePaused;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
