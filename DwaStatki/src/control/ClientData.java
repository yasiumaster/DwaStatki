package control;

public class ClientData {

	private int shipX = 0;
	private int shipY = 0;
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
