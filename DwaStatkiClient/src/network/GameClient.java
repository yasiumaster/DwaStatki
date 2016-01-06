package network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import control.ClientData;
import control.ServerData;

public class GameClient implements Sender{
	
	private Client client;
	
	public GameClient(ServerData serverData) {
		client = new Client();
		client.addListener(new NetworkListener(serverData));
		Log.set(Log.LEVEL_DEBUG);
	}
	
	@Override
	public void send(ClientData clientData) {
		/*int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();
		Packet.Data data = new Packet.Data(mouseX, mouseY);*/
		Packet.Data data = new Packet.Data(clientData.getShipX(), clientData.getShipY(), clientData.getIfNewShootAndReset());
		client.sendTCP(data);
		
	}
	
	public void start(int port) throws Exception {
		registerPackets();
		client.start();
		client.connect(5000, "localhost", port);
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
		kryo.register(java.util.ArrayList.class, new JavaSerializer());
		kryo.register(org.newdawn.slick.Image.class, new JavaSerializer());
	}

}
