package control;

public class ServerData implements ShipAction{

	private int shipX = 0;
	private int shipY = 0;
	private int hp = 100;
	private int points = 0;
	private boolean newShoot = false;
	private static ServerData serverData = null;
	
	private ServerData() {
	}
	
	public static ServerData createServerData(int x, int y) {
		if(serverData == null) {
			serverData = new ServerData();
			serverData.shipX = x;
			serverData.shipY = y;
		}
		
		return serverData;
	}
	
	public boolean getIfNewShootAndReset() {
		if(newShoot) {
			System.out.println("New shoot from server!");
			newShoot = false;
			return true;
		}
		return false;
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

	@Override
	public void shoot() {
		newShoot = true;
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPoints() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hurt() {
		// TODO Auto-generated method stub
		
	}
	
	
}
