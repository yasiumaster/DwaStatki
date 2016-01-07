package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import control.ClientData;

public class NetworkListener extends Listener {
	
	private ClientData clientData;
	
	public NetworkListener(ClientData clientData) {
		this.clientData = clientData;
	}
	
	@Override
	public void connected(Connection arg0) {
		System.out.println("Someone connected!");
	}
	
	@Override
	public void disconnected(Connection arg0) {
		// TODO Auto-generated method stub
		System.out.println("Someone disconnected!");
	}
	
	@Override
	public void received(Connection connection, Object obj) {
		if(obj instanceof Packet.Data) {
			//System.out.println("CLIENT DATA");
			Packet.Data revicePacket = (Packet.Data) obj;
			//System.out.println("Mouse X: " + revicePacket.getX() + " Mouse Y:" + revicePacket.getY());
			clientData.setShipX(revicePacket.getX());
			clientData.setShipY(revicePacket.getY());
			if(revicePacket.getNewShoot()) {
				clientData.shoot();
			}
		}
		if(obj instanceof Packet.RocksPacket){
			
		}
	}

}
