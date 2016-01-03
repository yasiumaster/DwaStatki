package control;

public class ClientData implements ShipAction{

	private int shipX = 0;
	private int shipY = 0;
	private int hp = 100;
	private int points = 0;
	private boolean newShoot = false;
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
