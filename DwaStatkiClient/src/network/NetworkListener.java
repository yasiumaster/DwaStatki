package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import control.ClientData;
import control.ServerData;

public class NetworkListener extends Listener {
	
	private ServerData serverData;
	
	public NetworkListener(ServerData serverData) {
		this.serverData = serverData;
	}
	
	@Override
	public void connected(Connection arg0) {
		// TODO Auto-generated method stub
		super.connected(arg0);
	}
	
	@Override
	public void disconnected(Connection arg0) {
		// TODO Auto-generated method stub
		super.disconnected(arg0);
	}
	
	@Override
	public void received(Connection connection, Object obj) {
		if(obj instanceof Packet.Data) {
			//System.out.println("SERVER DATA: ");
			Packet.Data revicePacket = (Packet.Data) obj;
			//System.out.println("Mouse X: " + revicePacket.getX() + " Mouse Y:" + revicePacket.getY());
			serverData.setShipX(revicePacket.getX());
			serverData.setShipY(revicePacket.getY());
			if(revicePacket.getNewShoot()) {
				serverData.shoot();
			}
		}
	}

}
