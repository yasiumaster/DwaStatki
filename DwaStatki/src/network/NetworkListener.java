package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class NetworkListener extends Listener {
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
		System.out.println("Reviced something...");
		if(obj instanceof Packet.DefaultPacket) {
			Packet.DefaultPacket revicePacket = (Packet.DefaultPacket) obj;
			System.out.println(revicePacket.toString());
			
			Packet.DefaultPacket responsePacket = new Packet.DefaultPacket("Odpowiadam");
			connection.sendTCP(responsePacket);
		}
		if(obj instanceof Packet.Data) {
			System.out.println("CLIENT DATA");
			Packet.Data revicePacket = (Packet.Data) obj;
			System.out.println("Mouse X: " + revicePacket.getX() + " Mouse Y:" + revicePacket.getY());
		}
	}

}
