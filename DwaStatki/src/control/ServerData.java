package control;

public class ServerData implements ShipAction {

	private int shipX;
	private int shipY;
	private int hp = 100;
	private int points = 0;
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
	
	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPoints() {
		points++;
	}

	@Override
	public void hurt() {
		hp-=1;
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
	
}
