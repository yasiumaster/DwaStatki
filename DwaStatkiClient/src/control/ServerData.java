package control;

public class ServerData {

	private int shipX;
	private int shipY;
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
