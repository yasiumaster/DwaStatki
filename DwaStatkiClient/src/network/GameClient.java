package network;

import java.io.IOException;

import org.newdawn.slick.GameContainer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

public class GameClient implements Sender{
	
	private Client client;
	
	public GameClient() {
		client = new Client();
		client.addListener(new NetworkListener());
		Log.set(Log.LEVEL_DEBUG);
	}
	
	@Override
	public void send(GameContainer gc) {
		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();
		Packet.Data data = new Packet.Data(mouseX, mouseY);
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
	}

}
