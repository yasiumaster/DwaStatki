package network;

import java.util.ArrayList;
import java.util.List;

import model.RockData;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import control.ClientData;
import control.ServerData;

public class NetworkListener extends Listener {
	
	private ServerData serverData;
	private List<RockData> rockData;
	private List<Integer> toRemoveRocks;
	
	public NetworkListener(ServerData serverData, List<RockData> rockData, List<Integer> toRemoveRocks) {
		this.serverData = serverData;
		this.rockData = rockData;
		this.toRemoveRocks = toRemoveRocks;
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
			//System.out.println("Data recived");
			Packet.Data revicePacket = (Packet.Data) obj;
			//System.out.println("Mouse X: " + revicePacket.getX() + " Mouse Y:" + revicePacket.getY());
			serverData.setShipX(revicePacket.getX());
			serverData.setShipY(revicePacket.getY());
			if(revicePacket.getNewShoot()) {
				serverData.shoot();
			}
		}
		if(obj instanceof Packet.RocksPacket){
			//TODO problem z przekazaniem otrzymanych danych do klasy Play i ich wygenerowania tam
			//System.out.println("RocketPacket recived");
			Packet.RocksPacket revicePacket = (Packet.RocksPacket) obj;
			List<RockData> revicedRockData = revicePacket.getRocksList();
			int lastIndex = revicedRockData.size() - 1;
			rockData.add(revicedRockData.get(lastIndex));
		}
		if(obj instanceof Packet.RockToRemove) {
			System.out.println("To remove");
			Packet.RockToRemove revicePacket = (Packet.RockToRemove) obj;
			toRemoveRocks.add(revicePacket.getRockToRemoveId());
		}
		
	}

}
