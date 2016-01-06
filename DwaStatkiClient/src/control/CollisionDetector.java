package control;

import org.newdawn.slick.Image;

public class CollisionDetector {

	//zaimplementowac dla Clienta
	private ServerData serverData;
	private Image serverShip;
	private ClientData clientData;
	private Image clientShip;

	public CollisionDetector(ServerData serverData, Image serverShip, ClientData clientData, Image clientShip) {
		this.clientData = clientData;
		this.clientShip = clientShip;
		this.serverData = serverData;
		this.serverShip = serverShip;
	}

	public boolean noRightCollisionDetected() {
		if (clientData.getShipX() + clientShip.getWidth() == serverData.getShipX()) {
			if (clientData.getShipY() <= serverData.getShipY() + clientShip.getHeight()) {
				if (serverData.getShipY() <= clientData.getShipY() + clientShip.getHeight()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean noLeftCollisionDetected() {
		if (serverData.getShipX() + serverShip.getWidth() == clientData.getShipX()) {
			if (serverData.getShipY() + serverShip.getHeight() >= clientData.getShipY()) {
				if (clientData.getShipY() + clientShip.getHeight() >= serverData.getShipY()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean noUpCollisionDetected() {
		if (clientData.getShipX() + clientShip.getWidth() >= serverData.getShipX() && clientData.getShipX() <= serverData.getShipX() + serverShip.getWidth()) {
			if (serverData.getShipY() + serverShip.getHeight() == clientData.getShipY() ) {
				return false;
			}
		}
		return true;
	}
	
	public boolean noDownCollisionDetected() {
		if (clientData.getShipX() + clientShip.getWidth() >= serverData.getShipX() && clientData.getShipX() <= serverData.getShipX() + serverShip.getWidth()) {
			if (serverData.getShipY() == clientData.getShipY() + clientShip.getHeight()) {
				return false;
			}
		}
		return true;
	}

}
