package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class NetworkListener extends Listener {
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
		if(obj instanceof Packet.DefaultPacket) {
			Packet.DefaultPacket revicePacket = (Packet.DefaultPacket) obj;
			System.out.println(revicePacket.toString());
		}
		if(obj instanceof Packet.Data) {
			System.out.println("SERVER DATA: ");
			Packet.Data revicePacket = (Packet.Data) obj;
			System.out.println("Mouse X: " + revicePacket.getX() + " Mouse Y:" + revicePacket.getY());
		}
	}

}
