package control;

import org.newdawn.slick.Image;

public class CollisionDetector {

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

	public boolean noLeftCollisionDetected() {
		if (clientData.getShipX() + clientShip.getWidth() == serverData.getShipX()) {
			if (clientData.getShipY() <= serverData.getShipY() + clientShip.getHeight()) {
				if (serverData.getShipY() <= clientData.getShipY() + clientShip.getHeight()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean noRightCollisionDetected() {
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
		if (serverData.getShipX() + serverShip.getWidth() >= clientData.getShipX() && serverData.getShipX() <= clientData.getShipX() + clientShip.getWidth()) {
			if (clientData.getShipY() + clientShip.getHeight() == serverData.getShipY() ) {
				return false;
			}
		}
		return true;
	}
	
	public boolean noDownCollisionDetected() {
		if (serverData.getShipX() + serverShip.getWidth() >= clientData.getShipX() && serverData.getShipX() <= clientData.getShipX() + clientShip.getWidth()) {
			if (clientData.getShipY() == serverData.getShipY() + serverShip.getHeight()) {
				return false;
			}
		}
		return true;
	}

}
