package network;

import java.util.List;

import model.RockData;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import control.ClientData;
import control.ServerData;

public class GameClient implements Sender{
	
	private Client client;
	
	public GameClient(ServerData serverData, List<RockData> rockData, List<Integer> toRemoveRocks) {
		client = new Client();
		client.addListener(new NetworkListener(serverData, rockData, toRemoveRocks));
		Log.set(Log.LEVEL_DEBUG);
	}
	
	@Override
	public void send(ClientData clientData) {
		/*int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();
		Packet.Data data = new Packet.Data(mouseX, mouseY);*/
		Packet.Data data = new Packet.Data(clientData.getShipX(), clientData.getShipY(), clientData.getPoints(), clientData.getIfNewShootAndReset());
		client.sendTCP(data);
		
	}
	
	public void start(String ip, int port) throws Exception {
		registerPackets();
		client.start();
		client.connect(5000, ip, port);
		Thread.sleep(1000);
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				while(client.isConnected()) {
					
				}
				
				//Packet.DefaultPacket requestPacket = new Packet.DefaultPacket("Pytam");
				//client.sendTCP(requestPacket);
				
			}
		}).start();

	}
	
	private void registerPackets() {
		Kryo kryo = client.getKryo();
		kryo.register(Packet.DefaultPacket.class);
		kryo.register(Packet.Data.class);
		kryo.register(Packet.RocksPacket.class);
		kryo.register(java.util.ArrayList.class);
		kryo.register(model.RockData.class);
		kryo.register(Packet.RockToRemove.class);
		kryo.register(Packet.GameData.class);
	}

}
